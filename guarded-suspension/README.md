---
title: "Guarded Suspension Pattern in Java: Ensuring Safe Concurrency in Critical Sections"
shortTitle: Guarded Suspension
description: "Learn about the Guarded Suspension design pattern in Java. Understand its implementation for efficient concurrency control, with real-world examples and code snippets."
category: Concurrency
language: en
tag:
  - Asynchronous
  - Decoupling
  - Resource management
  - Synchronization
  - Thread management
---

## Also known as

* Conditional Block
* Suspended Execution

## Intent of Guarded Suspension Design Pattern

The Guarded Suspension pattern is crucial in Java design patterns for managing operations that require both a lock and a condition to proceed. It optimizes concurrency control by allowing a thread to wait for the right condition efficiently.

## Detailed Explanation of Guarded Suspension Pattern with Real-World Examples

Real-world example

> A practical example of the Guarded Suspension pattern can be seen in a ride-sharing service. In this system, passengers wait for a car to become available, ensuring efficient resource use without continuous checking. When a passenger requests a ride, the request is suspended until a driver becomes available. The system monitors the availability of drivers, and once a driver is ready to take a new passenger, the system notifies the waiting passenger and resumes the ride request process. This ensures that passengers are not continuously checking for available drivers and that drivers are efficiently matched with passengers based on their availability.

In plain words

> Guarded Suspension pattern is used when one thread waits for the result of another thread's execution.

Wikipedia says

> In concurrent programming, Guarded Suspension manages operations requiring a lock and a precondition, delaying execution until the precondition is met.

## Programmatic Example of Guarded Suspension Pattern in Java

The `GuardedQueue` class in Java showcases concurrent programming using the Guarded Suspension pattern. It includes synchronized methods that manage thread management and synchronization, demonstrating how threads wait for the right conditions to execute.

The `GuardedQueue` class demonstrates the Guarded Suspension pattern by encapsulating a queue and providing two synchronized methods, `get` and `put`. The `get` method waits if the queue is empty, while the `put` method adds an item to the queue and notifies any waiting threads.

```java
@Slf4j
public class GuardedQueue {
    private final Queue<Integer> sourceList = new LinkedList<>();

    // Synchronized get method waits until the queue is not empty
    public synchronized Integer get() {
        while (sourceList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                LOGGER.error("Error occurred: ", e);
            }
        }
        return sourceList.poll();
    }

    // Synchronized put method adds an item to the queue and notifies waiting threads
    public synchronized void put(Integer e) {
        sourceList.add(e);
        notify();
    }
}
```

* `get`: This method waits while the `sourceList` is empty. When an item is added and `notify` is called, the waiting thread is awakened to continue execution and retrieve the item.
* `put`: This method adds an item to the queue and calls `notify` to wake up any waiting thread that is suspended in the `get` method.

Here is the `App` class driving the example:

```java
public class App {
    public static void main(String[] args) {
        GuardedQueue guardedQueue = new GuardedQueue();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Thread to get from the guardedQueue
        executorService.execute(() -> {
            Integer item = guardedQueue.get();
            LOGGER.info("Retrieved: " + item);
        });

        // Simulating some delay before putting an item
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("Error occurred: ", e);
        }

        // Thread to put an item into the guardedQueue
        executorService.execute(() -> {
            guardedQueue.put(20);
            LOGGER.info("Item added to queue");
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("Error occurred: ", e);
        }
    }
}
```

* `ExecutorService` is used to manage a pool of threads.
* The first thread attempts to retrieve an item from the `GuardedQueue` and waits since the queue is initially empty.
* After a 2-second delay, the second thread adds an item to the queue, waking up the first thread.
* The first thread then retrieves the item and logs it.

Execution yields:

```
19:22:58.984 [pool-1-thread-1] INFO com.iluwatar.guarded.suspension.GuardedQueue -- waiting
19:23:00.993 [pool-1-thread-2] INFO com.iluwatar.guarded.suspension.GuardedQueue -- putting
19:23:00.994 [pool-1-thread-2] INFO com.iluwatar.guarded.suspension.GuardedQueue -- notifying
19:23:00.994 [pool-1-thread-1] INFO com.iluwatar.guarded.suspension.GuardedQueue -- getting
19:23:00.994 [pool-1-thread-1] INFO com.iluwatar.guarded.suspension.GuardedQueue -- Retrieved: 20
```

* The log output shows the sequence of events: the first thread waits, the second thread puts an item, and the first thread then retrieves the item. This demonstrates the Guarded Suspension pattern in action.

## When to Use the Guarded Suspension Pattern in Java

This pattern is ideal for scenarios requiring a thread to wait for specific conditions, promoting efficient concurrency control and reducing busy waiting overhead.

## Real-World Applications of Guarded Suspension Pattern in Java

* Network servers waiting for client requests.
* Producer-consumer scenarios where the consumer must wait for the producer to provide data.
* Event-driven applications where actions are triggered only after specific events have occurred.

## Benefits and Trade-offs of Guarded Suspension Pattern

Benefits:

* Reduces CPU consumption by preventing busy waiting.
* Increases system responsiveness by synchronizing actions to the availability of necessary conditions or resources.

Trade-offs:

* Complexity in implementation, especially when multiple conditions need to be managed.
* Potential for deadlocks if not carefully managed.

## Related Java Design Patterns

* [Monitor](https://java-design-patterns.com/patterns/monitor/): Both patterns manage the synchronization of threads based on conditions. Guarded Suspension specifically deals with suspending threads until conditions are met, while Monitor Object encapsulates condition and mutual exclusion handling.
* [Producer-Consumer](https://java-design-patterns.com/patterns/producer-consumer/): Often implemented using Guarded Suspension to handle waiting consumers and producers efficiently.
* [Balking](https://java-design-patterns.com/patterns/balking/): Similar to Guarded Suspension, Balking is used when a thread checks a condition and only proceeds if the condition is favorable; if not, it immediately returns or bails out. This pattern complements Guarded Suspension by managing actions based on immediate condition checks without waiting.

## References and Credits

* [Concurrent Programming in Java : Design Principles and Patterns](https://amzn.to/4dIBqxL)
* [Java Concurrency in Practice](https://amzn.to/3JxnXek)
* [Pattern-Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://amzn.to/49Ke1c9)
