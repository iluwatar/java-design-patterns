---
title: "Master-Worker Pattern in Java: Coordinating Concurrent Processing with Ease"
shortTitle: Master-Worker
description: "Discover the Master-Worker design pattern in Java. Learn how it improves concurrency, scalability, and performance through parallel task processing. Includes real-world examples and code snippets."
category: Concurrency
language: en
tag:
  - Multithreading
  - Performance
  - Scalability
---

## Also known as

* Master-Slave
* Controller-Worker

## Intent of Master-Worker Design Pattern

The Master-Worker design pattern is designed to perform parallel computations by distributing tasks between a master process and multiple worker processes. This pattern enhances concurrency, performance, and scalability in software systems.

## Detailed Explanation of Master-Worker Pattern with Real-World Examples

Real-world example

> The Master-Worker pattern optimizes parallel task processing and throughput. For instance, in a restaurant kitchen, the head chef (master) delegates tasks to line cooks (workers), who work concurrently to prepare the order. The head chef receives the orders from the dining area and breaks down each order into specific tasks, such as grilling meat, preparing salads, and cooking desserts. Each task is assigned to a different line cook based on their expertise and current workload. The line cooks work in parallel to prepare their portion of the order, while the head chef oversees the process, ensuring everything is prepared correctly and timely. Once each component of the order is ready, the head chef gathers all parts, gives them a final check, and then plates the dishes for service. This kitchen operation mimics the Master-Worker pattern by distributing and managing tasks to optimize efficiency and output.

In plain words

> The Master-Worker pattern involves a master process delegating tasks to multiple worker processes, which execute them concurrently and report back, optimizing parallel task processing and throughput.

Wikipedia says

> Master–slave is a model of asymmetric communication or control where one device or process (the master) controls one or more other devices or processes (the slaves) and serves as their communication hub. In some systems, a master is selected from a group of eligible devices, with the other devices acting in the role of slaves.

## Programmatic Example of Master-Worker Pattern in Java

In the provided code, the `MasterWorker` class initiates the concurrent computation process. The `Master` class divides the work among `Worker` objects, each performing its task in parallel, thus optimizing task processing and enhancing system efficiency.

```java
// The MasterWorker class acts as the main entry point for the Master-Worker system.
public class MasterWorker {
  private Master master;

  public MasterWorker(Master master) {
    this.master = master;
  }

  public Result getResult(Input input) {
    return master.computeResult(input);
  }
}
```

In this code, the `MasterWorker` class is initialized with a `Master` object. The `getResult` method is used to start the computation process.

```java
// The Master class is responsible for dividing the work among the workers.
public abstract class Master {
  protected List<Worker> workers;

  public Master(List<Worker> workers) {
    this.workers = workers;
  }

  public abstract Result computeResult(Input input);
}
```

The `Master` class has a list of `Worker` objects. The `computeResult` method is abstract and should be implemented in a subclass to define how the work is divided and how the results are aggregated.

```java
// The Worker class is responsible for performing the actual computation.
public abstract class Worker extends Thread {
  protected Input input;

  public void setInput(Input input) {
    this.input = input;
  }

  public abstract Result compute();
}
```

The `Worker` class extends `Thread`, allowing it to perform computations in parallel. The `compute` method is abstract and should be implemented in a subclass to define the actual computation logic.

```java
// The Input and Result classes are used to encapsulate the input data and the result data.
public abstract class Input<T> {
  public final T data;

  public Input(T data) {
    this.data = data;
  }

  public abstract List<Input<T>> divideData(int num);
}

public abstract class Result<T> {
  public final T data;

  public Result(T data) {
    this.data = data;
  }
}
```

The `Input` class has a `divideData` method that is used to divide the input data into subtasks. The `Result` class simply encapsulates the result data.

## When to Use the Master-Worker Pattern in Java

* Suitable for scenarios where a task can be decomposed into smaller, independent tasks.
* Useful in applications requiring concurrent execution to enhance performance.
* Applicable in distributed computing where tasks need to be processed by multiple processors or machines.

## Master-Worker Pattern Java Tutorials

* [Master-Worker Pattern (Gigaspaces)](https://docs.gigaspaces.com/sbp/master-worker-pattern.html)

## Real-World Applications of Master-Worker Pattern in Java

* Implemented in distributed systems to manage tasks across different computing resources.
* Used in server architectures to process multiple client requests simultaneously.
* Utilized in scientific computation frameworks where large datasets require parallel processing.

## Benefits and Trade-offs of Master-Worker Pattern

Benefits:

* Enhances performance by parallelizing tasks.
* Increases responsiveness of systems handling large volumes of requests.
* Provides a clear separation of concerns between task coordination and task execution, simplifying design.

Trade-offs:

* Complexity in managing synchronization and state consistency between master and workers.
* Overhead of managing communication between master and workers, especially in distributed environments.

## Related Java Design Patterns

* Task Parallelism and Data Parallelism: Master-Worker utilizes these patterns to divide work into tasks or data segments.
* [Producer-Consumer](https://java-design-patterns.com/patterns/producer-consumer/): Similar in structure but focuses on balancing production and consumption rates; Master-Worker is more about task distribution and aggregation.
* [Pipeline](https://java-design-patterns.com/patterns/pipeline/): Both organize processing steps but Pipeline arranges them linearly whereas Master-Worker may not impose such a sequence.

## References and Credits

* [Distributed Systems: Principles and Paradigms](https://amzn.to/3UN2vbH)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Pattern-Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://amzn.to/3UgC24V)
