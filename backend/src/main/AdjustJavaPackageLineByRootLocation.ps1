<#
PS 5.1 compatible.

Run from backend/src/main (or pass -JavaRootRelative).
Adjusts package line to match folder location under ./java.
Writes UTF-8 WITHOUT BOM (avoids \ufeff error).
#>

[CmdletBinding()]
param(
  [switch]$Apply,
  [string]$JavaRootRelative = "java",
  [string]$BasePackage = "ch.Elodin.RealmQuill",
  [switch]$FixImports = $true,
  [string]$OldBasePackage = "ch.Elodin.DnD_Tool"
)

Set-StrictMode -Version Latest
$ErrorActionPreference="Stop"

function Utf8NoBom() { New-Object System.Text.UTF8Encoding($false) }
function WriteUtf8NoBom([string]$Path,[string]$Text){
  [System.IO.File]::WriteAllText($Path,$Text,(Utf8NoBom))
}
function DoOrPrint([string]$Msg,[scriptblock]$Action){
  if($Apply){ & $Action } else { Write-Host "[DRY-RUN] $Msg" -ForegroundColor Yellow }
}

function Get-RelativePathPS51([string]$BasePath,[string]$TargetPath){
  $base = (Resolve-Path -LiteralPath $BasePath).Path.TrimEnd('\') + '\'
  $target = (Resolve-Path -LiteralPath $TargetPath).Path
  if($target.StartsWith($base,[StringComparison]::OrdinalIgnoreCase)){
    return $target.Substring($base.Length)
  }
  # fallback: return target as-is (shouldn't happen if under base)
  return $target
}

function PathToPackage([string]$javaRoot,[string]$filePath){
  $dir = Split-Path -Parent $filePath
  $rel = Get-RelativePathPS51 -BasePath $javaRoot -TargetPath $dir
  $parts = @($rel.Split('\') | Where-Object { $_ -ne "" })

  if($parts.Count -eq 0){ return "" }

  if(-not [string]::IsNullOrWhiteSpace($BasePackage)){
    $baseParts = $BasePackage.Split('.')
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

$root = (Get-Location).Path
$javaRoot = Join-Path $root $JavaRootRelative
if(-not (Test-Path -LiteralPath $javaRoot)){
  throw "Java root not found: $javaRoot (run from backend/src/main or set -JavaRootRelative)"
}

Write-Host "Java root: $javaRoot"
Write-Host "Mode: " -NoNewline; if($Apply){Write-Host "APPLY" -ForegroundColor Red}else{Write-Host "DRY-RUN" -ForegroundColor Green}
Write-Host "BasePackage: $BasePackage"
if($FixImports){Write-Host "FixImports: ON ($OldBasePackage -> $BasePackage)"} else {Write-Host "FixImports: OFF"}

$pkgRegex = '(?m)^\s*package\s+([a-zA-Z_][\w\.]*)\s*;\s*\r?\n'

foreach($f in @(Get-ChildItem -LiteralPath $javaRoot -Recurse -File -Filter "*.java")){
  $path = $f.FullName
  $expectedPkg = PathToPackage -javaRoot $javaRoot -filePath $path

  $text = Get-Content -LiteralPath $path -Raw
  if($text.Length -gt 0 -and $text[0] -eq [char]0xFEFF){ $text = $text.Substring(1) } # strip BOM char if present

  $updated = $text

  if($updated -match $pkgRegex){
    $currentPkg = $Matches[1]
    if($currentPkg -ne $expectedPkg){
      $updated = [System.Text.RegularExpressions.Regex]::Replace($updated,$pkgRegex,("package {0};`r`n" -f $expectedPkg),1)
      DoOrPrint "Update package: $path ($currentPkg -> $expectedPkg)" { }
    }
  } else {
    $updated = ("package {0};`r`n`r`n{1}" -f $expectedPkg,$updated)
    DoOrPrint "Insert package: $path (-> $expectedPkg)" { }
  }

  if($FixImports -and $OldBasePackage -and $BasePackage){
    $u2 = $updated.Replace(("import {0}." -f $OldBasePackage),("import {0}." -f $BasePackage))
    if($u2 -ne $updated){
      $updated = $u2
      DoOrPrint "Fix imports: $path" { }
    }
  }

  if($updated -ne $text){
    DoOrPrint "Write: $path" { WriteUtf8NoBom -Path $path -Text $updated }
  }
}

Write-Host "`nDone."
Write-Host "Apply with:"
Write-Host "  powershell -ExecutionPolicy Bypass -File .\AdjustJavaPackageLineByRootLocation.ps1 -Apply"