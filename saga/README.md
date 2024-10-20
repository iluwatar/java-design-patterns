---
title: "Saga Pattern in Java: Mastering Long-Running Transactions in Distributed Systems"
shortTitle: Saga
description: "Explore the Saga pattern in Java for managing distributed transactions across microservices with resilience and fault tolerance. Learn how the Saga pattern ensures data consistency without locking resources."
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

## Intent of Saga Design Pattern

To manage and coordinate distributed transactions across multiple services in a fault-tolerant and reliable manner.

## Detailed Explanation of Saga Pattern with Real-World Examples

Real-world example

> Imagine a travel agency coordinating a vacation package for a customer. The package includes booking a flight, reserving a hotel room, and renting a car. Each of these bookings is managed by a different service provider. If the flight booking is successful but the hotel is fully booked, the agency needs to cancel the flight and notify the customer. This ensures that the customer does not end up with only a partial vacation package. In the Saga pattern, this scenario is managed by a series of coordinated transactions, with compensating actions (like canceling the flight) to maintain consistency.

In plain words

> The Saga pattern in Java coordinates distributed transactions across microservices using a sequence of events and compensating actions to ensure data consistency and fault tolerance.

Wikipedia says

> Long-running transactions (also known as the saga interaction pattern) are computer database transactions that avoid locks on non-local resources, use compensation to handle failures, potentially aggregate smaller ACID transactions (also referred to as atomic transactions), and typically use a coordinator to complete or abort the transaction. In contrast to rollback in ACID transactions, compensation restores the original state, or an equivalent, and is business-specific. For example, the compensating action for making a hotel reservation is canceling that reservation.

## Programmatic Example of Saga Pattern in Java

The Saga design pattern is a sequence of local transactions where each transaction updates data within a single service. It's particularly useful in a microservices architecture where each service has its own database. The Saga pattern ensures data consistency and fault tolerance across services. Here are the key components of the Saga pattern:

1. **Saga**: A Saga is a sequence of local transactions, each of which is called a chapter. The Saga manages the sequence of these transactions, ensuring that each transaction is performed in the correct order and that the Saga is rolled back if a transaction fails.

2. **Chapter**: Each chapter in a Saga represents a local transaction. A chapter has a name, a result (which can be `INIT`, `SUCCESS`, or `ROLLBACK`), and an input value. The `Chapter` class provides methods to get and set these properties.

3. **Service**: A service performs a local transaction. It processes the input value of a chapter and returns a `ChapterResult`. If the transaction fails, it sets the status of the chapter to `ROLLBACK`.

4. **Service Discovery**: This component is responsible for discovering available services and executing the Saga. It processes each chapter in the Saga in order. If a chapter fails, the Saga will be rolled back.

5. **Saga Result**: The result of a Saga can be `PROGRESS`, `FINISHED`, or `ROLLBACKED`. This is determined by the `getResult` method of the `Saga` class.

In a real-world application, the `Service` class would contain the logic to perform the local transaction and handle failures. The `Saga` class would manage the sequence of local transactions, ensuring that each transaction is performed in the correct order and that the Saga is rolled back if a transaction fails.

**Snippet 1: Creating a Saga**

The first step in using the Saga pattern is to create a Saga. A Saga is a sequence of chapters, each representing a local transaction. The `Saga` class provides methods to add chapters and to check if a chapter is present.

```java
// Create a new Saga
Saga saga = Saga.create();
```

**Snippet 2: Adding Chapters to the Saga**

Each chapter in a Saga represents a local transaction. We can add chapters to the Saga using the `chapter` method.

```java
// Add chapters to the Saga
saga.chapter("init an order");
saga.chapter("booking a Fly");
saga.chapter("booking a Hotel");
saga.chapter("withdrawing Money");
```

**Snippet 3: Setting Input Values for Chapters**

Each chapter in a Saga can have an input value. We can set the input value for the last added chapter using the `setInValue` method.

```java
// Set input values for the chapters
saga.chapter("init an order").setInValue("good_order");
```

**Snippet 4: Executing the Saga**

We can execute the Saga using a service. The service will process each chapter in the Saga in order. If a chapter fails, the Saga will be rolled back.

```java
// Execute the Saga
var service = sd.findAny();
var goodOrderSaga = service.execute(saga);
```

**Snippet 5: Checking the Result of the Saga**

We can check the result of the Saga using the `getResult` method. This method returns the result of the Saga, which can be `PROGRESS`, `FINISHED`, or `ROLLBACKED`.

```java
// Check the result of the Saga
SagaResult result = goodOrderSaga.getResult();
```

The `SagaApplication` class has a `main` method for running the example.

```java
@Slf4j
public class SagaApplication {

  public static void main(String[] args) {
    var sd = serviceDiscovery();
    var service = sd.findAny();
    var goodOrderSaga = service.execute(newSaga("good_order"));
    var badOrderSaga = service.execute(newSaga("bad_order"));
    LOGGER.info("orders: goodOrder is {}, badOrder is {}",
        goodOrderSaga.getResult(), badOrderSaga.getResult());
  }

  private static Saga newSaga(Object value) {
    return Saga
        .create()
        .chapter("init an order").setInValue(value)
        .chapter("booking a Fly")
        .chapter("booking a Hotel")
        .chapter("withdrawing Money");
  }

  private static ServiceDiscoveryService serviceDiscovery() {
    var sd = new ServiceDiscoveryService();
    return sd
        .discover(new OrderService(sd))
        .discover(new FlyBookingService(sd))
        .discover(new HotelBookingService(sd))
        .discover(new WithdrawMoneyService(sd));
  }
}
```

1. **Saga**: The `SagaApplication` creates a new Saga using the `Saga.create()` method. It then adds chapters to the Saga using the `chapter` method and sets the input value for each chapter using the `setInValue` method.
2. **Service**: The `SagaApplication` uses services to execute the chapters in the Saga. Each service represents a local transaction. The `SagaApplication` uses the `ServiceDiscoveryService` to discover available services and execute the Saga.
3. **Service Discovery**: The `ServiceDiscoveryService` is used to discover available services. The `SagaApplication` uses this to find a service and execute the Saga.
4. **Saga Execution**: The `SagaApplication` executes the Saga using the `execute` method of a service. It creates two Sagas, one for a good order and one for a bad order, and executes them.
5. **Saga Result**: The `SagaApplication` checks the result of the Saga using the `getResult` method. It logs the result of the good order Saga and the bad order Saga.

In summary, the `SagaApplication` creates a Saga, adds chapters to it, sets the input value for each chapter, discovers services, executes the Saga using a service, and checks the result of the Saga.

Running the example produces the following console output:

```
11:32:17.779 [main] INFO com.iluwatar.saga.choreography.Service -- The chapter 'init an order' has been started. The data good_order has been stored or calculated successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The chapter 'booking a Fly' has been started. The data good_order has been stored or calculated successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The chapter 'booking a Hotel' has been started. The data good_order has been stored or calculated successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The chapter 'withdrawing Money' has been started. The data good_order has been stored or calculated successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service --  the saga has been finished with FINISHED status
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The chapter 'init an order' has been started. The data bad_order has been stored or calculated successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The chapter 'booking a Fly' has been started. The data bad_order has been stored or calculated successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The chapter 'booking a Hotel' has been started. The data bad_order has been stored or calculated successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The chapter 'withdrawing Money' has been started. But the exception has been raised.The rollback is about to start
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The Rollback for a chapter 'booking a Hotel' has been started. The data bad_order has been rollbacked successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The Rollback for a chapter 'booking a Fly' has been started. The data bad_order has been rollbacked successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service -- The Rollback for a chapter 'init an order' has been started. The data bad_order has been rollbacked successfully
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.Service --  the saga has been finished with ROLLBACKED status
11:32:17.782 [main] INFO com.iluwatar.saga.choreography.SagaApplication -- orders: goodOrder is FINISHED, badOrder is ROLLBACKED
```

This is a basic example of how to use the Saga design pattern. In a real-world application, the `Saga` class would manage the sequence of local transactions, ensuring that each transaction is performed in the correct order and that the Saga is rolled back if a transaction fails.

## When to Use the Saga Pattern in Java

* When you have a complex transaction that spans multiple microservices.
* When you need to ensure data consistency across services without using a traditional two-phase commit.
* When you need to handle long-running transactions in an asynchronous manner.

## Real-World Applications of Saga Pattern in Java

* E-commerce platforms managing orders, inventory, and payment services.
* Banking systems coordinating between account debits and credits across multiple services.
* Travel booking systems coordinating flights, hotels, and car rentals.

## Benefits and Trade-offs of Saga Pattern

Benefits:

* Improved fault tolerance and reliability.
* Scalability due to decoupled services.
* Flexibility in handling long-running transactions.

Trade-offs:

* Increased complexity in handling compensating transactions.
* Requires careful design to handle partial failures and rollback scenarios.
* Potential latency due to asynchronous nature.

## Related Java Design Patterns

* [Event Sourcing](https://java-design-patterns.com/patterns/event-sourcing/): Used to capture state changes as a sequence of events, which can complement the Saga pattern by providing a history of state changes.
* [Command Query Responsibility Segregation (CQRS)](https://java-design-patterns.com/patterns/cqrs/): Can be used in conjunction with the Saga pattern to separate command and query responsibilities, improving scalability and fault tolerance.

## References and Credits

* [Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems](https://amzn.to/3y6yv1z)
* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/3WcFVui)
* [Microservices Patterns: With examples in Java](https://amzn.to/3UyWD5O)
* [Pattern: Saga (microservices.io)](https://microservices.io/patterns/data/saga.html)
* [Saga distributed transactions pattern (Microsoft)](https://docs.microsoft.com/en-us/azure/architecture/reference-architectures/saga/saga)
