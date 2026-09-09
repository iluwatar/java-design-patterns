#!/usr/bin/env bash
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

# Shell script to start Kafka container (if Docker is available) and run the Microservices Messaging App

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "======================================================"
echo " Starting Microservices Messaging Pattern Application"
echo "======================================================"

if nc -z localhost 9092 2>/dev/null || (echo > /dev/tcp/localhost/9092) 2>/dev/null; then
    echo "[INFO] Kafka is already running on port 9092."
else
    if command -v docker &> /dev/null; then
        echo "[INFO] Starting Kafka container via Docker Compose..."
        docker compose up -d
        
        echo "[INFO] Waiting for Kafka to become ready on port 9092..."
        retry=0
        until nc -z localhost 9092 2>/dev/null || (echo > /dev/tcp/localhost/9092) 2>/dev/null || [ $retry -eq 20 ]; do
            sleep 2
            retry=$((retry+1))
        done
        
        if [ $retry -lt 20 ]; then
            echo "[INFO] Kafka container started successfully!"
        else
            echo "[WARNING] Kafka did not become ready on port 9092 within timeout."
        fi
    else
        echo "[WARNING] Docker is not installed or not in PATH."
        echo "[INFO] Please ensure Kafka is running locally on localhost:9092."
    fi
fi

echo "[INFO] Compiling and running App.java..."
../mvnw compile exec:java -Dexec.mainClass="com.iluwatar.messaging.App"
