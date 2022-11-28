---
title: Value Object
category: Creational
language: en
tag:
 - Instantiation
---

## Intent

Provide objects which follow value semantics rather than reference semantics.
This means value objects' equality is not based on identity. Two value objects are
equal when they have the same value, not necessarily being the same object.

## Explanation

Real-world example

> There is a class for hero statistics in a role-playing game. The statistics contain attributes
> such as strength, intelligence, and luck. The statistics of different heroes should be equal
> when all the attributes are equal.

In plain words

> Value objects are equal when their attributes have the same value

Wikipedia says

> In computer science, a value object is a small object that represents a simple entity whose 
> equality is not based on identity: i.e. two value objects are equal when they have the same 
> value, not necessarily being the same object.

**Programmatic Example**

Here is the `HeroStat` class that is the value object. Notice the use of 
[Lombok's `@Value`](https://projectlombok.org/features/Value) annotation.

```java
@Value(staticConstructor = "valueOf")
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

LOGGER.info(statA.toString());
LOGGER.info(statB.toString());
LOGGER.info(statC.toString());

LOGGER.info("Is statA and statB equal : {}", statA.equals(statB));
LOGGER.info("Is statA and statC equal : {}", statA.equals(statC));
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

![alt text](./etc/value-object.png "Value Object")

## Applicability

Use the Value Object when

* The object's equality needs to be based on the object's value

## Known uses

* [java.util.Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
* [java.time.LocalDate](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html)
* [joda-time, money, beans](http://www.joda.org/)

## Credits

* [Patterns of Enterprise Application Architecture](http://www.martinfowler.com/books/eaa.html)
* [ValueObject](https://martinfowler.com/bliki/ValueObject.html)
* [VALJOs - Value Java Objects : Stephen Colebourne's blog](http://blog.joda.org/2014/03/valjos-value-java-objects.html)
* [Value Object : Wikipedia](https://en.wikipedia.org/wiki/Value_object)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
