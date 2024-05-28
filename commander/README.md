---
title: Commander
category: Behavioral
language: en
tag:
    - Cloud distributed
    - Microservices
    - Transactions
---

## Also known as

* Distributed Transaction Commander
* Transaction Coordinator

## Intent

The intent of the Commander pattern in the context of distributed transactions is to manage and coordinate complex transactions across multiple distributed components or services, ensuring consistency and integrity of the overall transaction. It encapsulates transaction commands and coordination logic, facilitating the implementation of distributed transaction protocols like two-phase commit or Saga.

## Explanation

Real-world example

> Imagine organizing a large international music festival where various bands from around the world are scheduled to perform. Each band's arrival, soundcheck, and performance are like individual transactions in a distributed system. The festival organizer acts as the "Commander," coordinating these transactions to ensure that if a band's flight is delayed (akin to a transaction failure), there's a backup plan, such as rescheduling or swapping time slots with another band (compensating actions), to keep the overall schedule intact. This setup mirrors the Commander pattern in distributed transactions, where various components must be coordinated to achieve a successful outcome despite individual failures.

In plain words

> The Commander pattern turns a request into a stand-alone object, allowing for the parameterization of commands, queueing of actions, and the implementation of undo operations.

**Programmatic Example**

Managing transactions across different services in a distributed system, such as an e-commerce platform with separate `Payment` and `Shipping` microservices, requires careful coordination to avoid issues. When a user places an order but one service (e.g., `Payment`) is unavailable while the other (e.g., `Shipping`) is ready, we need a robust solution to handle this discrepancy.

A strategy to address this involves using a `Commander` component that orchestrates the process. Initially, the order is processed by the available service (`Shipping` in this case). The commander then attempts to synchronize the order with the currently unavailable service (`Payment`) by storing the order details in a database or queueing it for future processing. This queueing system must also account for possible failures in adding requests to the queue.

The `Commander` repeatedly tries to process the queued orders to ensure both services eventually reflect the same transaction data. This process involves ensuring idempotence, meaning that even if the same order synchronization request is made multiple times, it will only be executed once, preventing duplicate transactions. The goal is to achieve eventual consistency across services, where all systems are synchronized over time despite initial failures or delays.

To get a grasp of how this works in practice, let's see `AppShippingFailCases` class and explain afterward how it works.

```java
public class AppShippingFailCases {

    private static final RetryParams retryParams = RetryParams.DEFAULT;
    private static final TimeLimits timeLimits = TimeLimits.DEFAULT;

    void itemUnavailableCase() {
        var ps = new PaymentService(new PaymentDatabase());
        var ss = new ShippingService(new ShippingDatabase(), new ItemUnavailableException());
        var ms = new MessagingService(new MessagingDatabase());
        var eh = new EmployeeHandle(new EmployeeDatabase());
        var qdb = new QueueDatabase();
        var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
        var user = new User("Jim", "ABCD");
        var order = new Order(user, "book", 10f);
        c.placeOrder(order);
    }

    void shippingNotPossibleCase() {
        var ps = new PaymentService(new PaymentDatabase());
        var ss = new ShippingService(new ShippingDatabase(), new ShippingNotPossibleException());
        var ms = new MessagingService(new MessagingDatabase());
        var eh = new EmployeeHandle(new EmployeeDatabase());
        var qdb = new QueueDatabase();
        var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
        var user = new User("Jim", "ABCD");
        var order = new Order(user, "book", 10f);
        c.placeOrder(order);
    }

    void shippingDatabaseUnavailableCase() {
        //rest is successful
        var ps = new PaymentService(new PaymentDatabase());
        var ss = new ShippingService(new ShippingDatabase(), new DatabaseUnavailableException(),
                new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                new DatabaseUnavailableException(), new DatabaseUnavailableException(),
                new DatabaseUnavailableException());
        var ms = new MessagingService(new MessagingDatabase());
        var eh = new EmployeeHandle(new EmployeeDatabase());
        var qdb = new QueueDatabase();
        var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
        var user = new User("Jim", "ABCD");
        var order = new Order(user, "book", 10f);
        c.placeOrder(order);
    }

    void shippingSuccessCase() {
        //goes to payment after 2 retries maybe - rest is successful for now
        var ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException());
        var ss = new ShippingService(new ShippingDatabase(), new DatabaseUnavailableException(),
                new DatabaseUnavailableException());
        var ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException());
        var eh = new EmployeeHandle(new EmployeeDatabase());
        var qdb = new QueueDatabase();
        var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
        var user = new User("Jim", "ABCD");
        var order = new Order(user, "book", 10f);
        c.placeOrder(order);
    }

    public static void main(String[] args) {
        var asfc = new AppShippingFailCases();
        asfc.shippingSuccessCase();
    }
}
```

The `AppShippingFailCases` class is designed to simulate different scenarios where the Shipping service is available or unavailable. It uses the Commander pattern to handle distributed transactions across multiple services.

Here's a breakdown of the methods in the `AppShippingFailCases` class:

1. `itemUnavailableCase`: This method simulates a scenario where the item to be shipped is unavailable. It creates instances of the `Commander` class with the `ShippingService` throwing an `ItemUnavailableException`. An order is placed and the system tries to handle this failure.

2. `shippingNotPossibleCase`: This method simulates a scenario where shipping is not possible. It creates instances of the `Commander` class with the `ShippingService` throwing a `ShippingNotPossibleException`. An order is placed and the system tries to handle this failure.

3. `shippingDatabaseUnavailableCase`: This method simulates a scenario where the `ShippingService` and `ShippingDatabase` are unavailable. It creates instances of the `Commander` class with the `ShippingService` throwing multiple `DatabaseUnavailableException`. An order is placed and the system tries to handle this failure.

4. `shippingSuccessCase`: This method simulates a successful scenario where all services are available except for some temporary unavailability of the `PaymentService`, `ShippingService`, and `MessagingService`. An order is placed and the system handles this situation.

In each of these methods, a `Commander` instance is created with the respective services and their databases. Then, a `User` and an `Order` are created, and the `placeOrder` method of the `Commander` instance is called with the order. This triggers the process of placing the order and handling any failures according to the Commander pattern.

In the `main` method, the `shippingSuccessCase` method is called to simulate a successful scenario.

Finally, let's execute the `main` method see the program output.

```
18:01:07.738 [main] DEBUG com.iluwatar.commander.Commander -- Order I07V78ZOB8RZ: Error in connecting to shipping service, trying again..
18:01:10.536 [main] DEBUG com.iluwatar.commander.Commander -- Order I07V78ZOB8RZ: Error in connecting to shipping service, trying again..
18:01:15.401 [main] INFO com.iluwatar.commander.Commander -- Order I07V78ZOB8RZ: Shipping placed successfully, transaction id: RCM0PO9N9B6J
18:01:15.401 [main] INFO com.iluwatar.commander.Commander -- Order has been placed and will be shipped to you. Please wait while we make your payment... 
18:01:15.407 [Thread-0] DEBUG com.iluwatar.commander.Commander -- Order I07V78ZOB8RZ: Error in connecting to payment service, trying again..
18:01:18.327 [Thread-0] INFO com.iluwatar.commander.Commander -- Order I07V78ZOB8RZ: Payment successful, transaction Id: UWS72C00JN9Q
18:01:18.328 [Thread-0] INFO com.iluwatar.commander.Commander -- Payment made successfully, thank you for shopping with us!!
18:01:18.332 [Thread-1] DEBUG com.iluwatar.commander.Commander -- Order I07V78ZOB8RZ: Error in connecting to messaging service (Payment Success msg), trying again..
18:01:20.693 [Thread-1] INFO com.iluwatar.commander.messagingservice.MessagingService -- Msg: Your order has been placed and paid for successfully! Thank you for shopping with us!
18:01:20.694 [Thread-1] INFO com.iluwatar.commander.Commander -- Order I07V78ZOB8RZ: Payment Success message sent, request Id: 72DOWH1D0WYS
```

## Applicability

Use the Commander pattern for distributed transactions when:

* You need to ensure data consistency across distributed services in the event of partial system failures.
* Transactions span multiple microservices or distributed components requiring coordinated commit or rollback.
* You are implementing long-lived transactions requiring compensating actions for rollback.

## Known Uses

* Two-Phase Commit (2PC) Protocols: Coordinating commit or rollback across distributed databases or services.
* Saga Pattern Implementations: Managing long-lived business processes that span multiple microservices, with each step having a compensating action for rollback.
* Distributed Transactions in Microservices Architecture: Coordinating complex operations across microservices while maintaining data integrity and consistency.

## Consequences

Benefits:

* Provides a clear mechanism for managing complex distributed transactions, enhancing system reliability.
* Enables the implementation of compensating transactions, which are crucial for maintaining consistency in long-lived transactions.
* Facilitates the integration of heterogeneous systems within a transactional context.

Trade-offs:

* Increases complexity, especially in failure scenarios, due to the need for coordinated rollback mechanisms.
* Potentially impacts performance due to the overhead of coordination and consistency checks.
* Saga-based implementations can lead to increased complexity in understanding the overall business process flow.

## Related Patterns

* [Saga Pattern](https://java-design-patterns.com/patterns/saga/): Often discussed in tandem with the Commander pattern for distributed transactions, focusing on long-lived transactions with compensating actions.

## Credits

* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/4aATcRe)
* [Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems](https://amzn.to/4axHwOV)
* [Microservices Patterns: With examples in Java](https://amzn.to/4axjnYW)
* [Distributed Transactions: The Icebergs of Microservices (Graham Lea)](https://www.grahamlea.com/2016/08/distributed-transactions-microservices-icebergs/)
