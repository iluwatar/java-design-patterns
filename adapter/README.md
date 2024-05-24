---
title: Adapter
category: Structural
language: en
tag:
    - Compatibility
    - Decoupling
    - Gang of Four
    - Interface
    - Object composition
    - Wrapping
---

## Also known as

* Wrapper

## Intent

The Adapter pattern converts the interface of a class into another interface that clients expect, enabling compatibility.

## Explanation

Real-world example

> Consider that you have some pictures on your memory card and you need to transfer them to your computer. To transfer them, you need some kind of adapter that is compatible with your computer ports so that you can attach a memory card to your computer. In this case card reader is an adapter. Another example would be the famous power adapter; a three-legged plug can't be connected to a two-pronged outlet, it needs to use a power adapter that makes it compatible with the two-pronged outlets. Yet another example would be a translator translating words spoken by one person to another

In plain words

> Adapter pattern lets you wrap an otherwise incompatible object in an adapter to make it compatible with another class.

Wikipedia says

> In software engineering, the adapter pattern is a software design pattern that allows the interface of an existing class to be used as another interface. It is often used to make existing classes work with others without modifying their source code.

**Programmatic Example**

Consider a wannabe captain that can only use rowing boats but can't sail at all.

First, we have interfaces `RowingBoat` and `FishingBoat`

```java
public interface RowingBoat {
    void row();
}

@Slf4j
public class FishingBoat {
    public void sail() {
        LOGGER.info("The fishing boat is sailing");
    }
}
```

The captain expects an implementation of `RowingBoat` interface to be able to move.

```java
public class Captain {

    private final RowingBoat rowingBoat;

    // default constructor and setter for rowingBoat
    public Captain(RowingBoat rowingBoat) {
        this.rowingBoat = rowingBoat;
    }

    public void row() {
        rowingBoat.row();
    }
}
```

Now, let's say the pirates are coming and our captain needs to escape but there is only a fishing boat available. We need to create an adapter that allows the captain to operate the fishing boat with his rowing boat skills.

```java
@Slf4j
public class FishingBoatAdapter implements RowingBoat {

    private final FishingBoat boat;

    public FishingBoatAdapter() {
        boat = new FishingBoat();
    }

    @Override
    public void row() {
        boat.sail();
    }
}
```

Now the `Captain` can use the `FishingBoat` to escape the pirates.

```java
  public static void main(final String[] args) {
    // The captain can only operate rowing boats but with adapter he is able to
    // use fishing boats as well
    var captain = new Captain(new FishingBoatAdapter());
    captain.row();
}
```

The program outputs:

```
10:25:08.074 [main] INFO com.iluwatar.adapter.FishingBoat -- The fishing boat is sailing
```

## Class diagram

![Adapter](./etc/adapter.urm.png "Adapter class diagram")

## Applicability

Use the Adapter pattern when

* You want to use an existing class, and its interface does not match the one you need
* You want to create a reusable class that cooperates with unrelated or unforeseen classes, that is, classes that don't necessarily have compatible interfaces
* You need to use several existing subclasses, but it's impractical to adapt their interface by subclassing everyone. An object adapter can adapt the interface of its parent class.
* Most of the applications using third-party libraries use adapters as a middle layer between the application and the 3rd party library to decouple the application from the library. If another library has to be used only an adapter for the new library is required without having to change the application code.

## Tutorials

* [Using the Adapter Design Pattern in Java (Dzone)](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Adapter in Java (Refactoring Guru)](https://refactoring.guru/design-patterns/adapter/java/example)
* [The Adapter Pattern in Java (Baeldung)](https://www.baeldung.com/java-adapter-pattern)
* [Adapter Design Pattern (GeeksForGeeks)](https://www.geeksforgeeks.org/adapter-pattern/)

## Consequences

Class and object adapters offer different benefits and drawbacks. A class adapter adapts the Adaptee to the Target by binding to a specific Adaptee class, which means it cannot adapt a class and all its subclasses. This type of adapter allows the Adapter to override some of the Adaptee’s behavior because the Adapter is a subclass of the Adaptee. Additionally, it introduces only one object without needing extra pointer indirection to reach the Adaptee.

On the other hand, an object adapter allows a single Adapter to work with multiple Adaptees, including the Adaptee and all its subclasses. This type of adapter can add functionality to all Adaptees simultaneously. However, it makes overriding the Adaptee’s behavior more difficult, as it requires subclassing the Adaptee and having the Adapter refer to this subclass instead of the Adaptee itself.

## Real-world examples

* `java.io.InputStreamReader` and `java.io.OutputStreamWriter` in the Java IO library.
* GUI component libraries that allow for plug-ins or adapters to convert between different GUI component interfaces.
* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
