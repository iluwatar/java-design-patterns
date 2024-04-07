---
title: Data Bus
category: Behavioral
language: en
tag:
    - Decoupling
    - Event-driven
    - Messaging
    - Publish/subscribe
---

## Also known as

* Event Bus
* Message Bus

## Intent

The Data Bus design pattern aims to provide a centralized communication channel through which various components of a system can exchange data without being directly connected, thus promoting loose coupling and enhancing scalability and maintainability.

## Explanation

Real world example

> Say you have an app that enables online bookings and participation of events. You want the app to send notifications such as event advertisements to everyone who is an ordinary member of the community or organisation holding the events. However, you do not want to send such notifications like advertisements to the event administrators or organisers, but you desire to send them and them only the time whenever a new advertisement is sent to all members of the community. The Data Bus enables you to selectively notify people of a community by type, whether it be ordinary community members or event administrators, by making their classes or components only accept messages of a certain type. Ultimately, there is no need for the components or classes of ordinary community members nor administrators to know anything about you in terms of the classes or components you are using to notify the entire community except for the need to know the type of the messages you are sending.

In plain words

> Data Bus is a design pattern that is able to connect components of an application for communication simply and solely by the type of message or event that may be transferred.

Programmatic Example

Translating the online events app example above, we firstly have our Member interface and its implementations composed of MessageCollectorMember (the ordinary community members) and StatusMember (the event administrators or organisers).

```java
public interface Member extends Consumer<DataType> {

    void accept(DataType event);
}
```

Then we have a data bus to subscribe someone to be a member or unsubscribe, and also to publish an event so to notify every member in the community.

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
     * Publish and event to all members.
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

As you can see, the accept method is applied for each member under the publish method.

Hence, as shown below, the accept method can be used to check the type of message to be published and successfully send/handle that message if the accept method has an instance for that message. Otherwise, the accept method cannot as is for the case of the MessageCollectorMember (the ordinary community members) when the type of message being sent is StartingData or StoppingData (information on the time whenever a new advertisement is sent to all members).

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

However, the StatusMember(the event administrators or organisers) can accept such types of messages as

```java
public class StatusMember implements Member {

    private final int id;

    private LocalDateTime started;

    private LocalDateTime stopped;

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

Here is the App class to demonstrate the Data Bus pattern in action:

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

Thus, the data bus outputs as follows:

```
//02:33:57.627 [main] INFO com.iluwatar.databus.members.StatusMember - Receiver 2 sees application started at 2022-10-26T02:33:57.613529100

//02:33:57.633 [main] INFO com.iluwatar.databus.members.StatusMember - Receiver 1 sees application started at 2022-10-26T02:33:57.613529100
```

Evidently, due to MessageCollectorMembers only accepting messages of type MessageData and none of either StartingData nor StoppingData, the MessageCollectorMembers are prevented from seeing what the StatusMembers (the event administrators or organisers) are shown: information on the time whenever a new advertisement is sent to all members.

## Class diagram

![data bus pattern uml diagram](./etc/data-bus.urm.png "Data Bus pattern")

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
