# **Publisher(Polling)**
**README.md** (Inside `polling-module/`)


## Publisher Microservice

This module is responsible for **polling a data source** at regular intervals and publishing updates to Kafka.

## **How It Works**
- Uses a **scheduler** to poll data periodically.
- Sends updates to Kafka
- Exposes an API to manually trigger polling.

## **Project Structure**

```
publisher-module/
│️— src/main/java/com/iluawatar/polling/
|    ├── App.java
│    ├── DataRepository.java
│    ├── DataSourceService.java
│    ├── KafkaProducer.java
│    ├── PollingController.java
│    └── PollingScheduler.java
│
│️— pom.xml
│️— README.md  (This file)

```

## 🛠 **Running the Publisher**
```sh
mvn spring-boot:run
```

## 📝 **Endpoints**
| Method | Endpoint         | Description                      |
|--------|------------------|----------------------------------|
| `GET`  | `/health`        | check health of polling          |
| `POST` | `/send?message=""` | Manually trigger data publishing |

