---
layout: pattern
title: Specification
folder: specification
permalink: /patterns/specification/
categories: Behavioral
tags:
 - Java
 - Difficulty-Beginner
 - Searching
---

## Also known as
Filter, Criteria

## Intent
Specification pattern separates the statement of how to match a
candidate, from the candidate object that it is matched against. As well as its
usefulness in selection, it is also valuable for validation and for building to
order.

![alt text](./etc/specification.png "Specification")

## Applicability
Use the Specification pattern when

* You need to select a subset of objects based on some criteria, and to refresh the selection at various times.
* You need to check that only suitable objects are used for a certain role (validation).

## Explanation

Real world example

> There is a pool of different creatures and we often need to select some subset of them. We can write our search specification such as "creatures that can fly" or "creatures heavier than 500 kilograms" and give it to the party that will perform the filtering.

In Plain Words

> Specification pattern allows us to separate the search criteria from the object that performs the search.

Wikipedia says

> In computer programming, the specification pattern is a particular software design pattern, whereby business rules can be recombined by chaining the business rules together using boolean logic.

**Programmatic Example**

If we look at our creature pool example from above, we have a set of creatures with certain properties.\
Those properties can be part of a pre-defined, limited set (represented here by the enums Size, Movement and Color); but they can also be discrete (e.g. the mass of a Creature). In this case, it is more appropriate to use what we call "parameterized specification", where the property value can be given as an argument when the Creature is created, allowing for more flexibility.

```java
public interface Creature {
  String getName();
  Size getSize();
  Movement getMovement();
  Color getColor();
  Mass getMass();
}
```

And dragon implementation looks like this.

```java
public class Dragon extends AbstractCreature {

  public Dragon() {
    super("Dragon", Size.LARGE, Movement.FLYING, Color.RED, new Mass(39300.0));
  }
}
```

Now that we want to select some subset of them, we use selectors. To select creatures that fly, we should use MovementSelector.

```java
public class MovementSelector implements Predicate<Creature> {

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

On the other hand, we selecting creatures heavier than a chosen amount, we use MassGreaterThanSelector.

```java
public class MassGreaterThanSelector implements Predicate<Creature> {

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

With these building blocks in place, we can perform a search for red and flying creatures like this.

```java
    List<Creature> redAndFlyingCreatures = creatures.stream()
            .filter(new ColorSelector(Color.RED).and(new MovementSelector(Movement.FLYING))).collect(Collectors.toList());
```

But we could also use our paramterized selector like this.

```java
    List<Creature> heavyCreatures = creatures.stream()
            .filter(new MassGreaterThanSelector(500.0).collect(Collectors.toList());
```

## Related patterns

* Repository

## Credits

* [Martin Fowler - Specifications](http://martinfowler.com/apsupp/spec.pdf)
