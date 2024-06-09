---
title: "Ambassador Pattern in Java: Simplifying Remote Resource Management"
shortTitle: Ambassador
description: "Explore the Ambassador Pattern in Java, its benefits, use cases, and practical examples. Learn how to decouple and offload common functionalities to improve system performance and maintainability."
category: Integration
language: en
tag:
  - API design
  - Decoupling
  - Fault tolerance
  - Proxy
  - Resilience
  - Scalability
---

## Intent of Ambassador Design Pattern

The Ambassador Pattern in Java helps offload common functionalities such as monitoring, logging, and routing from a shared resource to a helper service instance, enhancing performance and maintainability in distributed systems.

## Detailed Explanation of Ambassador Pattern with Real-World Examples

Real-world example

> Imagine a busy hotel where guests frequently request restaurant reservations, event tickets, or transportation arrangements. Instead of each guest individually contacting these services, the hotel provides a concierge. The concierge handles these tasks on behalf of the guests, ensuring that reservations are made smoothly, tickets are booked on time, and transportation is scheduled efficiently.
>
> In this analogy, the guests are the client services, the external providers (restaurants, ticket vendors, transportation) are the remote services, and the concierge represents the ambassador service. This setup allows the guests to focus on enjoying their stay while the concierge manages the complexities of external interactions, providing a seamless and enhanced experience.

In plain words

> With the Ambassador pattern, we can implement less-frequent polling from clients along with latency checks and logging.

Microsoft documentation states

> An ambassador service can be thought of as an out-of-process proxy which is co-located with the client. This pattern can be useful for offloading common client connectivity tasks such as monitoring, logging, routing, security (such as TLS), and resiliency patterns in a language agnostic way. It is often used with legacy applications, or other applications that are difficult to modify, in order to extend their networking capabilities. It can also enable a specialized team to implement those features.

## Programmatic Example of Ambassador Pattern in Java

In this example of the Ambassador Pattern in Java, we demonstrate how to implement latency checks, logging, and retry mechanisms to improve system reliability.

A remote service has many clients accessing a function it provides. The service is a legacy application and is impossible to update. Large numbers of requests from users are causing connectivity issues. New rules for request frequency should be implemented along with latency checks and client-side logging.

With the above introduction in mind we will imitate the functionality in this example. We have an interface implemented by the remote service as well as the ambassador service.

```java
interface RemoteServiceInterface {
    long doRemoteFunction(int value) throws Exception;
}
```

A remote services represented as a singleton.

```java

@Slf4j
public class RemoteService implements RemoteServiceInterface {
    private static RemoteService service = null;

    static synchronized RemoteService getRemoteService() {
        if (service == null) {
            service = new RemoteService();
        }
        return service;
    }

    private RemoteService() {
    }

    @Override
    public long doRemoteFunction(int value) {
        long waitTime = (long) Math.floor(Math.random() * 1000);

        try {
            sleep(waitTime);
        } catch (InterruptedException e) {
            LOGGER.error("Thread sleep interrupted", e);
        }

        return waitTime >= 200 ? value * 10 : -1;
    }
}
```

A service ambassador adds additional features such as logging and latency checks.

```java

@Slf4j
public class ServiceAmbassador implements RemoteServiceInterface {
    private static final int RETRIES = 3;
    private static final int DELAY_MS = 3000;

    ServiceAmbassador() {
    }

    @Override
    public long doRemoteFunction(int value) {
        return safeCall(value);
    }

    private long checkLatency(int value) {
        var startTime = System.currentTimeMillis();
        var result = RemoteService.getRemoteService().doRemoteFunction(value);
        var timeTaken = System.currentTimeMillis() - startTime;

        LOGGER.info("Time taken (ms): " + timeTaken);
        return result;
    }

    private long safeCall(int value) {
        var retries = 0;
        var result = (long) FAILURE;

        for (int i = 0; i < RETRIES; i++) {
            if (retries >= RETRIES) {
                return FAILURE;
            }

            if ((result = checkLatency(value)) == FAILURE) {
                LOGGER.info("Failed to reach remote: (" + (i + 1) + ")");
                retries++;
                try {
                    sleep(DELAY_MS);
                } catch (InterruptedException e) {
                    LOGGER.error("Thread sleep state interrupted", e);
                }
            } else {
                break;
            }
        }
        return result;
    }
}
```

A client has a local service ambassador used to interact with the remote service.

```java

@Slf4j
public class Client {
    private final ServiceAmbassador serviceAmbassador = new ServiceAmbassador();

    long useService(int value) {
        var result = serviceAmbassador.doRemoteFunction(value);
        LOGGER.info("Service result: " + result);
        return result;
    }
}
```

Here are two clients using the service.

```java
public class App {
    public static void main(String[] args) {
        var host1 = new Client();
        var host2 = new Client();
        host1.useService(12);
        host2.useService(73);
    }
}
```

Here's the output for running the example:

```java
Time taken(ms):111
Service result:120
Time taken(ms):931
Failed to reach remote:(1)
Time taken(ms):665
Failed to reach remote:(2)
Time taken(ms):538
Failed to reach remote:(3)
Service result:-1
```

## When to Use the Ambassador Pattern in Java

* The Ambassador Pattern is particularly beneficial for Cloud Native and Microservices Architectures in Java. It helps in monitoring, logging, and securing inter-service communication, making it ideal for distributed systems.
* Legacy System Integration: Facilitates communication with newer services by handling necessary but non-core functionalities.
* Performance Enhancement: Can be used to cache results or compress data to improve communication efficiency.

Typical use cases include:

* Control access to another object
* Implement logging
* Implement circuit breaking
* Offload remote service tasks
* Facilitate network connection

## Benefits and Trade-offs of Ambassador Pattern

Benefits:

* Separation of Concerns: Offloads cross-cutting concerns from the service logic, leading to cleaner, more maintainable code.
* Reusable Infrastructure Logic: The ambassador pattern allows the same logic (e.g., logging, monitoring) to be reused across multiple services.
* Improved Security: Centralizes security features like SSL termination or authentication, reducing the risk of misconfiguration.
* Flexibility: Makes it easier to update or replace infrastructure concerns without modifying the service code.

Trade-offs:

* Increased Complexity: Adds another layer to the architecture, which can complicate the system design and debugging.
* Potential Performance Overhead: The additional network hop can introduce latency and overhead, particularly if not optimized.
* Deployment Overhead: Requires additional resources and management for deploying and scaling ambassador services.

## Real-World Applications of Ambassador Pattern in Java

* Service Mesh Implementations: In a service mesh architecture, like Istio or Linkerd, the Ambassador pattern is often employed as a sidecar proxy that handles inter-service communications. This includes tasks such as service discovery, routing, load balancing, telemetry (metrics and tracing), and security (authentication and authorization).
* API Gateways: API gateways can use the Ambassador pattern to encapsulate common functionalities like rate limiting, caching, request shaping, and authentication. This allows backend services to focus on their core business logic without being burdened by these cross-cutting concerns.
* Logging and Monitoring: An Ambassador can aggregate logs and metrics from various services and forward them to centralized monitoring tools like Prometheus or ELK Stack (Elasticsearch, Logstash, Kibana). This simplifies the logging and monitoring setup for each service and provides a unified view of the system's health.
* Security: Security-related functionalities such as SSL/TLS termination, identity verification, and encryption can be managed by an Ambassador. This ensures consistent security practices across services and reduces the likelihood of security breaches due to misconfigurations.
* Resilience: The Ambassador can implement resilience patterns like circuit breakers, retries, and timeouts. For instance, Netflix's Hystrix library can be used within an Ambassador to prevent cascading failures in a microservices ecosystem.
* Database Proxy: Ambassadors can act as proxies for database connections, providing functionalities like connection pooling, read/write splitting for replicas, and query caching. This offloads significant complexity from the application services.
* Legacy System Integration: In scenarios where modern microservices need to communicate with legacy systems, an Ambassador can serve as an intermediary that translates protocols, handles necessary transformations, and implements modern security practices, easing the integration process.
* Network Optimization: For services deployed across different geographical locations or cloud regions, Ambassadors can optimize communication by compressing data, batching requests, or even implementing smart routing to reduce latency and costs.
* [Kubernetes-native API gateway for microservices](https://github.com/datawire/ambassador)

## Related Java Design Patterns

* [Circuit Breaker](https://java-design-patterns.com/patterns/circuit-breaker/): Often used in conjunction to manage fault tolerance by stopping calls to an unresponsive service.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): The decorator pattern is used to add functionality to an object dynamically, while the ambassador pattern is used to offload functionality to a separate object.
* [Proxy](https://java-design-patterns.com/patterns/proxy/): Shares similarities with the proxy pattern, but the ambassador pattern specifically focuses on offloading ancillary functionalities.
* Sidecar: A similar pattern used in the context of containerized applications, where a sidecar container provides additional functionality to the main application container.

## References and Credits

* [Building Microservices: Designing Fine-Grained Systems](https://amzn.to/43aGpSR)
* [Cloud Native Patterns: Designing Change-tolerant Software](https://amzn.to/3wUAl4O)
* [Designing Distributed Systems: Patterns and Paradigms for Scalable, Reliable Services](https://amzn.to/3T9g9Uj)
* [Microservices Patterns: With examples in Java](https://amzn.to/3UyWD5O)
* [Ambassador pattern (Microsoft)](https://docs.microsoft.com/en-us/azure/architecture/patterns/ambassador)
