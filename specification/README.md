---
layout: pattern
title: Specification
folder: specification
permalink: /patterns/specification/
categories: Behavioral
tags:
 - Data access
---

## Also known as
Filter, Criteria

## Intent
Specification pattern separates the statement of how to match a
candidate, from the candidate object that it is matched against. As well as its
usefulness in selection, it is also valuable for validation and for building to
order.

## Explanation

Real world example

> There is a pool of different creatures and we often need to select some subset of them.
> We can write our search specification such as "creatures that can fly", "creatures heavier than 500 kilograms", or as a combination of other search specifications, and then give it to the party that will perform the filtering.

In Plain Words

> Specification pattern allows us to separate the search criteria from the object that performs the search.

Wikipedia says

> In computer programming, the specification pattern is a particular software design pattern, whereby business rules can be recombined by chaining the business rules together using boolean logic.

**Programmatic Example**

If we look at our creature pool example from above, we have a set of creatures with certain properties.
Those properties can be part of a pre-defined, limited set (represented here by the enums Size, Movement and Color); but they can also be continuous values (e.g. the mass of a Creature).
In this case, it is more appropriate to use what we call "parameterized specification", where the property value can be given as an argument when the Creature is instantiated, allowing for more flexibility.
A third option is to combine pre-defined and/or parameterized properties using boolean logic, allowing for near-endless selection possibilities (this is called "composite specification", see below).
The pros and cons of each approach are detailed in the table at the end of this document.

```java
public interface Creature {
  String getName();
  Size getSize();
  Movement getMovement();
  Color getColor();
  Mass getMass();
}
```

And ``Dragon`` implementation looks like this.

```java
public class Dragon extends AbstractCreature {

  public Dragon() {
    super("Dragon", Size.LARGE, Movement.FLYING, Color.RED, new Mass(39300.0));
  }
}
```

Now that we want to select some subset of them, we use selectors. To select creatures that fly, we should use ``MovementSelector``.

```java
public class MovementSelector extends AbstractSelector<Creature> {

  private final Movement movement;

  public MovementSelector(Movement m) {
    this.movement = m;
  }

  @Override
  public boolean test(Creature t) {
    return t.getMovement().equals(movement);
  }
}
```

On the other hand, when selecting creatures heavier than a chosen amount, we use ``MassGreaterThanSelector``.

```java
public class MassGreaterThanSelector extends AbstractSelector<Creature> {

  private final Mass mass;

  public MassGreaterThanSelector(double mass) {
    this.mass = new Mass(mass);
  }

  @Override
  public boolean test(Creature t) {
    return t.getMass().greaterThan(mass);
  }
}
```

With these building blocks in place, we can perform a search for red creatures as follows:

```java
    var redCreatures = creatures.stream().filter(new ColorSelector(Color.RED))
      .collect(Collectors.toList());
```

But we could also use our parameterized selector like this:

```java
    var heavyCreatures = creatures.stream().filter(new MassGreaterThanSelector(500.0)
      .collect(Collectors.toList());
```

Our third option is to combine multiple selectors together. Performing a search for special creatures (defined as red, flying, and not small) could be done as follows:

```java
    var specialCreaturesSelector = 
      new ColorSelector(Color.RED).and(new MovementSelector(Movement.FLYING)).and(new SizeSelector(Size.SMALL).not());

    var specialCreatures = creatures.stream().filter(specialCreaturesSelector)
      .collect(Collectors.toList());
```

**More on Composite Specification**

In Composite Specification, we will create custom instances of ``AbstractSelector`` by combining other selectors (called "leaves") using the three basic logical operators.
These are implemented in ``ConjunctionSelector``, ``DisjunctionSelector`` and ``NegationSelector``.

```java
public abstract class AbstractSelector<T> implements Predicate<T> {

  public AbstractSelector<T> and(AbstractSelector<T> other) {
    return new ConjunctionSelector<>(this, other);
  }

  public AbstractSelector<T> or(AbstractSelector<T> other) {
    return new DisjunctionSelector<>(this, other);
  }

  public AbstractSelector<T> not() {
    return new NegationSelector<>(this);
  }
}
```

```java
public class ConjunctionSelector<T> extends AbstractSelector<T> {

  private List<AbstractSelector<T>> leafComponents;

  @SafeVarargs
  ConjunctionSelector(AbstractSelector<T>... selectors) {
    this.leafComponents = List.of(selectors);
  }

  /**
   * Tests if *all* selectors pass the test.
   */
  @Override
  public boolean test(T t) {
    return leafComponents.stream().allMatch(comp -> (comp.test(t)));
  }
}
```

All that is left to do is now to create leaf selectors (be it hard-coded or parameterized ones) that are as generic as possible,
and we will be able to instantiate the ``AbstractSelector`` class by combining any amount of selectors, as exemplified above.
We should be careful though, as it is easy to make a mistake when combining many logical operators; in particular, we should pay attention to the priority of the operations.\
In general, Composite Specification is a great way to write more reusable code, as there is no need to create a Selector class for each filtering operation.
Instead, we just create an instance of ``AbstractSelector`` "on the spot", using tour generic "leaf" selectors and some basic boolean logic.


**Comparison of the different approaches**

| Pattern | Usage | Pros | Cons |
|---|---|---|---|
| Hard-Coded Specification | Selection criteria are few and known in advance | + Easy to implement | - Inflexible |
| | | + Expressive |
| Parameterized Specification | Selection criteria are a large range of values (e.g. mass, speed,...) | + Some flexibility | - Still requires special-purpose classes |
| Composite Specification | There are a lot of selection criteria that can be combined in multiple ways, hence it is not feasible to create a class for each selector | + Very flexible, without requiring many specialized classes | - Somewhat more difficult to comprehend |
| | | + Supports logical operations | - You still need to create the base classes used as leaves |

## Class diagram
![alt text](./etc/specification.png "Specification")

## Applicability
Use the Specification pattern when

* You need to select a subset of objects based on some criteria, and to refresh the selection at various times.
* You need to check that only suitable objects are used for a certain role (validation).

## Related patterns

* Repository

## Credits

* [Martin Fowler - Specifications](http://martinfowler.com/apsupp/spec.pdf)
