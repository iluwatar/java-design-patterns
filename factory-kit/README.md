---
title: Factory Kit
category: Creational
language: en
tag:
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

> Imagine a magical weapon factory that can create any type of weapon wished for. When the factory is unboxed, the master recites the weapon types needed to prepare it. After that, any of those weapon types can be summoned in an instant.

In plain words

> Factory kit is a configurable object builder, a factory to create factories.

**Programmatic Example**

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
var factory = WeaponFactory.factory( builder-> {
    builder.add(WeaponType.SWORD,Sword::new);
    builder.add(WeaponType.AXE,Axe::new);
    builder.add(WeaponType.SPEAR,Spear::new);
    builder.add(WeaponType.BOW,Bow::new);
});
var list = new ArrayList<Weapon>();
list.add(factory.create(WeaponType.AXE));
list.add(factory.create(WeaponType.SPEAR));
list.add(factory.create(WeaponType.SWORD));
list.add(factory.create(WeaponType.BOW));
list.stream().forEach(weapon->LOGGER.info("{}",weapon.toString()));
```

Here is the console output when the example is run.

```
21:15:49.709 [main] INFO com.iluwatar.factorykit.App - Axe
21:15:49.713 [main] INFO com.iluwatar.factorykit.App - Spear
21:15:49.713 [main] INFO com.iluwatar.factorykit.App - Sword
21:15:49.713 [main] INFO com.iluwatar.factorykit.App - Bow
```

## Class diagram

![alt text](./etc/factory-kit.png "Factory Kit")

## Applicability

Use the Factory Kit pattern when

* The factory class can't anticipate the types of objects it must create
* A new instance of a custom builder is needed instead of a global one
* The types of objects that the factory can build need to be defined outside the class
* The builder and creator interfaces need to be separated
* Game developments and other applications that have user customisation

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

## Tutorials

* [Factory kit implementation tutorial](https://diego-pacheco.medium.com/factory-kit-pattern-66d5ccb0c405)

## Credits

* [Design Pattern Reloaded by Remi Forax](https://www.youtube.com/watch?v=-k2X7guaArU)
