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

Managing transactions across different services in a distributed system, such as an e-commerce platform with separate Payment and Shipping microservices, requires careful coordination to avoid issues. When a user places an order but one service (e.g., Payment) is unavailable while the other (e.g., Shipping) is ready, we need a robust solution to handle this discrepancy.

A strategy to address this involves using a Commander component that orchestrates the process. Initially, the order is processed by the available service (Shipping in this case). The commander then attempts to synchronize the order with the currently unavailable service (Payment) by storing the order details in a database or queueing it for future processing. This queueing system must also account for possible failures in adding requests to the queue.

The commander repeatedly tries to process the queued orders to ensure both services eventually reflect the same transaction data. This process involves ensuring idempotence, meaning that even if the same order synchronization request is made multiple times, it will only be executed once, preventing duplicate transactions. The goal is to achieve eventual consistency across services, where all systems are synchronized over time despite initial failures or delays.

In the provided code, the Commander pattern is used to handle distributed transactions across multiple services (PaymentService, ShippingService, MessagingService, EmployeeHandle). Each service has its own database and can throw exceptions to simulate failures.

The Commander class is the central part of this pattern. It takes instances of all services and their databases, along with some configuration parameters. The placeOrder method in the Commander class is used to place an order, which involves interacting with all the services.

```java
public class Commander {
    // ... constructor and other methods ...

    public void placeOrder(Order order) {
        // ... implementation ...
    }
}
```

The User and Order classes represent a user and an order respectively. An order is placed by a user.

```java
public class User {
    // ... constructor and other methods ...
}

public class Order {
    // ... constructor and other methods ...
}
```

Each service (e.g., PaymentService, ShippingService, MessagingService, EmployeeHandle) has its own database and can throw exceptions to simulate failures. For example, the PaymentService might throw a DatabaseUnavailableException if its database is unavailable.

```java
public class PaymentService {
    // ... constructor and other methods ...
}
```

The DatabaseUnavailableException, ItemUnavailableException, and ShippingNotPossibleException classes represent different types of exceptions that can occur.

```java
public class DatabaseUnavailableException extends Exception {
    // ... constructor and other methods ...
}

public class ItemUnavailableException extends Exception {
    // ... constructor and other methods ...
}

public class ShippingNotPossibleException extends Exception {
    // ... constructor and other methods ...
}
```

In the main method of each class (AppQueueFailCases, AppShippingFailCases), different scenarios are simulated by creating instances of the Commander class with different configurations and calling the placeOrder method.

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
