---
title: "Event Sourcing Pattern in Java: Building Immutable Historical Records for Robust Systems"
shortTitle: Event Sourcing
description: "Discover the Event Sourcing design pattern in Java. Learn how it stores state changes as events and benefits complex applications. See examples and explanations on Java Design Patterns."
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
head:
  - - meta
    - name: keywords
      content:
---

## Also known as

* Event Logging
* Event Streaming

## Intent of Event Sourcing Design Pattern

Event Sourcing is a design pattern that advocates for the storage of state changes as a sequence of events. Instead of updating a record in a database, all changes are stored as individual events which, when replayed, can recreate the state of an application at any point in time.

## Detailed Explanation of Event Sourcing Pattern with Real-World Examples

Real-world example

> Consider a banking application that tracks all transactions for user accounts. In this system, every deposit, withdrawal, and transfer is recorded as an individual event in an event log. Instead of simply updating the current account balance, each transaction is stored as a discrete event. This approach allows the bank to maintain a complete and immutable history of all account activities. If a discrepancy occurs, the bank can replay the sequence of events to reconstruct the account state at any point in time. This provides a robust audit trail, facilitates debugging, and supports features like transaction rollback and historical data analysis.

In plain words

> Event Sourcing records all state changes as a sequence of immutable events to ensure reliable state reconstruction and auditability.

[Microsoft's documentation](https://learn.microsoft.com/en-us/azure/architecture/patterns/event-sourcing) says

> The Event Sourcing pattern defines an approach to handling operations on data that's driven by a sequence of events, each of which is recorded in an append-only store. Application code sends a series of events that imperatively describe each action that has occurred on the data to the event store, where they're persisted. Each event represents a set of changes to the data (such as AddedItemToOrder).

## Programmatic Example of Event Sourcing Pattern in Java

In the programmatic example we transfer some money between bank accounts.

The `Event` class manages a queue of events and controls thread operations for asynchronous processing. Each event can be seen as a state change that affects the state of the system.

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

The `triggerEvent` method is where the events are created. Each time an event is triggered, it is created and added to the queue. This event contains the details of the state change.

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

The `init` and `startThread` methods ensure the thread is properly initialized and running. The `stopService` method is used to stop the thread when it's no longer needed. These methods manage the lifecycle of the thread used to process the events.

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

The example is driven by the `App` class and its `main` method.

```java
@Slf4j
public class App {

  public static final int ACCOUNT_OF_DAENERYS = 1;

  public static final int ACCOUNT_OF_JON = 2;

  public static void main(String[] args) {

    var eventProcessor = new DomainEventProcessor(new JsonFileJournal());

    LOGGER.info("Running the system first time............");
    eventProcessor.reset();

    LOGGER.info("Creating the accounts............");

    eventProcessor.process(new AccountCreateEvent(
        0, new Date().getTime(), ACCOUNT_OF_DAENERYS, "Daenerys Targaryen"));

    eventProcessor.process(new AccountCreateEvent(
        1, new Date().getTime(), ACCOUNT_OF_JON, "Jon Snow"));

    LOGGER.info("Do some money operations............");

    eventProcessor.process(new MoneyDepositEvent(
        2, new Date().getTime(), ACCOUNT_OF_DAENERYS, new BigDecimal("100000")));

    eventProcessor.process(new MoneyDepositEvent(
        3, new Date().getTime(), ACCOUNT_OF_JON, new BigDecimal("100")));

    eventProcessor.process(new MoneyTransferEvent(
        4, new Date().getTime(), new BigDecimal("10000"), ACCOUNT_OF_DAENERYS,
        ACCOUNT_OF_JON));

    LOGGER.info("...............State:............");
    LOGGER.info(AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS).toString());
    LOGGER.info(AccountAggregate.getAccount(ACCOUNT_OF_JON).toString());

    LOGGER.info("At that point system had a shut down, state in memory is cleared............");
    AccountAggregate.resetState();

    LOGGER.info("Recover the system by the events in journal file............");

    eventProcessor = new DomainEventProcessor(new JsonFileJournal());
    eventProcessor.recover();

    LOGGER.info("...............Recovered State:............");
    LOGGER.info(AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS).toString());
    LOGGER.info(AccountAggregate.getAccount(ACCOUNT_OF_JON).toString());
  }
}
```

Running the example produces the following console output.

```
22:40:47.982 [main] INFO com.iluwatar.event.sourcing.app.App -- Running the system first time............
22:40:47.984 [main] INFO com.iluwatar.event.sourcing.app.App -- Creating the accounts............
22:40:47.985 [main] INFO com.iluwatar.event.sourcing.domain.Account -- Some external api for only realtime execution could be called here.
22:40:48.089 [main] INFO com.iluwatar.event.sourcing.domain.Account -- Some external api for only realtime execution could be called here.
22:40:48.090 [main] INFO com.iluwatar.event.sourcing.app.App -- Do some money operations............
22:40:48.090 [main] INFO com.iluwatar.event.sourcing.domain.Account -- Some external api for only realtime execution could be called here.
22:40:48.095 [main] INFO com.iluwatar.event.sourcing.domain.Account -- Some external api for only realtime execution could be called here.
22:40:48.099 [main] INFO com.iluwatar.event.sourcing.domain.Account -- Some external api for only realtime execution could be called here.
22:40:48.099 [main] INFO com.iluwatar.event.sourcing.domain.Account -- Some external api for only realtime execution could be called here.
22:40:48.101 [main] INFO com.iluwatar.event.sourcing.app.App -- ...............State:............
22:40:48.104 [main] INFO com.iluwatar.event.sourcing.app.App -- Account{accountNo=1, owner='Daenerys Targaryen', money=90000}
22:40:48.104 [main] INFO com.iluwatar.event.sourcing.app.App -- Account{accountNo=2, owner='Jon Snow', money=10100}
22:40:48.104 [main] INFO com.iluwatar.event.sourcing.app.App -- At that point system had a shut down, state in memory is cleared............
22:40:48.104 [main] INFO com.iluwatar.event.sourcing.app.App -- Recover the system by the events in journal file............
22:40:48.124 [main] INFO com.iluwatar.event.sourcing.app.App -- ...............Recovered State:............
22:40:48.124 [main] INFO com.iluwatar.event.sourcing.app.App -- Account{accountNo=1, owner='Daenerys Targaryen', money=90000}
22:40:48.124 [main] INFO com.iluwatar.event.sourcing.app.App -- Account{accountNo=2, owner='Jon Snow', money=10100}
```

In this example, the state of the system can be recreated at any point by replaying the events in the queue. This is a key feature of the Event Sourcing pattern.

## When to Use the Event Sourcing Pattern in Java

* In systems where complete audit trails and historical changes are crucial.
* In complex domains where the state of an application is derived from a series of changes.
* For systems that benefit from high availability and scalability as Event Sourcing naturally lends itself to distributed systems.

## Real-World Applications of Event Sourcing Pattern in Java

* Financial systems to track transactions and account balances over time.
* E-commerce applications for order and inventory management.
* Real-time data processing systems where event consistency and replayability are critical.
* [The LMAX Architecture](https://martinfowler.com/articles/lmax.html)

## Benefits and Trade-offs of Event Sourcing Pattern

Benefits:

* Auditability: Each change to the state is recorded, allowing for comprehensive auditing.
* Replayability: Events can be reprocessed to recreate historical states or move to new states.
* Scalability: Events can be processed asynchronously and in parallel.

Trade-offs

* Complexity: Implementing and maintaining an event-sourced system can introduce additional complexity.
* Event store size: Storing every state change can lead to large data volumes.
* Event versioning: Changes in event structure over time require careful handling to ensure system integrity.

## Related Java Design Patterns

* [Command Query Responsibility Segregation (CQRS)](https://java-design-patterns.com/patterns/cqrs/): Often used together with Event Sourcing to separate read and write responsibilities, enhancing performance and scalability.
* Snapshot: Used to optimize Event Sourcing systems by periodically saving the current state to avoid replaying a long sequence of events.

## References and Credits

* [Building Microservices: Designing Fine-Grained Systems](https://amzn.to/443WfiS)
* [Implementing Domain-Driven Design](https://amzn.to/3JgvA8V)
* [Patterns, Principles, and Practices of Domain-Driven Design](https://amzn.to/3VVhfWX)
* [Event Sourcing (Martin Fowler)](https://martinfowler.com/eaaDev/EventSourcing.html)
* [Event Sourcing pattern (Microsoft)](https://docs.microsoft.com/en-us/azure/architecture/patterns/event-sourcing)
