---
title: Event Sourcing
category: Architectural
language: en
tag:
    - Decoupling
    - Event-driven
    - Fault tolerance
    - Messaging
    - Persistence
    - Scalability
    - Transactions
---

## Also known as

* Event Logging
* Event Streaming

## Intent

Event Sourcing is a design pattern that advocates for the storage of state changes as a sequence of events. Instead of updating a record in a database, all changes are stored as individual events which, when replayed, can recreate the state of an application at any point in time.

## Explanation

Real world example

> The modern emailing system is an example of the fundamental process behind the event-queue design pattern. When an email is sent, the sender continues their daily tasks without the necessity of an immediate response from the receiver. Additionally, the receiver has the freedom to access and process the email at their leisure. Therefore, this process decouples the sender and receiver so that they are not required to engage with the queue at the same time.


In plain words

> The buffer between sender and receiver improves maintainability and scalability of a system. Event queues are typically used to organise and carry out interprocess communication (IPC).

Wikipedia says

> Message queues (also known as event queues) implement an asynchronous communication pattern between two or more processes/threads whereby the sending and receiving party do not need to interact with the queue at the same time.


Key drawback

> As the event queue model decouples the sender-receiver relationship - this means that the event-queue design pattern is unsuitable for scenarios in which the sender requires a response. For example, this is a prominent feature within online multiplayer games, therefore, this approach require thorough consideration.

**Programmatic Example**

In the given code, we can see an example of the Event Sourcing pattern in the Event class. This class manages a queue of events and controls thread operations for asynchronous processing. Each event can be seen as a state change that affects the state of the system.

```java
public class Event {
    private static final Event INSTANCE = new Event();

    private static final int MAX_PENDING = 16;

    private int headIndex;

    private int tailIndex;

    private volatile Thread updateThread = null;

    private final EventMessage[] pendingEvents = new EventMessage[MAX_PENDING];

    Event() {}

    public static Event getInstance() {
        return INSTANCE;
    }
}
```

The triggerEvent method is where the events are created. Each time an event is triggered, it is created and added to the queue. This event contains the details of the state change.

```java
public void triggerEvent(EventMessage eventMessage) {
    init();
    for(var i = headIndex; i != tailIndex; i = (i + 1) % MAX_PENDING) {
        var pendingEvent = getPendingEvents()[i];
        if(pendingEvent.equals(eventMessage)) {
            return;
        }
    }
    getPendingEvents()[tailIndex] = eventMessage;
    tailIndex = (tailIndex + 1) % MAX_PENDING;
}
```

The init and startThread methods ensure the thread is properly initialized and running. The stopService method is used to stop the thread when it's no longer needed. These methods manage the lifecycle of the thread used to process the events.

```java
public synchronized void stopService() throws InterruptedException {
    if(updateThread != null) {
        updateThread.interrupt();
        updateThread.join();
        updateThread = null;
    }
}

public synchronized boolean isServiceRunning() {
    return updateThread != null && updateThread.isAlive();
}

public void init() {
    if(updateThread == null) {
        updateThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                update();
            }
        });
        startThread();
    }
}

private synchronized void startThread() {
    if (!updateThread.isAlive()) {
        updateThread.start();
        headIndex = 0;
        tailIndex = 0;
    }
}
```

In this example, the state of the system can be recreated at any point by replaying the events in the queue. This is a key feature of the Event Sourcing pattern.

## Class diagram

![Event Sourcing](./etc/event-sourcing.png "Event Sourcing")

## Applicability

* In systems where complete audit trails and historical changes are crucial.
* In complex domains where the state of an application is derived from a series of changes.
* For systems that benefit from high availability and scalability as Event Sourcing naturally lends itself to distributed systems.

## Known Uses

* Financial systems to track transactions and account balances over time.
* E-commerce applications for order and inventory management.
* Real-time data processing systems where event consistency and replayability are critical.
* [The LMAX Architecture](https://martinfowler.com/articles/lmax.html)

## Consequences

Benefits:

* Auditability: Each change to the state is recorded, allowing for comprehensive auditing.
* Replayability: Events can be reprocessed to recreate historical states or move to new states.
* Scalability: Events can be processed asynchronously and in parallel.

## Trade-offs

* Complexity: Implementing and maintaining an event-sourced system can introduce additional complexity.
* Event store size: Storing every state change can lead to large data volumes.
* Event versioning: Changes in event structure over time require careful handling to ensure system integrity.

## Related Patterns

* [Command Query Responsibility Segregation (CQRS)](https://java-design-patterns.com/patterns/cqrs/): Often used together with Event Sourcing to separate read and write responsibilities, enhancing performance and scalability.
* Snapshot: Used to optimize Event Sourcing systems by periodically saving the current state to avoid replaying a long sequence of events.

## Credits

* [Implementing Domain-Driven Design](https://amzn.to/3JgvA8V)
* [Patterns, Principles, and Practices of Domain-Driven Design](https://amzn.to/3VVhfWX)
* [Building Microservices: Designing Fine-Grained Systems](https://amzn.to/443WfiS)
* [Martin Fowler - Event Sourcing](https://martinfowler.com/eaaDev/EventSourcing.html)
* [Event Sourcing in Microsoft's documentation](https://docs.microsoft.com/en-us/azure/architecture/patterns/event-sourcing)
* [Reference 3: Introducing Event Sourcing](https://msdn.microsoft.com/en-us/library/jj591559.aspx)
* [Event Sourcing pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/event-sourcing)
