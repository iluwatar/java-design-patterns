---
title: "Commander Pattern in Java: Orchestrating Complex Commands with Ease"
shortTitle: Commander
description: "Learn about the Commander design pattern in Java, a powerful approach for managing distributed transactions across multiple services. Ensure data consistency and reliability in your microservices architecture with practical examples and use cases."
category: Behavioral
language: en
tag:
  - Cloud distributed
  - Microservices
  - Transactions
head:
  - - meta
    - name: keywords
      content:
---

## Also known as

* Distributed Transaction Commander
* Transaction Coordinator

## Intent of Commander Design Pattern

The intent of the Commander pattern in Java, especially in the context of distributed transactions, is to manage and coordinate complex transactions across multiple distributed components or services. This pattern ensures data consistency and integrity in distributed systems, making it crucial for microservices architecture. It encapsulates transaction commands and coordination logic, facilitating the implementation of distributed transaction protocols like two-phase commit or Saga.

## Detailed Explanation of Commander Pattern with Real-World Examples

Real-world example

> Imagine organizing a large international music festival where various bands from around the world are scheduled to perform. Each band's arrival, soundcheck, and performance are akin to individual transactions in a distributed system. This scenario mirrors the Commander pattern in Java, where the "Commander" coordinates distributed transactions to maintain overall consistency and reliability. The festival organizer acts as the "Commander," coordinating these transactions to ensure that if a band's flight is delayed (akin to a transaction failure), there's a backup plan, such as rescheduling or swapping time slots with another band (compensating actions), to keep the overall schedule intact. This setup mirrors the Commander pattern in distributed transactions, where various components must be coordinated to achieve a successful outcome despite individual failures.

In plain words

> The Commander pattern turns a request into a stand-alone object, allowing for the parameterization of commands, queueing of actions, and the implementation of undo operations.

## Programmatic Example of Commander Pattern in Java

Managing transactions across different services in a distributed system, such as an e-commerce platform with separate `Payment` and `Shipping` microservices, requires careful coordination. Using the Commander pattern in Java for transaction coordination helps ensure data consistency and reliability, even when services experience partial failures.

A strategy to address this involves using a `Commander` component that orchestrates the process. Initially, the order is processed by the available service (`Shipping` in this case). The `Commander` then attempts to synchronize the order with the currently unavailable service (`Payment`) by storing the order details in a database or queueing it for future processing. This queueing system must also account for possible failures in adding requests to the queue.

The `Commander` repeatedly tries to process the queued orders to ensure both services eventually reflect the same transaction data. This process involves ensuring idempotence, meaning that even if the same order synchronization request is made multiple times, it will only be executed once, preventing duplicate transactions. The goal is to achieve eventual consistency across services, where all systems are synchronized over time despite initial failures or delays.

Here's a simplified example of how the `Commander` class is used in the `AppAllCases` class:

```java
public class AppAllCases {
  // ... other methods ...

  // Shipping Database Fail Cases
  void itemUnavailableCase() {
    var ps = new PaymentService(new PaymentDatabase());
    var ss = new ShippingService(new ShippingDatabase(), new ItemUnavailableException());
    var ms = new MessagingService(new MessagingDatabase());
    var eh = new EmployeeHandle(new EmployeeDatabase());
    var qdb = new QueueDatabase();
    // Create a Commander instance
    var c = new Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits);
    var user = new User("Jim", "ABCD");
    var order = new Order(user, "book", 10f);
    // Use the Commander instance to place an order
    c.placeOrder(order);
  }

  // ... other methods ...
}
```

In the `itemUnavailableCase` method, a `Commander` instance is created with the respective services and their databases. Then, a `User` and an `Order` are created, and the `placeOrder` method of the `Commander` instance is called with the order. This triggers the process of placing the order and handling any failures according to the Commander pattern.

The `Commander` class encapsulates the logic for handling the order placement and any potential failures. This separation of concerns makes the code easier to understand and maintain, and it allows for the reuse of the `Commander` class in different parts of the application.  In a real-world application, the `Commander` class would be more complex and would include additional logic for handling different types of failures, retrying failed operations, and coordinating transactions across multiple services.

Here is the output from executing the `itemUnavailableCase`:

```
09:10:13.894 [main] DEBUG com.iluwatar.commander.Commander -- Order YN3V8B7IL2PI: Error in creating shipping request..
09:10:13.896 [main] INFO com.iluwatar.commander.Commander -- This item is currently unavailable. We will inform you as soon as the item becomes available again.
09:10:13.896 [main] INFO com.iluwatar.commander.Commander -- Order YN3V8B7IL2PI: Item book unavailable, trying to add problem to employee handle..
09:10:13.897 [Thread-0] INFO com.iluwatar.commander.Commander -- Order YN3V8B7IL2PI: Added order to employee database
```

## When to Use the Commander Pattern in Java

Use the Commander pattern in Java for distributed transactions when:

* You need to ensure data consistency across distributed services in the event of partial system failures.
* Transactions span multiple microservices or distributed components requiring coordinated commit or rollback.
* You are implementing long-lived transactions requiring compensating actions for rollback.

## Real-World Applications of Commander Pattern in Java

* Two-Phase Commit (2PC) Protocols: Coordinating commit or rollback across distributed databases or services.
* Saga Pattern Implementations: Managing long-lived business processes that span multiple microservices, with each step having a compensating action for rollback.
* Distributed Transactions in Microservices Architecture: Coordinating complex operations across microservices while maintaining data integrity and consistency.

## Benefits and Trade-offs of Commander Pattern

Benefits:

* Provides a clear mechanism for managing complex distributed transactions, enhancing system reliability.
* Enables the implementation of compensating transactions, which are crucial for maintaining consistency in long-lived transactions.
* Facilitates the integration of heterogeneous systems within a transactional context.

Trade-offs:

* Increases complexity, especially in failure scenarios, due to the need for coordinated rollback mechanisms.
* Potentially impacts performance due to the overhead of coordination and consistency checks.
* Saga-based implementations can lead to increased complexity in understanding the overall business process flow.

## Related Java Design Patterns

* [Saga Pattern](https://java-design-patterns.com/patterns/saga/): Often discussed in tandem with the Commander pattern for distributed transactions, focusing on long-lived transactions with compensating actions.

## References and Credits

* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/4aATcRe)
* [Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems](https://amzn.to/4axHwOV)
* [Microservices Patterns: With examples in Java](https://amzn.to/4axjnYW)
* [Distributed Transactions: The Icebergs of Microservices (Graham Lea)](https://www.grahamlea.com/2016/08/distributed-transactions-microservices-icebergs/)
