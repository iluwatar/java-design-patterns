---
title: Event-Based Asynchronous
category: Concurrency
language: en
tag:
    - Asynchronous
    - Decoupling
    - Event-driven
    - Fault tolerance
    - Messaging
    - Reactive
    - Scalability
---

## Also known as

* Asynchronous Event Handling

## Intent

The Event-Based Asynchronous pattern allows a system to handle tasks that might take some time to complete without blocking the execution of the program. It enables better resource utilization by freeing up a thread that would otherwise be blocked waiting for the task to complete.

## Explanation

Real-world example

> A real-world analogy of the Event-Based Asynchronous design pattern is how a restaurant operates. When a customer places an order, the waiter records the order and passes it to the kitchen. Instead of waiting at the kitchen for the food to be prepared, the waiter continues to serve other tables. Once the kitchen completes the order, they signal (event) the waiter, who then delivers the food to the customer. This allows the waiter to handle multiple tasks efficiently without idle waiting, similar to how asynchronous programming handles tasks in parallel, enhancing overall efficiency and responsiveness.

In Plain Words

> The Event-Based Asynchronous design pattern allows tasks to be executed in the background, notifying the main program via events when completed, thereby enhancing system efficiency and responsiveness without blocking ongoing operations.

**Programmatic Example**

The Event-Based Asynchronous design pattern allows tasks to be executed in the background, notifying the main program via events when completed. This enhances system efficiency and responsiveness without blocking ongoing operations.

In the provided code, we have several key classes implementing this pattern:

- `App`: The main class that runs the application. It interacts with the `EventManager` to create, start, stop, and check the status of events.
- `EventManager`: Manages the lifecycle of events, including creating, starting, stopping, and checking the status of events. It maintains a map of event IDs to `Event` objects.
- `Event`: An abstract class that represents an event. It has two concrete subclasses: `AsyncEvent` and `SyncEvent`.
- `AsyncEvent` and `SyncEvent`: Represent asynchronous and synchronous events respectively.
- Custom exceptions: Thrown by the `EventManager` when certain conditions are not met.

Here's a simplified code example of how these classes interact:

```java
// Create an EventManager
EventManager eventManager = new EventManager();

// Create an asynchronous event that runs for 60 seconds
int asyncEventId = eventManager.createAsync(Duration.ofSeconds(60));

// Start the asynchronous event
eventManager.start(asyncEventId);

// Check the status of the asynchronous event
eventManager.status(asyncEventId);

// Stop the asynchronous event
eventManager.cancel(asyncEventId);
```

In this example, the `App` class creates an `EventManager`, then uses it to create, start, check the status of, and stop an asynchronous event. The `EventManager` creates an `AsyncEvent` object, starts it in a separate thread, checks its status, and stops it when requested.

The `EventManager` class is the core of the Event-Based Asynchronous pattern implementation. It manages the lifecycle of events, including creating, starting, stopping, and checking the status of events. It maintains a map of event IDs to `Event` objects. Here's a snippet of how it creates an asynchronous event:

```java
public int createAsync(Duration runtime) throws MaxNumOfEventsAllowedException, LongRunningEventException {
  int id = counter.incrementAndGet();
  events.put(id, new AsyncEvent(id, runtime));
  return id;
}
```

The `Event` class is an abstract class that represents an event. It has two concrete subclasses: `AsyncEvent` and `SyncEvent`. An `Event` has an ID, a runtime (how long it should run), and a status (whether it's running, completed, or ready to start). It also has methods to start and stop the event. Here's a snippet of how an `AsyncEvent` starts:

```java
@Override
public void start() {
  Thread thread = new Thread(() -> {
    try {
      handleRunStart();
      Thread.sleep(getRuntime().toMillis());
      handleRunComplete();
    } catch (InterruptedException e) {
      handleRunFailure(e.getMessage());
    }
  });
  thread.start();
}
```

In this snippet, when an `AsyncEvent` is started, it runs in a separate thread without blocking the main thread.

A synchronous event is created and managed similarly to an asynchronous event. Here's a snippet of how it creates and manages a synchronous event:

```java
// Create an EventManager
EventManager eventManager = new EventManager();

// Create a synchronous event that runs for 60 seconds
int syncEventId = eventManager.create(Duration.ofSeconds(60));

// Start the synchronous event
eventManager.start(syncEventId);

// Check the status of the synchronous event
eventManager.status(syncEventId);

// Stop the synchronous event
eventManager.cancel(syncEventId);
```

In the `EventManager` class, a synchronous event is created using the `create` method:

```java
public int create(Duration runtime) throws MaxNumOfEventsAllowedException, LongRunningEventException {
  int id = counter.incrementAndGet();
  events.put(id, new SyncEvent(id, runtime));
  return id;
}
```

The `SyncEvent` class is a subclass of `Event` that represents a synchronous event. When a `SyncEvent` is started, it runs on the main thread and blocks it until the event is completed. Here's a snippet of how a `SyncEvent` starts:

```java
@Override
public void start() {
  try {
    handleRunStart();
    Thread.sleep(getRuntime().toMillis());
    handleRunComplete();
  } catch (InterruptedException e) {
    handleRunFailure(e.getMessage());
  }
}
```

In this snippet, when a `SyncEvent` is started, it runs on the main thread, blocking it until the event is completed. This is in contrast to an `AsyncEvent`, which runs in a separate thread without blocking the main thread.

These are the key parts of the Event-Based Asynchronous design pattern as implemented in this code. The pattern allows tasks to be executed in the background, notifying the main program via events when completed, thereby enhancing system efficiency and responsiveness without blocking ongoing operations.

## Class diagram

![Event-Based Asynchronous](./etc/event-asynchronous.png "Event-Based Asynchronous")

## Applicability

* When multiple tasks can be processed in parallel and independently.
* Systems that require responsiveness and cannot afford to have threads blocked waiting for an operation to complete.
* In GUI applications where user interface responsiveness is critical.
* Distributed systems where long network operations are involved.

## Known Uses

* GUI libraries in Java (e.g., JavaFX, Swing with SwingWorker).
* Java Message Service (JMS) for handling asynchronous messaging.
* Java’s CompletableFuture and various Event-Driven Frameworks.

## Consequences

Benefits:

* Improves application scalability and responsiveness.
* Reduces the resources wasted on threads that would simply wait for I/O operations.
* Enhances fault tolerance through isolation of process execution.

Trade-offs:

* Increases complexity of error handling as errors may occur in different threads or at different times.
* Can lead to harder-to-follow code and debugging challenges due to the non-linear nature of asynchronous code execution.

Related Patterns

* [Observer](https://java-design-patterns.com/patterns/observer/): Often used in conjunction where the observer reacts to events as they occur.
* Publish/Subscribe: Related in terms of event handling mechanisms, particularly for messaging and event distribution across components.
* [Command](https://java-design-patterns.com/patterns/command/): Useful for encapsulating all information needed to perform an action or trigger an event.

## Credits

* [Java Concurrency in Practice](https://amzn.to/4cYY4kU)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3Uh7rW1)
* [Pro JavaFX 8: A Definitive Guide to Building Desktop, Mobile, and Embedded Java Clients](https://amzn.to/3vHUqLL)
* [Event-based Asynchronous Pattern Overview (Microsoft)](https://msdn.microsoft.com/en-us/library/wewwczdw%28v=vs.110%29.aspx?f=255&MSPPError=-2147217396)
