---
title: "Acyclic Visitor Pattern in Java: Streamlining Object Interactions"
shortTitle: Acyclic Visitor
description: "Learn about the Acyclic Visitor pattern in Java. This guide explains how it decouples operations from object hierarchies, providing examples and real-world applications."
category: Behavioral
language: en
tag:
  - Decoupling
  - Extensibility
  - Interface
  - Object composition
---

## Intent of Acyclic Visitor Design Pattern

The Acyclic Visitor pattern in Java decouples operations from an object hierarchy, providing a flexible design for various applications.

## Detailed Explanation of Acyclic Visitor Pattern with Real-World Examples

Real-world example

> An analogous real-world example of the Acyclic Visitor pattern in Java is a museum guide system, demonstrating the practical application of this design pattern. Imagine a museum with various exhibits like paintings, sculptures, and historical artifacts. The museum has different types of guides (audio guide, human guide, virtual reality guide) that provide information about each exhibit. Instead of modifying the exhibits every time a new guide type is introduced, each guide implements an interface to visit different exhibit types. This way, the museum can add new types of guides without altering the existing exhibits, ensuring that the system remains extensible and maintainable without forming any dependency cycles.

In plain words

> Acyclic Visitor allows functions to be added to existing class hierarchies without modifying the hierarchies.

[WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor) says

> The Acyclic Visitor pattern allows new functions to be added to existing class hierarchies without affecting those hierarchies, and without creating the dependency cycles that are inherent to the GangOfFour VisitorPattern.

## Programmatic Example of Acyclic Visitor in Java

In this Java example, we have a hierarchy of modem classes illustrating the Acyclic Visitor pattern. The modems in this hierarchy need to be visited by an external algorithm based on filtering criteria (is it Unix or DOS compatible modem).

Here's the `Modem` hierarchy.

```java
public abstract class Modem {
    public abstract void accept(ModemVisitor modemVisitor);
}

public class Zoom extends Modem {

    // Other properties and methods...

    @Override
    public void accept(ModemVisitor modemVisitor) {
        if (modemVisitor instanceof ZoomVisitor) {
            ((ZoomVisitor) modemVisitor).visit(this);
        } else {
            LOGGER.info("Only ZoomVisitor is allowed to visit Zoom modem");
        }
    }
}

public class Hayes extends Modem {

    // Other properties and methods...

    @Override
    public void accept(ModemVisitor modemVisitor) {
        if (modemVisitor instanceof HayesVisitor) {
            ((HayesVisitor) modemVisitor).visit(this);
        } else {
            LOGGER.info("Only HayesVisitor is allowed to visit Hayes modem");
        }
    }
}
```

Next, we introduce the `ModemVisitor` hierarchy.

```java
public interface ModemVisitor {
}

public interface HayesVisitor extends ModemVisitor {
    void visit(Hayes hayes);
}

public interface ZoomVisitor extends ModemVisitor {
    void visit(Zoom zoom);
}

public interface AllModemVisitor extends ZoomVisitor, HayesVisitor {
}

public class ConfigureForDosVisitor implements AllModemVisitor {

    // Other properties and methods...

    @Override
    public void visit(Hayes hayes) {
        LOGGER.info(hayes + " used with Dos configurator.");
    }

    @Override
    public void visit(Zoom zoom) {
        LOGGER.info(zoom + " used with Dos configurator.");
    }
}

public class ConfigureForUnixVisitor implements ZoomVisitor {

    // Other properties and methods...

    @Override
    public void visit(Zoom zoom) {
        LOGGER.info(zoom + " used with Unix configurator.");
    }
}
```

Finally, here are the visitors in action.

```java
public static void main(String[] args) {
    var conUnix = new ConfigureForUnixVisitor();
    var conDos = new ConfigureForDosVisitor();

    var zoom = new Zoom();
    var hayes = new Hayes();

    hayes.accept(conDos); // Hayes modem with Dos configurator
    zoom.accept(conDos); // Zoom modem with Dos configurator
    hayes.accept(conUnix); // Hayes modem with Unix configurator
    zoom.accept(conUnix); // Zoom modem with Unix configurator   
}
```

Program output:

```
09:15:11.125 [main] INFO com.iluwatar.acyclicvisitor.ConfigureForDosVisitor -- Hayes modem used with Dos configurator.
09:15:11.127 [main] INFO com.iluwatar.acyclicvisitor.ConfigureForDosVisitor -- Zoom modem used with Dos configurator.
09:15:11.127 [main] INFO com.iluwatar.acyclicvisitor.Hayes -- Only HayesVisitor is allowed to visit Hayes modem
09:15:11.127 [main] INFO com.iluwatar.acyclicvisitor.ConfigureForUnixVisitor -- Zoom modem used with Unix configurator.
```

## Acyclic Visitor Pattern Class Diagram

![Acyclic Visitor](./etc/acyclic-visitor.png "Acyclic Visitor")

## When to Use the Acyclic Visitor Pattern in Java

This pattern can be used:

* When you need to add a new function to an existing hierarchy without the need to alter or affect that hierarchy.
* When there are functions that operate upon a hierarchy, but which do not belong in the hierarchy itself. e.g. the ConfigureForDOS / ConfigureForUnix / ConfigureForX issue.
* When you need to perform very different operations on an object depending upon its type.
* When the visited class hierarchy will be frequently extended with new derivatives of the Element class.
* When the recompilation, relinking, retesting or redistribution of the derivatives of Element is very expensive.

## Acyclic Visitor Pattern Java Tutorials

* [The Acyclic Visitor Pattern (Code Crafter)](https://codecrafter.blogspot.com/2012/12/the-acyclic-visitor-pattern.html)

## Benefits and Trade-offs of Acyclic Visitor Pattern

Benefits:

* Extensible: New operations can be added easily without changing the object structure.
* Decoupled: Reduces coupling between the objects and the operations on them.
* No dependency cycles: Ensures acyclic dependencies, improving maintainability and reducing complexity.

Trade-offs:

* Increased complexity: Can introduce additional complexity with the need for multiple visitor interfaces.
* Maintenance overhead: Modifying the object hierarchy requires updating all visitors.

## Related Java Design Patterns

* [Composite](https://java-design-patterns.com/patterns/composite/): Often used in conjunction with Acyclic Visitor to allow treating individual objects and compositions uniformly.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Can be used alongside to add responsibilities to objects dynamically.
* [Visitor](https://java-design-patterns.com/patterns/visitor/): The Acyclic Visitor pattern is a variation of the Visitor pattern that avoids cyclic dependencies.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Patterns in Java: A Catalog of Reusable Design Patterns Illustrated with UML](https://amzn.to/4bOtzwF)
* [Acyclic Visitor (Robert C. Martin)](http://condor.depaul.edu/dmumaugh/OOT/Design-Principles/acv.pdf)
* [Acyclic Visitor (WikiWikiWeb)](https://wiki.c2.com/?AcyclicVisitor)
