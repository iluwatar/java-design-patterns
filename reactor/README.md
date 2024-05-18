---
title: Reactor
category: Concurrency
language: en
tag:
    - Asynchronous
    - Event-driven
    - Fault tolerance
    - Messaging
    - Reactive
    - Scalability
    - Synchronization
    - Thread management
---

## Also known as

* Dispatcher
* Notifier

## Intent

Handle service requests that are delivered concurrently to a service handler by one or more inputs.

## Explanation

Real-world example

> Imagine a busy restaurant kitchen where multiple orders come in from different tables at the same time. Instead of each chef handling one order at a time, there is a head chef who acts as the dispatcher. The head chef receives all the orders and decides which chef will handle which part of each order, ensuring that all chefs are utilized efficiently. This way, the kitchen can handle many orders simultaneously, ensuring that dishes are prepared quickly and efficiently without any one chef becoming a bottleneck. This setup is analogous to the Reactor pattern, where the head chef dispatches tasks (events) to various chefs (event handlers) to process multiple tasks concurrently.

In plain words

> The Reactor pattern efficiently handles multiple concurrent service requests by dispatching them to appropriate event handlers using a single or a limited number of threads.

Wikipedia says

> The reactor software design pattern is an event handling strategy that can respond to many potential service requests concurrently. The pattern's key component is an event loop, running in a single thread or process, which demultiplexes incoming requests and dispatches them to the correct request handler.

**Programmatic Example**

The Reactor design pattern is a concurrency model that efficiently handles multiple simultaneous I/O operations using a single or a limited number of threads. It is particularly useful in scenarios where an application needs to handle multiple clients sending service requests concurrently.

In the given code, the Reactor pattern is implemented using Java's NIO (Non-blocking I/O) framework. The key components of this pattern in the code are:

1. `NioReactor`: This class acts as the Synchronous Event De-multiplexer and Initiation Dispatcher. It waits for events on multiple channels registered to it in an event loop and dispatches them to the appropriate handlers.

2. `AbstractNioChannel`: This class acts as a Handle that is registered to the reactor. When any events occur on a handle, the reactor calls the appropriate handler.

3. `ChannelHandler`: This class acts as an Event Handler, which is bound to a channel and is called back when any event occurs on any of its associated handles. Application logic resides in event handlers.

Here is a simplified example of how these components interact:

```java
// Create a dispatcher
Dispatcher dispatcher = new ThreadPoolDispatcher(2);

// Create a reactor with the dispatcher
NioReactor reactor = new NioReactor(dispatcher);

// Create a handler for handling events
ChannelHandler loggingHandler = new LoggingHandler();

// Register channels with the reactor
reactor.registerChannel(new NioServerSocketChannel(16666, loggingHandler));
reactor.registerChannel(new NioDatagramChannel(16668, loggingHandler));

// Start the reactor
reactor.start();
```

In this example, the `NioReactor` is created with a `ThreadPoolDispatcher` which uses 2 threads for dispatching events. Two channels, a `NioServerSocketChannel` and a `NioDatagramChannel`, are registered with the reactor. These channels are associated with a `LoggingHandler` which handles the events that occur on these channels. Finally, the reactor is started, and it begins to listen for events on the registered channels.

When an event occurs on a channel, the reactor's event loop detects it and dispatches the event to the `LoggingHandler` associated with that channel. The `LoggingHandler` then processes the event.

## Class diagram

![Reactor](./etc/reactor.png "Reactor")

## Applicability

* Use the Reactor pattern when you need to handle multiple simultaneous I/O operations efficiently.
* Ideal for applications requiring high scalability and low-latency, such as web servers and networking frameworks.

## Known Uses

* Netty: An asynchronous event-driven network application framework for rapid development of maintainable high-performance protocol servers and clients.
* Akka: A toolkit and runtime for building concurrent, distributed, and fault-tolerant applications on the JVM.
* Java NIO (New I/O): Provides non-blocking I/O operations, allowing a single thread to manage multiple channels.

## Consequences

Benefits:

* Improves application performance by efficiently handling multiple simultaneous connections.
* Reduces resource consumption by using a small number of threads to handle many I/O operations.
* Enhances scalability by allowing applications to serve many clients with minimal threads.

Trade-offs:

* Increased complexity in managing state and event handling.
* Debugging and maintaining asynchronous code can be challenging.
* Potential difficulty in ensuring thread safety and avoiding race conditions.

## Related Patterns

* [Observer](https://java-design-patterns.com/patterns/observer/): Reactor uses the Observer pattern for handling events where event handlers are notified of changes.
* Proactor: Similar to Reactor but handles asynchronous I/O completion rather than readiness.
* [Command](https://java-design-patterns.com/patterns/command/): Encapsulates a request as an object, allowing parameterization and queuing of requests.

## Credits

* [Douglas C. Schmidt - Reactor](https://www.dre.vanderbilt.edu/~schmidt/PDF/Reactor.pdf)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Pattern-Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://amzn.to/3UgC24V)
* [Reactive Programming with RxJava: Creating Asynchronous, Event-Based Applications](https://amzn.to/4dNTLJC)
* [Scalable IO in Java - Doug Lea](http://gee.cs.oswego.edu/dl/cpjslides/nio.pdf)
