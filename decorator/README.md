---
title: "Decorator Pattern in Java: Extending Classes Dynamically"
shortTitle: Decorator
description: "Learn how the Decorator Design Pattern enhances flexibility in Java programming by allowing dynamic addition of responsibilities to objects without modifying their existing code. Explore real-world examples and implementation."
category: Structural
language: en
tag:
  - Enhancement
  - Extensibility
  - Gang of Four
  - Object composition 
  - Wrapping
---

## Also known as

* Smart Proxy
* Wrapper

## Intent of Decorator Design Pattern

The Decorator pattern allows for the dynamic addition of responsibilities to objects without modifying their existing code. It achieves this by providing a way to "wrap" objects within objects of similar interface, enhancing Java design patterns flexibility.

## Detailed Explanation of Decorator Pattern with Real-World Examples

Real-world example

> Imagine a coffee shop where you can customize your coffee order. You start with a basic coffee, and you can add different ingredients like milk, sugar, whipped cream, and so on. Each addition is like a decorator in the Decorator design pattern. The base coffee object can be decorated with additional functionality (flavors, toppings) dynamically. For example, you can start with a plain coffee object, then wrap it with a milk decorator, followed by a sugar decorator, and finally a whipped cream decorator. Each decorator adds new features or modifies the behavior of the coffee object, similar to how the Decorator pattern works in software design.

In plain words

> Decorator pattern lets you dynamically change the behavior of an object at run time by wrapping them in an object of a decorator class.

Wikipedia says

> In object-oriented programming, the decorator pattern is a design pattern that allows behavior to be added to an individual object, either statically or dynamically, without affecting the behavior of other objects from the same class. The decorator pattern is often useful for adhering to the Single Responsibility Principle, as it allows functionality to be divided between classes with unique areas of concern as well as to the Open-Closed Principle, by allowing the functionality of a class to be extended without being modified.

## Programmatic Example of Decorator Pattern in Java

There is an angry troll living in the nearby hills. Usually, it goes bare-handed, but sometimes it has a weapon. To arm the troll it's not necessary to create a new troll but to decorate it dynamically with a suitable weapon.

First, we have a `SimpleTroll` implementing the `Troll` interface:

```java
public interface Troll {
    void attack();

    int getAttackPower();

    void fleeBattle();
}

@Slf4j
public class SimpleTroll implements Troll {

    @Override
    public void attack() {
        LOGGER.info("The troll tries to grab you!");
    }

    @Override
    public int getAttackPower() {
        return 10;
    }

    @Override
    public void fleeBattle() {
        LOGGER.info("The troll shrieks in horror and runs away!");
    }
}
```

Next, we want to add a club for the troll. We can do it dynamically by using a decorator:

```java
@Slf4j
@RequiredArgsConstructor
public class ClubbedTroll implements Troll {

    private final Troll decorated;

    @Override
    public void attack() {
        decorated.attack();
        LOGGER.info("The troll swings at you with a club!");
    }

    @Override
    public int getAttackPower() {
        return decorated.getAttackPower() + 10;
    }

    @Override
    public void fleeBattle() {
        decorated.fleeBattle();
    }
}
```

Here's the troll in action:

```java
  public static void main(String[] args) {

    // simple troll
    LOGGER.info("A simple looking troll approaches.");
    var troll = new SimpleTroll();
    troll.attack();
    troll.fleeBattle();
    LOGGER.info("Simple troll power: {}.\n", troll.getAttackPower());

    // change the behavior of the simple troll by adding a decorator
    LOGGER.info("A troll with huge club surprises you.");
    var clubbedTroll = new ClubbedTroll(troll);
    clubbedTroll.attack();
    clubbedTroll.fleeBattle();
    LOGGER.info("Clubbed troll power: {}.\n", clubbedTroll.getAttackPower());
}
```

Program output:

```
11:34:18.098 [main] INFO com.iluwatar.decorator.App -- A simple looking troll approaches.
11:34:18.100 [main] INFO com.iluwatar.decorator.SimpleTroll -- The troll tries to grab you!
11:34:18.100 [main] INFO com.iluwatar.decorator.SimpleTroll -- The troll shrieks in horror and runs away!
11:34:18.100 [main] INFO com.iluwatar.decorator.App -- Simple troll power: 10.

11:34:18.100 [main] INFO com.iluwatar.decorator.App -- A troll with huge club surprises you.
11:34:18.101 [main] INFO com.iluwatar.decorator.SimpleTroll -- The troll tries to grab you!
11:34:18.101 [main] INFO com.iluwatar.decorator.ClubbedTroll -- The troll swings at you with a club!
11:34:18.101 [main] INFO com.iluwatar.decorator.SimpleTroll -- The troll shrieks in horror and runs away!
11:34:18.101 [main] INFO com.iluwatar.decorator.App -- Clubbed troll power: 20.
```

## When to Use the Decorator Pattern in Java

Decorator is used to:

* Add responsibilities to individual objects dynamically and transparently, that is, without affecting other objects, a key feature of Java design patterns.
* For responsibilities that can be withdrawn.
* When extending a class is impractical due to the proliferation of subclasses that could result.
* For when a class definition might be hidden or otherwise unavailable for subclassing.

## Decorator Pattern Java Tutorials

* [Decorator Design Pattern in Java Example (DigitalOcean)](https://www.digitalocean.com/community/tutorials/decorator-design-pattern-in-java-example)

## Real-World Applications of Decorator Pattern in Java

* GUI toolkits often use decorators to dynamically add behaviors like scrolling, borders, or layout management to components.
* The [java.io.InputStream](http://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html), [java.io.OutputStream](http://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html), [java.io.Reader](http://docs.oracle.com/javase/8/docs/api/java/io/Reader.html) and [java.io.Writer](http://docs.oracle.com/javase/8/docs/api/java/io/Writer.html) classes in Java are well-known examples utilizing the Decorator pattern.
* [java.util.Collections#synchronizedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedCollection-java.util.Collection-)
* [java.util.Collections#unmodifiableXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-)
* [java.util.Collections#checkedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#checkedCollection-java.util.Collection-java.lang.Class-)

## Benefits and Trade-offs of Decorator Pattern

Benefits:

* Greater flexibility than static inheritance.
* Avoids feature-laden classes high up in the hierarchy, showcasing the power of Java design patterns.
* A decorator and its component aren't identical.
* Responsibilities can be added or removed at runtime.

Trade-offs:

* A decorator and its component aren't identical, so tests for object type will fail.
* Decorators can lead to a system with lots of small objects that look alike to the programmer, making the desired configuration hard to achieve.
* Overuse can complicate the code structure due to the introduction of numerous small classes.

## Related Java Design Patterns

* [Adapter](https://java-design-patterns.com/patterns/adapter/): A decorator changes an object's responsibilities, while an adapter changes an object's interface.
* [Composite](https://java-design-patterns.com/patterns/composite/): Decorators can be viewed as a degenerate composite with only one component. However, a decorator adds additional responsibilities—it isn't intended for object aggregation.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Decorator lets you change the skin of an object, while Strategy lets you change the guts.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Functional Programming in Java](https://amzn.to/3JUIc5Q)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/4aKFTgS)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
