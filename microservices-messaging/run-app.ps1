# PowerShell script to start Kafka container (if Docker is available) and run the Microservices Messaging App

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $ScriptDir

Write-Host "======================================================" -ForegroundColor Cyan
Write-Host " Starting Microservices Messaging Pattern Application" -ForegroundColor Cyan
Write-Host "======================================================" -ForegroundColor Cyan

# Check if Kafka is already running on port 9092
$portActive = $false
try {
    $socket = New-Object System.Net.Sockets.TcpClient("localhost", 9092)
    if ($socket.Connected) {
        $portActive = $true
        $socket.Close()
    }
} catch {
    $portActive = $false
}

if ($portActive) {
    Write-Host "[INFO] Kafka is already running on port 9092." -ForegroundColor Green
} else {
    # Check if Docker is available, or add Docker Desktop to PATH
    if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
        $dockerPath = "$env:LOCALAPPDATA\Programs\DockerDesktop\resources\bin"
        if (Test-Path $dockerPath) {
            $env:PATH += ";$dockerPath"
        }
    }
    $dockerCmd = Get-Command docker -ErrorAction SilentlyContinue
    if ($dockerCmd) {
        Write-Host "[INFO] Starting Kafka container via Docker Compose..." -ForegroundColor Yellow
        docker compose up -d
        
        Write-Host "[INFO] Waiting for Kafka to become ready on port 9092..." -ForegroundColor Yellow
        $retryCount = 0
        while (-not $portActive -and $retryCount -lt 20) {
            Start-Sleep -Seconds 2
            try {
                $socket = New-Object System.Net.Sockets.TcpClient("localhost", 9092)
                if ($socket.Connected) {
                    $portActive = $true
                    $socket.Close()
                }
            } catch {
                $retryCount++
            }
        }
        
        if ($portActive) {
            Write-Host "[INFO] Kafka container started successfully!" -ForegroundColor Green
        } else {
            Write-Host "[WARNING] Kafka did not become ready on port 9092 within timeout." -ForegroundColor Red
        }
    } else {
        Write-Host "[WARNING] Docker is not installed or not in PATH." -ForegroundColor Yellow
        Write-Host "[INFO] Please ensure Kafka is running locally on localhost:9092 before running the app." -ForegroundColor Yellow
    }
}

Write-Host "[INFO] Compiling and running App.java..." -ForegroundColor Cyan
& "..\mvnw.cmd" compile exec:java "-Dexec.mainClass=com.iluwatar.messaging.App"
