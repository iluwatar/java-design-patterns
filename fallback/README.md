---
title: "Fallback Pattern in Java: Graceful Degradation in Microservices"
shortTitle: Fallback
description: "Learn about the Fallback pattern in Java design, which ensures microservice resilience and graceful system degradation when primary dependencies fail."
category: Resilience
language: en
tag:
  - Cloud distributed
  - Fault tolerance
  - Microservices
---

## Intent of Fallback Design Pattern

The Fallback design pattern is a resiliency pattern used in microservices architecture to handle failures gracefully. It ensures that when a service is unavailable, fails, or times out, the system can continue to operate by providing an alternative response or executing a predefined fallback mechanism. This pattern enhances robustness and reliability by preventing cascading failures and improving the overall user experience.

## Detailed Explanation of Fallback Pattern with Real-World Examples

Real-world example

> Consider a movie streaming application like Netflix. The home page loads personalized recommendations for the logged-in user. If the recommendation microservice goes offline or is too slow, the user shouldn't see a broken page. Instead, the system falls back to a cached list of globally popular movies. While the response is degraded (not personalized), the application remains functional, providing a seamless user experience.

In plain words

> Fallback ensures that if a primary service call fails, the application falls back to a backup strategy (e.g. cached response, default value, or simplified service) rather than raising an error and failing completely.

Wikipedia says

> A fallback is a contingency option to be taken if the preferred choice is unavailable. In software, fallback mechanisms are crucial for fault tolerance, allowing systems to degrade gracefully rather than crash.

## Programmatic Example of Fallback Pattern in Java

This Java example demonstrates how the Fallback pattern can manage service failures, integrate with a Circuit Breaker, and apply timeout limits.

1. **Defining the Remote Service Interface**
   
   The `RemoteService` interface represents any external dependency call.
   
```java
public interface RemoteService {
  String execute() throws Exception;
}
```

2. **Defining the Primary Service and Fallback Service**
   
   The `PrimaryService` simulates our main external dependency which may suffer from errors or latency. The `FallbackService` returns a cached or degraded static response.

```java
// Primary Service simulating latency and errors
var healthyPrimary = new PrimaryService("Healthy data from primary service", 10, false);
var failingPrimary = new PrimaryService("Failing service", 0, true);
var slowPrimary = new PrimaryService("Slow response from primary service", 500, false);

// Fallback Service providing degraded response
var fallback = new FallbackService("Fallback degraded/cached response");
```

3. **Monitoring Health with a Circuit Breaker**
   
   A `SimpleCircuitBreaker` tracks the number of failures to trip the circuit to `OPEN`, bypassing the primary service immediately to avoid waiting for timeouts.

```java
// Trip after 2 failures; retry after 1 second
var circuitBreaker = new SimpleCircuitBreaker(2, 1000);
```

4. **Executing Calls with the FallbackExecutor**
   
   The `FallbackExecutor` uses virtual threads to execute the primary service call. It applies timeouts, handles exceptions, records failures to the circuit breaker, and falls back to the fallback service as needed.

```java
try (var executor = new FallbackExecutor()) {
  // Scenario 1: Healthy primary service call
  String response1 = executor.execute(healthyPrimary, fallback, circuitBreaker, 100);
  LOGGER.info("Response: {}", response1); // Healthy data from primary service

  // Scenario 2: Failing service call triggers fallback
  String response2 = executor.execute(failingPrimary, fallback, circuitBreaker, 100);
  LOGGER.info("Response: {}", response2); // Fallback degraded/cached response
}
```

## When to Use the Fallback Pattern in Java

The Fallback pattern is applicable:

* In microservices architectures where dependencies are called over the network and are prone to network partitions, timeouts, and outages.
* When returning a default, empty, or cached value is preferable to failing the entire request.
* In user-facing systems where maintaining a working UI (even with degraded features) is critical for user satisfaction.

## Real-World Applications of Fallback Pattern in Java

* [Resilience4j Fallback mechanism](https://resilience4j.readme.io/docs/fallback)
* [Netflix Hystrix Fallback](https://github.com/Netflix/Hystrix/wiki/How-To-Use#Fallback)
* Spring Cloud Circuit Breaker integrations

## Benefits and Trade-offs of Fallback Pattern

Benefits:

* **Graceful Degradation**: Improves user experience by returning partial/cached data instead of errors.
* **Cascading Failure Prevention**: Avoids blocking threads waiting on hung services.
* **Fault Tolerance**: Improves system uptime and reliability.

Trade-Offs:

* **Stale Data**: Fallback cached responses may present out-of-date information to the user.
* **Increased Complexity**: Requires writing alternative execution flows and testing fallback scenarios.

## Related Patterns

- [Circuit Breaker](https://github.com/iluwatar/java-design-patterns/tree/master/circuit-breaker): Restricts calls to failing services. Often wraps the primary service before fallback is triggered.
- [Retry Pattern](https://github.com/iluwatar/java-design-patterns/tree/master/retry): Retries failed calls before triggering the fallback.
