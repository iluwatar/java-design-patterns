#!/usr/bin/env bash
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
