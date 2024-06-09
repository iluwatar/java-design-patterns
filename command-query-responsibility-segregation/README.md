---
title: "Command Query Responsibility Segregation in Java: Optimizing Data Interaction for Scalability"
shortTitle: Command Query Responsibility Segregation (CQRS)
description: "Learn about the Command Query Responsibility Segregation (CQRS) pattern in Java. Discover how segregating commands and queries can enhance the scalability, performance, and maintainability of your software systems."
category: Architectural
language: en
tag:
  - Event-driven
  - Performance
  - Scalability
---

## Also known as

* CQRS

## Intent of Command Query Responsibility Segregation Design Pattern

Command Query Responsibility Segregation (CQRS) aims to segregate the operations that modify the state of an application (commands) from the operations that read the state (queries). This separation enhances scalability, performance, and maintainability in complex software systems.

## Detailed Explanation of Command Query Responsibility Segregation Pattern with Real-World Examples

Real-world example

> Imagine a modern library where the tasks of borrowing and returning books (commands) are handled at the front desk, while the task of searching for books and reading them (queries) happens in the reading area. The front desk optimizes for transaction efficiency and record-keeping, ensuring books are properly checked in and out. Meanwhile, the reading area is optimized for comfort and accessibility, making it easy for readers to find and engage with the books. This separation improves the library's overall efficiency and user experience, much like the CQRS pattern enhances a software system's performance and maintainability.

In plain words

> The CQRS design pattern separates the actions of modifying data (commands) from the actions of retrieving data (queries) to enhance performance, scalability, and maintainability in software systems. By implementing CQRS, you can optimize your system's read and write operations independently, allowing for more efficient data handling and improved overall system performance.

Microsoft's documentation says

> CQRS separates reads and writes into different models, using commands to update data, and queries to read data.

## Programmatic Example of CQRS Pattern in Java

One way to implement the Command Query Responsibility Segregation (CQRS) pattern is to separate the read and write operations into different services.

Let's see the code implementation first and explain how it works afterward.

```java
public static void main(String[] args) {

    // Create Authors and Books using CommandService
    var commands = new CommandServiceImpl();

    commands.authorCreated(AppConstants.E_EVANS, "Eric Evans", "evans@email.com");
    commands.authorCreated(AppConstants.J_BLOCH, "Joshua Bloch", "jBloch@email.com");
    commands.authorCreated(AppConstants.M_FOWLER, "Martin Fowler", "mFowler@email.com");

    commands.bookAddedToAuthor("Domain-Driven Design", 60.08, AppConstants.E_EVANS);
    commands.bookAddedToAuthor("Effective Java", 40.54, AppConstants.J_BLOCH);
    commands.bookAddedToAuthor("Java Puzzlers", 39.99, AppConstants.J_BLOCH);
    commands.bookAddedToAuthor("Java Concurrency in Practice", 29.40, AppConstants.J_BLOCH);
    commands.bookAddedToAuthor("Patterns of Enterprise"
            + " Application Architecture", 54.01, AppConstants.M_FOWLER);
    commands.bookAddedToAuthor("Domain Specific Languages", 48.89, AppConstants.M_FOWLER);
    commands.authorNameUpdated(AppConstants.E_EVANS, "Eric J. Evans");

    // Query the database using QueryService
    var queries = new QueryServiceImpl();

    var nullAuthor = queries.getAuthorByUsername("username");
    var evans = queries.getAuthorByUsername(AppConstants.E_EVANS);
    var blochBooksCount = queries.getAuthorBooksCount(AppConstants.J_BLOCH);
    var authorsCount = queries.getAuthorsCount();
    var dddBook = queries.getBook("Domain-Driven Design");
    var blochBooks = queries.getAuthorBooks(AppConstants.J_BLOCH);

    LOGGER.info("Author username : {}", nullAuthor);
    LOGGER.info("Author evans : {}", evans);
    LOGGER.info("jBloch number of books : {}", blochBooksCount);
    LOGGER.info("Number of authors : {}", authorsCount);
    LOGGER.info("DDD book : {}", dddBook);
    LOGGER.info("jBloch books : {}", blochBooks);

    HibernateUtil.getSessionFactory().close();
}
```

1. Command Service: The `CommandServiceImpl` class is used for write operations. It provides methods to create authors and books, and to add books to authors.

2. Query Service: The `QueryServiceImpl` class is used for read operations. It provides methods to get author and book details.

This separation of concerns allows for flexibility in how the application handles data access and manipulation, and is a key aspect of the CQRS pattern.

Program output:

```
17:37:56.040 [main] INFO  com.iluwatar.cqrs.app.App - Author username : null
17:37:56.040 [main] INFO  com.iluwatar.cqrs.app.App - Author evans : Author(name=Eric J. Evans, email=evans@email.com, username=eEvans)
17:37:56.041 [main] INFO  com.iluwatar.cqrs.app.App - jBloch number of books : 3
17:37:56.041 [main] INFO  com.iluwatar.cqrs.app.App - Number of authors : 3
17:37:56.041 [main] INFO  com.iluwatar.cqrs.app.App - DDD book : Book(title=Domain-Driven Design, price=60.08)
17:37:56.042 [main] INFO  com.iluwatar.cqrs.app.App - jBloch books : [Book(title=Effective Java, price=40.54), Book(title=Java Puzzlers, price=39.99), Book(title=Java Concurrency in Practice, price=29.4)]
```

## When to Use the Command Query Responsibility Segregation Pattern in Java

* Systems requiring distinct models for read and write operations for scalability and maintainability, such as e-commerce platforms and high-traffic websites.
* Complex domain models, like financial services or healthcare applications, where the task of updating objects differs significantly from the task of reading object data.
* Scenarios where performance optimization for read operations is crucial, and the system can benefit from different data models or databases for reads and writes, enhancing data retrieval speed and accuracy.

## Real-World Applications of CQRS Pattern in Java

* Distributed Systems and Microservices Architecture, where different services manage read and write responsibilities.
* Event-Sourced Systems, where changes to the application state are stored as a sequence of events.
* High-Performance Web Applications, segregating read and write databases to optimize load handling.

## Benefits and Trade-offs of Command Query Responsibility Segregation Pattern

Benefits:

* Scalability: By separating read and write models, each can be scaled independently according to their specific demands.
* Optimization: Allows for the optimization of read models for query efficiency and write models for transactional integrity.
* Maintainability: Reduces complexity by separating the concerns, leading to cleaner, more maintainable code.
* Flexibility: Offers the flexibility to choose different technologies for the read and write sides according to their requirements.

Trade-Offs:

* Complexity: Introduces complexity due to synchronization between read and write models, especially in consistency maintenance.
* Overhead: Might be an overkill for simple systems where the benefits do not outweigh the additional complexity.
* Learning Curve: Requires a deeper understanding and careful design to implement effectively, increasing the initial learning curve.

## Related Java Design Patterns

* [Event Sourcing](https://java-design-patterns.com/patterns/event-sourcing/): Often used in conjunction with CQRS, where changes to the application state are stored as a sequence of events.
* Domain-Driven Design (DDD): CQRS fits well within the DDD context, providing clear boundaries and separation of concerns.
* [Repository](https://java-design-patterns.com/patterns/repository/): Can be used to abstract the data layer, providing a more seamless integration between the command and query sides.

## References and Credits

* [Implementing Domain-Driven Design](https://amzn.to/3TJN2HH)
* [Microsoft .NET: Architecting Applications for the Enterprise](https://amzn.to/4aktRes)
* [Patterns, Principles, and Practices of Domain-Driven Design](https://amzn.to/3vNV4Hm)
* [CQRS, Task Based UIs, Event Sourcing agh! (Greg Young)](http://codebetter.com/gregyoung/2010/02/16/cqrs-task-based-uis-event-sourcing-agh/)
* [CQRS (Martin Fowler)](https://martinfowler.com/bliki/CQRS.html)
* [CQRS for Great Good (Oliver Wolf)](https://www.youtube.com/watch?v=Ge53swja9Dw)
* [CQRS pattern (Microsoft)](https://docs.microsoft.com/en-us/azure/architecture/patterns/cqrs)
