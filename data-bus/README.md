---
title: Data Bus
category: Messaging
language: en
tag:
    - Decoupling
    - Event-driven
    - Messaging
    - Publish/subscribe
    - Scalability
---

## Also known as

* Event Bus
* Message Bus

## Intent

The Data Bus design pattern aims to provide a centralized communication channel through which various components of a system can exchange data without being directly connected, thus promoting loose coupling and enhancing scalability and maintainability.

## Explanation

Real-world example

> Consider a large airport as an analogous real-world example of the Data Bus design pattern. In an airport, various airlines, passengers, baggage handlers, and security personnel all need to communicate and share information. Instead of each entity communicating directly with every other entity, the airport uses a centralized announcement system (the Data Bus). Flight information, security alerts, and other critical updates are broadcast over this system, and each entity listens for the messages relevant to them. This setup allows the airport to decouple the communication process, ensuring that each entity only receives the information they need, while allowing the system to scale and integrate new entities without disrupting the existing ones.

In plain words

> Data Bus is a design pattern that is able to connect components of an application for communication simply and solely by the type of message or event that may be transferred.

**Programmatic Example**

Say you have an app that enables online bookings and participation in events. You want the app to send notifications, such as event advertisements, to all ordinary members of the community or organization holding the events. However, you do not want to send such advertisements to event administrators or organizers. Instead, you want to send them notifications about the timing of new advertisements sent to all members. The Data Bus enables you to selectively notify community members by type (ordinary members or event administrators) by making their classes or components only accept messages of a certain type. Thus, ordinary members and administrators do not need to know about each other or the specific classes or components used to notify the entire community, except for knowing the type of messages being sent.

In the online events app example above, we first define our `Member` interface and its implementations: `MessageCollectorMember` (ordinary community members) and `StatusMember` (event administrators or organizers).

```java
public interface Member extends Consumer<DataType> {

    void accept(DataType event);
}
```

Next, we implement a data bus to subscribe or unsubscribe members and to publish events to notify all community members.

```java
public class DataBus {

    private static final DataBus INSTANCE = new DataBus();

    private final Set<Member> listeners = new HashSet<>();

    public static DataBus getInstance() {
        return INSTANCE;
    }

    /**
     * Register a member with the data-bus to start receiving events.
     *
     * @param member The member to register
     */
    public void subscribe(final Member member) {
        this.listeners.add(member);
    }

    /**
     * Deregister a member to stop receiving events.
     *
     * @param member The member to deregister
     */
    public void unsubscribe(final Member member) {
        this.listeners.remove(member);
    }

    /**
     * Publish an event to all members.
     *
     * @param event The event
     */
    public void publish(final DataType event) {
        event.setDataBus(this);

        listeners.forEach(
                listener -> listener.accept(event));
    }
}
```

The `accept` method is applied to each member in the `publish` method.

For ordinary community members (`MessageCollectorMember`), the `accept` method can handle only `MessageData` type messages.

```java
public class MessageCollectorMember implements Member {

    private final String name;

    private final List<String> messages = new ArrayList<>();

    public MessageCollectorMember(String name) {
        this.name = name;
    }

    @Override
    public void accept(final DataType data) {
        if (data instanceof MessageData) {
            handleEvent((MessageData) data);
        }
    }
}
```

For event administrators or organizers (`StatusMember`), the `accept` method can handle `StartingData` and `StoppingData` type messages.

```java
public class StatusMember implements Member {

    private final int id;

    private LocalDateTime started;

    private LocalDateTime stopped;

    public StatusMember(int id) {
        this.id = id;
    }

    @Override
    public void accept(final DataType data) {
        if (data instanceof StartingData) {
            handleEvent((StartingData) data);
        } else if (data instanceof StoppingData) {
            handleEvent((StoppingData) data);
        }
    }
}
```

Here is the `App` class to demonstrate the Data Bus pattern in action:

```java
class App {

    public static void main(String[] args) {
        final var bus = DataBus.getInstance();
        bus.subscribe(new StatusMember(1));
        bus.subscribe(new StatusMember(2));
        final var foo = new MessageCollectorMember("Foo");
        final var bar = new MessageCollectorMember("Bar");
        bus.subscribe(foo);
        bus.publish(StartingData.of(LocalDateTime.now()));
    }
}
```

When the data bus publishes a message, the output is as follows:

```
02:33:57.627 [main] INFO com.iluwatar.databus.members.StatusMember - Receiver 2 sees application started at 2022-10-26T02:33:57.613529100
02:33:57.633 [main] INFO com.iluwatar.databus.members.StatusMember - Receiver 1 sees application started at 2022-10-26T02:33:57.613529100
```

As shown, `MessageCollectorMembers` only accept messages of type `MessageData`, so they do not see the `StartingData` or `StoppingData` messages, which are only visible to `StatusMember` (the event administrators or organizers). This selective message handling prevents ordinary community members from receiving administrative notifications.

## Class diagram

![Data Bus](./etc/data-bus.urm.png "Data Bus pattern")

## Applicability

* When multiple components need to share data or events but direct coupling is undesirable.
* In complex systems where the flow of information varies dynamically.
* In distributed systems where components might be deployed across different environments.

## Known Uses

* Event handling systems in large-scale applications.
* Microservices architectures for inter-service communication.
* Real-time data processing systems, such as stock trading platforms.
* In frameworks like Spring, particularly with its application event mechanism.

## Consequences

Benefits:

* Loose Coupling: Components can interact without having direct dependencies on each other.
* Flexibility: New subscribers or publishers can be added without impacting existing components.
* Scalability: The pattern supports scaling components independently.
* Reusability: The bus and components can be reused across different systems.

Trade-offs:

* Complexity: Introducing a data bus can add complexity to the system architecture.
* Performance Overhead: The additional layer of communication may introduce latency.
* Debugging Difficulty: Tracing data flow through the bus can be challenging, especially in systems with many events.

## Related Patterns

* [Mediator](https://java-design-patterns.com/patterns/mediator/): Facilitates communication between components, but unlike Data Bus, it centralizes control.
* [Observer](https://java-design-patterns.com/patterns/observer/): Similar in nature to the publish-subscribe mechanism used in Data Bus for notifying changes to multiple objects.
* Publish/Subscribe: The Data Bus pattern is often implemented using the publish-subscribe mechanism, where publishers post messages to the bus without knowledge of the subscribers.

## Credits

* [Enterprise Integration Patterns](https://amzn.to/3J6WoYS)
* [Pattern-Oriented Software Architecture, Volume 4: A Pattern Language for Distributed Computing](https://amzn.to/3PTRGBM)
