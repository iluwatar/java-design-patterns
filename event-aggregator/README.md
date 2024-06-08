---
title: "Event Aggregator Pattern in Java: Centralizing Event Management in Large Applications"
shortTitle: Event Aggregator
description: "Explore the Event Aggregator design pattern with our in-depth guide. Learn how to implement it effectively with examples and improve your Java applications. Perfect for developers seeking to enhance their design pattern knowledge."
category: Messaging
language: en
tag:
  - Decoupling
  - Event-driven
  - Messaging
  - Publish/subscribe
  - Reactive
head:
  - - meta
    - name: keywords
      content:
---

## Also known as

* Event Channel
* Event Central
* Message Hub

## Intent of Event Aggregator Design Pattern

An Event Aggregator is a design pattern used for handling events in a system. It centralizes the event handling logic, making it easier to manage and maintain. The Event Aggregator design pattern aims to decouple event generation from event handling. This design pattern collects events from multiple sources and routes them to the appropriate handlers.

## Detailed Explanation of Event Aggregator Pattern with Real-World Examples

Real-world example

> The Event Aggregator pattern is often compared to a hub in a wheel. In this analogy, the Event Aggregator is the hub, and the spokes are the event sources. The hub collects events from all the spokes and then distributes them to the appropriate handlers.

In Plain Words

> Event Aggregator is a design pattern that allows multiple event sources to communicate with event handlers through a central point, rather than having each event source communicate directly with each handler.

## Programmatic Example of Event Aggregator Pattern in Java

Consider the following example where we use the Event Aggregator to handle multiple events.

King Joffrey sits on the iron throne and rules the seven kingdoms of Westeros. He receives most of his critical information from King's Hand, the second in command. King's hand has many close advisors himself, feeding him with relevant information about events occurring in the kingdom.

In our programmatic example, we demonstrate the implementation of an event aggregator pattern. Some of the objects are event listeners, some are event emitters, and the event aggregator does both.

```java
public interface EventObserver {
    void onEvent(Event e);
}
```

```java
public abstract class EventEmitter {

    private final Map<Event, List<EventObserver>> observerLists;

    public EventEmitter() {
        observerLists = new HashMap<>();
    }

    public final void registerObserver(EventObserver obs, Event e) {
      // implementation omitted
    }

    protected void notifyObservers(Event e) {
        // implementation omitted
    }
}
```

`KingJoffrey` is listening to events from `KingsHand`.

```java

@Slf4j
public class KingJoffrey implements EventObserver {
    @Override
    public void onEvent(Event e) {
        LOGGER.info("Received event from the King's Hand: {}", e.toString());
    }
}
```

`KingsHand` is listening to events from his subordinates `LordBaelish`, `LordVarys`, and `Scout`. Whatever he hears from them, he delivers to `KingJoffrey`.

```java
public class KingsHand extends EventEmitter implements EventObserver {

    public KingsHand() {
    }

    public KingsHand(EventObserver obs, Event e) {
        super(obs, e);
    }

    @Override
    public void onEvent(Event e) {
        notifyObservers(e);
    }
}
```

For example, `LordVarys` finds a traitor every Sunday and notifies the `KingsHand`.

```java

@Slf4j
public class LordVarys extends EventEmitter implements EventObserver {
    @Override
    public void timePasses(Weekday day) {
        if (day == Weekday.SATURDAY) {
            notifyObservers(Event.TRAITOR_DETECTED);
        }
    }
}
```

The following snippet demonstrates how the objects are constructed and wired together.

```java
public static void main(String[] args) {

    var kingJoffrey = new KingJoffrey();

    var kingsHand = new KingsHand();
    kingsHand.registerObserver(kingJoffrey, Event.TRAITOR_DETECTED);
    kingsHand.registerObserver(kingJoffrey, Event.STARK_SIGHTED);
    kingsHand.registerObserver(kingJoffrey, Event.WARSHIPS_APPROACHING);
    kingsHand.registerObserver(kingJoffrey, Event.WHITE_WALKERS_SIGHTED);

    var varys = new LordVarys();
    varys.registerObserver(kingsHand, Event.TRAITOR_DETECTED);
    varys.registerObserver(kingsHand, Event.WHITE_WALKERS_SIGHTED);

    var scout = new Scout();
    scout.registerObserver(kingsHand, Event.WARSHIPS_APPROACHING);
    scout.registerObserver(varys, Event.WHITE_WALKERS_SIGHTED);

    var baelish = new LordBaelish(kingsHand, Event.STARK_SIGHTED);

    var emitters = List.of(
            kingsHand,
            baelish,
            varys,
            scout
    );

    Arrays.stream(Weekday.values())
            .<Consumer<? super EventEmitter>>map(day -> emitter -> emitter.timePasses(day))
            .forEachOrdered(emitters::forEach);
}
```

The console output after running the example.

```
21:37:38.737 [main] INFO com.iluwatar.event.aggregator.KingJoffrey -- Received event from the King's Hand: Warships approaching
21:37:38.739 [main] INFO com.iluwatar.event.aggregator.KingJoffrey -- Received event from the King's Hand: White walkers sighted
21:37:38.739 [main] INFO com.iluwatar.event.aggregator.KingJoffrey -- Received event from the King's Hand: Stark sighted
21:37:38.739 [main] INFO com.iluwatar.event.aggregator.KingJoffrey -- Received event from the King's Hand: Traitor detected
```

## Detailed Explanation of Event Aggregator Pattern with Real-World Examples

![Event Aggregator](./etc/classes.png "Event Aggregator")

## When to Use the Event Aggregator Pattern in Java

Use the Event Aggregator pattern when

* You have multiple event sources and handlers.
* You want to decouple the event generation and handling logic.
* You need a centralized event management system.

## Real-World Applications of Event Aggregator Pattern in Java

* Enterprise application integrations where systems need a central point to handle events generated by various subsystems.
* Complex GUI applications where user actions in one part of the interface need to affect other parts without tight coupling between the components.

## Benefits and Trade-offs of Event Aggregator Pattern

Benefits:

* Decoupling: By centralizing event handling, the Event Aggregator minimizes direct interaction between components, leading to a more modular and easier-to-manage system.
* Improves Flexibility and Scalability: Adding new publishers or subscribers involves less effort since the central aggregator handles all routing.
* Simplifies Component Interface: Components need to know only about the Event Aggregator, not about other components.
* Centralizes event management: Makes the system easier to maintain.

Trade-offs:

* Complexity of the Aggregator: The Event Aggregator itself can become a complex and high-maintenance component if not properly designed.
* Potential Performance Bottleneck: If not scaled properly, the central event handling mechanism can become a bottleneck in the system.

## Related Java Design Patterns

* [Mediator](https://java-design-patterns.com/patterns/mediator/): Similar to Mediator in that it abstracts direct communications between components, but focused specifically on event messages.
* [Observer](https://java-design-patterns.com/patterns/observer/): The Event Aggregator pattern is often implemented using the Observer pattern, where the aggregator observes events and notifies subscribers.
* Publish-Subscribe: The Event Aggregator can be seen as a special case of the Publish-Subscribe pattern, with the aggregator acting as the broker.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/44eWKXv)
* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/440b0CZ)
* [Java Design Pattern Essentials](https://amzn.to/43XHCgM)
* [Event Aggregator (Martin Fowler)](http://martinfowler.com/eaaDev/EventAggregator.html)
