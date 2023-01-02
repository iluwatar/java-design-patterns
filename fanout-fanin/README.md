---
title: Fan-Out/Fan-In
category: Integration
language: en
tag:
- Microservices
---

## Intent
The pattern is used when a source system needs to run one or more long-running processes that will fetch some data. 
The source will not block itself waiting for the reply. <br> The pattern will run the same function in multiple 
services or machines to fetch the data. This is equivalent to invoking the function multiple times on different chunks of data.  

## Explanation
The FanOut/FanIn service will take in a list of requests and a consumer. Each request might complete at a different time.
FanOut/FanIn service will accept the input params and returns the initial system an ID to acknowledge that the pattern
service has received the requests. Now the caller will not wait or expect the result in the same connection. 

Meanwhile, the pattern service will invoke the requests that have come. The requests might complete at different time. 
These requests will be processed in different instances of the same function in different machines or services. As the 
requests get completed, a callback service everytime is called that transforms the result into a common single object format
that gets pushed to a consumer. The caller will be at the other end of the consumer receiving the result.

**Programmatic Example**

The implementation provided has a list of numbers and end goal is to square the numbers and add them to a single result.
`FanOutFanIn` class receives the list of numbers in the form of list of `SquareNumberRequest` and a `Consumer` instance 
that collects the results as the requests get over. `SquareNumberRequest` will square the number with a random delay
to give the impression of a long-running process that can complete at any time. `Consumer` instance will add the results from
different `SquareNumberRequest` that will come random time instances. 

Let's look at `FanOutFanIn` class that fans out the requests in async processes. 

```java
public class FanOutFanIn {
  public static Long fanOutFanIn(
      final List<SquareNumberRequest> requests, final Consumer consumer) {

    ExecutorService service = Executors.newFixedThreadPool(requests.size());

    // fanning out
    List<CompletableFuture<Void>> futures =
        requests.stream()
            .map(
                request ->
                    CompletableFuture.runAsync(() -> request.delayedSquaring(consumer), service))
            .collect(Collectors.toList());

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

    return consumer.getSumOfSquaredNumbers().get();
  }
}
```

`Consumer` is used a callback class that will be called when a request is completed. This will aggregate
the result from all requests.

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

Request is represented as a `SquareNumberRequest` that squares the number with random delay and calls the 
`Consumer` once it is squared.

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

## Class diagram
![alt-text](./etc/fanout-fanin.png)

## Applicability

Use this pattern when you can divide the workload into multiple chunks that can be dealt with separately.

## Related patterns

* [Aggregator Microservices](https://java-design-patterns.com/patterns/aggregator-microservices/)
* [API Gateway](https://java-design-patterns.com/patterns/api-gateway/)

## Credits

* [Understanding Azure Durable Functions - Part 8: The Fan Out/Fan In Pattern](http://dontcodetired.com/blog/post/Understanding-Azure-Durable-Functions-Part-8-The-Fan-OutFan-In-Pattern)
* [Fan-out/fan-in scenario in Durable Functions - Cloud backup example](https://docs.microsoft.com/en-us/azure/azure-functions/durable/durable-functions-cloud-backup)
* [Understanding the Fan-Out/Fan-In API Integration Pattern](https://dzone.com/articles/understanding-the-fan-out-fan-in-api-integration-p)