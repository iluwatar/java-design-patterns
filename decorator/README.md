---
title: Decorator
category: Structural
language: en
tag:
    - Gang of Four
    - Enhancement
    - Extensibility
    - Wrapping
---

## Also known as

* Smart Proxy
* Wrapper

## Intent

The Decorator pattern allows for the dynamic addition of responsibilities to objects without modifying their existing code. It achieves this by providing a way to "wrap" objects within objects of similar interface.

## Explanation

Real-world example

> There is an angry troll living in the nearby hills. Usually, it goes bare-handed, but sometimes it has a weapon. To arm the troll it's not necessary to create a new troll but to decorate it dynamically with a suitable weapon.

In plain words

> Decorator pattern lets you dynamically change the behavior of an object at run time by wrapping them in an object of a decorator class.

Wikipedia says

> In object-oriented programming, the decorator pattern is a design pattern that allows behavior to be added to an individual object, either statically or dynamically, without affecting the behavior of other objects from the same class. The decorator pattern is often useful for adhering to the Single Responsibility Principle, as it allows functionality to be divided between classes with unique areas of concern as well as to the Open-Closed Principle, by allowing the functionality of a class to be extended without being modified.

**Programmatic Example**

Let's take the troll example. First of all we have a `SimpleTroll` implementing the `Troll` interface:

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
public class ClubbedTroll implements Troll {

    private final Troll decorated;

    public ClubbedTroll(Troll decorated) {
        this.decorated = decorated;
    }

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
// simple troll
LOGGER.info("A simple looking troll approaches.");
var troll=new SimpleTroll();
troll.attack();
troll.fleeBattle();
LOGGER.info("Simple troll power: {}.\n",troll.getAttackPower());

// change the behavior of the simple troll by adding a decorator
LOGGER.info("A troll with huge club surprises you.");
var clubbedTroll=new ClubbedTroll(troll);
clubbedTroll.attack();
clubbedTroll.fleeBattle();
LOGGER.info("Clubbed troll power: {}.\n",clubbedTroll.getAttackPower());
```

Program output:

```java
A simple looking troll approaches.
The troll tries to grab you!
The troll shrieks in horror and runs away!
Simple troll power:10.

A troll with huge club surprises you.
The troll tries to grab you!
The troll swings at you with a club!
The troll shrieks in horror and runs away!
Clubbed troll power:20.
```

## Class diagram

![alt text](./etc/decorator.urm.png "Decorator pattern class diagram")

## Applicability

Decorator is used to:

* Add responsibilities to individual objects dynamically and transparently, that is, without affecting other objects.
* For responsibilities that can be withdrawn.
* When extending a class is impractical due to the proliferation of subclasses that could result.
* For when a class definition might be hidden or otherwise unavailable for subclassing.

## Tutorials

* [Decorator Pattern Tutorial](https://www.journaldev.com/1540/decorator-design-pattern-in-java-example)

## Known uses

* GUI toolkits often use decorators to dynamically add behaviors like scrolling, borders, or layout management to components.
* [java.io.InputStream](http://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html), [java.io.OutputStream](http://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html), [java.io.Reader](http://docs.oracle.com/javase/8/docs/api/java/io/Reader.html) and [java.io.Writer](http://docs.oracle.com/javase/8/docs/api/java/io/Writer.html)
* [java.util.Collections#synchronizedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedCollection-java.util.Collection-)
* [java.util.Collections#unmodifiableXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-)
* [java.util.Collections#checkedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#checkedCollection-java.util.Collection-java.lang.Class-)

## Consequences

Benefits:

* Greater flexibility than static inheritance.
* Avoids feature-laden classes high up in the hierarchy.
* A decorator and its component aren't identical.
* Responsibilities can be added or removed at runtime.

Trade-offs:

* A decorator and its component aren't identical, so tests for object type will fail.
* Decorators can lead to a system with lots of small objects that look alike to the programmer, making the desired configuration hard to achieve.
* Overuse can complicate the code structure due to the introduction of numerous small classes.

## Related Patterns

* [Adapter](https://java-design-patterns.com/patterns/adapter/): A decorator changes an object's responsibilities, while an adapter changes an object's interface.
* [Composite](https://java-design-patterns.com/patterns/composite/): Decorators can be viewed as a degenerate composite with only one component. However, a decorator adds additional responsibilities—it isn't intended for object aggregation.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Decorator lets you change the skin of an object, while Strategy lets you change the guts.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/4aKFTgS)
