---
title: Bridge
category: Structural
language: en
tag:
    - Abstraction
    - Decoupling
    - Extensibility
    - Gang of Four
    - Object composition
---

## Also known as

* Handle/Body

## Intent

Decouple an abstraction from its implementation so that the two can vary independently.

## Explanation

Real-world example

> A real-world example of the Bridge design pattern can be found in the remote control and television relationship.
>
> Imagine a universal remote control (abstraction) that can operate different brands and types of televisions (implementations). The remote control provides a consistent interface for operations like turning on/off, changing channels, and adjusting the volume. Each television brand or type has its own specific implementation of these operations. By using the Bridge pattern, the remote control interface is decoupled from the television implementations, allowing the remote control to work with any television regardless of its brand or internal workings. This separation allows new television models to be added without changing the remote control's code, and different remote controls can be developed to work with the same set of televisions.

In Plain Words

> Bridge pattern is about preferring composition to inheritance. Implementation details are pushed from a hierarchy to another object with a separate hierarchy.

Wikipedia says

> The bridge pattern is a design pattern used in software engineering that is meant to "decouple an abstraction from its implementation so that the two can vary independently"

**Programmatic Example**

Consider you have a weapon with different enchantments, and you are supposed to allow mixing different weapons with different enchantments. What would you do? Create multiple copies of each of the weapons for each of the enchantments or would you just create separate enchantment and set it for the weapon as needed? Bridge pattern allows you to do the second.

Here we have the `Weapon` hierarchy:

```java
public interface Weapon {
    void wield();

    void swing();

    void unwield();

    Enchantment getEnchantment();
}

public class Sword implements Weapon {

    private final Enchantment enchantment;

    public Sword(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        LOGGER.info("The sword is wielded.");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        LOGGER.info("The sword is swung.");
        enchantment.apply();
    }

    @Override
    public void unwield() {
        LOGGER.info("The sword is unwielded.");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }
}

public class Hammer implements Weapon {

    private final Enchantment enchantment;

    public Hammer(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        LOGGER.info("The hammer is wielded.");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        LOGGER.info("The hammer is swung.");
        enchantment.apply();
    }

    @Override
    public void unwield() {
        LOGGER.info("The hammer is unwielded.");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }
}
```

Here's the separate enchantment hierarchy:

```java
public interface Enchantment {
    void onActivate();

    void apply();

    void onDeactivate();
}

public class FlyingEnchantment implements Enchantment {

    @Override
    public void onActivate() {
        LOGGER.info("The item begins to glow faintly.");
    }

    @Override
    public void apply() {
        LOGGER.info("The item flies and strikes the enemies finally returning to owner's hand.");
    }

    @Override
    public void onDeactivate() {
        LOGGER.info("The item's glow fades.");
    }
}

public class SoulEatingEnchantment implements Enchantment {

    @Override
    public void onActivate() {
        LOGGER.info("The item spreads bloodlust.");
    }

    @Override
    public void apply() {
        LOGGER.info("The item eats the soul of enemies.");
    }

    @Override
    public void onDeactivate() {
        LOGGER.info("Bloodlust slowly disappears.");
    }
}
```

Here are both hierarchies in action:

```java
public static void main(String[] args) {
    LOGGER.info("The knight receives an enchanted sword.");
    var enchantedSword = new Sword(new SoulEatingEnchantment());
    enchantedSword.wield();
    enchantedSword.swing();
    enchantedSword.unwield();

    LOGGER.info("The valkyrie receives an enchanted hammer.");
    var hammer = new Hammer(new FlyingEnchantment());
    hammer.wield();
    hammer.swing();
    hammer.unwield();
}
```

Here's the console output.

```
The knight receives an enchanted sword.
The sword is wielded.
The item spreads bloodlust.
The sword is swung.
The item eats the soul of enemies.
The sword is unwielded.
Bloodlust slowly disappears.
The valkyrie receives an enchanted hammer.
The hammer is wielded.
The item begins to glow faintly.
The hammer is swung.
The item flies and strikes the enemies finally returning to owner's hand.
The hammer is unwielded.
The item's glow fades.
```

## Class diagram

![Bridge](./etc/bridge.urm.png "Bridge class diagram")

## Applicability

Consider using the Bridge pattern when:

* You need to avoid a permanent binding between an abstraction and its implementation, such as when the implementation must be chosen or switched at runtime.
* Both the abstractions and their implementations should be extendable via subclassing, allowing independent extension of each component.
* Changes to the implementation of an abstraction should not affect clients, meaning their code should not require recompilation.
* You encounter a large number of classes in your hierarchy, indicating the need to split an object into two parts, a concept referred to as "nested generalizations" by Rumbaugh.
* You want to share an implementation among multiple objects, potentially using reference counting, while keeping this detail hidden from the client, as exemplified by Coplien's String class, where multiple objects can share the same string representation.

## Tutorials

* [Bridge Pattern Tutorial (DigitalOcean)](https://www.digitalocean.com/community/tutorials/bridge-design-pattern-java)

## Known uses

* GUI Frameworks where the abstraction is the window, and the implementation could be the underlying OS windowing system.
* Database Drivers where the abstraction is a generic database interface, and the implementations are database-specific drivers.
* Device Drivers where the abstraction is the device-independent code, and the implementation is the device-dependent code.

## Consequences

Benefits:

* Decoupling Interface and Implementation: The Bridge pattern enhances modularity by separating the interface (the high-level operations) from the implementation (the low-level operations).
* Improved Extensibility: You can extend the abstraction and implementation hierarchies independently.
* Hiding Implementation Details: Clients only see the abstraction's interface, not its implementation.

Trade-offs:

* Increased Complexity: The pattern can complicate the system architecture and code, especially for clients unfamiliar with the pattern.
* Runtime Overhead: The extra layer of abstraction can introduce a performance penalty, although it is often negligible in practice.

## Related Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): The Abstract Factory pattern can be used along with the Bridge pattern to create platforms that are independent of the concrete classes used to create their objects.
* [Adapter](https://java-design-patterns.com/patterns/adapter/): The Adapter pattern is used to provide a different interface to an object, while the Bridge pattern is used to separate an object's interface from its implementation.
* [Composite](https://java-design-patterns.com/patterns/composite/): The Bridge pattern is often used with the Composite pattern to model the implementation details of a component.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): The Strategy pattern is like the Bridge pattern, but with a different intent. Both patterns are based on composition: Strategy uses composition to change the behavior of a class, while Bridge uses composition to separate an abstraction from its implementation.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Pattern-Oriented Software Architecture Volume 1: A System of Patterns](https://amzn.to/3TEnhtl)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
