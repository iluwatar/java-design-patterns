---
title: Facade
category: Structural
language: en
tag:
    - Abstraction
    - API design
    - Code simplification
    - Decoupling
    - Encapsulation
    - Gang Of Four
    - Interface
    - Object composition
---

## Intent

Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.

## Explanation

Real-world example

> Imagine a home theater system with multiple components: a DVD player, projector, surround sound system, and lights. Each component has a complex interface with numerous functions and settings. To simplify the use of the home theater system, a remote control (the Facade) is provided. The remote control offers a unified interface with simple buttons like "Play Movie," "Stop," "Pause," and "Volume Up/Down," which internally communicate with the various components, managing their interactions. This makes the system easier to use without needing to understand the detailed operations of each component.

In plain words

> Facade pattern provides a simplified interface to a complex subsystem.

Wikipedia says

> A facade is an object that provides a simplified interface to a larger body of code, such as a class library.

**Programmatic Example**

How does a goldmine work? "Well, the miners go down there and dig gold!" you say. That is what you believe because you are using a simple interface that goldmine provides on the outside, internally it has to do a lot of stuff to make it happen. This simple interface to the complex subsystem is a facade.

Here we have the dwarven mine worker hierarchy. First, there's a base class `DwarvenMineWorker`:

```java

@Slf4j
public abstract class DwarvenMineWorker {

    public void goToSleep() {
        LOGGER.info("{} goes to sleep.", name());
    }

    public void wakeUp() {
        LOGGER.info("{} wakes up.", name());
    }

    public void goHome() {
        LOGGER.info("{} goes home.", name());
    }

    public void goToMine() {
        LOGGER.info("{} goes to the mine.", name());
    }

    private void action(Action action) {
        switch (action) {
            case GO_TO_SLEEP -> goToSleep();
            case WAKE_UP -> wakeUp();
            case GO_HOME -> goHome();
            case GO_TO_MINE -> goToMine();
            case WORK -> work();
            default -> LOGGER.info("Undefined action");
        }
    }

    public void action(Action... actions) {
        Arrays.stream(actions).forEach(this::action);
    }

    public abstract void work();

    public abstract String name();

    enum Action {
        GO_TO_SLEEP, WAKE_UP, GO_HOME, GO_TO_MINE, WORK
    }
}
```

Then we have the concrete dwarf classes `DwarvenTunnelDigger`, `DwarvenGoldDigger` and `DwarvenCartOperator`:

```java

@Slf4j
public class DwarvenTunnelDigger extends DwarvenMineWorker {

    @Override
    public void work() {
        LOGGER.info("{} creates another promising tunnel.", name());
    }

    @Override
    public String name() {
        return "Dwarven tunnel digger";
    }
}

@Slf4j
public class DwarvenGoldDigger extends DwarvenMineWorker {

    @Override
    public void work() {
        LOGGER.info("{} digs for gold.", name());
    }

    @Override
    public String name() {
        return "Dwarf gold digger";
    }
}

@Slf4j
public class DwarvenCartOperator extends DwarvenMineWorker {

    @Override
    public void work() {
        LOGGER.info("{} moves gold chunks out of the mine.", name());
    }

    @Override
    public String name() {
        return "Dwarf cart operator";
    }
}

```

To operate all these goldmine workers we have the `DwarvenGoldmineFacade`:

```java
public class DwarvenGoldmineFacade {

    private final List<DwarvenMineWorker> workers;

    public DwarvenGoldmineFacade() {
        workers = List.of(
                new DwarvenGoldDigger(),
                new DwarvenCartOperator(),
                new DwarvenTunnelDigger());
    }

    public void startNewDay() {
        makeActions(workers, DwarvenMineWorker.Action.WAKE_UP, DwarvenMineWorker.Action.GO_TO_MINE);
    }

    public void digOutGold() {
        makeActions(workers, DwarvenMineWorker.Action.WORK);
    }

    public void endDay() {
        makeActions(workers, DwarvenMineWorker.Action.GO_HOME, DwarvenMineWorker.Action.GO_TO_SLEEP);
    }

    private static void makeActions(Collection<DwarvenMineWorker> workers,
                                    DwarvenMineWorker.Action... actions) {
        workers.forEach(worker -> worker.action(actions));
    }
}
```

Now let's use the facade:

```java
public static void main(String[] args) {
    var facade = new DwarvenGoldmineFacade();
    facade.startNewDay();
    facade.digOutGold();
    facade.endDay();
}
```

Program output:

```
06:07:20.676 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarf gold digger wakes up.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarf gold digger goes to the mine.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarf cart operator wakes up.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarf cart operator goes to the mine.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarven tunnel digger wakes up.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarven tunnel digger goes to the mine.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenGoldDigger -- Dwarf gold digger digs for gold.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenCartOperator -- Dwarf cart operator moves gold chunks out of the mine.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenTunnelDigger -- Dwarven tunnel digger creates another promising tunnel.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarf gold digger goes home.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarf gold digger goes to sleep.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarf cart operator goes home.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarf cart operator goes to sleep.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarven tunnel digger goes home.
06:07:20.678 [main] INFO com.iluwatar.facade.DwarvenMineWorker -- Dwarven tunnel digger goes to sleep.
```

## Class diagram

![Facade](./etc/facade.urm.png "Facade pattern class diagram")

## Applicability

Use the Facade pattern when

* You want to provide a simple interface to a complex subsystem.
* Subsystems are getting more complex and depend on multiple classes, but most clients only need a part of the functionality.
* There is a need to layer your subsystems. Use a facade to define an entry point to each subsystem level.

## Tutorials

* [Facade Design Pattern in Java (DigitalOcean)](https://www.digitalocean.com/community/tutorials/facade-design-pattern-in-java)
* [Facade (Refactoring Guru)](https://refactoring.guru/design-patterns/facade)
* [Facade Method Design Pattern (GeekforGeeks)](https://www.geeksforgeeks.org/facade-design-pattern-introduction/)
* [Design Patterns - Facade Pattern (TutorialsPoint)](https://www.tutorialspoint.com/design_pattern/facade_pattern.htm)

## Known Uses

* Java libraries such as java.net.URL and javax.faces.context.FacesContext use Facade to simplify complex underlying classes.
* In many Java frameworks, facades are used to simplify the usage of APIs by providing a simpler interface to more complex underlying code structures.

## Consequences

Benefits:

* Isolates clients from subsystem components, making it easier to use and reducing dependencies.
* Promotes weak coupling between the subsystem and its clients.
* Often simplifies the API of complex systems.

Trade-offs:

* A facade can become a god object coupled to all classes of an app if not implemented correctly.

## Related Patterns

* [Adapter](https://java-design-patterns.com/patterns/adapter/): Facade provides a unified interface while Adapter makes two existing interfaces work together.
* [Mediator](https://java-design-patterns.com/patterns/mediator/): Facade defines a simpler interface to a subsystem while Mediator centralizes complex communications and control between objects.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3QbO7qN)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/3UpTLrG)
