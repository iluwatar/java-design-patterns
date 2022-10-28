---
layout: pattern
title: Acyclic Visitor
folder: acyclic-visitor
permalink: /patterns/acyclic-visitor/
categories: Behavioral
language: en
tags:
 - Extensibility
---

## Intent

Allow new functions to be added to existing class hierarchies without affecting those hierarchies, and without creating 
the troublesome dependency cycles that are inherent to the GoF Visitor Pattern.

## Explanation

Real world example

> We have a hierarchy of modem classes. The modems in this hierarchy need to be visited by an external algorithm based 
> on filtering criteria (is it Unix or DOS compatible modem). 

In plain words

> Acyclic Visitor allows functions to be added to existing class hierarchies without modifying the hierarchies.

[WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor) says

> The Acyclic Visitor pattern allows new functions to be added to existing class hierarchies without affecting those 
> hierarchies, and without creating the dependency cycles that are inherent to the GangOfFour VisitorPattern.

**Programmatic Example**

Here's the `Modem` hierarchy.

```java
public abstract class Modem {
  public abstract void accept(ModemVisitor modemVisitor);
}

public class Zoom extends Modem {
  ...
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
  ...
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

Next we introduce the `ModemVisitor` hierarchy.

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
  ...
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
  ...
  @Override
  public void visit(Zoom zoom) {
    LOGGER.info(zoom + " used with Unix configurator.");
  }
}
```

Finally, here are the visitors in action.

```java
    var conUnix = new ConfigureForUnixVisitor();
    var conDos = new ConfigureForDosVisitor();
    var zoom = new Zoom();
    var hayes = new Hayes();
    hayes.accept(conDos);
    zoom.accept(conDos);
    hayes.accept(conUnix);
    zoom.accept(conUnix);   
```

Program output:

```
    // Hayes modem used with Dos configurator.
    // Zoom modem used with Dos configurator.
    // Only HayesVisitor is allowed to visit Hayes modem
    // Zoom modem used with Unix configurator.
```

## Class diagram

![alt text](./etc/acyclic-visitor.png "Acyclic Visitor")

## Applicability

This pattern can be used:

* When you need to add a new function to an existing hierarchy without the need to alter or affect that hierarchy.
* When there are functions that operate upon a hierarchy, but which do not belong in the hierarchy itself. e.g. the ConfigureForDOS / ConfigureForUnix / ConfigureForX issue.
* When you need to perform very different operations on an object depending upon its type.
* When the visited class hierarchy will be frequently extended with new derivatives of the Element class.
* When the recompilation, relinking, retesting or redistribution of the derivatives of Element is very expensive.

## Consequences

The good:

* No dependency cycles between class hierarchies.
* No need to recompile all the visitors if a new one is added.
* Does not cause compilation failure in existing visitors if class hierarchy has a new member.

The bad:

* Violates [Liskov's Substitution Principle](https://java-design-patterns.com/principles/#liskov-substitution-principle) by showing that it can accept all visitors but actually only being interested in particular visitors.
* Parallel hierarchy of visitors has to be created for all members in visitable class hierarchy.

## Related patterns

* [Visitor Pattern](https://java-design-patterns.com/patterns/visitor/)

## Credits

* [Acyclic Visitor by Robert C. Martin](http://condor.depaul.edu/dmumaugh/OOT/Design-Principles/acv.pdf)
* [Acyclic Visitor in WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor)
