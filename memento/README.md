---
title: Memento
category: Behavioral
language: en
tag:
    - Encapsulation
    - Gang of Four
    - Memory management
    - Object composition
    - State tracking
    - Undo
---

## Also known as

* Snapshot
* Token

## Intent

Without violating encapsulation, capture and externalize an object's internal state so that the object can be restored to this state later.

## Explanation

Real-world example

> We are working on an astrology application where we need to analyze star properties over time. We are creating snapshots of star states using the Memento pattern.

In plain words

> Memento pattern captures object internal state making it easy to store and restore objects in any point of time.

Wikipedia says

> The memento pattern is a software design pattern that provides the ability to restore an object to its previous state (undo via rollback).

**Programmatic Example**

Let's first define the types of stars we are capable to handle.

```java
public enum StarType {
  SUN("sun"),
  RED_GIANT("red giant"),
  WHITE_DWARF("white dwarf"),
  SUPERNOVA("supernova"),
  DEAD("dead star");
  // ...
}
```

Next, let's jump straight to the essentials. Here's the `Star` class along with the mementos that we need to manipulate. Especially pay attention to `getMemento` and `setMemento` methods.

```java
public interface StarMemento {
}

public class Star {

    private StarType type;
    private int ageYears;
    private int massTons;

    public Star(StarType startType, int startAge, int startMass) {
        this.type = startType;
        this.ageYears = startAge;
        this.massTons = startMass;
    }

    public void timePasses() {
        ageYears *= 2;
        massTons *= 8;
        switch (type) {
            case RED_GIANT -> type = StarType.WHITE_DWARF;
            case SUN -> type = StarType.RED_GIANT;
            case SUPERNOVA -> type = StarType.DEAD;
            case WHITE_DWARF -> type = StarType.SUPERNOVA;
            case DEAD -> {
                ageYears *= 2;
                massTons = 0;
            }
            default -> {
            }
        }
    }

    StarMemento getMemento() {
        var state = new StarMementoInternal();
        state.setAgeYears(ageYears);
        state.setMassTons(massTons);
        state.setType(type);
        return state;
    }

    void setMemento(StarMemento memento) {
        var state = (StarMementoInternal) memento;
        this.type = state.getType();
        this.ageYears = state.getAgeYears();
        this.massTons = state.getMassTons();
    }

    @Override
    public String toString() {
        return String.format("%s age: %d years mass: %d tons", type.toString(), ageYears, massTons);
    }

    private static class StarMementoInternal implements StarMemento {

        private StarType type;
        private int ageYears;
        private int massTons;

        // setters and getters ->
        // ...
    }
}
```

And finally here's how we use the mementos to store and restore star states.

```java
    var states = new Stack<>();
    var star = new Star(StarType.SUN, 10000000, 500000);
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    while (states.size() > 0) {
      star.setMemento(states.pop());
      LOGGER.info(star.toString());
    }
```

Program output:

```
sun age: 10000000 years mass: 500000 tons
red giant age: 20000000 years mass: 4000000 tons
white dwarf age: 40000000 years mass: 32000000 tons
supernova age: 80000000 years mass: 256000000 tons
dead star age: 160000000 years mass: 2048000000 tons
supernova age: 80000000 years mass: 256000000 tons
white dwarf age: 40000000 years mass: 32000000 tons
red giant age: 20000000 years mass: 4000000 tons
sun age: 10000000 years mass: 500000 tons
```

## Class diagram

![alt text](./etc/memento.png "Memento")

## Applicability

Use the Memento pattern when

* A snapshot of an object's state must be saved so that it can be restored to that state later, and
* A direct interface to obtaining the state would expose implementation details and break the object's encapsulation

## Known uses

* Java Util Package: Various classes in the Java Util Package, such as java.util.Date and java.util.Calendar, can be reverted to previous states using similar principles, though not implemented directly as the Memento Pattern.
* Undo mechanisms in software: Text editors and graphic editors often use this pattern to implement undo actions.

## Consequences

Benefits:

* Preserves encapsulation boundaries.
* Simplifies the originator by removing the need to manage version history or undo functionality directly.

Trade-offs:

* Can be expensive in terms of memory if a large number of states are saved.
* Care must be taken to manage the lifecycle of mementos to avoid memory leaks.

## Related Patterns

* [Command](https://java-design-patterns.com/patterns/command/): Often used together; commands store state for undoing operations in mementos.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Mementos may use prototyping to store the state.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
