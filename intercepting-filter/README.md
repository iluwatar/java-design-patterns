---
title: Intercepting Filter
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

## Intent

The Intercepting Filter pattern is intended to provide a pluggable framework for preprocessing and postprocessing web requests and responses. It allows different filters to process client requests and server responses in a configurable, decoupled manner.

## Explanation

Real-world example

> Consider entering a secure office building where you pass through several checkpoints: a security desk checks your ID, a metal detector ensures safety, and a registration desk logs your visit. Each checkpoint acts like a filter in the Intercepting Filter pattern, processing and validating your entry step-by-step, similar to how filters handle different aspects of web requests and responses in a software system.

In plain words

> The Intercepting Filter design pattern allows you to define processing steps (filters) that execute sequentially to handle and modify web requests and responses before they reach the application or are sent to the client.

Wikipedia says

> Intercepting Filter is a Java pattern which creates pluggable filters to process common services in a standard manner without requiring changes to core request processing code.

## Programmatic Example

Intercepting Filter is a pattern that creates pluggable filters to process common services in a standard manner without requiring changes to core request processing code. These filters can perform tasks such as authentication, logging, data compression, and encryption.

In the provided code, we can see an example of the Intercepting Filter pattern in the `App`, `FilterManager`, `Client`, and various `Filter` classes.

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

## Applicability

Use the Intercepting Filter pattern when

* Use the Intercepting Filter pattern when you need to apply pre-processing and post-processing steps to requests and responses, typically in web applications.
* Suitable for handling cross-cutting concerns such as logging, authentication, data compression, and encryption transparently.

## Tutorials

* [Introduction to Intercepting Filter Pattern in Java (Baeldung)](https://www.baeldung.com/intercepting-filter-pattern-in-java)
* [Design Pattern - Intercepting Filter Pattern (TutorialsPoint)](http://www.tutorialspoint.com/design_pattern/intercepting_filter_pattern.htm)

## Known Uses

* Web servers like Apache Tomcat and Java EE web containers often use this pattern to implement filters that manipulate byte streams from requests and responses.
* Frameworks like Spring MVC utilize this pattern to manage interceptors that add behavior to web requests.
* [javax.servlet.FilterChain](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/FilterChain.html) and [javax.servlet.Filter](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/Filter.html)
* [Struts 2 - Interceptors](https://struts.apache.org/core-developers/interceptors.html)

## Consequences

Benefits:

* Promotes separation of concerns by allowing filters to be independently developed, tested, and reused.
* Enhances flexibility through configurable filter chains.
* Simplifies application maintenance by centralizing control in filter management.

Trade-offs:

* Introducing many filters can lead to performance overhead due to the processing of each request and response through multiple filters.
* Debugging and tracing the request flow through multiple filters can be complex.

## Related Patterns

* [Decorator](https://java-design-patterns.com/patterns/decorator/): Filters in the Intercepting Filter pattern can be considered as decorators that add additional responsibilities to request handling. They modify the request/response without altering their fundamental behavior.
* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain-of-responsibility/): Filters are linked in a chain, where each filter processes the request or response and optionally passes it to the next filter in the chain, similar to how responsibilities are passed along in the Chain of Responsibility pattern.

## Credits

* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cAbDap)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
