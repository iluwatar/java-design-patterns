# **Subscriber**
README.md (Inside `subscriber-module/`)

## Subscriber Module

This module **listens** to Kafka topic **`updates_topic`** and processes updates.

## How It Works
- Uses **Spring Kafka** to consume messages.
- Listens
- Processes the received messages.

## Project Structure
```
subscriber-module/
│➜ src/main/java/com/example/subscriber/
|   ├── App.java
│
│➜ pom.xml
│➜ README.md  (This file)
```

## Running the Subscriber
```sh
mvn spring-boot:run
```

## Testing
Verify subscriber output:
```sh
mvn spring-boot:run
```
Expected console output:
```

```

---