# Run from backend/src/main
Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$javaRoot = Join-Path (Get-Location) "java"

Get-ChildItem -LiteralPath $javaRoot -Recurse -File -Filter "*.java" | ForEach-Object {
  $path = $_.FullName

  # Read bytes
  $bytes = [System.IO.File]::ReadAllBytes($path)

  # UTF-8 BOM = EF BB BF
  if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
    $newBytes = $bytes[3..($bytes.Length-1)]
    [System.IO.File]::WriteAllBytes($path, $newBytes)
    Write-Host "Removed UTF-8 BOM: $path"
  }
}