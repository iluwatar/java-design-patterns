# **Subscriber**
README.md (Inside `subscriber-module/`)

## Subscriber Microservice

This module **listens** to Kafka topic **`updates & API`** and processes data.

## How It Works
- Uses **Spring Kafka** to consume messages.
- Listens
- Processes the received messages.

## Project Structure
```
subscriber-module/
│➜ src/main/java/com/example/subscriber/
|   ├── App.java
|   |-- KafkaConsumer.java
│
│➜ pom.xml
│➜ README.md  (This file)
```

## Running the Subscriber
```sh
mvn spring-boot:run
```

---