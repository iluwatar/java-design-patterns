---
title: Health Check Pattern
category: Performance
language: en
tag:
  - Microservices
  - Resilience
  - Observability
---

# Health Check Pattern

## Also known as
Health Monitoring, Service Health Check

## Intent
To ensure the stability and resilience of services in a microservices architecture by providing a way to monitor and diagnose their health.

## Explanation
In microservices architecture, it's critical to continuously check the health of individual services. The Health Check Pattern is a mechanism for microservices to expose their health status. This pattern is implemented by including a health check endpoint in microservices that returns the service's current state. This is vital for maintaining system resilience and operational readiness.

## Class Diagram
![alt text](./etc/health-check.png "Health Check")

## Applicability
Use the Health Check Pattern when:
- You have an application composed of multiple services and need to monitor the health of each service individually.
- You want to implement automatic service recovery or replacement based on health status.
- You are employing orchestration or automation tools that rely on health checks to manage service instances.

## Tutorials
- Implementing Health Checks in Java using Spring Boot Actuator.

## Known Uses
- Kubernetes Liveness and Readiness Probes
- AWS Elastic Load Balancing Health Checks
- Spring Boot Actuator

## Consequences
**Pros:**
- Enhances the fault tolerance of the system by detecting failures and enabling quick recovery.
- Improves the visibility of system health for operational monitoring and alerting.

**Cons:**
- Adds complexity to service implementation.
- Requires a strategy to handle cascading failures when dependent services are unhealthy.

## Related Patterns
- Circuit Breaker
- Retry Pattern
- Timeout Pattern

## Credits
Inspired by the Health Check API pattern from [microservices.io](https://microservices.io/patterns/observability/health-check-api.html) and the issue [#2695](https://github.com/iluwatar/java-design-patterns/issues/2695) on iluwatar's Java design patterns repository.
