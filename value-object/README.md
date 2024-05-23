---
title: Value Object
category: Creational
language: en
tag:
    - Data binding
    - Domain
    - Encapsulation
    - Immutable
---

## Also known as

* Immutable Object

## Intent

To create immutable objects that represent a descriptive aspect of the domain with no conceptual identity.

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
var statA = HeroStat.valueOf(10, 5, 0);
var statB = HeroStat.valueOf(10, 5, 0);
var statC = HeroStat.valueOf(5, 1, 8);

LOGGER.info("statA: {}", statA);
LOGGER.info("statB: {}", statB);
LOGGER.info("statC: {}", statC);

LOGGER.info("Are statA and statB equal? {}", statA.equals(statB));
LOGGER.info("Are statA and statC equal? {}", statA.equals(statC));
```

Here's the console output.

```
20:11:12.199 [main] INFO com.iluwatar.value.object.App - HeroStat(strength=10, intelligence=5, luck=0)
20:11:12.202 [main] INFO com.iluwatar.value.object.App - HeroStat(strength=10, intelligence=5, luck=0)
20:11:12.202 [main] INFO com.iluwatar.value.object.App - HeroStat(strength=5, intelligence=1, luck=8)
20:11:12.202 [main] INFO com.iluwatar.value.object.App - Is statA and statB equal : true
20:11:12.203 [main] INFO com.iluwatar.value.object.App - Is statA and statC equal : false
```

## Class diagram

![Value Object](./etc/value-object.png "Value Object")

## Applicability

Use the Value Object when

* When representing a set of attributes that together describe an entity but without an identity.
* When the equality of the objects is based on the value of the properties, not the identity.
* When you need to ensure that objects cannot be altered once created.

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

Trade-offs:

* Creating a new object for every change can be less efficient for complex objects.
* Increased memory usage due to the creation of multiple objects representing different states.

## Related Patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Often used to create instances of value objects.
* [Builder](https://java-design-patterns.com/patterns/builder/): Can be used to construct complex value objects step by step.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Can be used to clone existing value objects, though cloning is less common with immutable objects.

## Credits

* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3wlDrze)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [ValueObject - Martin Fowler](https://martinfowler.com/bliki/ValueObject.html)
* [VALJOs - Value Java Objects: Stephen Colebourne](http://blog.joda.org/2014/03/valjos-value-java-objects.html)
* [Value Object : Wikipedia](https://en.wikipedia.org/wiki/Value_object)
