---
title: "Fan-Out Fan-In Pattern in Java: Maximizing Concurrency for Efficient Data Processing"
shortTitle: Fan-Out/Fan-In
description: "Learn how the Fan-Out/Fan-In design pattern in Java can optimize concurrency and processing efficiency. Explore real-world examples, detailed explanations, and programmatic implementations."
category: Concurrency
language: en
tag:
  - Asynchronous
  - Data processing
  - Decoupling
  - Scalability
---

## Also known as

* Scatter-Gather

## Intent of Fan-Out/Fan-In Design Pattern

The Fan-Out/Fan-In design pattern in Java aims to improve concurrency and optimize processing time by dividing a task into multiple sub-tasks that can be processed in parallel (fan-out) and then combining the results of these sub-tasks into a single outcome (fan-in).

## Detailed Explanation of Fan-Out/Fan-In Pattern with Real-World Examples

Real-world example

> A real-world example of the Fan-Out/Fan-In pattern in Java is a food delivery service like UberEats or DoorDash. When a customer places an order, the service (fan-out) sends out individual tasks to different restaurants to prepare the various items. Each restaurant works independently to prepare its part of the order. Once all restaurants have completed their tasks, the delivery service (fan-in) aggregates the items from different restaurants into a single order, ensuring that everything is delivered together to the customer. This parallel processing improves efficiency and ensures timely delivery.

In plain words

> The Fan-Out/Fan-In pattern distributes tasks across multiple concurrent processes or threads and then aggregates the results.

Wikipedia says

> In message-oriented middleware, the fan-out pattern models information exchange by delivering messages to one or multiple destinations in parallel, without waiting for responses. This allows a process to distribute tasks to various receivers simultaneously.
>
> The fan-in concept, on the other hand, typically refers to the aggregation of multiple inputs. In digital electronics, it describes the number of inputs a logic gate can handle. Combining these concepts, the Fan-Out/Fan-In pattern in software engineering involves distributing tasks (fan-out) and then aggregating the results (fan-in).

## Programmatic Example of Fan-Out/Fan-In Pattern in Java

The provided implementation involves a list of numbers with the objective to square them and aggregate the results. The `FanOutFanIn` class receives the list of numbers as `SquareNumberRequest` objects and a `Consumer` instance that collects the squared results as the requests complete. Each `SquareNumberRequest` squares its number with a random delay, simulating a long-running process that finishes at unpredictable times. The `Consumer` instance gathers the results from the various `SquareNumberRequest` objects as they become available at different times.

Here's the `FanOutFanIn` class in Java that demonstrates the Fan-Out/Fan-In pattern by asynchronously distributing the requests.

```java
public class FanOutFanIn {
    public static Long fanOutFanIn(final List<SquareNumberRequest> requests, final Consumer consumer) {

        ExecutorService service = Executors.newFixedThreadPool(requests.size());

        // fanning out
        List<CompletableFuture<Void>> futures = requests
                .stream()
                .map(request -> CompletableFuture.runAsync(() -> request.delayedSquaring(consumer), service))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return consumer.getSumOfSquaredNumbers().get();
    }
}
```

`Consumer` is used a callback class that will be called when a request is completed. This will aggregate the result from all requests.

```java
public class Consumer {

    private final AtomicLong sumOfSquaredNumbers;

    Consumer(Long init) {
        sumOfSquaredNumbers = new AtomicLong(init);
    }

    public Long add(final Long num) {
        return sumOfSquaredNumbers.addAndGet(num);
    }
}
```

Request is represented as a `SquareNumberRequest` that squares the number with random delay and calls the `Consumer` once it is squared.

```java
public class SquareNumberRequest {

    private final Long number;

    public void delayedSquaring(final Consumer consumer) {

        var minTimeOut = 5000L;

        SecureRandom secureRandom = new SecureRandom();
        var randomTimeOut = secureRandom.nextInt(2000);

        try {
            // this will make the thread sleep from 5-7s.
            Thread.sleep(minTimeOut + randomTimeOut);
        } catch (InterruptedException e) {
            LOGGER.error("Exception while sleep ", e);
            Thread.currentThread().interrupt();
        } finally {
            consumer.add(number * number);
        }
    }
}
```

Here is the App class with main method to drive the example.

```java
public static void main(String[] args) {
    final List<Long> numbers = Arrays.asList(1L, 3L, 4L, 7L, 8L);

    LOGGER.info("Numbers to be squared and get sum --> {}", numbers);

    final List<SquareNumberRequest> requests =
        numbers.stream().map(SquareNumberRequest::new).toList();

    var consumer = new Consumer(0L);

    // Pass the request and the consumer to fanOutFanIn or sometimes referred as Orchestrator
    // function
    final Long sumOfSquaredNumbers = FanOutFanIn.fanOutFanIn(requests, consumer);

    LOGGER.info("Sum of all squared numbers --> {}", sumOfSquaredNumbers);
}
```

Running the example produces the following console output.

```
06:52:04.622 [main] INFO com.iluwatar.fanout.fanin.App -- Numbers to be squared and get sum --> [1, 3, 4, 7, 8]
06:52:11.465 [main] INFO com.iluwatar.fanout.fanin.App -- Sum of all squared numbers --> 139
```

## When to Use the Fan-Out/Fan-In Pattern in Java

The Fan-Out/Fan-In design pattern in Java is appropriate in scenarios where tasks can be broken down and executed in parallel, especially suitable for data processing, batch processing, and situations requiring aggregation of results from various sources.

## Fan-Out/Fan-In Pattern Java Tutorials

* [Fan-out/fan-in scenario in Durable Functions - Cloud backup example (Microsoft)](https://docs.microsoft.com/en-us/azure/azure-functions/durable/durable-functions-cloud-backup)
* [Understanding Azure Durable Functions - Part 8: The Fan Out/Fan In Pattern (Don't Code Tired)](http://dontcodetired.com/blog/post/Understanding-Azure-Durable-Functions-Part-8-The-Fan-OutFan-In-Pattern)
* [Understanding the Fan-Out/Fan-In API Integration Pattern (DZone)](https://dzone.com/articles/understanding-the-fan-out-fan-in-api-integration-p)

## Real-World Applications of Fan-Out/Fan-In Pattern in Java

* The Fan-Out/Fan-In pattern in Java is widely used in large-scale data processing applications.
* Services requiring aggregation from multiple sources before delivering a response, such as in distributed caching or load balancing systems.

## Benefits and Trade-offs of Fan-Out/Fan-In Pattern

Benefits:

* Enhances performance by parallel processing.
* Increases responsiveness of systems.
* Efficient utilization of multi-core processor architectures.

Trade-offs:

* Increased complexity in error handling.
* Potential for increased overhead due to task synchronization and result aggregation.
* Dependency on the underlying infrastructure's ability to support concurrent execution.

## Related Java Design Patterns

* MapReduce: Similar to Fan-Out/Fan-In, MapReduce also involves distributing tasks across a number of workers (map) and aggregating the results (reduce), which is particularly useful for processing large data sets.
* [Command](https://java-design-patterns.com/patterns/command/): Command Pattern facilitates the decoupling of the sender and the receiver, akin to how Fan-Out/Fan-In decouples task submission from task processing.
* [Producer-Consumer](https://java-design-patterns.com/patterns/producer-consumer/): Works synergistically with Fan-Out/Fan-In by organizing task execution where producers distribute tasks that are processed by multiple consumers, and results are then combined, enhancing throughput and efficiency in data processing.

## References and Credits

* [Java Concurrency in Practice](https://amzn.to/3vXytsb)
* [Patterns of Enterprise Application Architecture](https://amzn.to/49QQcPD)
