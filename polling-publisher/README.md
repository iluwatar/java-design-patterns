---
title: "Polling Publisher-Subscriber Pattern in Java: Mastering Asynchronous Messaging Elegantly"
shortTitle: Polling Pub/Sub
description: "Learn how to implement a Polling Publisher-Subscriber system in Java using Spring Boot and Kafka. Explore the architecture, real-world analogies, and benefits of asynchronous communication with clean code examples."
category: Architectural
language: en
tag:
  - Spring Boot
  - Kafka
  - Microservices
  - Asynchronous Messaging
  - Decoupling
---

## Also known as

* Event-Driven Architecture
* Asynchronous Pub/Sub Pattern
* Message Queue-Based Polling System

## Intent of Polling Publisher-Subscriber Pattern

The Polling Publisher-Subscriber pattern decouples data producers from consumers by enabling asynchronous, message-driven communication. A service polls a data source and publishes messages to a message broker (e.g., Kafka), which are then consumed by one or more subscriber services.

## Detailed Explanation of the Pattern with Real-World Examples

### Real-world analogy

> A news agency constantly polls for the latest news updates. Once it receives new information, it publishes them to different news outlets (TV, newspapers, apps). Each outlet consumes and displays the updates independently.

### In plain words

> One service regularly checks for updates (polls) and sends messages to Kafka. Another service listens to Kafka and processes the messages asynchronously.

### Wikipedia says

> This pattern closely resembles the [Publish–subscribe model](https://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern), where messages are sent by publishers and received by subscribers without them knowing each other.

### Architecture Flow

```
+------------+      +--------+      +-------------+
|  Publisher | ---> | Kafka  | ---> | Subscriber  |
+------------+      +--------+      +-------------+
```

## Programmatic Example (Spring Boot + Kafka)

### Publisher Service

- Uses Spring's `@Scheduled` to poll data periodically.
- Publishes data to a Kafka topic.
- Optionally exposes a REST API for manual data publishing.

```java
@Scheduled(fixedRate = 5000)
public void pollAndPublish() {
    String data = pollingService.getLatestData();
    kafkaTemplate.send("updates-topic", data);
}
```

### Subscriber Service

- Listens to Kafka topic using `@KafkaListener`.
- Processes messages asynchronously.

```java
@KafkaListener(topics = "updates-topic")
public void processUpdate(String message) {
    log.info("Received update: {}", message);
    updateProcessor.handle(message);
}
```

## When to Use the Polling Publisher-Subscriber Pattern

Use this pattern when:

* Real-time push from the producer is not possible.
* Loose coupling between producers and consumers is desired.
* You need asynchronous, scalable event processing.
* You are building an event-driven microservices architecture.

## Real-World Applications

* Real-time reporting dashboards
* Health check aggregators for distributed systems
* IoT telemetry processing
* Notification and alerting systems

## Benefits and Trade-offs of Polling Pub/Sub Pattern

### Benefits

* Loose coupling between services
* Asynchronous and scalable architecture
* Fault-tolerant with message persistence in Kafka
* Easy to extend with new consumers or publishers

### Trade-Offs

* Polling introduces latency between data generation and consumption
* Requires managing and configuring Kafka (or other brokers)
* Slightly more complex deployment and infrastructure setup

## Related Java Design Patterns

* [Observer Pattern](https://java-design-patterns.com/patterns/observer/)
* [Mediator Pattern](https://java-design-patterns.com/patterns/mediator/)
* [Message Queue Pattern](https://java-design-patterns.com/patterns/event-queue/)

## References and Credits

* [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
* [Spring Kafka Documentation](https://docs.spring.io/spring-kafka)
* [Spring Scheduled Tasks](https://www.baeldung.com/spring-scheduled-tasks)
* [Spring Kafka Tutorial – Baeldung](https://www.baeldung.com/spring-kafka)
* Inspired by: [iluwatar/java-design-patterns](https://github.com/iluwatar/java-design-patterns)