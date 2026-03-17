<#
Interactive rename + structure cleanup script.

Run it from the directory you want as "root scope" (only subfolders/files will be touched).
Recommended: run from backend/src/main (as you did).

Features:
- Asks:
  1) What should be replaced? (Old -> New)
  2) Should file contents be changed too?
  3) App type (Java / JS / CPP / Node) -> chooses default file extensions for in-file replacements
  4) Any exclusions? (folder/file patterns)
  4.5) If yes: if you want "ONLY these file extensions" -> user input list

- Renames directory/file NAMES containing Old -> New
- Replaces inside files (optional) for selected extensions
- Java-specific cleanup (optional if Java selected):
  - flatten redundant "controller/.../controller/..." etc
  - delete empty folders iteratively

Dry-run by default; ask user to apply at the end.
#>

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function Ask($prompt, $default = $null) {
  if ($null -ne $default -and $default -ne "") {
    $v = Read-Host "$prompt [$default]"
    if ([string]::IsNullOrWhiteSpace($v)) { return $default }
    return $v
  }
  return (Read-Host $prompt)
}

function AskYesNo($prompt, $defaultYes = $true) {
  $suffix = if ($defaultYes) { "[Y/n]" } else { "[y/N]" }
  while ($true) {
    $v = Read-Host "$prompt $suffix"
    if ([string]::IsNullOrWhiteSpace($v)) { return $defaultYes }
    switch -Regex ($v.Trim()) {
      '^(y|yes|j|ja)$' { return $true }
      '^(n|no|nein)$' { return $false }
    }
  }
}

function SplitCsv($s) {
  if ([string]::IsNullOrWhiteSpace($s)) { return @() }
  return @($s.Split(",") | ForEach-Object { $_.Trim() } | Where-Object { $_ -ne "" })
}

function ShouldExcludePath($fullPath, [string[]]$excludePatterns) {
  foreach ($pat in $excludePatterns) {
    if ([string]::IsNullOrWhiteSpace($pat)) { continue }
    if ($fullPath -like $pat) { return $true }
  }
  return $false
}

function DoOrPrint($apply, $msg, [scriptblock]$action) {
  if ($apply) { & $action }
  else { Write-Host "[DRY-RUN] $msg" -ForegroundColor Yellow }
}

function EnsureDir($apply, $dir) {
  if (-not (Test-Path -LiteralPath $dir)) {
    DoOrPrint $apply "Create dir $dir" { New-Item -ItemType Directory -Path $dir | Out-Null }
  }
}

function MoveChildren($apply, $from, $to) {
  EnsureDir $apply $to
  foreach ($c in @(Get-ChildItem -LiteralPath $from -Force)) {
    $dest = Join-Path $to $c.Name
    DoOrPrint $apply "Move $($c.FullName) -> $dest" {
      Move-Item -LiteralPath $c.FullName -Destination $dest -Force
    }
  }
}

function RemoveEmptyDirs($apply, $root, [string[]]$excludePatterns) {
  $any = $false
  $dirs = @(Get-ChildItem -LiteralPath $root -Recurse -Directory -Force |
            Sort-Object { $_.FullName.Length } -Descending)

  foreach ($d in $dirs) {
    if (ShouldExcludePath $d.FullName $excludePatterns) { continue }
    $items = @(Get-ChildItem -LiteralPath $d.FullName -Force -EA SilentlyContinue)
    if ($items.Count -eq 0) {
      $any = $true
      DoOrPrint $apply "Remove empty dir $($d.FullName)" { Remove-Item -LiteralPath $d.FullName -Force }
    }
  }
  return $any
}

function RenameNames($apply, $root, $old, $new, [string[]]$excludePatterns) {
  # bottom-up
  $items = @(Get-ChildItem -LiteralPath $root -Recurse -Force |
             Where-Object { -not (ShouldExcludePath $_.FullName $excludePatterns) } |
             Sort-Object { $_.FullName.Length } -Descending)

  foreach ($it in $items) {
    if ($it.Name -like "*$old*") {
      $newName = $it.Name.Replace($old, $new)
      DoOrPrint $apply "Rename $($it.FullName) -> $newName" {
        Rename-Item -LiteralPath $it.FullName -NewName $newName -Force
      }
    }
  }
}

function ReplaceInFiles($apply, $root, $old, $new, [string[]]$extensions, [string[]]$excludePatterns) {
  $extSet = @{}
  foreach ($e in $extensions) { $extSet[$e.ToLowerInvariant()] = $true }

  $files = @(Get-ChildItem -LiteralPath $root -Recurse -File -Force |
             Where-Object { -not (ShouldExcludePath $_.FullName $excludePatterns) })

  foreach ($f in $files) {
    $ext = [IO.Path]::GetExtension($f.Name).ToLowerInvariant()
    if (-not $extSet.ContainsKey($ext)) { continue }

    $path = $f.FullName
    $orig = Get-Content -LiteralPath $path -Raw -EA Stop
    $upd  = $orig.Replace($old, $new)

    if ($upd -ne $orig) {
      DoOrPrint $apply "Replace in file $path" {
        Set-Content -LiteralPath $path -Value $upd -Encoding UTF8
      }
    }
  }
}

function FlattenLayerRedundancy($apply, $base, $layer, [string[]]$excludePatterns) {
  # Fix: <base>\<layer>\<X>\<layer>\<...> -> <base>\<layer>\<X>\<...>
  $changed = $false
  $layerRoot = Join-Path $base $layer
  if (-not (Test-Path -LiteralPath $layerRoot)) { return $false }

  foreach ($x in @(Get-ChildItem -LiteralPath $layerRoot -Directory -Force)) {
    if (ShouldExcludePath $x.FullName $excludePatterns) { continue }

    $redundant = Join-Path $x.FullName $layer
    if (-not (Test-Path -LiteralPath $redundant)) { continue }

    $changed = $true

    foreach ($sub in @(Get-ChildItem -LiteralPath $redundant -Directory -Force)) {
      $dst = Join-Path $x.FullName $sub.Name
      DoOrPrint $apply "Flatten $($sub.FullName) -> $dst" {
        MoveChildren $apply $sub.FullName $dst
      }
    }

    $files = @(Get-ChildItem -LiteralPath $redundant -File -Force -EA SilentlyContinue)
    if ($files.Count -gt 0) {
      DoOrPrint $apply "Flatten files $redundant -> $($x.FullName)" {
        MoveChildren $apply $redundant $x.FullName
      }
    }
  }

  return $changed
}

# ----------------- interactive prompts -----------------
$root = (Get-Location).Path
Write-Host "Root scope (only below): $root"

$old = Ask "1) What string should be replaced (OLD)" ""
$new = Ask "   What string should it become (NEW)" ""

if ([string]::IsNullOrWhiteSpace($old) -or [string]::IsNullOrWhiteSpace($new)) {
  throw "OLD and NEW must not be empty."
}

$doInFile = AskYesNo "2) Change inside file contents too?" $true

$app = Ask "3) App type? (Java/JS/CPP/Node)" "Java"
$app = $app.Trim().ToLowerInvariant()

$excludeInput = Ask "4) Exclusions (comma-separated wildcard patterns, e.g. *\target\*,*\.\git\*). Empty = none" ""
$excludePatterns = SplitCsv $excludeInput

$onlyThese = $false
$onlyExts = @()

if (AskYesNo "4.5) Do you want to limit in-file replacement to ONLY specific file extensions?" $false) {
  $onlyThese = $true
  $extInput = Ask "     Enter extensions (comma-separated), e.g. .java,.xml,.properties" ".java"
  $onlyExts = SplitCsv $extInput
}

# defaults by app type
$defaultExts = switch ($app) {
  "java" { @(".java") }
  "js"   { @(".js",".jsx",".ts",".tsx") }
  "cpp"  { @(".c",".cc",".cpp",".h",".hpp") }
  "node" { @(".js",".jsx",".ts",".tsx",".json",".yml",".yaml",".env") }
  default { @(".txt") }
}

$exts = if ($onlyThese) { $onlyExts } else { $defaultExts }

$apply = AskYesNo "Apply changes now? (No = dry-run)" $false

Write-Host "`n=== PLAN ===" -ForegroundColor Cyan
Write-Host "Root: $root"
Write-Host "Rename names: '$old' -> '$new'"
Write-Host "In-file replace: $doInFile (extensions: $($exts -join ', '))"
Write-Host "Exclusions: $($excludePatterns -join ', ')"
Write-Host "Apply: $apply"

# ----------------- operations -----------------
Write-Host "`n=== 1) Rename file/folder names ===" -ForegroundColor Cyan
RenameNames -apply $apply -root $root -old $old -new $new -excludePatterns $excludePatterns

if ($doInFile) {
  Write-Host "`n=== 2) In-file replacements ===" -ForegroundColor Cyan
  ReplaceInFiles -apply $apply -root $root -old $old -new $new -extensions $exts -excludePatterns $excludePatterns
}

# Java cleanup (optional based on app selection)
if ($app -eq "java") {
  $javaBase = Join-Path $root "java\ch\Elodin\RealmQuill"
  if (Test-Path -LiteralPath $javaBase) {
    Write-Host "`n=== 3) Java structure cleanup ===" -ForegroundColor Cyan
    $pass = 0
    do {
      $pass++
      Write-Host "`n--- Cleanup pass #$pass ---" -ForegroundColor Gray
      $c1 = FlattenLayerRedundancy -apply $apply -base $javaBase -layer "controller" -excludePatterns $excludePatterns
      $c2 = FlattenLayerRedundancy -apply $apply -base $javaBase -layer "repository" -excludePatterns $excludePatterns
      $c3 = FlattenLayerRedundancy -apply $apply -base $javaBase -layer "service" -excludePatterns $excludePatterns
      $c4 = RemoveEmptyDirs -apply $apply -root $javaBase -excludePatterns $excludePatterns
    } while ($pass -lt 20 -and ($c1 -or $c2 -or $c3 -or $c4))
  } else {
    Write-Host "Java base package dir not found (skipping cleanup): $javaBase" -ForegroundColor DarkYellow
  }
}

Write-Host "`n=== DONE ===" -ForegroundColor Green
if (-not $apply) { Write-Host "Re-run and choose Apply=Yes when the plan looks correct." -ForegroundColor Yellow }