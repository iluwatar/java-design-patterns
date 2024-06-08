---
title: "Factory Kit Pattern in Java: Crafting Flexible Component Assemblies"
shortTitle: Factory Kit
description: "Learn about the Factory Kit Pattern in Java with detailed explanations, real-world examples, and practical applications. Improve your Java skills with our comprehensive guide."
category: Creational
language: en
tag:
  - Abstraction
  - Decoupling
  - Encapsulation
  - Generic
  - Instantiation
  - Object composition
---

## Also known as

* Object Kit
* Toolkit

## Intent of Factory Kit Design Pattern

The Factory Kit Pattern in Java is a powerful design pattern that helps in creating factories with separated builder and factory interfaces. This pattern is essential for managing complex object creation scenarios.

## Detailed Explanation of Factory Kit Pattern with Real-World Examples

Real-world example

> An analogous real-world example of the Factory Kit Pattern is a restaurant kitchen where different types of dishes are prepared efficiently. This setup promotes flexibility and consistency, similar to how the Factory Kit Pattern operates in Java. Imagine the kitchen has a central station with various ingredients and recipes registered for different dishes. When an order comes in, the chef consults this central station to gather the necessary ingredients and follow the registered recipe to prepare the dish. This setup allows the kitchen to efficiently manage and switch between different dish preparations without the need for each chef to know the specifics of every recipe, promoting flexibility and consistency in the cooking process.

In plain words

> Factory kit is a configurable object builder, a factory to create factories.

## Programmatic Example of Factory Kit Pattern in Java

Imagine a magical weapon factory in Java capable of creating any desired weapon using the Factory Kit Pattern. This pattern allows for configurable object builders, making it ideal for scenarios where the types of objects are not known upfront.

Upon activation, the master recites the names of the weapon types needed to configure it. Once set up, any of these weapon types can be summoned instantly.

Let's first define the simple `Weapon` hierarchy.

```java
public interface Weapon {
}

public enum WeaponType {
    SWORD,
    AXE,
    BOW,
    SPEAR
}

public class Sword implements Weapon {
    @Override
    public String toString() {
        return "Sword";
    }
}

// Axe, Bow, and Spear are defined similarly...
```

Next, we define a functional interface that allows adding a builder with a name to the factory.

```java
public interface Builder {
    void add(WeaponType name, Supplier<Weapon> supplier);
}
```

The meat of the example is the `WeaponFactory` interface that effectively implements the factory kit pattern. The method `#factory` is used to configure the factory with the classes it needs to be able to construct. The method `#create` is then used to create object instances.

```java
public interface WeaponFactory {

    static WeaponFactory factory(Consumer<Builder> consumer) {
        var map = new HashMap<WeaponType, Supplier<Weapon>>();
        consumer.accept(map::put);
        return name -> map.get(name).get();
    }

    Weapon create(WeaponType name);
}
```

Now, we can show how `WeaponFactory` can be used.

```java
  public static void main(String[] args) {
    var factory = WeaponFactory.factory(builder -> {
        builder.add(WeaponType.SWORD, Sword::new);
        builder.add(WeaponType.AXE, Axe::new);
        builder.add(WeaponType.SPEAR, Spear::new);
        builder.add(WeaponType.BOW, Bow::new);
    });
    var list = new ArrayList<Weapon>();
    list.add(factory.create(WeaponType.AXE));
    list.add(factory.create(WeaponType.SPEAR));
    list.add(factory.create(WeaponType.SWORD));
    list.add(factory.create(WeaponType.BOW));
    list.forEach(weapon -> LOGGER.info("{}", weapon.toString()));
}
```

Here is the console output when the example is run.

```
06:32:23.026 [main] INFO com.iluwatar.factorykit.App -- Axe
06:32:23.029 [main] INFO com.iluwatar.factorykit.App -- Spear
06:32:23.029 [main] INFO com.iluwatar.factorykit.App -- Sword
06:32:23.029 [main] INFO com.iluwatar.factorykit.App -- Bow
```

## When to Use the Factory Kit Pattern in Java

Use the Factory Kit Pattern when

* The factory class cannot anticipate the types of objects it must create, and a new instance of a custom builder is needed.
* A new instance of a custom builder is needed instead of a global one.
* The types of objects that the factory can build need to be defined outside the class.
* The builder and creator interfaces need to be separated.
* Game developments and other applications that have user customization.

## Factory Kit Pattern Java Tutorials

* [Factory Kit Pattern (Diego Pacheco)](https://diego-pacheco.medium.com/factory-kit-pattern-66d5ccb0c405)

## Real-World Applications of Factory Kit Pattern in Java

* In Java libraries such as the Java Development Kit (JDK) where different rendering engines might be instantiated based on the runtime environment.
* Frameworks like Spring or applications where dependency injection is heavily used, often implement this pattern to manage object creation more flexibly.

## Benefits and Trade-offs of Factory Kit Pattern

Benefits:

* The Factory Kit Pattern in Java promotes loose coupling by eliminating the need to bind application-specific classes into the code.
* It simplifies the code by shifting the responsibility of instantiation to a factory object, making the development process more efficient.

Trade-offs:

* Can introduce complexity into the code by requiring additional classes and interfaces.
* Sometimes can lead to dependency issues if not properly managed.

## Related Java Design Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Often used together with the Factory Kit to create families of related objects.
* [Builder](https://java-design-patterns.com/patterns/builder/): Can be used to construct complex objects step-by-step using a similar approach.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Objects that are created by cloning a prototypical instance often use a factory to manage it.

## References and Credits

* [Design Pattern Reloaded (Remi Forax)](https://www.youtube.com/watch?v=-k2X7guaArU)
