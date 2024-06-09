---
title: "Chain of Responsibility Pattern in Java: Building Robust Request Handling Mechanisms"
shortTitle: Chain of Responsibility
description: "Learn the Chain of Responsibility design pattern in Java with real-world examples, code snippets, and class diagrams. Enhance your coding skills with our detailed explanations."
category: Behavioral
language: en
tag:
  - Decoupling
  - Event-driven
  - Gang of Four
  - Messaging
---

## Also known as

* Chain of Command
* Chain of Objects
* Responsibility Chain

## Intent of Chain of Responsibility Design Pattern

The Chain of Responsibility pattern in Java is a behavioral design pattern that decouples the sender of a request from its receivers by giving more than one object a chance to handle the request. The receiving objects are chained and the request is passed along the chain until an object handles it.

## Detailed Explanation of Chain of Responsibility Pattern with Real-World Examples

Real-world example

> A real-world example of the Chain of Responsibility pattern in Java is a technical support call center. When implementing this Java design pattern, each level of support represents a handler in the chain. When a customer calls in with an issue, the call is first received by a front-line support representative. If the issue is simple, the representative handles it directly. If the issue is more complex, the representative forwards the call to a second-level support technician. This process continues, with the call being escalated through multiple levels of support until it reaches a specialist who can resolve the problem. Each level of support represents a handler in the chain, and the call is passed along the chain until it finds an appropriate handler, thereby decoupling the request from the specific receiver.

In plain words

> It helps to build a chain of objects. A request enters from one end and keeps going from an object to another until it finds a suitable handler.

Wikipedia says

> In object-oriented design, the chain-of-responsibility pattern is a design pattern consisting of a source of command objects and a series of processing objects. Each processing object contains logic that defines the types of command objects that it can handle; the rest are passed to the next processing object in the chain.

## Programmatic Example of Chain of Responsibility Pattern

In this Java example, the Orc King gives orders which are processed by a chain of command representing the Chain of Responsibility pattern. Learn how to implement this design pattern in Java with the following code snippet.

The Orc King gives loud orders to his army. The closest one to react is the commander, then an officer, and then a soldier. The commander, officer, and soldier form a chain of responsibility.

First, we have the `Request` class:

```java
@Getter
public class Request {

    private final RequestType requestType;
    private final String requestDescription;
    private boolean handled;

    public Request(final RequestType requestType, final String requestDescription) {
        this.requestType = Objects.requireNonNull(requestType);
        this.requestDescription = Objects.requireNonNull(requestDescription);
    }

    public void markHandled() {
        this.handled = true;
    }

    @Override
    public String toString() {
        return getRequestDescription();
    }
}

public enum RequestType {
    DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX
}
```

Next, we show the `RequestHandler` hierarchy.

```java
public interface RequestHandler {

    boolean canHandleRequest(Request req);

    int getPriority();

    void handle(Request req);

    String name();
}

@Slf4j
public class OrcCommander implements RequestHandler {
    @Override
    public boolean canHandleRequest(Request req) {
        return req.getRequestType() == RequestType.DEFEND_CASTLE;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void handle(Request req) {
        req.markHandled();
        LOGGER.info("{} handling request \"{}\"", name(), req);
    }

    @Override
    public String name() {
        return "Orc commander";
    }
}

// OrcOfficer and OrcSoldier are defined similarly as OrcCommander ...

```

The `OrcKing` gives the orders and forms the chain.

```java
public class OrcKing {

    private List<RequestHandler> handlers;

    public OrcKing() {
        buildChain();
    }

    private void buildChain() {
        handlers = Arrays.asList(new OrcCommander(), new OrcOfficer(), new OrcSoldier());
    }

    public void makeRequest(Request req) {
        handlers
                .stream()
                .sorted(Comparator.comparing(RequestHandler::getPriority))
                .filter(handler -> handler.canHandleRequest(req))
                .findFirst()
                .ifPresent(handler -> handler.handle(req));
    }
}
```

The chain of responsibility in action.

```java
  public static void main(String[] args) {

    var king = new OrcKing();
    king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
    king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torture prisoner"));
    king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));
}
```

The console output:

```
Orc commander handling request "defend castle"
Orc officer handling request "torture prisoner"
Orc soldier handling request "collect tax"
```

## Chain of Responsibility Pattern Class Diagram

![Chain of Responsibility](./etc/chain-of-responsibility.urm.png "Chain of Responsibility class diagram")

## When to Use the Chain of Responsibility Pattern in Java

Use Chain of Responsibility when

* More than one object may handle a request, and the handler isn't known a priori. The handler should be ascertained automatically.
* You want to issue a request to one of several objects without specifying the receiver explicitly.
* The set of objects that can handle a request should be specified dynamically.

## Real-World Applications of Chain of Responsibility Pattern in Java

* Event bubbling in GUI frameworks where an event might be handled at multiple levels of a UI component hierarchy
* Middleware frameworks where a request passes through a chain of processing objects
* Logging frameworks where messages can be passed through a series of loggers, each possibly handling them differently
* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)
* [Apache Commons Chain](https://commons.apache.org/proper/commons-chain/index.html)
* [javax.servlet.Filter#doFilter()](http://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html#doFilter-javax.servlet.ServletRequest-javax.servlet.ServletResponse-javax.servlet.FilterChain-)

## Benefits and Trade-offs of Chain of Responsibility Pattern

Benefits:

* Reduced coupling. The sender of a request does not need to know the concrete handler that will process the request.
* Increased flexibility in assigning responsibilities to objects. You can add or change responsibilities for handling a request by changing the members and order of the chain.
* Allows you to set a default handler if no concrete handler can handle the request.

Trade-Offs:

* It can be challenging to debug and understand the flow, especially if the chain is long and complex.
* The request might end up unhandled if the chain doesn't include a catch-all handler.
* Performance concerns might arise due to potentially going through several handlers before finding the right one, or not finding it at all.

## Related Java Design Patterns

* [Command](https://java-design-patterns.com/patterns/command/): can be used to encapsulate a request as an object, which might be passed along the chain.
* [Composite](https://java-design-patterns.com/patterns/composite/): the Chain of Responsibility is often applied in conjunction with the Composite pattern.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): decorators can be chained in a similar manner as responsibilities in the Chain of Responsibility pattern.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/3PAJUg5)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
* [Pattern languages of program design 3](https://amzn.to/4a4NxTH)
