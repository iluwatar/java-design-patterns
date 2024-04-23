---
title: Gateway
category: Integration
language: en
tag:
    - API design
    - Data access
    - Decoupling
    - Enterprise patterns
---

## Also known as

* Service Gateway

## Intent

The Gateway design pattern aims to encapsulate the interaction with a remote service or external system, providing a simpler and more unified API to the rest of the application.

## Explanation

Real-world example

> Gateway acts like a real front gate of a certain city. The people inside the city are called internal system, and different outside cities are called external services. The gateway is here to provide access for internal system to different external services.

In plain words

> Gateway can provide an interface which lets internal system to utilize external service.

Wikipedia says

> A server that acts as an API front-end, receives API requests, enforces throttling and security policies, passes requests to the back-end service and then passes the response back to the requester.

**Programmatic Example**

The main class in our example is the `ExternalService` that contains items.

```java
class ExternalServiceA implements Gateway {
    @Override
    public void execute() throws Exception {
        LOGGER.info("Executing Service A");
        // Simulate a time-consuming task
        Thread.sleep(1000);
    }
}

/**
 * ExternalServiceB is one of external services.
 */
class ExternalServiceB implements Gateway {
    @Override
    public void execute() throws Exception {
        LOGGER.info("Executing Service B");
        // Simulate a time-consuming task
        Thread.sleep(1000);
    }
}

/**
 * ExternalServiceC is one of external services.
 */
class ExternalServiceC implements Gateway {
    @Override
    public void execute() throws Exception {
        LOGGER.info("Executing Service C");
        // Simulate a time-consuming task
        Thread.sleep(1000);
    }

    public void error() throws Exception {
        // Simulate an exception
        throw new RuntimeException("Service C encountered an error");
    }
}
```

To operate these external services, Here's the `App` class:

```java
public class App {
    /**
     * Simulate an application calling external services.
     */
    public static void main(String[] args) throws Exception {
        GatewayFactory gatewayFactory = new GatewayFactory();

        // Register different gateways
        gatewayFactory.registerGateway("ServiceA", new ExternalServiceA());
        gatewayFactory.registerGateway("ServiceB", new ExternalServiceB());
        gatewayFactory.registerGateway("ServiceC", new ExternalServiceC());

        // Use an executor service for asynchronous execution
        Gateway serviceA = gatewayFactory.getGateway("ServiceA");
        Gateway serviceB = gatewayFactory.getGateway("ServiceB");
        Gateway serviceC = gatewayFactory.getGateway("ServiceC");

        // Execute external services
        try {
            serviceA.execute();
            serviceB.execute();
            serviceC.execute();
        } catch (ThreadDeath e) {
            LOGGER.info("Interrupted!" + e);
            throw e;
        }
    }
}
```

The `Gateway` interface is extremely simple.

```java
interface Gateway {
    void execute() throws Exception;
}
```

Program output:

```java
Executing Service A
Executing Service B
Executing Service C
```

## Class diagram

![Gateway](./etc/gateway.urm.png "gateway")

## Applicability

Use the Gateway pattern when you need to integrate with remote services or APIs, and you want to minimize the coupling between your application and external systems. It is particularly useful in microservices architectures where different services need to communicate through well-defined APIs.

## Tutorials

* [Pattern: API Gateway / Backends for Frontends](https://microservices.io/patterns/apigateway.html)

## Known uses

* [10 most common use cases of an API Gateway](https://apisix.apache.org/blog/2022/10/27/ten-use-cases-api-gateway/)
* API Gateways in Microservices: Acts as an intermediary that processes incoming requests from clients, directing them to appropriate services within a microservices architecture.
* Database Gateways: Provides a unified interface to access data from various database systems, hiding the specifics of database querying and data retrieval.

## Consequences

Benefits:

* Reduces complexity by hiding the details of the external API or service behind a simpler interface.
* Promotes loose coupling between the application and its dependencies on external systems.
* Makes the system easier to test and maintain.

Trade-offs:

* Introduces an additional layer that could potentially impact performance.
* Requires careful design to avoid creating a monolithic gateway that becomes a bottleneck.

## Related Patterns

* [Facade](https://java-design-patterns.com/patterns/facade/): Similar to Gateway in abstracting complex subsystems, but Gateway specifically targets external or remote interfaces.
* [Adapter](https://java-design-patterns.com/patterns/adapter/): While both patterns provide a different interface to a subsystem, Gateway focuses more on networked data sources and services.
* [Proxy](https://java-design-patterns.com/patterns/proxy/): Often used together, as both can control and manage access to another object, but Gateway specifically deals with external services.
* [API Gateway](https://java-design-patterns.com/patterns/api-gateway/): Often considered a specialization of the Gateway pattern, it specifically manages API requests and routes them to the appropriate services within a backend system.

## Credits

* [Gateway - Martin Fowler](https://martinfowler.com/articles/gateway-pattern.html)
* [What is the difference between Facade and Gateway design patterns? - Stack Overflow](https://stackoverflow.com/questions/4422211/what-is-the-difference-between-facade-and-gateway-design-patterns)
* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/3WcFVui)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
