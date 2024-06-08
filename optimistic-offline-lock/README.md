---
title: "Optimistic Offline Lock Pattern in Java: Mastering Conflict Resolution in Database Transactions"
shortTitle: Optimistic Offline Lock
description: "Explore the Optimistic Offline Lock design pattern in Java with detailed implementation guidelines and practical examples. Learn how to manage data concurrency effectively in your Java applications."
category: Data access
language: en
tag:
  - Concurrency
  - Data access
  - Fault tolerance
  - Isolation
  - Persistence
  - Transactions
---

## Also known as

* Optimistic Concurrency Control

## Intent of Optimistic Offline Lock Design Pattern

The Optimistic Offline Lock pattern in Java is specifically designed to manage concurrent data modifications without the need for long-duration database locks, thus enhancing system performance and scalability.

## Detailed Explanation of Optimistic Offline Lock Pattern with Real-World Examples

Real-world example

> Imagine a library with multiple users checking out and returning books. Instead of locking each book while a user is browsing or deciding whether to borrow it, the library uses an optimistic approach. Each book has a timestamp or version number. When a user decides to borrow a book, they check the book's version number. If it matches the current version, the transaction proceeds. If another user has borrowed the book in the meantime, causing a version mismatch, the first user is informed to retry. This approach allows multiple users to browse and attempt to borrow books concurrently, improving the library's efficiency and user satisfaction without locking the entire catalog.

In plain words

> The Optimistic Offline Lock pattern manages concurrent data modifications by allowing transactions to proceed without locks, resolving conflicts only when they occur to enhance performance and scalability.

Wikipedia says

> Optimistic concurrency control (OCC), also known as optimistic locking, is a concurrency control method applied to transactional systems such as relational database management systems and software transactional memory.

## Programmatic Example of Optimistic Offline Lock Pattern in Java

In this section, we delve into the practical implementation of the Optimistic Offline Lock in Java. By following these steps, you can ensure that your application handles data conflicts and concurrency with minimal overhead.

The Optimistic Offline Lock pattern is a concurrency control method that allows multiple transactions to proceed without locks, resolving conflicts only when they occur. This pattern is useful in scenarios where the likelihood of conflicting transactions is low and long-duration locks could hamper performance and scalability.

First, we have a `Card` entity that represents a bank card with a sum of money and a version number.

```java

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    private long id;

    private long personId;

    private float sum;

    private int version;
}
```

The `CardUpdateService` class implements the `UpdateService` interface and provides a method `doUpdate` to update the Card entity. The `doUpdate` method first retrieves the current version of the `Card` entity. It then performs some business logic to update the sum of money in the `Card`. Before updating the `Card` in the database, it checks if the version of the `Card` in the database is the same as the initial version it retrieved. If the versions match, it proceeds with the update. If the versions do not match, it means that another transaction has updated the `Card` in the meantime, and it throws an `ApplicationException`.

```java

@RequiredArgsConstructor
public class CardUpdateService implements UpdateService<Card> {

    private final JpaRepository<Card> cardJpaRepository;

    @Override
    @Transactional(rollbackFor = ApplicationException.class) //will roll back transaction in case ApplicationException
    public Card doUpdate(Card card, long cardId) {
        float additionalSum = card.getSum();
        Card cardToUpdate = cardJpaRepository.findById(cardId);
        int initialVersion = cardToUpdate.getVersion();
        float resultSum = cardToUpdate.getSum() + additionalSum;
        cardToUpdate.setSum(resultSum);
        //Maybe more complex business-logic e.g. HTTP-requests and so on

        if (initialVersion != cardJpaRepository.getEntityVersionById(cardId)) {
            String exMessage = String.format("Entity with id %s were updated in another transaction", cardId);
            throw new ApplicationException(exMessage);
        }

        cardJpaRepository.update(cardToUpdate);
        return cardToUpdate;
    }
}
```

In this code snippet, the doUpdate method in the CardUpdateService class is a programmatic example of the Optimistic Offline Lock pattern. It allows the Card entity to be updated without locks and resolves conflicts by checking the version of the Card before the update.

## When to Use the Optimistic Offline Lock Pattern in Java

* When multiple transactions need to access and modify the same data simultaneously without causing data inconsistencies.
* In systems where the likelihood of conflicting transactions is low.
* When you want to avoid long-duration locks that could hamper performance and scalability.

## Optimistic Offline Lock Pattern Java Tutorials

* [Offline Concurrency Control (Baeldung)](https://www.baeldung.com/cs/offline-concurrency-control)
* [Optimistic Locking in JPA (Baeldung)](https://www.baeldung.com/jpa-optimistic-locking)

## Real-World Applications of Optimistic Offline Lock Pattern in Java

* Web-based applications with high-read, low-write access patterns.
* Distributed systems where locking resources for long durations is not feasible.
* Java enterprise applications using JPA or Hibernate for data persistence.

## Benefits and Trade-offs of Optimistic Offline Lock Pattern

Benefits:

* Reduces the need for locking resources, which improves performance.
* Increases system scalability by allowing more transactions to proceed concurrently.
* Simplifies transaction management by handling conflicts only when they occur.

Trade-offs:

* Requires additional logic (versioning, rollback/retry) to handle conflicts, which can complicate the application code.
* Can lead to more frequent retries of transactions if conflicts are common.
* Not suitable for high-conflict scenarios where frequent data modification collisions occur.

## Related Java Design Patterns

* Pessimistic Offline Lock: Unlike the Optimistic Offline Lock, this pattern uses locks to prevent conflicts by locking the data during the entire transaction. It is useful in high-conflict scenarios.
* [Unit of Work](https://java-design-patterns.com/patterns/unit-of-work/): Helps in managing a set of changes as a single transaction, ensuring data integrity. It can be used in conjunction with Optimistic Offline Lock to handle complex transactions.
* [Version Number](https://java-design-patterns.com/patterns/version-number/): A common technique used in Optimistic Offline Lock to detect conflicts by maintaining a version number for each data entity.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Java Persistence with Hibernate](https://amzn.to/44tP1ox)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Optimistic Offline Lock (Martin Fowler)](https://martinfowler.com/eaaCatalog/optimisticOfflineLock.html)
