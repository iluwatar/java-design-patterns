---
title: Guarded Suspension
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

## Intent

The Guarded Suspension pattern manages operations that require both a lock and a condition to proceed, allowing a thread to wait for an appropriate condition while being efficient with resource use.

## Explanation

Real world example

> When we book a dining room online and arrive to find it not yet prepared, the manager arranges for it to be cleaned while we wait. Once the room is ready, we are then escorted to it. This scenario illustrates the Guarded Suspension pattern, where our access to the room is contingent upon a specific condition being met—namely, the room being cleaned.

In plain words

> Guarded Suspension pattern is used when one thread waits for the result of another thread's execution.

Wikipedia says

> In concurrent programming, Guarded Suspension manages operations requiring a lock and a precondition, delaying execution until the precondition is met.

**Programmatic Example**

The `GuardedQueue` class encapsulates a queue, and provides two synchronized methods, `get` and `put`. The `get` method waits if the queue is empty, and the `put` method adds an item to the queue and notifies waiting threads:

```java
@Slf4j
public class GuardedQueue {
    private final Queue<Integer> sourceList = new LinkedList<>();

    public synchronized Integer get() {
        while (sourceList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                LOGGER.error("Error occurred: ", e);
            }
        }
        return sourceList.peek();
    }

    public synchronized void put(Integer e) {
        sourceList.add(e);
        notify();
    }
}
```

Here is the `App` class driving the example.

```java
public class App {
    public static void main(String[] args) {
        GuardedQueue guardedQueue = new GuardedQueue();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Here we create the first thread which is supposed to get from guardedQueue
        executorService.execute(guardedQueue::get);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("Error occurred: ", e);
        }

        // Here we create the second thread which is supposed to put to guardedQueue
        executorService.execute(() -> {
            guardedQueue.put(20);
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

Executing the example yields:

```
19:22:58.984 [pool-1-thread-1] INFO com.iluwatar.guarded.suspension.GuardedQueue -- waiting
19:23:00.993 [pool-1-thread-2] INFO com.iluwatar.guarded.suspension.GuardedQueue -- putting
19:23:00.994 [pool-1-thread-2] INFO com.iluwatar.guarded.suspension.GuardedQueue -- notifying
19:23:00.994 [pool-1-thread-1] INFO com.iluwatar.guarded.suspension.GuardedQueue -- getting
```

## Class diagram

![Guarded Suspension diagram](./etc/guarded-suspension.png)

## Applicability

This pattern is used in scenarios where a thread needs to wait for certain conditions to be met before it can proceed, ensuring that resources are utilized only when necessary and reducing the overhead of busy waiting.

## Known Uses

* Network servers waiting for client requests.
* Producer-consumer scenarios where the consumer must wait for the producer to provide data.
* Event-driven applications where actions are triggered only after specific events have occurred.

## Consequences

Benefits:

* Reduces CPU consumption by preventing busy waiting.
* Increases system responsiveness by synchronizing actions to the availability of necessary conditions or resources.

Trade-offs:

* Complexity in implementation, especially when multiple conditions need to be managed.
* Potential for deadlocks if not carefully managed.

## Related Patterns

* Monitor Object: Both patterns manage the synchronization of threads based on conditions. Guarded Suspension specifically deals with suspending threads until conditions are met, while Monitor Object encapsulates condition and mutual exclusion handling.
* Producer-Consumer: Often implemented using Guarded Suspension to handle waiting consumers and producers efficiently.
* Balking: Similar to Guarded Suspension, Balking is used when a thread checks a condition and only proceeds if the condition is favorable; if not, it immediately returns or bails out. This pattern complements Guarded Suspension by managing actions based on immediate condition checks without waiting.

## Credits

* [Java Concurrency in Practice](https://amzn.to/3JxnXek)
* [Pattern-Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://amzn.to/49Ke1c9)
