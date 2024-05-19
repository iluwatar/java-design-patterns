---
title: Saga
category: Resilience
language: en
tag:
    - Asynchronous
    - Decoupling
    - Fault tolerance
    - Integration
    - Microservices
    - Transactions
---

## Intent

To manage and coordinate distributed transactions across multiple services in a fault-tolerant and reliable manner.

## Explanation

Real-world example

> Imagine a travel agency coordinating a vacation package for a customer. The package includes booking a flight, reserving a hotel room, and renting a car. Each of these bookings is managed by a different service provider. If the flight booking is successful but the hotel is fully booked, the agency needs to cancel the flight and notify the customer. This ensures that the customer does not end up with only a partial vacation package. In the Saga pattern, this scenario is managed by a series of coordinated transactions, with compensating actions (like canceling the flight) to maintain consistency.

In plain words

> The Saga pattern coordinates distributed transactions across microservices using a sequence of events and compensating actions to ensure data consistency and fault tolerance.

Wikipedia says

> Long-running transactions (also known as the saga interaction pattern) are computer database transactions that avoid locks on non-local resources, use compensation to handle failures, potentially aggregate smaller ACID transactions (also referred to as atomic transactions), and typically use a coordinator to complete or abort the transaction. In contrast to rollback in ACID transactions, compensation restores the original state, or an equivalent, and is business-specific. For example, the compensating action for making a hotel reservation is canceling that reservation.

**Programmatic Example**

The Saga design pattern is a sequence of local transactions where each transaction updates data within a single service. The Saga pattern is a way to manage transactions and it's particularly useful in microservices architecture where each service has its own database.

The Saga pattern is implemented in the `Saga` class. A Saga is a sequence of chapters, each representing a local transaction. The `Saga` class provides methods to add chapters and to check if a chapter is present.

```java
public class Saga {

    private final List<Chapter> chapters;

    private Saga() {
        this.chapters = new ArrayList<>();
    }

    public Saga chapter(String name) {
        this.chapters.add(new Chapter(name));
        return this;
    }

    public Chapter get(int idx) {
        return chapters.get(idx);
    }

    public boolean isPresent(int idx) {
        return idx >= 0 && idx < chapters.size();
    }

    public static Saga create() {
        return new Saga();
    }

    public static class Chapter {
        String name;

        public Chapter(String name) {
            this.name = name;
        }
    }
}
```

**Explanation:**

The `WithdrawMoneyService` class represents a service that performs a local transaction. It extends the `Service` class and overrides the `process` method to perform the transaction. If the transaction fails, it sets the status of the saga to `ROLLBACK`.

```java
public class WithdrawMoneyService extends Service<String> {
    @Override
    public String getName() {
        return "withdrawing Money";
    }

    @Override
    public ChapterResult<String> process(String value) {
        if (value.equals("bad_order") || value.equals("crashed_order")) {
            LOGGER.info("The chapter '{}' has been started. But the exception has been raised."
                            + "The rollback is about to start",
                    getName());
            return ChapterResult.failure(value);
        }
        return super.process(value);
    }
}
```

In a real-world application, the `Service` class would contain the logic to perform the local transaction and handle failures. The `Saga` class would manage the sequence of local transactions, ensuring that each transaction is performed in the correct order and that the saga is rolled back if a transaction fails.

## Class diagram

![Saga](./etc/saga.urm.png "Saga pattern class diagram")

## Applicability

* When you have a complex transaction that spans multiple microservices.
* When you need to ensure data consistency across services without using a traditional two-phase commit.
* When you need to handle long-running transactions in an asynchronous manner.

## Known Uses

* E-commerce platforms managing orders, inventory, and payment services.
* Banking systems coordinating between account debits and credits across multiple services.
* Travel booking systems coordinating flights, hotels, and car rentals.

## Consequences

Benefits:

* Improved fault tolerance and reliability.
* Scalability due to decoupled services.
* Flexibility in handling long-running transactions.

Trade-offs:

* Increased complexity in handling compensating transactions.
* Requires careful design to handle partial failures and rollback scenarios.
* Potential latency due to asynchronous nature.

## Related Patterns

* [Event Sourcing](https://java-design-patterns.com/patterns/event-sourcing/): Used to capture state changes as a sequence of events, which can complement the Saga pattern by providing a history of state changes.
* [Command Query Responsibility Segregation (CQRS)](https://java-design-patterns.com/patterns/cqrs/): Can be used in conjunction with the Saga pattern to separate command and query responsibilities, improving scalability and fault tolerance.

## Credits

* [Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems](https://amzn.to/3y6yv1z)
* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/3WcFVui)
* [Microservices Patterns: With examples in Java](https://amzn.to/3UyWD5O)
* [Saga pattern - microservices.io](https://microservices.io/patterns/data/saga.html)
* [Saga distributed transactions pattern - Microsoft](https://docs.microsoft.com/en-us/azure/architecture/reference-architectures/saga/saga)
