---
title: Service Locator
category: Structural
language: en
tag:
    - Decoupling
    - Dependency management
    - Enterprise patterns
    - Instantiation
---

## Also known as

* Service Registry

## Intent

The Service Locator design pattern provides a way to decouple the creation of clients and services by using a central registry to locate service instances.

## Explanation

Real-world example

> In a large hotel, the concierge desk acts as a Service Locator. When guests need a service, such as booking a restaurant reservation, finding transportation, or arranging a city tour, they do not directly seek out each service themselves. Instead, they go to the concierge desk, which locates and arranges the required services. This way, the guests are decoupled from the service providers and can rely on a central point to handle their requests, ensuring convenience and efficiency.

In plain words

> The Service Locator pattern centralizes the logic for locating services, thereby decoupling clients from the concrete implementations of these services.

Wikipedia says

> The service locator pattern is a design pattern used in software development to encapsulate the processes involved in obtaining a service with a strong abstraction layer. This pattern uses a central registry known as the "service locator", which on request returns the information necessary to perform a certain task. Proponents of the pattern say the approach simplifies component-based applications where all dependencies are cleanly listed at the beginning of the whole application design, consequently making traditional dependency injection a more complex way of connecting objects. Critics of the pattern argue that it is an antipattern which obscures dependencies and makes software harder to test.

**Programmatic Example**

The Service Locator design pattern is used to abstract the processes involved in obtaining a service. It uses a central registry, the "service locator", which returns the necessary information to perform a task upon request. This pattern is particularly useful in large-scale applications where services need to be centrally managed and reused.

In this example, we have a `Service` interface and a `ServiceLocator` class. The `Service` interface defines the methods that all services must implement. The `ServiceLocator` class is responsible for retrieving and caching these services.

```java
public interface Service {
    String getName();

    int getId();

    void execute();
}
```

The `Service` interface defines three methods: `getName()`, `getId()`, and `execute()`. Any class that implements this interface must provide an implementation for these methods.

```java
public class App {

    public static final String JNDI_SERVICE_A = "jndi/serviceA";
    public static final String JNDI_SERVICE_B = "jndi/serviceB";

    public static void main(String[] args) {
        // Get the service from the ServiceLocator
        var service = ServiceLocator.getService(JNDI_SERVICE_A);
        // Execute the service
        service.execute();
        // Get another service from the ServiceLocator
        service = ServiceLocator.getService(JNDI_SERVICE_B);
        // Execute the service
        service.execute();
        // Get the service from the ServiceLocator again
        service = ServiceLocator.getService(JNDI_SERVICE_A);
        // Execute the service
        service.execute();
        // Get the service from the ServiceLocator again
        service = ServiceLocator.getService(JNDI_SERVICE_A);
        // Execute the service
        service.execute();
    }
}
```

In the `App` class, we use the `ServiceLocator` to get services by their names and then execute them. The `ServiceLocator` handles the details of looking up and caching the services. This way, the `App` class is decoupled from the concrete implementations of the services.

Here is the output from running the example:

```
15:39:51.417 [main] INFO com.iluwatar.servicelocator.InitContext -- Looking up service A and creating new service for A
15:39:51.419 [main] INFO com.iluwatar.servicelocator.ServiceImpl -- Service jndi/serviceA is now executing with id 56
15:39:51.420 [main] INFO com.iluwatar.servicelocator.InitContext -- Looking up service B and creating new service for B
15:39:51.420 [main] INFO com.iluwatar.servicelocator.ServiceImpl -- Service jndi/serviceB is now executing with id 196
15:39:51.420 [main] INFO com.iluwatar.servicelocator.ServiceCache -- (cache call) Fetched service jndi/serviceA(56) from cache... !
15:39:51.420 [main] INFO com.iluwatar.servicelocator.ServiceImpl -- Service jndi/serviceA is now executing with id 56
15:39:51.420 [main] INFO com.iluwatar.servicelocator.ServiceCache -- (cache call) Fetched service jndi/serviceA(56) from cache... !
15:39:51.420 [main] INFO com.iluwatar.servicelocator.ServiceImpl -- Service jndi/serviceA is now executing with id 56
```

## Applicability

* Use when you want to decouple service creation from client classes to reduce dependencies and improve code maintainability.
* Applicable in large-scale enterprise applications where multiple services are used and dependencies need to be managed centrally.
* Suitable when service instances need to be reused or shared among multiple clients.

## Known Uses

* Enterprise Java applications often use Service Locator to manage business services.
* Spring Framework uses a similar concept with its BeanFactory and ApplicationContext for dependency injection.
* EJB (Enterprise JavaBeans) uses the Service Locator pattern to find and access EJB components.

## Consequences

Benefits:

* Decouples client and service classes, reducing code dependencies.
* Centralizes service management, making it easier to configure and manage services.
* Promotes reuse of service instances, which can improve performance and resource utilization.

Trade-offs:

* Can introduce a single point of failure if the Service Locator itself fails.
* May add complexity to the codebase, especially in terms of configuration and maintenance.
* Potential performance overhead due to the lookup mechanism.

## Related Patterns

* [Factory](https://java-design-patterns.com/patterns/factory/): Both patterns deal with object creation but Service Locator focuses on locating services while Factory focuses on creating them.
* [Dependency Injection](https://java-design-patterns.com/patterns/dependency-injection/): An alternative to Service Locator that injects dependencies directly into clients rather than having clients request them from a locator.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Service Locator often uses Singleton pattern to ensure a single instance of the locator.

## Credits

* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cAbDap)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Pattern-Oriented Software Architecture Volume 3: Patterns for Resource Management](https://amzn.to/4bnBcKZ)
