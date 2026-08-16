#
# This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
#
# The MIT License
# Copyright © 2014-2022 Ilkka Seppälä
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.
#

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
