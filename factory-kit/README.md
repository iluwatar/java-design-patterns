---
title: Factory Kit
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

## Intent

Define a factory of immutable content with separated builder and factory interfaces.

## Explanation

Real-world example

> An analogous real-world example of the Factory Kit pattern is a restaurant kitchen where different types of dishes are prepared. Imagine the kitchen has a central station with various ingredients and recipes registered for different dishes. When an order comes in, the chef consults this central station to gather the necessary ingredients and follow the registered recipe to prepare the dish. This setup allows the kitchen to efficiently manage and switch between different dish preparations without the need for each chef to know the specifics of every recipe, promoting flexibility and consistency in the cooking process.

In plain words

> Factory kit is a configurable object builder, a factory to create factories.

**Programmatic Example**

Imagine a magical weapon factory capable of creating any desired weapon. Upon activation, the master recites the names of the weapon types needed to configure it. Once set up, any of these weapon types can be summoned instantly.

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

## Applicability

Use the Factory Kit pattern when

* The factory class can't anticipate the types of objects it must create
* A new instance of a custom builder is needed instead of a global one
* The types of objects that the factory can build need to be defined outside the class
* The builder and creator interfaces need to be separated
* Game developments and other applications that have user customisation

## Tutorials

* [Factory Kit Pattern (Diego Pacheco)](https://diego-pacheco.medium.com/factory-kit-pattern-66d5ccb0c405)

## Known Uses

* In Java libraries such as the Java Development Kit (JDK) where different rendering engines might be instantiated based on the runtime environment.
* Frameworks like Spring or applications where dependency injection is heavily used, often implement this pattern to manage object creation more flexibly.

## Consequences

Benefits:

* Promotes loose coupling by eliminating the need to bind application-specific classes into the code.
* Simplifies code by shifting the responsibility of instantiation to a factory object.

Trade-offs:

* Can introduce complexity into the code by requiring additional classes and interfaces.
* Sometimes can lead to dependency issues if not properly managed.

## Related patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Often used together with the Factory Kit to create families of related objects.
* [Builder](https://java-design-patterns.com/patterns/builder/): Can be used to construct complex objects step-by-step using a similar approach.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Objects that are created by cloning a prototypical instance often use a factory to manage it.

## Credits

* [Design Pattern Reloaded (Remi Forax)](https://www.youtube.com/watch?v=-k2X7guaArU)
