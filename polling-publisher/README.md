## Polling-publisher

---
**Title:** "Polling Publisher-Subscriber Microservice Pattern in Java: Mastering Asynchronous Messaging Elegantly"
**ShortTitle:** Polling Pub/Sub  
**description:** "This project shows how to build a Polling Publisher-Subscriber system using Spring Boot and Apache Kafka. The Publisher Service polls data at regular intervals and sends updates to Kafka. The Subscriber Service listens to Kafka and processes the updates. It helps to separate data producers from consumers and is useful when real-time push is not possible."

**Category:** Architectural  
**Language:** en

**Tags:**
- Spring Boot
- Kafka
- Microservices
- Asynchronous Messaging
- Decoupling
---



## Also Known As
- Event-Driven Architecture
- Asynchronous Pub/Sub Pattern
- Message Queue-Based Polling System

## Intent of Polling Publisher-Subscriber Pattern
To decouple data producers and consumers in a distributed system, enabling asynchronous message-driven communication by polling a data source and transmitting updates via a message broker like Kafka.

## Detailed Explanation of the Pattern

### Real-World Analogy
A news agency polls for latest updates and broadcasts them to newspapers and channels, which are then read by people independently.

### In Plain Words
One service polls for data and publishes messages to Kafka. Another service consumes and processes these messages asynchronously.

### Wikipedia Says
This pattern closely resembles the Publish–subscribe model. See: https://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern

### Architecture
Publisher → Kafka → Subscriber

## Algorithm Logic (Spring Boot + Kafka)

### Publisher Service
- Polls data periodically using Spring Scheduler.
- Publishes data to Kafka.
- Exposes REST API for manual triggering.

### Subscriber Service
- Listens to Kafka topic using Spring Kafka.
- Processes messages asynchronously.

## When to Use This Pattern
- When producer and consumer need decoupling.
- When real-time push is unavailable, requiring polling.
- When building event-driven microservices.

## Java Tutorials
- https://www.baeldung.com/spring-kafka
- https://www.baeldung.com/spring-scheduled-tasks

## Real-World Applications
- Real-time reporting dashboards
- System health check aggregators
- IoT telemetry processing
- Notification/event services

## Benefits and Trade-offs

### Benefits
- Loose coupling between services
- Scalable and asynchronous
- Durable and fault-tolerant via Kafka
- Extendable and modular

### Trade-offs
- Polling introduces a delay
- Requires message broker setup
- Increases deployment complexity

## Related Design Patterns
- Observer Pattern
- Mediator Pattern
- Message Queue Pattern

## References and Credits
- Apache Kafka Docs: https://kafka.apache.org/documentation/
- Spring Kafka Docs: https://docs.spring.io/spring-kafka
- iluwatar/java-design-patterns
