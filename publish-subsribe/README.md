---
title: "Publish/Subscribe"
shortTitle: "Pub/Sub Pattern"
description: "A messaging pattern that enables loose coupling between publishers and subscribers through message topics"
category: Behavioral
language: en
tag:
    - Messaging
    - Event-Driven
    - Decoupling
---

## Also known as

-   Pub/Sub
-   Observer Pattern (similar but not identical)

## Intent

Establish a one-to-many, many-to-one, or many-to-many dependency between objects where multiple publishers send messages to topics, and multiple subscribers can receive those messages without direct knowledge of each other, promoting loose coupling and scalability.

## Core Concepts

### Topics and Messaging Components

A topic is a distribution mechanism for publishing messages between message producers and message consumers in a one-to-many relationship. Key components include:

-   **Topics**: Destinations where publishers send messages and from which subscribers receive them. Unlike queues (used in point-to-point messaging), topics can have multiple consumers.

-   **Message Producers**: Lightweight objects created to send messages to a topic. They are typically created per topic and can be created for each message since they don't consume significant resources.

-   **Message Consumers**: Objects that subscribe to topics and receive messages. Consumers can receive messages either:
    -   Synchronously: By calling a `receive()` method that blocks until a message arrives
    -   Asynchronously: By implementing a message listener with an `onMessage()` callback (which is discussed in this implementation)

### Types of Subscriptions

1. **Non-Durable Subscriptions**

    - Simplest form of subscription
    - Exists only while the consumer is active
    - Messages sent while the subscriber is inactive are missed
    - Best for real-time data where missing messages is acceptable
    - Example: Live sports score updates

2. **Durable Subscriptions**

    - Maintains subscription state even when subscriber is offline
    - Messages are stored by JMS provider until consumer reconnects
    - Requires a unique client identifier for persistence
    - Two subtypes:
        - Unshared: Only one consumer can use the subscription
        - Shared: Multiple consumers can share the subscription
    - Example: Critical business notifications

3. **Shared Subscriptions**
    - Allows multiple consumers to share message load
    - Messages are distributed among active consumers
    - Can be combined with durability
    - Useful for load balancing and high-throughput scenarios
    - Example: Distributed processing of bank transactions

### Messaging Infrastructure

The JMS (Java Message Service) provider handles:

-   Message persistence for durable subscriptions
-   Message distribution to appropriate subscribers
-   Connection and session management
-   Transaction support when integrated with JTA
-   Load balancing for shared subscriptions

## Detailed Explanation with Real-World Examples

Real-world example

> Consider a news distribution system where different types of subscribers receive news updates:
>
> -   Regular subscribers who only receive messages while they're online
> -   Durable subscribers who receive missed messages when they reconnect
> -   Shared subscribers who distribute the message load among multiple instances
>     This is exactly what our demonstration implements in the App.java examples.

In plain words

> A news publishing system where publishers can send news to topics, and different types of subscribers (basic, durable, shared) can receive these updates in various ways, as demonstrated in our three main scenarios: basic publish-subscribe, durable subscriptions, and shared subscriptions.

Wikipedia says

> Publish–subscribe is a messaging pattern where senders of messages, called publishers, do not program the messages to be sent directly to specific receivers, called subscribers, but instead categorize published messages into classes without knowledge of which subscribers, if any, may be interested.

## Programmatic Example

### 1. Basic Publish-Subscribe Pattern

The most straightforward implementation where all subscribers receive all messages:

```java
// Create basic subscribers that receive messages only while online
TopicSubscriber basicSub1 = new TopicSubscriber(
    "BasicSub1", "NEWS", SubscriberType.NONDURABLE, null
);
TopicSubscriber basicSub2 = new TopicSubscriber(
    "BasicSub2", "NEWS", SubscriberType.NONDURABLE, null
);

// Create publisher and send messages
TopicPublisher publisher = new TopicPublisher("NEWS");
publisher.publish(new Message("Basic message 1", "NEWS"));
publisher.publish(new Message("Basic message 2", "NEWS"));

// Both BasicSub1 and BasicSub2 will receive all messages
```

### 2. Durable Subscriptions Pattern

Demonstrates how subscribers can receive messages that were published while they were offline:

```java
// Create durable subscriber with client ID for persistence
TopicSubscriber durableSub = new TopicSubscriber(
    "DurableSub", "NEWS", SubscriberType.DURABLE, "durable-client"
);

// First message - subscriber receives while online
publisher.publish(new Message("Durable message while online", "NEWS"));

// Subscriber goes offline (close connection)
durableSub.close();

// Message sent while subscriber is offline
publisher.publish(new Message("Durable message while offline", "NEWS"));

// When subscriber reconnects, it receives the missed message
durableSub = new TopicSubscriber(
    "DurableSub", "NEWS", SubscriberType.DURABLE, "durable-client"
);
```

### 3. Shared Subscriptions Pattern

Shows how messages can be distributed among multiple subscribers for load balancing:

```java
// Create shared subscribers that will distribute the message load
TopicSubscriber sharedSub1 = new TopicSubscriber(
    "SharedSub1", "NEWS", SubscriberType.SHARED, null
);
TopicSubscriber sharedSub2 = new TopicSubscriber(
    "SharedSub2", "NEWS", SubscriberType.SHARED, null
);

// Send multiple messages that will be distributed
publisher.publish(new Message("Shared message 1", "NEWS"));
publisher.publish(new Message("Shared message 2", "NEWS"));
publisher.publish(new Message("Shared message 3", "NEWS"));
publisher.publish(new Message("Shared message 4", "NEWS"));

// Messages are distributed between SharedSub1 and SharedSub2
// Each subscriber receives approximately half of the messages
```

## Implementation Details

-   Basic subscribers demonstrate the simplest form of pub/sub where all subscribers receive all messages
-   Durable subscribers maintain their subscription state even when offline, ensuring no messages are missed
-   Shared subscribers enable load balancing by distributing messages among multiple consumers
-   Messages are delivered asynchronously through the `onMessage()` callback
-   The JMS provider (ActiveMQ in this implementation) handles message persistence and distribution

## When to Use

-   When you need different types of message consumption patterns:
    -   Basic subscribers for simple real-time updates
    -   Durable subscribers for critical messages that can't be missed
    -   Shared subscribers for load balancing
-   When subscribers need to receive messages even after being offline (demonstrated in durableSubscriptions() example)
-   When message load needs to be distributed among multiple consumers (demonstrated in sharedSubscriptions() example)

## Real-World Applications

Any event-driven system that requires loose coupling between publishers and subscribers can benefit from the pub/sub pattern. Some common examples include:

-   IOT systems where multiple devices publish data to a central server
-   Enterprise messaging systems (JMS) for inter-application communication
-   Microservices architectures where services communicate through message brokers
-   News distribution systems (as demonstrated in our NEWS topic example)
-   Load-balanced message processing systems (using shared subscriptions)
-   Message broadcasting systems (using basic pub/sub)

## Benefits and Trade-offs

Benefits:

-   Loose coupling between publishers and subscribers
-   Scalability through message distribution
-   Flexibility to add/remove components
-   Support for offline components through durable subscriptions
-   Asynchronous communication

Trade-offs:

-   Additional complexity in message delivery guarantees
-   Potential performance overhead from message broker
-   Message ordering challenges in distributed systems
-   Durable subscriptions which allow for message persistence add a coniderable overhead

## Related Patterns

-   Observer Pattern
-   Mediator Pattern
-   Event Sourcing
-   Message Queue Pattern

## References

-   Java EE Tutorial - JMS Messaging
-   Enterprise Integration Patterns
