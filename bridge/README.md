---
title: Bridge
category: Structural
language: en
tag:
    - Decoupling
    - Extensibility
    - Gang of Four
---

## Also known as

Handle/Body

## Intent

Decouple an abstraction from its implementation so that the two can vary independently.

## Explanation

Real-world example

> Consider you have a weapon with different enchantments, and you are supposed to allow mixing different weapons with different enchantments. What would you do? Create multiple copies of each of the weapons for each of the enchantments or would you just create separate enchantment and set it for the weapon as needed? Bridge pattern allows you to do the second.

In Plain Words

> Bridge pattern is about preferring composition to inheritance. Implementation details are pushed from a hierarchy to another object with a separate hierarchy.

Wikipedia says

> The bridge pattern is a design pattern used in software engineering that is meant to "decouple an abstraction from its implementation so that the two can vary independently"

**Programmatic Example**

Translating our weapon example from above. Here we have the `Weapon` hierarchy:

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
LOGGER.info("The knight receives an enchanted sword.");
        var enchantedSword=new Sword(new SoulEatingEnchantment());
        enchantedSword.wield();
        enchantedSword.swing();
        enchantedSword.unwield();

        LOGGER.info("The valkyrie receives an enchanted hammer.");
        var hammer=new Hammer(new FlyingEnchantment());
        hammer.wield();
        hammer.swing();
        hammer.unwield();
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

![alt text](./etc/bridge.urm.png "Bridge class diagram")

## Applicability

Use the Bridge pattern when

* You want to avoid a permanent binding between an abstraction and its implementation. This might be the case, for example, when the implementation must be selected or switched at run-time.
* Both the abstractions and their implementations should be extensible by subclassing. In this case, the Bridge pattern lets you combine the different abstractions and implementations and extend them independently.
* Changes in the implementation of an abstraction should have no impact on clients; that is, their code should not have to be recompiled.
* You have a proliferation of classes. Such a class hierarchy indicates the need for splitting an object into two parts. Rumbaugh uses the term "nested generalizations" to refer to such class hierarchies.
* You want to share an implementation among multiple objects (perhaps using reference counting), and this fact should be hidden from the client. A simple example is Coplien's String class, in which multiple objects can share the same string representation.

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

* [Adapter](https://java-design-patterns.com/patterns/adapter/): The Adapter pattern is used to provide a different interface to an object, while the Bridge pattern is used to separate an object's interface from its implementation.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): The Strategy pattern is like the Bridge pattern, but with a different intent. Both patterns are based on composition: Strategy uses composition to change the behavior of a class, while Bridge uses composition to separate an abstraction from its implementation.
* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): The Abstract Factory pattern can be used along with the Bridge pattern to create platforms that are independent of the concrete classes used to create their objects.
* [Composite](https://java-design-patterns.com/patterns/composite/): The Bridge pattern is often used with the Composite pattern to model the implementation details of a component.

## Tutorials

* [Bridge Pattern Tutorial](https://www.journaldev.com/1491/bridge-design-pattern-java)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Pattern-Oriented Software Architecture Volume 1: A System of Patterns](https://amzn.to/3TEnhtl)
