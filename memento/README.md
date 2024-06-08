---
title: "Memento Pattern in Java: Preserving Object State for Undo Operations"
shortTitle: Memento
description: "Learn how to implement the Memento design pattern in Java to capture and restore object state without violating encapsulation. Ideal for undo functionality in applications."
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

## Intent of Memento Design Pattern

The Memento design pattern in Java allows developers to capture and restore an object's internal state without violating encapsulation.

## Detailed Explanation of Memento Pattern with Real-World Examples

Real-world example

> A text editor application can utilize the Memento design pattern in Java to enable undo and redo functionalities. By capturing the current state of a document as a memento each time a change is made, the application can easily restore the document to any previous state. The snapshots are stored in a history list. When the user clicks the undo button, the editor restores the document to the state saved in the most recent memento. This process allows users to revert to previous versions of their document without exposing or altering the internal data structures of the editor.

In plain words

> Memento pattern captures object internal state making it easy to store and restore objects in any point of time.

Wikipedia says

> The memento pattern is a software design pattern that provides the ability to restore an object to its previous state (undo via rollback).

## Programmatic Example of Memento Pattern in Java

In our astrology application, we use the Memento pattern to capture and restore the state of star objects. Each state is saved as a memento, allowing us to revert to previous states as needed.

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
public static void main(String[] args) {
    var states = new Stack<StarMemento>();

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
    while (!states.isEmpty()) {
        star.setMemento(states.pop());
        LOGGER.info(star.toString());
    }
}
```

Program output:

```
14:09:15.878 [main] INFO com.iluwatar.memento.App -- sun age: 10000000 years mass: 500000 tons
14:09:15.880 [main] INFO com.iluwatar.memento.App -- red giant age: 20000000 years mass: 4000000 tons
14:09:15.880 [main] INFO com.iluwatar.memento.App -- white dwarf age: 40000000 years mass: 32000000 tons
14:09:15.880 [main] INFO com.iluwatar.memento.App -- supernova age: 80000000 years mass: 256000000 tons
14:09:15.880 [main] INFO com.iluwatar.memento.App -- dead star age: 160000000 years mass: 2048000000 tons
14:09:15.880 [main] INFO com.iluwatar.memento.App -- supernova age: 80000000 years mass: 256000000 tons
14:09:15.880 [main] INFO com.iluwatar.memento.App -- white dwarf age: 40000000 years mass: 32000000 tons
14:09:15.881 [main] INFO com.iluwatar.memento.App -- red giant age: 20000000 years mass: 4000000 tons
14:09:15.881 [main] INFO com.iluwatar.memento.App -- sun age: 10000000 years mass: 500000 tons
```

## When to Use the Memento Pattern in Java

Use the Memento pattern when

* You need to capture an object's state in Java and restore it later without exposing its internal structure. This is crucial for maintaining encapsulation and simplifying the management of object states.
* A direct interface to obtaining the state would expose implementation details and break the object's encapsulation.

## Real-World Applications of Memento Pattern in Java

The Memento pattern is used in various Java applications, including the java.util.Date and java.util.Calendar classes, which can revert to previous states. It's also common in text editors and graphic editors for undo mechanisms.

## Benefits and Trade-offs of Memento Pattern

Benefits:

* Preserves encapsulation boundaries.
* Simplifies the originator by removing the need to manage version history or undo functionality directly.

Trade-offs:

* Can be expensive in terms of memory if a large number of states are saved.
* Care must be taken to manage the lifecycle of mementos to avoid memory leaks.

## Related Java Design Patterns

* [Command](https://java-design-patterns.com/patterns/command/): Often used together; commands store state for undoing operations in mementos.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Mementos may use prototyping to store the state.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
