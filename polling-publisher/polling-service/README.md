# **Publisher(Polling)**
**README.md** (Inside `polling-module/`)


## Publisher Module

This module is responsible for **polling a data source** at regular intervals and publishing updates to Kafka.

## **How It Works**
- Uses a **scheduler** to poll data periodically.
- Sends updates to Kafka
- Exposes an API to manually trigger polling.

## **Project Structure**

```
publisher-module/
│️— src/main/java/com/iluawatar/polling/
|   ├── App.java
│
│️— pom.xml
│️— README.md  (This file)

```

## 🛠 **Running the Publisher**
```sh
mvn spring-boot:run
```

## 📝 **Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/publish` | Manually trigger data publishing |

## 🛠 **Testing Kafka Output**