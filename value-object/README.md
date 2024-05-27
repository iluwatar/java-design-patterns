---
title: Value Object
category: Structural
language: en
tag:
    - Data access
    - Data binding
    - Domain
    - Encapsulation
    - Enterprise patterns
    - Immutable
    - Optimization
    - Performance
    - Persistence
---

## Also known as

* Embedded Value
* Immutable Object
* Inline Value
* Integrated Value

## Intent

To create immutable objects that represent a descriptive aspect of the domain with no conceptual identity. It aims to enhance performance and reduce memory overhead by storing frequently accessed immutable data directly within the object that uses it, rather than separately.

## Explanation

Real-world example

> Consider the case of a business card. In the real world, a business card contains information such as the person's name, job title, phone number, and email address. This information represents a specific and complete set of attributes describing the contact details of an individual but doesn't have an identity itself beyond this information.
>
> In a software system, you can create a `BusinessCard` class as a Value Object. This class would be immutable, meaning once a `BusinessCard` object is created with a person's details, those details cannot change. If you need a different business card, you create a new instance rather than modifying the existing one. The equality of two `BusinessCard` objects would be based on their contained data rather than their memory addresses, ensuring that two business cards with the same details are considered equal. This mirrors how business cards in real life are used and compared based on their content, not on the physical card itself.

In plain words

> Value objects are equal when their attributes have the same value.

Wikipedia says

> In computer science, a value object is a small object that represents a simple entity whose equality is not based on identity: i.e. two value objects are equal when they have the same value, not necessarily being the same object.

**Programmatic Example**

There is a class for hero statistics in a role-playing game. The statistics contain attributes such as strength, intelligence, and luck. The statistics of different heroes should be equal when all the attributes are equal.

Here is the `HeroStat` class that is the value object. Notice the use of [Lombok's `@Value`](https://projectlombok.org/features/Value) annotation.

```java
@Value(staticConstructor = "valueOf")
@ToString
class HeroStat {
    int strength;
    int intelligence;
    int luck;
}
```

The example creates three different `HeroStat`s and compares their equality.

```java
public static void main(String[] args) {
    var statA = HeroStat.valueOf(10, 5, 0);
    var statB = HeroStat.valueOf(10, 5, 0);
    var statC = HeroStat.valueOf(5, 1, 8);

    LOGGER.info("statA: {}", statA);
    LOGGER.info("statB: {}", statB);
    LOGGER.info("statC: {}", statC);

    LOGGER.info("Are statA and statB equal? {}", statA.equals(statB));
    LOGGER.info("Are statA and statC equal? {}", statA.equals(statC));
}
```

Here's the console output.

```
20:11:12.199 [main] INFO com.iluwatar.value.object.App - HeroStat(strength=10, intelligence=5, luck=0)
20:11:12.202 [main] INFO com.iluwatar.value.object.App - HeroStat(strength=10, intelligence=5, luck=0)
20:11:12.202 [main] INFO com.iluwatar.value.object.App - HeroStat(strength=5, intelligence=1, luck=8)
20:11:12.202 [main] INFO com.iluwatar.value.object.App - Is statA and statB equal : true
20:11:12.203 [main] INFO com.iluwatar.value.object.App - Is statA and statC equal : false
```

## Applicability

Use the Value Object when

* When representing a set of attributes that together describe an entity but without an identity.
* When the equality of the objects is based on the value of the properties, not the identity.
* When you need to ensure that objects cannot be altered once created.
* An application requires high performance and the data involved is immutable.
* Memory footprint reduction is critical, especially in environments with limited resources.
* Objects frequently access a particular piece of immutable data.

## Tutorials

* [VALJOs - Value Java Objects (Stephen Colebourne)](http://blog.joda.org/2014/03/valjos-value-java-objects.html)

## Known uses

* Implementing complex data types like monetary values, measurements, and other domain-specific values.
* [java.util.Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
* [java.time.LocalDate](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html)
* [java.awt.Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html)
* [joda-time, money, beans](http://www.joda.org/)

## Consequences

Benefits:

* Simplifies code by making objects immutable.
* Thread-safe as the object's state cannot change after creation.
* Easier to reason about and maintain.
* Reduces the memory overhead by avoiding separate allocations for immutable data.
* Improves performance by minimizing memory accesses and reducing cache misses.

Trade-offs:

* Creating a new object for every change can be less efficient for complex objects.
* Increased memory usage due to the creation of multiple objects representing different states.
* Increases complexity in object design and can lead to tightly coupled systems.
* Modifying the embedded value necessitates changes across all objects that embed this value, which can complicate maintenance.

## Related Patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Often used to create instances of value objects.
* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): Shares objects to support large quantities using a minimal amount of memory, somewhat similar in intent but different in implementation.
* [Builder](https://java-design-patterns.com/patterns/builder/): Can be used to construct complex value objects step by step.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Can be used to clone existing value objects, though cloning is less common with immutable objects.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Ensures a class has only one instance and provides a global point of access to it, can be used to manage a shared embedded value.

## Credits

* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3wlDrze)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [ValueObject (Martin Fowler)](https://martinfowler.com/bliki/ValueObject.html)
