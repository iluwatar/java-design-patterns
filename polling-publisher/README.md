# Polling Publisher-Subscriber Microservice Pattern

This project implements a **Polling Publisher-Subscriber** system using **Spring Boot** and **Apache Kafka**. It consists of two microservices:

1. **Publisher Service** → Periodically polls a data source and publishes updates via Kafka.
2. **Subscriber Service** → Listens to Kafka for updates and processes them.

## 📌 **Project Structure**
```
polling-publisher-subscriber/
│️— pom.xml  (Parent POM)
│️— README.md  (This file)
│
├── polling-service/
│   ├── src/main/java/com/iluwatar/polling/
│   ├── src/main/resources/application.yml
│   ├── pom.xml
│   └── README.md  (Polling-specific documentation)
│
├── subscriber-service/
│   ├── src/main/java/com/iluwatar/subscriber/
│   ├── src/main/resources/application.yml
│   ├── pom.xml
│   └── README.md  (Subscriber-specific documentation)
```

## 🚀 **Tech Stack**
- **Spring Boot** (Microservices)
- **Apache Kafka** (Messaging)
- **Maven** (Build Tool)

## 🛠 **Setup & Running**
### 1️⃣ **Start Kafka & Zookeeper**
If you don’t have Kafka installed, use Docker:
```sh
docker-compose up -d
```

### 2️⃣ **Build the Project**
```sh
mvn clean install
```

### 3️⃣ **Run Service**
```sh
mvn spring-boot:run
```
