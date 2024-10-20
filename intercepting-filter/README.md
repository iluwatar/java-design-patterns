---
title: "Intercepting Filter Pattern in Java: Enhancing Request Processing in Web Applications"
shortTitle: Intercepting Filter
description: "Learn about the Intercepting Filter Pattern in Java. Discover how to design, implement, and use this pattern to enhance web request handling with practical examples and detailed explanations."
category: Architectural
language: en
tag:
  - API design
  - Decoupling
  - Layered architecture
  - Performance
  - Security
  - Web development
---

## Intent of Intercepting Filter Design Pattern

The Intercepting Filter Pattern in Java is a powerful design pattern that allows for efficient web request handling. This pattern enables the application of multiple filters in a filter chain to process and modify requests and responses.

## Detailed Explanation of Intercepting Filter Pattern with Real-World Examples

Real-world example

> Consider entering a secure office building where you pass through several checkpoints: a security desk checks your ID, a metal detector ensures safety, and a registration desk logs your visit. Each checkpoint acts like a filter in the Intercepting Filter pattern, processing and validating your entry step-by-step, similar to how filters handle different aspects of web requests and responses in a software system.

In plain words

> The Intercepting Filter design pattern allows you to define processing steps (filters) that execute sequentially to handle and modify web requests and responses before they reach the application or are sent to the client.

Wikipedia says

> Intercepting Filter is a Java pattern which creates pluggable filters to process common services in a standard manner without requiring changes to core request processing code.

## Programmatic Example of Intercepting Filter Pattern in Java

In this article, we delve into the Intercepting Filter Pattern and provide a Java example to illustrate its use. This pattern is essential for Java web development, offering a modular approach to handling common services such as logging, authentication, and data compression.

The Java implementation of the Intercepting Filter Pattern includes classes like `FilterManager` and `Client`, which facilitate the management and application of filters. Each filter in the chain performs specific tasks, ensuring a clean and efficient design.

The `App` class is the entry point of the application. It creates an instance of `FilterManager`, adds various filters to it, and sets it to a `Client`.

```java
public class App {

  public static void main(String[] args) {
    var filterManager = new FilterManager();
    filterManager.addFilter(new NameFilter());
    filterManager.addFilter(new ContactFilter());
    filterManager.addFilter(new AddressFilter());
    filterManager.addFilter(new DepositFilter());
    filterManager.addFilter(new OrderFilter());

    var client = new Client();
    client.setFilterManager(filterManager);
  }
}
```

The `FilterManager` class manages the filters and applies them to the requests.

```java
public class FilterManager {
  private final List<Filter> filters = new ArrayList<>();

  public void addFilter(Filter filter) {
    filters.add(filter);
  }

  public void filterRequest(String request) {
    for (Filter filter : filters) {
      filter.execute(request);
    }
  }
}
```

The `Client` class sends the request to the `FilterManager`.

```java
public class Client {
  private FilterManager filterManager;

  public void setFilterManager(FilterManager filterManager) {
    this.filterManager = filterManager;
  }

  public void sendRequest(String request) {
    filterManager.filterRequest(request);
  }
}
```

The `Filter` interface and its implementations (`NameFilter`, `ContactFilter`, `AddressFilter`, `DepositFilter`, `OrderFilter`) define the filters that can be applied to the requests.

```java
public interface Filter {
  void execute(String request);
}

public class NameFilter extends AbstractFilter {

    @Override
    public String execute(Order order) {
        var result = super.execute(order);
        var name = order.getName();
        if (name == null || name.isEmpty() || name.matches(".*[^\\w|\\s]+.*")) {
            return result + "Invalid name! ";
        } else {
            return result;
        }
    }
}

// Other Filter implementations...
```

In this example, the `App` class sets up a `FilterManager` with various filters and assigns it to a `Client`. When the `Client` sends a request, the `FilterManager` applies all the filters to the request. This is a basic example of the Intercepting Filter pattern, where common processing tasks are encapsulated in filters and applied to requests in a standard manner.

## When to Use the Intercepting Filter Pattern in Java

Use the Intercepting Filter pattern when

* In Java web applications to manage cross-cutting concerns.
* When you need to apply pre-processing and post-processing steps to requests and responses, typically in web applications.
* Suitable for handling cross-cutting concerns such as logging, authentication, data compression, and encryption transparently.

## Intercepting Filter Pattern Java Tutorials

* [Introduction to Intercepting Filter Pattern in Java (Baeldung)](https://www.baeldung.com/intercepting-filter-pattern-in-java)
* [Design Pattern - Intercepting Filter Pattern (TutorialsPoint)](http://www.tutorialspoint.com/design_pattern/intercepting_filter_pattern.htm)

## Real-World Applications of Intercepting Filter Pattern in Java

* Frameworks like Spring MVC and web servers such as Apache Tomcat utilize the Intercepting Filter Pattern to enhance Java web development. This pattern's ability to centralize control and streamline web request handling makes it a go-to choice for developers.
* [javax.servlet.FilterChain](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/FilterChain.html) and [javax.servlet.Filter](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/Filter.html)
* [Struts 2 - Interceptors](https://struts.apache.org/core-developers/interceptors.html)

## Benefits and Trade-offs of Intercepting Filter Pattern

Benefits:

* Promotes separation of concerns by allowing filters to be independently developed, tested, and reused.
* Enhances flexibility through configurable filter chains.
* Simplifies application maintenance by centralizing control in filter management.

Trade-offs:

* Introducing many filters can lead to performance overhead due to the processing of each request and response through multiple filters.
* Debugging and tracing the request flow through multiple filters can be complex.

## Related Java Design Patterns

* [Decorator](https://java-design-patterns.com/patterns/decorator/): Filters in the Intercepting Filter pattern can be considered as decorators that add additional responsibilities to request handling. They modify the request/response without altering their fundamental behavior.
* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain-of-responsibility/): Filters are linked in a chain, where each filter processes the request or response and optionally passes it to the next filter in the chain, similar to how responsibilities are passed along in the Chain of Responsibility pattern.

## References and Credits

* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cAbDap)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
