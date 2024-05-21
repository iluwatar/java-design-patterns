---
title: Specification
category: Behavioral
language: en
tag:
    - Business
    - Domain
    - Encapsulation
    - Enterprise patterns
    - Extensibility
---

## Also known as

* Filter
* Criteria

## Intent

Encapsulate business rules and criteria that an object must satisfy to enable checking these rules in various parts of the application.

## Explanation

Real world example

> Imagine you are organizing a conference and need to filter attendees based on specific criteria such as registration status, payment completion, and session interests.
> 
> Using the Specification design pattern, you would create separate specifications for each criterion (e.g., "IsRegistered", "HasPaid", "IsInterestedInSessionX"). These specifications can be combined dynamically to filter attendees who meet all the required criteria, such as those who are registered, have completed their payment, and are interested in a particular session. This approach allows for flexible and reusable business rules, ensuring that the filtering logic can be easily adjusted as needed without changing the underlying attendee objects.

In Plain Words

> The Specification design pattern allows for the encapsulation and reuse of business rules and criteria in a flexible, combinable manner.

Wikipedia says

> In computer programming, the specification pattern is a particular software design pattern, whereby business rules can be recombined by chaining the business rules together using boolean logic.

**Programmatic Example**

Let's consider a creature pool example. We have a collection of creatures with specific properties. These properties might belong to a predefined, limited set (represented by enums like Size, Movement, and Color) or they might be continuous values (e.g., the mass of a Creature). In cases with continuous values, it's better to use a "parameterized specification," where the property value is provided as an argument when the Creature is instantiated, allowing for greater flexibility. Additionally, predefined and/or parameterized properties can be combined using boolean logic, offering almost limitless selection possibilities (this is known as a "composite specification," explained further below). The advantages and disadvantages of each approach are detailed in the table at the end of this document.

First, here is interface `Creature`.

```java
public interface Creature {
    String getName();

    Size getSize();

    Movement getMovement();

    Color getColor();

    Mass getMass();
}
```

And `Dragon` implementation looks like this.

```java
public class Dragon extends AbstractCreature {

  public Dragon() {
    super("Dragon", Size.LARGE, Movement.FLYING, Color.RED, new Mass(39300.0));
  }
}
```

Now that we want to select some subset of them, we use selectors. To select creatures that fly, we should use `MovementSelector`. The snippet also shows the base class `AbstractSelector`.

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

On the other hand, when selecting creatures heavier than a chosen amount, we use `MassGreaterThanSelector`.

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
var redCreatures = creatures.stream().filter(new ColorSelector(Color.RED)).collect(Collectors.toList());
```

But we could also use our parameterized selector like this:

```java
var heavyCreatures = creatures.stream().filter(new MassGreaterThanSelector(500.0).collect(Collectors.toList()));
```

Our third option is to combine multiple selectors together. Performing a search for special creatures (defined as red, flying, and not small) could be done as follows:

```java
var specialCreaturesSelector = new ColorSelector(Color.RED).and(new MovementSelector(Movement.FLYING)).and(new SizeSelector(Size.SMALL).not());

var specialCreatures = creatures.stream().filter(specialCreaturesSelector).collect(Collectors.toList());
```

## Class diagram

![Specification](./etc/specification.png "Specification")

## Applicability

* Use when you need to filter objects based on different criteria.
* Use when the filtering criteria can change dynamically.
* Ideal for use cases involving complex business rules that must be reused across different parts of an application.

## Known Uses

* Validating user inputs in enterprise applications.
* Filtering search results in e-commerce applications.
* Business rule validation in domain-driven design (DDD).

## Consequences

Benefits:

* Enhances the flexibility and reusability of business rules.
* Promotes [single responsibility principle](https://java-design-patterns.com/principles/#single-responsibility-principle) by separating business rules from the entities.
* Facilitates unit testing of business rules.

Trade-offs:

* Can lead to a proliferation of small classes, increasing complexity.
* Might introduce performance overhead due to the dynamic checking of specifications.

## Related Patterns

* [Strategy](https://java-design-patterns.com/patterns/strategy/): Both patterns involve encapsulating a family of algorithms. Strategy encapsulates different strategies or algorithms, while Specification encapsulates business rules.
* [Composite](https://java-design-patterns.com/patterns/composite/): Often used together with Specification to combine multiple specifications.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Can be used to add additional criteria to a specification dynamically.

## Credits

* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3wlDrze)
* [Implementing Domain-Driven Design](https://amzn.to/4dmBjrB)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Specifications - Martin Fowler](http://martinfowler.com/apsupp/spec.pdf)
