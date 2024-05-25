---
title: Event-Driven Architecture
category: Architectural
language: en
tag:
    - Asynchronous
    - Decoupling
    - Enterprise patterns
    - Event-driven
    - Messaging
    - Publish/subscribe
    - Reactive
    - Scalability
---

## Also known as

* Event-Driven System
* Event-Based Architecture

## Intent

Event-Driven Architecture (EDA) is designed to orchestrate behavior around the production, detection, consumption of, and reaction to events. This architecture enables highly decoupled, scalable, and dynamic interconnections between event producers and consumers.

## Explanation

Real-world example

> A real-world example of the Event-Driven Architecture (EDA) pattern is the operation of an air traffic control system. In this system, events such as aircraft entering airspace, changes in weather conditions, and ground vehicle movements trigger specific responses like altering flight paths, scheduling gate assignments, and updating runway usage. This setup allows for highly efficient, responsive, and safe management of airport operations, reflecting EDA's core principles of asynchronous communication and dynamic event handling.

In plain words

> Event-Driven Architecture is a design pattern where system behavior is dictated by the occurrence of specific events, allowing for dynamic, efficient, and decoupled responses.

Wikipedia says

> Event-driven architecture (EDA) is a software architecture paradigm concerning the production and detection of events.

**Programmatic Example**

The Event-Driven Architecture (EDA) pattern in this module is implemented using several key classes and concepts:  

* Event: This is an abstract class that represents an event. It's the base class for all types of events that can occur in the system.  
* UserCreatedEvent and UserUpdatedEvent: These are concrete classes that extend the Event class. They represent specific types of events that can occur in the system, namely the creation and updating of a user.  
* EventDispatcher: This class is responsible for dispatching events to their respective handlers. It maintains a mapping of event types to handlers.  
* UserCreatedEventHandler and UserUpdatedEventHandler: These are the handler classes for the UserCreatedEvent and UserUpdatedEvent respectively. They contain the logic to execute when these events occur.  

First, we'll define the `Event` abstract class and the concrete event classes `UserCreatedEvent` and `UserUpdatedEvent`.

```java
public abstract class Event {
  // Event related properties and methods
}

public class UserCreatedEvent extends Event {
  private User user;

  public UserCreatedEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}

public class UserUpdatedEvent extends Event {
  private User user;

  public UserUpdatedEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
```

Next, we'll define the event handlers `UserCreatedEventHandler` and `UserUpdatedEventHandler`.

```java
public class UserCreatedEventHandler {
  public void onUserCreated(UserCreatedEvent event) {
    // Logic to execute when a UserCreatedEvent occurs
  }
}

public class UserUpdatedEventHandler {
  public void onUserUpdated(UserUpdatedEvent event) {
    // Logic to execute when a UserUpdatedEvent occurs
  }
}
```

Then, we'll define the `EventDispatcher` class that is responsible for dispatching events to their respective handlers.

```java
public class EventDispatcher {
  private Map<Class<? extends Event>, List<Consumer<Event>>> handlers = new HashMap<>();

  public <E extends Event> void registerHandler(Class<E> eventType, Consumer<E> handler) {
    handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler::accept);
  }

  public void dispatch(Event event) {
    List<Consumer<Event>> eventHandlers = handlers.get(event.getClass());
    if (eventHandlers != null) {
      eventHandlers.forEach(handler -> handler.accept(event));
    }
  }
}
```

Finally, we'll demonstrate how to use these classes in the main application.

```java
public class App {
  public static void main(String[] args) {
    // Create an EventDispatcher
    EventDispatcher dispatcher = new EventDispatcher();

    // Register handlers for UserCreatedEvent and UserUpdatedEvent
    dispatcher.registerHandler(UserCreatedEvent.class, new UserCreatedEventHandler()::onUserCreated);
    dispatcher.registerHandler(UserUpdatedEvent.class, new UserUpdatedEventHandler()::onUserUpdated);

    // Create a User
    User user = new User("iluwatar");

    // Dispatch UserCreatedEvent
    dispatcher.dispatch(new UserCreatedEvent(user));

    // Dispatch UserUpdatedEvent
    dispatcher.dispatch(new UserUpdatedEvent(user));
  }
}
```

Running the example produces the following console output:

```
22:15:19.997 [main] INFO com.iluwatar.eda.handler.UserCreatedEventHandler -- User 'iluwatar' has been Created!
22:15:20.000 [main] INFO com.iluwatar.eda.handler.UserUpdatedEventHandler -- User 'iluwatar' has been Updated!
```

This example demonstrates the Event-Driven Architecture pattern, where the occurrence of events drives the flow of the program. The system is designed to respond to events as they occur, which allows for a high degree of flexibility and decoupling between components.

## Class diagram

![Event-Driven Architecture](./etc/eda.png "Event-Driven Architecture")

## Applicability

Use an Event-driven architecture when

* Systems where change detection is crucial.
* Applications that require real-time features and reactive systems.
* Systems needing to efficiently handle high throughput and sporadic loads.
* When integrating with microservices to enhance agility and scalability.

## Known Uses

* Real-time data processing applications.
* Complex event processing systems in finance, such as stock trading platforms.
* IoT systems for dynamic device and information management.
* Chargify, a billing API, exposes payment activity through various events (https://docs.chargify.com/api-events)
* Amazon's AWS Lambda, lets you execute code in response to events such as changes to Amazon S3 buckets, updates to an Amazon DynamoDB table, or custom events generated by your applications or devices. (https://aws.amazon.com/lambda)
* MySQL runs triggers based on events such as inserts and update events happening on database tables.

## Consequences

Benefits:

* Scalability: Efficiently processes fluctuating loads with asynchronous processing.
* Flexibility and Agility: New event types and event consumers can be added with minimal impact on existing components.
* Responsiveness: Improves responsiveness by decoupling event processing and state management.

Trade-offs:

* Complexity in Tracking: Can be challenging to debug and track due to loose coupling and asynchronous behaviors.
* Dependency on Messaging Systems: Heavily relies on robust messaging infrastructures.
* Event Consistency: Requires careful design to handle event ordering and consistency.

## Related Patterns

* Microservices Architecture: Often used together with EDA to enhance agility and scalability.
* Publish/Subscribe: A common pattern used within EDA for messaging between event producers and consumers.

## Credits

* [Patterns of Enterprise Application Architecture](https://amzn.to/3Q3vBki)
* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/49Aljz0)
* [Reactive Messaging Patterns With the Actor Model: Applications and Integration in Scala and Akka](https://amzn.to/3UeoBUa)
* [What is an Event-Driven Architecture (Amazon)](https://aws.amazon.com/event-driven-architecture/)
