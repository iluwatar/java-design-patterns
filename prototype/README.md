---
title: "Prototype Pattern in Java: Mastering Object Cloning for Efficient Instantiation"
shortTitle: Prototype
description: "Explore the Prototype design pattern in Java with a comprehensive guide on its implementation, advantages, and real-world applications. Learn how to efficiently clone objects and manage object creation in your Java applications."
category: Creational
language: en
tag: 
  - Gang Of Four
  - Instantiation
  - Object composition
  - Polymorphism
---

## Also known as 

* Clone

## Intent of Prototype Design Pattern

The Prototype pattern is used to specify the kinds of objects to create using a prototypical instance, and create new instances through object cloning.

## Detailed Explanation of Prototype Pattern with Real-World Examples

Real-world example

> Imagine a company that manufactures custom-designed furniture. Instead of creating each piece from scratch every time an order is placed, they keep prototypes of their most popular designs. When a customer places an order for a specific design, the company simply clones the prototype of that design and makes the necessary customizations. This approach saves time and resources as the basic structure and design details are already in place, allowing the company to quickly fulfill orders with consistent quality.
>
> In this scenario, the furniture prototypes act like object prototypes in software, enabling efficient creation of new, customized pieces based on existing models.

In plain words

> Create an object based on an existing object through cloning.

Wikipedia says

> The prototype pattern is a creational design pattern in software development. It is used when the type of objects to create is determined by a prototypical instance, which is cloned to produce new objects.

## Programmatic Example of Prototype Pattern in Java

In Java, the prototype pattern is recommended to be implemented as follows. First, create an interface with a method for cloning objects. In this example, `Prototype` interface accomplishes this with its `copy` method.

```java
public abstract class Prototype<T> implements Cloneable {
    @SneakyThrows
    public T copy() {
        return (T) super.clone();
    }
}
```

Our example contains a hierarchy of different creatures. For example, let's look at `Beast` and `OrcBeast` classes.

```java
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class Beast extends Prototype<Beast> {
  public Beast(Beast source) {}
}
```

```java
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class OrcBeast extends Beast {

  private final String weapon;

  public OrcBeast(OrcBeast orcBeast) {
    super(orcBeast);
    this.weapon = orcBeast.weapon;
  }

  @Override
  public String toString() {
    return "Orcish wolf attacks with " + weapon;
  }
}
```

We don't want to go into too many details, but the full example contains also base classes `Mage` and `Warlord` and there are specialized implementations of those for elves and orcs.

To take full advantage of the prototype pattern, we create `HeroFactory` and `HeroFactoryImpl` classes to produce different kinds of creatures from prototypes.

```java
public interface HeroFactory {
  Mage createMage();
  Warlord createWarlord();
  Beast createBeast();
}
```

```java
@RequiredArgsConstructor
public class HeroFactoryImpl implements HeroFactory {

  private final Mage mage;
  private final Warlord warlord;
  private final Beast beast;

  public Mage createMage() {
    return mage.copy();
  }

  public Warlord createWarlord() {
    return warlord.copy();
  }

  public Beast createBeast() {
    return beast.copy();
  }
}
```

Now, we are able to show the full prototype pattern in action producing new creatures by cloning existing instances.

```java
public static void main(String[] args) {
    var factory = new HeroFactoryImpl(
            new ElfMage("cooking"),
            new ElfWarlord("cleaning"),
            new ElfBeast("protecting")
    );
    var mage = factory.createMage();
    var warlord = factory.createWarlord();
    var beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());

    factory = new HeroFactoryImpl(
            new OrcMage("axe"),
            new OrcWarlord("sword"),
            new OrcBeast("laser")
    );
    mage = factory.createMage();
    warlord = factory.createWarlord();
    beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());
}
```

Here's the console output from running the example.

```
08:36:19.012 [main] INFO com.iluwatar.prototype.App -- Elven mage helps in cooking
08:36:19.013 [main] INFO com.iluwatar.prototype.App -- Elven warlord helps in cleaning
08:36:19.014 [main] INFO com.iluwatar.prototype.App -- Elven eagle helps in protecting
08:36:19.014 [main] INFO com.iluwatar.prototype.App -- Orcish mage attacks with axe
08:36:19.014 [main] INFO com.iluwatar.prototype.App -- Orcish warlord attacks with sword
08:36:19.014 [main] INFO com.iluwatar.prototype.App -- Orcish wolf attacks with laser
```

## Detailed Explanation of Prototype Pattern with Real-World Examples

![alt text](./etc/prototype.urm.png "Prototype pattern class diagram")

## When to Use the Prototype Pattern in Java

* When the classes to instantiate are specified at run-time, for example, by dynamic loading.
* To avoid building a class hierarchy of factories that parallels the class hierarchy of products.
* When instances of a class can have one of only a few different combinations of state. It may be more convenient to install a corresponding number of prototypes and clone them rather than instantiating the class manually, each time with the appropriate state.
* When object creation is expensive compared to cloning.
* When the concrete classes to instantiate are unknown until runtime.

## Real-World Applications of Prototype Pattern in Java

* In Java, the `Object.clone()` method is a classic implementation of the Prototype pattern.
* GUI libraries often use prototypes for creating buttons, windows, and other widgets.
* In game development, creating multiple objects (like enemy characters) with similar attributes.

## Benefits and Trade-offs of Prototype Pattern

Benefits:

Leveraging the Prototype pattern in Java applications

* Hides the complexities of instantiating new objects.
* Reduces the number of classes.
* Allows adding and removing objects at runtime.

Trade-offs:

* Requires implementing a cloning mechanism which might be complex.
* Deep cloning can be difficult to implement correctly, especially if the classes have complex object graphs with circular references.

## Related Java Design Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Both involve creating objects, but Prototype uses cloning of a prototype instance whereas Abstract Factory creates objects using factory methods.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Singleton can use a prototype for creating instances if it allows cloning of its single instance.
* [Composite](https://java-design-patterns.com/patterns/composite/): Prototypes are often used within composites to allow for dynamic creation of component trees.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
