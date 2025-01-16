---
title: "Join Pattern in Java: Streamlining Concurrent Operations"
shortTitle: Join
description: "Master the Join Design Pattern in Java to coordinate and synchronize concurrent tasks effectively. Explore examples, code implementations, benefits, and practical applications."
category: Concurrency
language: en
tag:
  - Concurrency
  - Synchronization
  - Parallel processing
  - Gang of Four
---

## Also known as

* Fork-Join Pattern

## Intent of Join Pattern

The Join Pattern in Java focuses on coordinating and synchronizing concurrent tasks to achieve a specific outcome. It ensures that multiple tasks can execute independently, and their results are merged once all tasks complete.

## Detailed Explanation of Join Pattern with Real-World Examples

Real-world example

> Imagine a multi-chef kitchen preparing different dishes for a single order. Each chef works independently on their assigned dish, but the order cannot be delivered until every dish is ready. The kitchen manager, acting as the join point, ensures synchronization and prepares the final order once all dishes are done. Similarly, the Join Pattern allows tasks to execute concurrently and synchronizes their results for a final outcome.

In plain words

> The Join Pattern helps in synchronizing multiple independent tasks, allowing them to work concurrently and combining their outcomes efficiently.

Wikipedia says

> The join design pattern is a parallel processing pattern that helps merge results of concurrently executed tasks.

## Programmatic Example of Join Pattern in Java

In this example, we demonstrate how the Join Pattern can be implemented to manage multiple threads and synchronize their results. We use a task aggregator that collects data from individual tasks and combines it into a final result.

```java

package com.iluwatar.join;

import lombok.extern.slf4j.Slf4j;

/** Here main thread will execute after completion of 4 demo threads 
 * main thread will continue when CountDownLatch count becomes 0 
 * CountDownLatch will start with count 4 and 4 demo threads will decrease it by 1 
 * everytime when they will finish .
 * DemoThreads are implemented in join pattern such that every newly created thread 
 * waits for the completion of previous thread by previous.join() . Hence maintaining 
 * execution order of demo threads .
 * JoinPattern object ensures that dependent threads execute only after completion of
 * demo threads by pattern.await() . This method keep the main thread in waiting state
 * until countdown latch becomes 0 . CountdownLatch will become 0 as all demo threads 
 * will be completed as each of them have decreased it by 1 and its initial count was set to noOfDemoThreads.
 * Hence this pattern ensures dependent threads will start only after completion of demo threads .
 */
    pattern.await();

    //Dependent threads after execution of DemoThreads
    for (int i = 0; i < noOfDependentThreads; i++) {
      new DependentThread(i + 1).start();
    }
    LOGGER.info("end of program ");

    /**
   * use to run demo thread.
   * every newly created thread waits for 
   * the completion of previous thread 
   * by previous.join() .
   */
  @Override
  public void run() {
    if (previous != null) {
      try {
        previous.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOGGER.error("Interrupted exception : ", e);
      }
    }


```

### Program Output:

[INFO] Running com.iluwatar.join.JoinPatternTest
16:12:01.815 [Thread-2] INFO com.iluwatar.join.DemoThread -- Thread 1 starts
16:12:02.086 [Thread-2] INFO com.iluwatar.join.DemoThread -- Thread 1 ends
16:12:02.087 [Thread-3] INFO com.iluwatar.join.DemoThread -- Thread 4 starts
16:12:03.090 [Thread-3] INFO com.iluwatar.join.DemoThread -- Thread 4 ends
16:12:03.091 [Thread-4] INFO com.iluwatar.join.DemoThread -- Thread 3 starts
16:12:03.851 [Thread-4] INFO com.iluwatar.join.DemoThread -- Thread 3 ends
16:12:03.851 [Thread-5] INFO com.iluwatar.join.DemoThread -- Thread 2 starts
16:12:04.352 [Thread-5] INFO com.iluwatar.join.DemoThread -- Thread 2 ends
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.904 s -- in com.iluwatar.join.JoinPatternTest
[INFO] 
[INFO] Results:
[INFO]
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO]

## When to Use the Join Pattern in Java

Use the Join Pattern in Java:

* To synchronize results from multiple independent tasks executing in parallel.
* To aggregate and process data from various sources concurrently.
* To reduce the complexity of managing multiple threads in parallel operations.

## Real-World Applications of Join Pattern in Java

* Managing concurrent HTTP requests and aggregating their responses into a single result.
* Parallel processing of large datasets, such as in map-reduce frameworks.
* Synchronizing asynchronous operations, e.g., CompletableFutures in Java.

## Benefits and Trade-offs of Join Pattern

### Benefits:

* Efficiently handles parallel processing tasks with minimal synchronization overhead.
* Improves application performance by utilizing available system resources optimally.
* Simplifies the logic for managing and synchronizing multiple tasks.

### Trade-offs:

* Debugging can become challenging with large numbers of asynchronous tasks.
* Improper use may lead to deadlocks or performance bottlenecks.

## Related Java Design Patterns

* [Fork-Join Framework](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html): Built-in Java framework for recursive task splitting and joining.
* [Future and CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html): Used for handling and synchronizing asynchronous operations.
* [Observer Pattern](https://java-design-patterns.com/patterns/observer/): Can be combined with Join to monitor task progress.

## References and Credits

* [Java Concurrency in Practice](https://amzn.to/3sfS8mT)
* [Effective Java](https://amzn.to/3GxS8p4)
* [Oracle Java Documentation on Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
