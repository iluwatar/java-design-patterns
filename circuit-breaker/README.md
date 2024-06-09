---
title: "Circuit Breaker Pattern in Java: Enhancing System Resilience"
shortTitle: Circuit Breaker
description: "Learn about the Circuit Breaker pattern in Java design, which ensures fault tolerance and prevents cascading failures in distributed systems and microservices architectures."
category: Resilience
language: en
tag:
  - Cloud distributed
  - Fault tolerance
  - Microservices
  - Retry
---

## Also known as

* Fault Tolerance Switch

## Intent of Circuit Breaker Design Pattern

The Circuit Breaker pattern is a critical Java design pattern that helps ensure fault tolerance and resilience in microservices and distributed systems. Using Circuit Breaker, it is possible to prevent a system from repeatedly trying to execute an operation likely to fail, allowing it to recover from faults and prevent cascading failures.

## Detailed Explanation of Circuit Breaker Pattern with Real-World Examples

Real-world example

> Consider a real-world example of an e-commerce website that depends on multiple external payment gateways to process transactions. If one of the payment gateways becomes unresponsive or slow, the Circuit Breaker pattern can be used to detect the failure and prevent the system from repeatedly attempting to use the problematic gateway. Instead, it can quickly switch to alternative payment gateways or display an error message to the user, ensuring that the rest of the website remains functional and responsive. This avoids resource exhaustion and provides a better user experience by allowing transactions to be processed through other available services. This way, the Circuit Breaker pattern handles external API failures, ensuring the system remains functional.

In plain words

> Circuit Breaker allows graceful handling of failed remote services. It's especially useful when all parts of our application are highly decoupled from each other, and failure of one component doesn't mean the other parts will stop working.

Wikipedia says

> Circuit breaker is a design pattern used in modern software development. It is used to detect failures and encapsulates the logic of preventing a failure from constantly recurring, during maintenance, temporary external system failure or unexpected system difficulties.

## Programmatic Example of Circuit Breaker Pattern in Java

This Java example demonstrates how the Circuit Breaker pattern can manage remote service failures and maintain system stability.

Imagine a web application that uses both local files/images and remote services to fetch data. Remote services can become slow or unresponsive, which may cause the application to hang due to thread starvation. The Circuit Breaker pattern can help detect such failures and allow the application to degrade gracefully.

1. **Simulating a Delayed Remote Service**

```java
// The DelayedRemoteService simulates a remote service that responds after a certain delay.
var delayedService = new DelayedRemoteService(serverStartTime, 5);
```

2. **Setting Up the Circuit Breaker**

```java
// The DefaultCircuitBreaker wraps the remote service and monitors for failures.
var delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000, 2, 2000 * 1000 * 1000);
```

3. **Monitoring Service to Handle Requests**

```java
// The MonitoringService is responsible for calling the remote services.
var monitoringService = new MonitoringService(delayedServiceCircuitBreaker, quickServiceCircuitBreaker);

// Fetch response from local resource
LOGGER.info(monitoringService.localResourceResponse());

// Fetch response from delayed service 2 times to meet the failure threshold
LOGGER.info(monitoringService.delayedServiceResponse());
LOGGER.info(monitoringService.delayedServiceResponse());
```

4. **Handling Circuit Breaker States**

```java
// Fetch current state of delayed service circuit breaker after crossing failure threshold limit
LOGGER.info(delayedServiceCircuitBreaker.getState()); // Should be OPEN

// Meanwhile, the delayed service is down, fetch response from the healthy quick service
LOGGER.info(monitoringService.quickServiceResponse());
LOGGER.info(quickServiceCircuitBreaker.getState());
```

5. **Recovering from Failure**

```java
// Wait for the delayed service to become responsive
try {
  LOGGER.info("Waiting for delayed service to become responsive");
  Thread.sleep(5000);
} catch (InterruptedException e) {
  LOGGER.error("An error occurred: ", e);
}

// Check the state of delayed circuit breaker, should be HALF_OPEN
LOGGER.info(delayedServiceCircuitBreaker.getState());

// Fetch response from delayed service, which should be healthy by now
LOGGER.info(monitoringService.delayedServiceResponse());

// As successful response is fetched, it should be CLOSED again.
LOGGER.info(delayedServiceCircuitBreaker.getState());
```

6. **Full example**

```java
public static void main(String[] args) {

    var serverStartTime = System.nanoTime();

    var delayedService = new DelayedRemoteService(serverStartTime, 5);
    var delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000, 2,
        2000 * 1000 * 1000);

    var quickService = new QuickRemoteService();
    var quickServiceCircuitBreaker = new DefaultCircuitBreaker(quickService, 3000, 2,
        2000 * 1000 * 1000);

    //Create an object of monitoring service which makes both local and remote calls
    var monitoringService = new MonitoringService(delayedServiceCircuitBreaker,
        quickServiceCircuitBreaker);

    //Fetch response from local resource
    LOGGER.info(monitoringService.localResourceResponse());

    //Fetch response from delayed service 2 times, to meet the failure threshold
    LOGGER.info(monitoringService.delayedServiceResponse());
    LOGGER.info(monitoringService.delayedServiceResponse());

    //Fetch current state of delayed service circuit breaker after crossing failure threshold limit
    //which is OPEN now
    LOGGER.info(delayedServiceCircuitBreaker.getState());

    //Meanwhile, the delayed service is down, fetch response from the healthy quick service
    LOGGER.info(monitoringService.quickServiceResponse());
    LOGGER.info(quickServiceCircuitBreaker.getState());

    //Wait for the delayed service to become responsive
    try {
      LOGGER.info("Waiting for delayed service to become responsive");
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      LOGGER.error("An error occurred: ", e);
    }
    //Check the state of delayed circuit breaker, should be HALF_OPEN
    LOGGER.info(delayedServiceCircuitBreaker.getState());

    //Fetch response from delayed service, which should be healthy by now
    LOGGER.info(monitoringService.delayedServiceResponse());
    //As successful response is fetched, it should be CLOSED again.
    LOGGER.info(delayedServiceCircuitBreaker.getState());
}
```

Summary of the example

- Initialize the Circuit Breaker with parameters: `timeout`, `failureThreshold`, and `retryTimePeriod`.
- Start in the `closed` state.
- On successful calls, reset the state.
- On failures exceeding the threshold, transition to the `open` state to prevent further calls.
- After the retry timeout, transition to the `half-open` state to test the service.
- On success in `half-open` state, transition back to `closed`. On failure, return to `open`.

Program output:

```
16:59:19.767 [main] INFO com.iluwatar.circuitbreaker.App -- Local Service is working
16:59:19.769 [main] INFO com.iluwatar.circuitbreaker.App -- Delayed service is down
16:59:19.769 [main] INFO com.iluwatar.circuitbreaker.App -- Delayed service is down
16:59:19.769 [main] INFO com.iluwatar.circuitbreaker.App -- OPEN
16:59:19.769 [main] INFO com.iluwatar.circuitbreaker.App -- Quick Service is working
16:59:19.769 [main] INFO com.iluwatar.circuitbreaker.App -- CLOSED
16:59:19.769 [main] INFO com.iluwatar.circuitbreaker.App -- Waiting for delayed service to become responsive
16:59:24.779 [main] INFO com.iluwatar.circuitbreaker.App -- HALF_OPEN
16:59:24.780 [main] INFO com.iluwatar.circuitbreaker.App -- Delayed service is working
16:59:24.780 [main] INFO com.iluwatar.circuitbreaker.App -- CLOSED
```

This example demonstrates how the Circuit Breaker pattern can help maintain application stability and resilience by managing remote service failures.

## When to Use the Circuit Breaker Pattern in Java

The Circuit Breaker pattern is applicable:

* In distributed systems where individual service failures can lead to cascading system-wide failures
* For applications that interact with third-party services or databases that might become unresponsive or slow
* In microservices architectures where the failure of one service can affect the availability of others

## Real-World Applications of Circuit Breaker Pattern in Java

* Cloud-based services to gracefully handle the failure of external services
* E-commerce platforms to manage high volumes of transactions and dependency on external APIs
* Microservices architectures for maintaining system stability and responsiveness
* [Spring Circuit Breaker module](https://spring.io/guides/gs/circuit-breaker)
* [Netflix Hystrix API](https://github.com/Netflix/Hystrix)

## Benefits and Trade-offs of Circuit Breaker Pattern

Benefits:

* Prevents the system from performing futile operations that are likely to fail, thus saving resources
* Helps in maintaining the system stability and performance of the application during partial system failures
* Facilitates faster system recovery by avoiding the overwhelming of failing services with repeated requests

Trade-Offs:

* The complexity of the system increases as the pattern requires additional logic to detect failures and manage the state of the circuit breaker
* May lead to system degradation if not properly configured, as legitimate requests might be blocked if the circuit is open
* Requires careful tuning of thresholds and timeout periods to balance between responsiveness and protection

## Related Patterns

- Bulkhead: Can be used to isolate different parts of the system to prevent failures from spreading across the system
- [Retry Pattern](https://github.com/iluwatar/java-design-patterns/tree/master/retry): Can be used in conjunction with the Circuit Breaker pattern to retry failed operations before opening the circuit

## References and Credits

* [Building Microservices: Designing Fine-Grained Systems](https://amzn.to/43Dx86g)
* [Microservices Patterns: With examples in Java](https://amzn.to/3xaZwk0)
* [Release It! Design and Deploy Production-Ready Software](https://amzn.to/4aqTNEP)
* [Understand CircuitBreaker Design Pattern with Simple Practical Example (ITNEXT)](https://itnext.io/understand-circuitbreaker-design-pattern-with-simple-practical-example-92a752615b42)
* [Circuit Breaker (Martin Fowler)](https://martinfowler.com/bliki/CircuitBreaker.html)
* [Fault tolerance in a high volume, distributed system (Netflix)](https://medium.com/netflix-techblog/fault-tolerance-in-a-high-volume-distributed-system-91ab4faae74a)
* [Circuit Breaker pattern (Microsoft)](https://docs.microsoft.com/en-us/azure/architecture/patterns/circuit-breaker)
