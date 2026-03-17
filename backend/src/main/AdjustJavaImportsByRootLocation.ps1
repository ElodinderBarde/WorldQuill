<#
PS 5.1 compatible. Run from backend/src/main.

What it does:
- For every *.java under ./java:
  1) Determine the expected package of each class by its folder path (relative to ./java).
  2) Build a map: SimpleClassName -> FullyQualifiedName (FQN).
  3) For each file:
     - Remove wrong imports that reference mapped classes.
     - Add missing imports for mapped classes that are referenced in code.
     - Keep java.* / jakarta.* / org.* etc untouched unless they match a mapped class.
- Writes UTF-8 WITHOUT BOM.

Notes / limitations (intentional to stay safe):
- Does NOT try to resolve static imports.
- If two classes share the same simple name in different packages, it will NOT auto-fix (prints a warning).
- Only manages imports for classes that exist in your source tree (not external libs).
#>

[CmdletBinding()]
param(
  [switch]$Apply,
  [string]$JavaRootRelative = "java",
  [string]$BasePackage = "ch.Elodin.RealmQuill"
)

Set-StrictMode -Version Latest
$ErrorActionPreference="Stop"

function Utf8NoBom() { New-Object System.Text.UTF8Encoding($false) }
function WriteUtf8NoBom([string]$Path,[string]$Text){ [System.IO.File]::WriteAllText($Path,$Text,(Utf8NoBom)) }
function DoOrPrint([string]$Msg,[scriptblock]$Action){ if($Apply){&$Action}else{Write-Host "[DRY-RUN] $Msg" -ForegroundColor Yellow} }

function Get-RelativePathPS51([string]$BasePath,[string]$TargetPath){
  $base = (Resolve-Path -LiteralPath $BasePath).Path.TrimEnd('\') + '\'
  $target = (Resolve-Path -LiteralPath $TargetPath).Path
  if($target.StartsWith($base,[StringComparison]::OrdinalIgnoreCase)){
    return $target.Substring($base.Length)
  }
  return $target
}

function PathToPackage([string]$javaRoot,[string]$filePath,[string]$basePackage){
  $dir = Split-Path -Parent $filePath
  $rel = Get-RelativePathPS51 -BasePath $javaRoot -TargetPath $dir
  $parts = @($rel.Split('\') | Where-Object { $_ -ne "" })
  if($parts.Count -eq 0){ return "" }

  if(-not [string]::IsNullOrWhiteSpace($basePackage)){
    $baseParts = $basePackage.Split('.')
    for($i=0; $i -le $parts.Count - $baseParts.Length; $i++){
      $match=$true
      for($j=0; $j -lt $baseParts.Length; $j++){
        if($parts[$i+$j] -ne $baseParts[$j]){ $match=$false; break }
      }
      if($match){
        $parts = $parts[$i..($parts.Count-1)]
        break
      }
    }
  }
  return ($parts -join '.')
}

function Get-PackageAndClassName([string]$content){
  $pkg = ""
  if($content -match '(?m)^\s*package\s+([a-zA-Z_][\w\.]*)\s*;'){ $pkg = $Matches[1] }

  # first top-level class / interface / enum / record
  $cls = $null
  if($content -match '(?m)^\s*(public\s+)?(class|interface|enum|record)\s+([A-Za-z_]\w*)\b'){
    $cls = $Matches[3]
  }
  return @($pkg,$cls)
}

$root = (Get-Location).Path
$javaRoot = Join-Path $root $JavaRootRelative
if(-not (Test-Path -LiteralPath $javaRoot)){ throw "Java root not found: $javaRoot (run from backend/src/main or set -JavaRootRelative)" }

Write-Host "Java root: $javaRoot"
Write-Host "Mode: " -NoNewline; if($Apply){Write-Host "APPLY" -ForegroundColor Red}else{Write-Host "DRY-RUN" -ForegroundColor Green}
Write-Host "BasePackage: $BasePackage"

$javaFiles = @(Get-ChildItem -LiteralPath $javaRoot -Recurse -File -Filter "*.java")

# ---- 1) Build class map from filesystem (more reliable than parsing package after it's been edited)
# Map: SimpleName -> FQN  (but handle duplicates)
$classMap = @{}            # simple -> fqn
$classDupes = @{}          # simple -> @([fqn1,fqn2,...])

foreach($f in $javaFiles){
  $path = $f.FullName
  $expectedPkg = PathToPackage -javaRoot $javaRoot -filePath $path -basePackage $BasePackage
  $simple = [System.IO.Path]::GetFileNameWithoutExtension($path)
  if([string]::IsNullOrWhiteSpace($expectedPkg)){ continue }
  $fqn = "$expectedPkg.$simple"

  if($classMap.ContainsKey($simple)){
    if(-not $classDupes.ContainsKey($simple)){ $classDupes[$simple] = @($classMap[$simple]) }
    $classDupes[$simple] += $fqn
    $classMap.Remove($simple) | Out-Null
  } elseif (-not $classDupes.ContainsKey($simple)) {
    $classMap[$simple] = $fqn
  }
}

if($classDupes.Count -gt 0){
  Write-Warning "Found duplicate class names (will NOT auto-fix imports for these):"
  foreach($k in $classDupes.Keys){
    Write-Warning ("  {0}: {1}" -f $k, ($classDupes[$k] -join ", "))
  }
}

# Regex helpers
$reImportLine = '(?m)^\s*import\s+(static\s+)?([a-zA-Z_][\w\.]*\.[A-Za-z_]\w*)\s*;\s*\r?\n'
$rePackageLine = '(?m)^\s*package\s+[a-zA-Z_][\w\.]*\s*;\s*\r?\n'
$reClassOrInterfaceOrEnum = '(?m)^\s*(public\s+)?(class|interface|enum|record)\s+[A-Za-z_]\w*\b'

# ---- 2) Fix per file
foreach($f in $javaFiles){
  $path = $f.FullName
  $text = Get-Content -LiteralPath $path -Raw
  if($text.Length -gt 0 -and $text[0] -eq [char]0xFEFF){ $text = $text.Substring(1) }

  # Split header area: package + imports + rest
  $pkgLine = ""
  $rest = $text

  if($rest -match $rePackageLine){
    $m = [System.Text.RegularExpressions.Regex]::Match($rest,$rePackageLine)
    $pkgLine = $m.Value
    $rest = $rest.Substring($m.Index + $m.Length)
  }

  # collect import lines at top (until first non-import non-empty non-comment-ish line)
  $importsBlock = ""
  while($true){
    $m = [System.Text.RegularExpressions.Regex]::Match($rest,'\A(\s*import\s+.*?;\s*\r?\n)')
    if(-not $m.Success){ break }
    $importsBlock += $m.Groups[1].Value
    $rest = $rest.Substring($m.Length)
  }

  # Parse current imports list
  $imports = @()
  foreach($m in [System.Text.RegularExpressions.Regex]::Matches($importsBlock,$reImportLine)){
    $imports += $m.Groups[2].Value
  }

  # Determine this file's own package (for skipping self-import)
  $thisPkg = ""
  if($pkgLine -match '(?m)^\s*package\s+([a-zA-Z_][\w\.]*)\s*;'){ $thisPkg = $Matches[1] }

  # Build a set of referenced simple names in the rest of the file
  # (simple heuristic: word boundary match, excludes if it is declared as a type in same file)
  $declared = @{}
  foreach($m in [System.Text.RegularExpressions.Regex]::Matches($rest,'(?m)^\s*(public\s+)?(class|interface|enum|record)\s+([A-Za-z_]\w*)\b')){
    $declared[$m.Groups[3].Value] = $true
  }

  $neededImports = @{}  # fqn -> $true
  foreach($simple in $classMap.Keys){
    if($declared.ContainsKey($simple)){ continue } # declared here
    if($rest -match "(?<!\.)\b$([Regex]::Escape($simple))\b"){
      $fqn = $classMap[$simple]
      # don't import same package
      $pkg = $fqn.Substring(0, $fqn.LastIndexOf('.'))
      if($pkg -ne $thisPkg){
        $neededImports[$fqn] = $true
      }
    }
  }

  # Remove wrong imports that refer to our mapped classes but not the mapped FQN
  $importsKept = @()
  foreach($imp in $imports){
    $simple = $imp.Substring($imp.LastIndexOf('.')+1)
    if($classMap.ContainsKey($simple)){
      $correct = $classMap[$simple]
      if($imp -ne $correct){
        DoOrPrint "Remove wrong import in $path : $imp (correct: $correct)" { }
        continue
      }
    }
    $importsKept += $imp
  }

  # Add missing needed imports
  $importSet = @{}
  foreach($i in $importsKept){ $importSet[$i] = $true }
  foreach($need in $neededImports.Keys){
    if(-not $importSet.ContainsKey($need)){
      DoOrPrint "Add import in $path : $need" { }
      $importsKept += $need
      $importSet[$need] = $true
    }
  }

  # Sort imports (simple alpha sort)
  $importsKept = @($importsKept | Sort-Object)

  # Rebuild file
  $newImportsBlock = ""
  if($importsKept.Count -gt 0){
    foreach($i in $importsKept){
      $newImportsBlock += "import $i;`r`n"
    }
    $newImportsBlock += "`r`n"
  }

  $newText = $pkgLine + $newImportsBlock + $rest

  if($newText -ne $text){
    DoOrPrint "Write file: $path" { WriteUtf8NoBom -Path $path -Text $newText }
  }
}

Write-Host "`nDone."
Write-Host "Apply with:"
Write-Host "  powershell -ExecutionPolicy Bypass -File .\FixJavaImportsByFolderPackage.ps1 -Apply"