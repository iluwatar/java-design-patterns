---
title: Abstract Factory
category: Creational
language: en
tag:
    - Abstraction
    - Decoupling
    - Gang of Four
    - Instantiation
    - Polymorphism
---

## Also known as

* Kit

## Intent

Provide an interface for creating families of related or dependent objects without specifying their concrete classes.

## Explanation

Real-world example

> Imagine a furniture company that produces various styles of furniture: modern, Victorian, and rustic. Each style includes products like chairs, tables, and sofas. To ensure consistency within each style, the company uses an Abstract Factory pattern.
>
> In this scenario, the Abstract Factory is an interface for creating families of related furniture objects (chairs, tables, sofas). Each concrete factory (ModernFurnitureFactory, VictorianFurnitureFactory, RusticFurnitureFactory) implements the Abstract Factory interface and creates a set of products that match the specific style. This way, clients can create a whole set of modern or Victorian furniture without worrying about the details of their instantiation. This maintains a consistent style and allows easy swapping of one style of furniture for another.

In plain words

> A factory of factories; a factory that groups the individual but related/dependent factories together without specifying their concrete classes.

Wikipedia says

> The abstract factory pattern provides a way to encapsulate a group of individual factories that have a common theme without specifying their concrete classes

**Programmatic Example**

To create a kingdom we need objects with a common theme. The elven kingdom needs an elven king, elven castle, and elven army whereas the orcish kingdom needs an orcish king, orcish castle, and orcish army. There is a dependency between the objects in the kingdom.

Translating the kingdom example above. First of all, we have some interfaces and implementation for the objects in the kingdom.

```java
public interface Castle {
    String getDescription();
}

public interface King {
    String getDescription();
}

public interface Army {
    String getDescription();
}

// Elven implementations ->
public class ElfCastle implements Castle {
    static final String DESCRIPTION = "This is the elven castle!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

public class ElfKing implements King {
    static final String DESCRIPTION = "This is the elven king!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

public class ElfArmy implements Army {
    static final String DESCRIPTION = "This is the elven Army!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

// Orcish implementations similarly -> ...
```

Then we have the abstraction and implementations for the kingdom factory.

```java
public interface KingdomFactory {
    Castle createCastle();

    King createKing();

    Army createArmy();
}

public class ElfKingdomFactory implements KingdomFactory {

    @Override
    public Castle createCastle() {
        return new ElfCastle();
    }

    @Override
    public King createKing() {
        return new ElfKing();
    }

    @Override
    public Army createArmy() {
        return new ElfArmy();
    }
}

// Orcish implementations similarly -> ...
```

Now, we can design a factory for our different kingdom factories. In this example, we created `FactoryMaker`, responsible for returning an instance of either `ElfKingdomFactory` or `OrcKingdomFactory`. The client can use `FactoryMaker` to create the desired concrete factory which, in turn, will produce different concrete objects (derived from `Army`, `King`, `Castle`). In this example, we also used an enum to parameterize which type of kingdom factory the client will ask for.

```java
public static class FactoryMaker {

    public enum KingdomType {
        ELF, ORC
    }

    public static KingdomFactory makeFactory(KingdomType type) {
        return switch (type) {
            case ELF -> new ElfKingdomFactory();
            case ORC -> new OrcKingdomFactory();
        };
    }
}
```

Here is the main function of our example application:

```java
LOGGER.info("elf kingdom");
createKingdom(Kingdom.FactoryMaker.KingdomType.ELF);
LOGGER.info(kingdom.getArmy().getDescription());
LOGGER.info(kingdom.getCastle().getDescription());
LOGGER.info(kingdom.getKing().getDescription());

LOGGER.info("orc kingdom");
createKingdom(Kingdom.FactoryMaker.KingdomType.ORC);
LOGGER.info(kingdom.getArmy().getDescription());
LOGGER.info(kingdom.getCastle().getDescription());
LOGGER.info(kingdom.getKing().getDescription());
```

The program output:

```
07:35:46.340 [main] INFO com.iluwatar.abstractfactory.App -- elf kingdom
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the elven army!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the elven castle!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the elven king!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- orc kingdom
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the orc army!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the orc castle!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the orc king!
```

## Class diagram

![Abstract Factory](./etc/abstract-factory.urm.png "Abstract Factory class diagram")

## Applicability

Use the Abstract Factory pattern when:

* The system should be independent of how its products are created, composed, and represented.
* You need to configure the system with one of multiple families of products.
* A family of related product objects must be used together, enforcing consistency.
* You want to provide a class library of products, exposing only their interfaces, not their implementations.
* The lifetime of dependencies is shorter than the consumer's lifetime.
* Dependencies need to be constructed using runtime values or parameters.
* You need to choose which product to use from a family at runtime.
* Adding new products or families should not require changes to existing code.

## Tutorials

* [Abstract Factory Design Pattern in Java (DigitalOcean)](https://www.digitalocean.com/community/tutorials/abstract-factory-design-pattern-in-java)
* [Abstract Factory(Refactoring Guru)](https://refactoring.guru/design-patterns/abstract-factory)

## Consequences

Benefits:

* Flexibility: Easily switch between product families without code modifications.

* Decoupling: Client code only interacts with abstract interfaces, promoting portability and maintainability.

* Reusability: Abstract factories and products facilitate component reuse across projects.

* Maintainability: Changes to individual product families are localized, simplifying updates.

Trade-offs:

* Complexity: Defining abstract interfaces and concrete factories adds initial overhead.

* Indirectness: Client code interacts with products indirectly through factories, potentially reducing transparency.

## Known uses

* Java Swing's `LookAndFeel` classes for providing different look-and-feel options.
* Various implementations in the Java Abstract Window Toolkit (AWT) for creating different GUI components.
* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## Related patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Abstract Factory uses Factory Methods to create products.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Abstract Factory classes are often implemented as Singletons.
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/): Similar to Abstract Factory but focuses on configuring and managing a set of related objects in a flexible way.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Design Patterns in Java](https://amzn.to/3Syw0vC)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3HWNf4U)
