---
layout: pattern
title: Abstract Factory
folder: abstract-factory
permalink: /patterns/abstract-factory/
categories: Creational
language: en
tags:
 - Gang of Four
---

## Also known as

Kit

## Intent

Provide an interface for creating families of related or dependent
objects without specifying their concrete classes.

## Explanation

Real-world example

> To create a kingdom we need objects with a common theme. The elven kingdom needs an elven king, elven castle, and elven army whereas the orcish kingdom needs an orcish king, orcish castle, and orcish army. There is a dependency between the objects in the kingdom.

In plain words

> A factory of factories; a factory that groups the individual but related/dependent factories together without specifying their concrete classes.

Wikipedia says

> The abstract factory pattern provides a way to encapsulate a group of individual factories that have a common theme without specifying their concrete classes

**Programmatic Example**

Translating the kingdom example above. First of all, we have some interfaces and implementation for the objects in the 
kingdom.

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
  public Castle createCastle() {
    return new ElfCastle();
  }
  public King createKing() {
    return new ElfKing();
  }
  public Army createArmy() {
    return new ElfArmy();
  }
}

public class OrcKingdomFactory implements KingdomFactory {
  public Castle createCastle() {
    return new OrcCastle();
  }
  public King createKing() {
    return new OrcKing();
  }
  public Army createArmy() {
    return new OrcArmy();
  }
}
```

Now we have the abstract factory that lets us make a family of related objects i.e. elven kingdom factory creates elven castle, king and army, etc.

```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

Program output:

```java
This is the elven castle!
This is the elven king!
This is the elven Army!
```

Now, we can design a factory for our different kingdom factories. In this example, we created `FactoryMaker`, responsible for returning an instance of either `ElfKingdomFactory` or `OrcKingdomFactory`.  
The client can use `FactoryMaker` to create the desired concrete factory which, in turn, will produce different concrete objects (derived from `Army`, `King`, `Castle`).  
In this example, we also used an enum to parameterize which type of kingdom factory the client will ask for.

```java
public static class FactoryMaker {

  public enum KingdomType {
    ELF, ORC
  }

  public static KingdomFactory makeFactory(KingdomType type) {
    switch (type) {
      case ELF:
        return new ElfKingdomFactory();
      case ORC:
        return new OrcKingdomFactory();
      default:
        throw new IllegalArgumentException("KingdomType not supported.");
    }
  }
}

public static void main(String[] args) {
  var app = new App();

  LOGGER.info("Elf Kingdom");
  app.createKingdom(FactoryMaker.makeFactory(KingdomType.ELF));
  LOGGER.info(app.getArmy().getDescription());
  LOGGER.info(app.getCastle().getDescription());
  LOGGER.info(app.getKing().getDescription());

  LOGGER.info("Orc Kingdom");
  app.createKingdom(FactoryMaker.makeFactory(KingdomType.ORC));
  -- similar use of the orc factory
}
```

## Class diagram

![alt text](./etc/abstract-factory.urm.png "Abstract Factory class diagram")


## Applicability

Use the Abstract Factory pattern when

* The system should be independent of how its products are created, composed, and represented
* The system should be configured with one of the multiple families of products
* The family of related product objects is designed to be used together, and you need to enforce this constraint
* You want to provide a class library of products, and you want to reveal just their interfaces, not their implementations
* The lifetime of the dependency is conceptually shorter than the lifetime of the consumer.
* You need a run-time value to construct a particular dependency
* You want to decide which product to call from a family at runtime.
* You need to supply one or more parameters only known at run-time before you can resolve a dependency.
* When you need consistency among products
* You don’t want to change existing code when adding new products or families of products to the program.

Example use cases	

* Selecting to call to the appropriate implementation of FileSystemAcmeService or DatabaseAcmeService or NetworkAcmeService at runtime.
* Unit test case writing becomes much easier
* UI tools for different OS

## Consequences:

* Dependency injection in java hides the service class dependencies that can lead to runtime errors that would have been caught at compile time.
* While the pattern is great when creating predefined objects, adding the new ones might be challenging.
* The code becomes more complicated than it should be since a lot of new interfaces and classes are introduced along with the pattern.

## Tutorial

* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## Known uses

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## Related patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
