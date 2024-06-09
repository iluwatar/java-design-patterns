---
title: "Half-Sync/Half-Async Pattern in Java: Enhancing System Performance with Dual Processing"
shortTitle: Half-Sync/Half-Async
description: "Learn how the Half-Sync/Half-Async design pattern in Java improves concurrency and system efficiency by decoupling asynchronous and synchronous processing. Explore real-world examples, programmatic implementations, and key use cases."
category: Concurrency
language: en
tag:
  - Asynchronous
  - Decoupling
  - Synchronization
  - Thread management
---

## Also known as

* Async-Sync Bridge
* Half-Synchronous/Half-Asynchronous

## Intent of Half-Sync/Half-Async Design Pattern

The Half-Sync/Half-Async pattern in Java aims to decouple asynchronous and synchronous processing in concurrent systems, enhancing efficiency and performance. This pattern is particularly useful for managing complex concurrent operations in software systems.

## Detailed Explanation of Half-Sync/Half-Async Pattern with Real-World Examples

Real-world example

> Imagine a busy restaurant kitchen where order taking is asynchronous, allowing waiters to keep working while chefs cook each dish synchronously. Similarly, the Half-Sync/Half-Async pattern handles multiple asynchronous tasks and synchronous processing in Java applications efficiently. Meanwhile, the cooking (synchronous part) follows a specific sequence and requires waiting for each dish to be prepared before starting the next. This setup enables the restaurant to handle multiple customer orders efficiently, while ensuring each dish is cooked with the required attention and timing, much like the Half-Sync/Half-Async pattern manages asynchronous tasks and synchronous processing in software systems.

In plain words

> The Half-Sync/Half-Async pattern separates operations into asynchronous tasks that handle events without waiting, and synchronous tasks that process these events in an orderly and blocking manner.

Wikipedia says

> The Half-Sync/Half-Async design pattern is used to solve situations where one part of the application runs synchronously while another runs asynchronously, and the two modules need to communicate with each other.

## Programmatic Example of Half-Sync/Half-Async Pattern in Java

The Half-Sync/Half-Async design pattern is a concurrency pattern that separates synchronous and asynchronous processing in a system, simplifying the programming model without affecting performance. It's particularly useful in scenarios where you have a mix of short, mid, and long duration tasks.

In the provided Java implementation, we can see an example of the Half-Sync/Half-Async pattern in the `App`, `AsynchronousService`, and `ArithmeticSumTask` classes.

The `App` class is the entry point of the application. It creates an instance of `AsynchronousService` and uses it to handle various tasks asynchronously.

```java
public class App {

  public static void main(String[] args) {
    var service = new AsynchronousService(new LinkedBlockingQueue<>());
    service.execute(new ArithmeticSumTask(1000));
    service.execute(new ArithmeticSumTask(500));
    service.execute(new ArithmeticSumTask(2000));
    service.execute(new ArithmeticSumTask(1));
    service.close();
  }
}
```

The `AsynchronousService` class is the asynchronous part of the system. It manages a queue of tasks and processes them in a separate thread.

```java
public class AsynchronousService {
  // Implementation details...
}
```

The `ArithmeticSumTask` class represents a task that can be processed asynchronously. It implements the `AsyncTask` interface, which defines methods for pre-processing, post-processing, and error handling.

```java
static class ArithmeticSumTask implements AsyncTask<Long> {
  private final long numberOfElements;

  public ArithmeticSumTask(long numberOfElements) {
    this.numberOfElements = numberOfElements;
  }

  @Override
  public Long call() throws Exception {
    return ap(numberOfElements);
  }

  @Override
  public void onPreCall() {
    if (numberOfElements < 0) {
      throw new IllegalArgumentException("n is less than 0");
    }
  }

  @Override
  public void onPostCall(Long result) {
    LOGGER.info(result.toString());
  }

  @Override
  public void onError(Throwable throwable) {
    throw new IllegalStateException("Should not occur");
  }
}
```

This is the `main` function in the `App` class.

```java
public static void main(String[] args) {
    
    var service = new AsynchronousService(new LinkedBlockingQueue<>());

    service.execute(new ArithmeticSumTask(1000));

    service.execute(new ArithmeticSumTask(500));
    service.execute(new ArithmeticSumTask(2000));
    service.execute(new ArithmeticSumTask(1));

    service.close();
}
```

In this example, the `App` class enqueues tasks to the `AsynchronousService`, which processes them asynchronously. The `ArithmeticSumTask` class defines the task to be processed, including pre-processing, the actual processing, and post-processing steps. 

Running the code produces:

```
10:56:33.922 [pool-1-thread-4] INFO com.iluwatar.halfsynchalfasync.App -- 1
10:56:34.425 [pool-1-thread-2] INFO com.iluwatar.halfsynchalfasync.App -- 125250
10:56:34.925 [pool-1-thread-1] INFO com.iluwatar.halfsynchalfasync.App -- 500500
10:56:35.925 [pool-1-thread-3] INFO com.iluwatar.halfsynchalfasync.App -- 2001000
```

This is a basic example of the Half-Sync/Half-Async pattern, where tasks are enqueued and processed asynchronously, while the main thread continues to handle other tasks.

## When to Use the Half-Sync/Half-Async Pattern in Java

Use the Half-Sync/Half-Async pattern in scenarios where:

* High performance and efficient concurrency are crucial, such as in Java's standard libraries and network servers managing concurrent connections.
* The system needs to effectively utilize multicore architectures to balance tasks between asynchronous and synchronous processing.
* Decoupling of asynchronous tasks from synchronous processing is necessary to simplify the design and implementation.

## Real-World Applications of Half-Sync/Half-Async Pattern in Java

* The Half-Sync/Half-Async pattern is utilized in various frameworks and systems, including BSD Unix networking, Real-Time CORBA, and Android's AsyncTask framework.
* Java's standard libraries utilize this pattern with thread pools and execution queues in the concurrency utilities (e.g., java.util.concurrent).
* Network servers handling concurrent connections where IO operations are handled asynchronously and processing of requests is done synchronously.

## Benefits and Trade-offs of Half-Sync/Half-Async Pattern

Benefits:

* This pattern improves system responsiveness and throughput by isolating blocking operations from non-blocking ones, making it a valuable design pattern in Java concurrency.
* Simplifies programming model by isolating asynchronous and synchronous processing layers.

Trade-offs:

* Adds complexity in managing two different processing modes.
* Requires careful design to avoid bottlenecks between the synchronous and asynchronous parts.

## Related Java Design Patterns

* [Leader/Followers](https://java-design-patterns.com/patterns/leader-followers/): Both patterns manage thread assignments and concurrency, but Leader/Followers uses a single thread to handle all I/O events, dispatching work to others.
* [Producer/Consumer](https://java-design-patterns.com/patterns/producer-consumer/): Can be integrated with Half-Sync/Half-Async to manage work queues between the async and sync parts.
* [Reactor](https://java-design-patterns.com/patterns/reactor/): Often used with Half-Sync/Half-Async to handle multiple service requests delivered to a service handler without blocking the handler.

## References and Credits

* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Pattern-Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://amzn.to/3UgC24V)
* [Half-Sync/Half-Async (Douglas C. Schmidt and Charles D. Cranor)](https://www.dre.vanderbilt.edu/~schmidt/PDF/PLoP-95.pdf)
