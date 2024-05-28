---
title: Front Controller
category: Architectural
language: en
tag:
    - Architecture
    - Decoupling
    - Enterprise patterns
    - Layered architecture
    - Web development
---

## Also known as

* Centralized Request Handling

## Intent

The Front Controller design pattern aims to provide a centralized entry point for handling all incoming web requests, ensuring that request handling is managed consistently and efficiently across an application.

## Explanation

Real-world example

> Imagine a busy hotel where all guest requests and queries are first directed to a central reception desk. This desk acts as the "front controller" of the hotel, responsible for receiving all inquiries, from room service orders to maintenance requests. The receptionist assesses each request and routes it to the appropriate department—housekeeping, the kitchen, or maintenance. This system centralizes request handling, ensuring that guest needs are addressed efficiently and consistently, similar to how a Front Controller in a software application manages all incoming requests and delegates them to specific handlers.

In plain words

> The Front Controller design pattern centralizes incoming web requests into a single handling point, allowing consistent processing and delegation across an application.

Wikipedia says

> The front controller software design pattern is listed in several pattern catalogs and is related to the design of web applications. It is "a controller that handles all requests for a website", which is a useful structure for web application developers to achieve flexibility and reuse without code redundancy.

**Programmatic example**

The Front Controller design pattern is a pattern that provides a centralized entry point for handling all requests in a web application. It ensures that request handling is managed consistently and efficiently across an application.

In the provided code, we can see an example of the Front Controller pattern in the `App` and `FrontController` classes.

The `App` class is the entry point of the application. It creates an instance of `FrontController` and uses it to handle various requests.

```java
public class App {

  public static void main(String[] args) {
    var controller = new FrontController();
    controller.handleRequest("Archer");
    controller.handleRequest("Catapult");
    controller.handleRequest("foobar");
  }
}
```

The `FrontController` class is the front controller in this example. It handles all requests and routes them to the appropriate handlers.

```java
public class FrontController {

  public void handleRequest(String request) {
    Command command;

    switch (request) {
      case "Archer":
        command = new ArcherCommand();
        break;
      case "Catapult":
        command = new CatapultCommand();
        break;
      default:
        command = new UnknownCommand();
    }

    command.process();
  }
}
```

In this example, when a request is received, the `FrontController` creates a command object based on the request and calls its `process` method. The command object is responsible for handling the request and rendering the appropriate view.

This is a basic example of the Front Controller pattern, where all requests are handled by a single controller, ensuring consistent and efficient request handling.

## Class diagram

![Front Controller](./etc/front-controller.png "Front Controller")

## Applicability

* Web applications requiring a centralized mechanism for request handling.
* Systems that need a common processing point for all requests to perform tasks such as authentication, logging, and routing.

## Known uses

* [Apache Struts](https://struts.apache.org/)
* Java web frameworks like Spring MVC and JavaServer Faces (JSF) implement the Front Controller pattern through their central dispatcher servlet, which manages web requests and delegates responsibilities.

## Consequences

Benefits:

* Centralizes request handling, which simplifies maintenance and promotes consistency.
* Eases the integration of services like security and user session management.
* Facilitates common behavior like routing, logging, and authentication across requests.

Trade-offs:

* Can become a bottleneck if not properly managed.
* Increases complexity in the dispatcher controller, requiring careful design to avoid tight coupling.

## Related Patterns

* [Page Controller](https://java-design-patterns.com/patterns/page-controller/): Front Controller can delegate requests to Page Controllers, which handle specific page requests. This division supports the Single Responsibility Principle.
* [Model-View-Controller (MVC)](https://java-design-patterns.com/patterns/model-view-controller/): Front Controller acts as the controller, managing the flow between model and view.
* [Command](https://java-design-patterns.com/patterns/command/): Can be used to encapsulate a request as an object, which the Front Controller can manipulate and delegate.

## Credits

* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
