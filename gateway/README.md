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

> Consider a logistics company that uses multiple third-party services for various operations, such as shipping, inventory management, and customer notifications. Each of these services has its own API with different protocols and data formats. To simplify the interaction, the company implements a Gateway design pattern. This gateway acts as a unified interface for all third-party service interactions, allowing the company's internal systems to communicate with these services seamlessly. The gateway handles the translation of protocols, data transformation, and routing of requests, ensuring that the internal systems remain decoupled from the specifics of each external service. This setup improves maintainability and scalability while providing a single point of control for external communications.

In plain words

> Gateway can provide an interface which lets internal system to utilize external service.

Wikipedia says

> A server that acts as an API front-end, receives API requests, enforces throttling and security policies, passes requests to the back-end service and then passes the response back to the requester.

**Programmatic Example**

First, we define a `Gateway` interface. This interface represents the contract for our external services. Each service that we want to interact with will implement this interface.

```java
public interface Gateway {
  void execute();
}
```

Next, we create our external services. These are the services that our application needs to interact with. Each service implements the `Gateway` interface and provides its own implementation of the `execute` method.

```java
public class ExternalServiceA implements Gateway {
  @Override
  public void execute() {
    // Implementation for ExternalServiceA
  }
}

public class ExternalServiceB implements Gateway {
  @Override
  public void execute() {
    // Implementation for ExternalServiceB
  }
}

public class ExternalServiceC implements Gateway {
  @Override
  public void execute() {
    // Implementation for ExternalServiceC
  }
}
```

We then create a `GatewayFactory` class. This class maintains a registry of all available gateways. It provides methods to register a new gateway and to retrieve a gateway by its key.

```java
public class GatewayFactory {
  private Map<String, Gateway> gateways = new HashMap<>();

  public void registerGateway(String key, Gateway gateway) {
    gateways.put(key, gateway);
  }

  public Gateway getGateway(String key) {
    return gateways.get(key);
  }
}
```

Finally, we have our main application. The application uses the `GatewayFactory` to register and retrieve gateways. It then uses these gateways to interact with the external services.

```java
public class App {
  public static void main(String[] args) throws Exception {
    GatewayFactory gatewayFactory = new GatewayFactory();

    // Register different gateways
    gatewayFactory.registerGateway("ServiceA", new ExternalServiceA());
    gatewayFactory.registerGateway("ServiceB", new ExternalServiceB());
    gatewayFactory.registerGateway("ServiceC", new ExternalServiceC());

    // Use an executor service for execution
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

Running the example produces the following output.

```
09:24:44.030 [main] INFO com.iluwatar.gateway.ExternalServiceA -- Executing Service A
09:24:45.038 [main] INFO com.iluwatar.gateway.ExternalServiceB -- Executing Service B
09:24:46.043 [main] INFO com.iluwatar.gateway.ExternalServiceC -- Executing Service C
```

This example demonstrates how the Gateway design pattern can be used to simplify the interaction with multiple external services. Each service is encapsulated behind a common interface, and the application interacts with this interface rather than directly with the services. This reduces coupling and makes the application easier to maintain and extend.

## Class diagram

![Gateway](./etc/gateway.urm.png "Gateway")

## Applicability

Use the Gateway pattern when you need to integrate with remote services or APIs, and you want to minimize the coupling between your application and external systems. It is particularly useful in microservices architectures where different services need to communicate through well-defined APIs.

## Known uses

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

* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/3WcFVui)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Gateway (Martin Fowler)](https://martinfowler.com/articles/gateway-pattern.html)
