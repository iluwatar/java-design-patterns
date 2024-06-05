---
title: Health Check
category: Behavioral
language: en
tag:
  - Fault tolerance
  - Microservices
  - Monitoring
  - System health
---

## Also known as

* Health Monitoring
* Service Health Check

## Intent

The Health Check pattern is designed to proactively monitor the health of individual software components or services, allowing for quick identification and remediation of issues that may affect overall system functionality.

## Explanation

Real-world example

> Consider a hospital where patient monitoring systems are used to ensure the health of patients. Each monitoring device periodically checks the vital signs of a patient (such as heart rate, blood pressure, and oxygen levels) and reports back to a central system. If any device detects abnormal vital signs, it triggers an alert for immediate medical attention. Similarly, in software, a Health Check pattern allows each service to periodically report its status to a central monitoring system. If a service is found to be unhealthy, the system can take corrective actions such as alerting administrators, restarting the service, or redirecting traffic to healthy instances, thereby ensuring continuous and reliable operation.

In plain words

> The Health Check Pattern is like a regular doctor's visit for services in a microservices architecture. It helps in early detection of issues and ensures that services are healthy and available.

## Programmatic Example

The Health Check design pattern is a pattern that allows a system to proactively monitor the health of its components. This pattern is particularly useful in distributed systems where the health of individual components can affect the overall health of the system.

In the provided code, we can see an example of the Health Check pattern in the `App` class and the use of Spring Boot's Actuator.

The `App` class is the entry point of the application. It starts a Spring Boot application which has health check capabilities built-in through the use of Spring Boot Actuator.

```java
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
```

Spring Boot Actuator provides several built-in health checks through its `/actuator/health` endpoint. For example, it can check the status of the database connection, disk space, and other important system parameters. You can also add custom health checks as needed.

To add a custom health check, you can create a class that implements the `HealthIndicator` interface and override its `health` method. Here is an example:

```java
@Component
public class CustomHealthCheck implements HealthIndicator {
    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down()
              .withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }
     
    public int check() {
        // Our logic to check health
        return 0;
    }
}
```

In this example, the `check` method contains the logic for the health check. If the health check fails, it returns a non-zero error code, and the `health` method builds a `DOWN` health status with the error code. If the health check passes, it returns a `UP` health status.

This is a basic example of the Health Check pattern, where health checks are built into the system and can be easily accessed and monitored.

## Applicability

* Use when building microservices or distributed systems where it is crucial to monitor the health of each service.
* Suitable for scenarios where automated systems need to determine the operational status of services to perform load balancing, failover, or recovery operations.

## Known Uses

* Kubernetes liveness and readiness probes
* AWS elastic load balancing health checks
* Spring Boot Actuator

## Consequences

Benefits:

* Improved system reliability through early detection of failures.
* Enhanced system availability by allowing for automatic or manual recovery processes.
* Simplifies maintenance and operations by providing clear visibility into system health.

Trade-offs:

* Additional overhead for implementing and maintaining health check mechanisms.
* May introduce complexity in handling false positives and negatives in health status reporting.

## Related Patterns

* [Circuit Breaker](https://java-design-patterns.com/patterns/circuit-breaker/): Both patterns enhance system resilience; while Health Check monitors health status, Circuit Breaker protects a system from repeated failures.
* [Observer](https://java-design-patterns.com/patterns/observer/): Health Check can be seen as a specific use case of the Observer pattern, where the subject being observed is the system’s health.

## Credits

* [Microservices Patterns: With examples in Java](https://amzn.to/3UyWD5O)
* [Release It! Design and Deploy Production-Ready Software](https://amzn.to/3Uul4kF)
* [Pattern: Health Check API (Microservices.io)](https://microservices.io/patterns/observability/health-check-api.html)
