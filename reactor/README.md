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

1. **Reactor**: This is the event loop that demultiplexes the incoming requests and dispatches them to the appropriate handlers. In our example, `NioReactor` is the reactor.

2. **Dispatcher**: This is responsible for managing the execution of the tasks that are triggered by the events. In our example, `Dispatcher` is the dispatcher and `ThreadPoolDispatcher` is a concrete implementation of it.

3. **Handles**: These are resources that are managed by the reactor. They are associated with specific events and are used by the reactor to identify the event handlers to which the events should be dispatched. In our example, `AbstractNioChannel` represents a handle.

4. **Event Handlers**: These are associated with specific handles and are responsible for handling the events that occur on those handles. In our example, `ChannelHandler` is an event handler and `LoggingHandler` is a concrete implementation of it.

5. **Synchronous Event Demultiplexer**: This is a system-level component (not shown in the code) that provides a blocking call that waits for events to occur on any of the handles. In our example, this is part of the Java NIO framework.

6. **Concrete Event Handlers**: These are application-specific implementations of the event handlers. In our example, `LoggingHandler` is a concrete event handler.

7. **Initiation Dispatcher**: This is a component that initializes the association between event handlers and handles. In our example, this is done by the `registerChannel` method in the `NioReactor` class.

**Part 1: Creating the Dispatcher**

The first part of our example involves creating a dispatcher. The dispatcher is responsible for managing the execution of the tasks that are triggered by the events.

```java
// Create a dispatcher
Dispatcher dispatcher = new ThreadPoolDispatcher(2);
```

In this snippet, we're creating a `ThreadPoolDispatcher` with 2 threads. This dispatcher will use a thread pool to execute the tasks.

**Part 2: Creating the Reactor**

Next, we create a reactor with the dispatcher. The reactor is the core component of the Reactor pattern. It waits for events on multiple channels registered to it in an event loop and dispatches them to the appropriate handlers.

```java
// Create a reactor with the dispatcher
NioReactor reactor = new NioReactor(dispatcher);
```

Here, we're creating a `NioReactor` and passing the dispatcher we created earlier to its constructor.

**Part 3: Creating the Handler**

Now, we create a handler for handling events. The handler is responsible for processing the events that occur on the channels.

```java
// Create a handler for handling events
ChannelHandler loggingHandler = new LoggingHandler();
```

In this snippet, we're creating a `LoggingHandler`. This handler will log the events that occur on the channels.

**Part 4: Registering Channels with the Reactor**

Next, we register channels with the reactor. These channels are the sources of the events that the reactor will handle.

```java
// Register channels with the reactor
reactor.registerChannel(new NioServerSocketChannel(16666, loggingHandler));
reactor.registerChannel(new NioDatagramChannel(16668, loggingHandler));
```

Here, we're registering a `NioServerSocketChannel` and a `NioDatagramChannel` with the reactor. These channels are associated with the `LoggingHandler` we created earlier.

**Part 5: Starting the Reactor**

Finally, we start the reactor. Once started, the reactor begins to listen for events on the registered channels.

```java
// Start the reactor
reactor.start();
```

In this snippet, we're starting the reactor. From this point on, the reactor will start handling events from the registered channels.

**Part 6: Creating the App Class**

The `App` class is the entry point of our application. It creates the reactor, registers the channels, and starts the reactor.

```java
public class App {

    public static void main(String[] args) {
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
    }
}
```

In this snippet, we're creating an instance of the `App` class. Inside the `main` method, we're following the same steps as before: creating a dispatcher, creating a reactor with the dispatcher, creating a handler, registering channels with the reactor, and finally starting the reactor.

This `App` class demonstrates how an application interacts with the reactor. It sets up the necessary components (dispatcher, reactor, handler, channels) and starts the reactor. Once the reactor is started, it will handle events from the registered channels using the specified handler.

Running the code produces the following output:

```
09:50:08.317 [main] INFO com.iluwatar.reactor.framework.NioServerSocketChannel -- Bound TCP socket at port: 16666
09:50:08.320 [main] INFO com.iluwatar.reactor.framework.NioServerSocketChannel -- Bound TCP socket at port: 16667
09:50:08.323 [main] INFO com.iluwatar.reactor.framework.NioDatagramChannel -- Bound UDP socket at port: 16668
09:50:08.324 [main] INFO com.iluwatar.reactor.framework.NioDatagramChannel -- Bound UDP socket at port: 16669
09:50:08.324 [pool-2-thread-1] INFO com.iluwatar.reactor.framework.NioReactor -- Reactor started, waiting for events...
```

This concludes our detailed explanation of the Reactor design pattern. The Reactor pattern allows us to handle multiple simultaneous I/O operations efficiently using a single or a limited number of threads.

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

* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Pattern-Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://amzn.to/3UgC24V)
* [Reactive Programming with RxJava: Creating Asynchronous, Event-Based Applications](https://amzn.to/4dNTLJC)
* [Scalable IO in Java - Doug Lea](http://gee.cs.oswego.edu/dl/cpjslides/nio.pdf)
* [Reactor - An Object Behavioral Pattern for Demultiplexing and Dispatching Handles for Synchronous Events (Douglas C. Schmidt)](https://www.dre.vanderbilt.edu/~schmidt/PDF/Reactor.pdf)
