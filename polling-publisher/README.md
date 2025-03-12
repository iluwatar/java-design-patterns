# Polling Publisher-Subscriber System

This project implements a **Polling Publisher-Subscriber** system using **Spring Boot** and **Apache Kafka**. It consists of two microservices:

1. **Publisher Module** → Periodically polls a data source and publishes updates via Kafka.
2. **Subscriber Module** → Listens to Kafka for updates and processes them.

## 📌 **Project Structure**
```
polling-publisher-subscriber/
│️— pom.xml  (Parent POM)
│️— README.md  (This file)
│
├── publisher-module/
│   ├── src/main/java/com/iluwatar/polling-service/
│   ├── src/main/resources/application.yml
│   ├── pom.xml
│   └── README.md  (Polling-specific documentation)
│
├── subscriber-module/
│   ├── src/main/java/com/iluwatar/subscriber-service/
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

### 3️⃣ **Run the Publisher Module**
```sh
mvn spring-boot:run -pl publisher-module
```

### 4️⃣ **Run the Subscriber Module**
```sh
mvn spring-boot:run -pl subscriber-module
```

## 📝 **Endpoints**
| Service | Endpoint | Description |
|---------|----------|-------------|
| Publisher | `GET /publish` | Manually trigger data publishing |
| Subscriber | (Kafka Consumer) | Listens for updates |

## 🛠 **Testing**
You can test Kafka messages using:
