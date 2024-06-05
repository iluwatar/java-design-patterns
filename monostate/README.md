---
title: Monostate
category: Creational
language: en
tag:
    - Encapsulation
    - Instantiation
    - Object composition
    - Persistence
    - Polymorphism
---

## Also known as

* Borg

## Intent

Monostate is an alternative approach to achieving a singleton-like behavior in object-oriented design. It enforces a unique behavior where all instances of a class share the same state. Unlike the Singleton pattern, which restricts a class to a single instance, Monostate allows for multiple instances but ensures they all have a shared state.

## Explanation

Real-word example

> Imagine a library with multiple desks where patrons can access the library's catalog. While each desk appears to be independent, any changes made to the catalog (like adding or removing a book) are immediately reflected at all desks. This setup ensures that no matter which desk a patron uses, they see the exact same, up-to-date catalog. This is analogous to the Monostate pattern, where multiple instances of a class share the same state, ensuring consistent data across all instances.

In plain words

> Monostate allows multiple instances of a class to share the same state by channeling their state management through a common shared structure. This ensures consistent state across all instances while maintaining the facade of independent objects.

wiki.c2.com says

> A monostate is a "conceptual singleton" - all data members of a monostate are static, so all instances of the monostate use the same (static) data. Applications using a monostate can create any number of instances that they desire, as each instance uses the same data.

**Programmatic Examples**

The Monostate pattern is a way to ensure that all instances of a class share the same state. This is achieved by using static fields in the class. Any changes to these fields will be reflected across all instances of the class. This pattern is useful when you want to avoid global variables but still need a shared state across multiple instances.

Let's take a look at the `LoadBalancer` class from the `monostate` module:

```java
public class LoadBalancer {
    private static List<Server> servers = new ArrayList<>();
    private static int nextServerIndex = 0;

    public LoadBalancer() {
        // Initialize servers
        servers.add(new Server("192.168.0.1", 8080, 1));
        servers.add(new Server("192.168.0.2", 8080, 2));
        servers.add(new Server("192.168.0.3", 8080, 3));
    }

    public void serverRequest(Request request) {
        Server server = servers.get(nextServerIndex);
        server.serve(request);
        nextServerIndex = (nextServerIndex + 1) % servers.size();
    }
}
```

In this class, `servers` and `nextServerIndex` are static fields. This means that they are shared across all instances of `LoadBalancer`. The method `serverRequest` is used to serve a request and then update the `nextServerIndex` to the next server in the list.

Now, let's see how this works in practice:

```java
public class App {
    public static void main(String[] args) {
        LoadBalancer loadBalancer1 = new LoadBalancer();
        LoadBalancer loadBalancer2 = new LoadBalancer();

        // Both instances share the same state
        loadBalancer1.serverRequest(new Request("Hello"));  // Server 1 serves the request
        loadBalancer2.serverRequest(new Request("Hello World"));  // Server 2 serves the request
    }
}
```

In this example, we create two instances of `LoadBalancer`: `loadBalancer1` and `loadBalancer2`. They share the same state. When we make a request via `loadBalancer1`, the request is served by the first server. When we make a request via `loadBalancer2`, the request is served by the second server, not the first one, because the `nextServerIndex` has been updated by `loadBalancer1`. This demonstrates the Monostate pattern in action.

## Applicability

Use the Monostate pattern when

1. **Shared State Across Instances:** All instances of a class must share the same state. Changes in one instance should be reflected across all instances.

2. **Transparent Usage:** Unlike Singleton, which can be less transparent in its usage, Monostate allows for a more transparent way of sharing state across instances. Clients interact with instances of the Monostate class as if they were regular instances, unaware of the shared state.

3. **Subclass Flexibility:** Monostate provides an advantage over Singleton when it comes to extending behavior through subclasses. Subclasses of a Monostate class can introduce additional behavior or modify existing behavior while still sharing the same state as instances of the base class. This allows for dynamic and diverse behaviors across different parts of an application, all while maintaining a shared state.

4. **Avoiding Global Variables:** The pattern is applicable when you want to avoid global variables but still need a shared state across multiple instances.

5. **Integration with Existing Systems:** Monostate can be more easily integrated into existing systems that expect to work with instances rather than a single global instance. This can lead to a smoother transition when refactoring code to use shared state.

6. **Consistent Configuration or State Management:** In scenarios where you need consistent configuration management or state management across different parts of an application, Monostate provides a pattern to ensure all parts of the system are in sync.

## Known uses

* Configuration settings that need to be shared and accessible by various parts of an application.
* Resource pools where the state needs to be consistent across different consumers.

## Consequences

Benefits:

* Provides a shared state without restricting the creation of multiple instances.
* Ensures consistency of data across instances.

Trade-offs:

* Can lead to hidden dependencies due to shared state, making the system harder to understand and debug.
* Reduces the flexibility to have instances with independent states.

## Related Patterns

* [Singleton](https://java-design-patterns.com/patterns/singleton/): Both Singleton and Monostate ensure a single shared state, but Singleton does so by restricting instance creation.
* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): Flyweight shares state to reduce memory usage, similar to how Monostate shares state among instances.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
