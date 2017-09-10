---
layout: pattern
title: Abstract Factory
folder: abstract-factory
permalink: /patterns/abstract-factory/
pumlid: PSZB3OD034NHLa81Czwd6sCC39gVxEUWT1_ssLmTtQLqgR5fM7sTmFGtaV6TZu8prd0r6HtQaMKqAZLk1XjT_E6qgPUZfyc0MdTgx0-8LuUn8ErFXdr98NypXxKyezKV
categories: Creational
tags:
 - Java
 - Gang Of Four
 - Difficulty-Intermediate
---

## Also known as
Kit

## Intent
Provide an interface for creating families of related or dependent
objects without specifying their concrete classes.

## Explanation
Real world example

> To create a kingdom we need objects with common theme. Elven kingdom needs an Elven king, Elven castle and Elven army whereas Orcish kingdom needs an Orcish king, Orcish castle and Orcish army. There is a dependency between the objects in the kingdom.

In plain words

> A factory of factories; a factory that groups the individual but related/dependent factories together without specifying their concrete classes.

Wikipedia says

> The abstract factory pattern provides a way to encapsulate a group of individual factories that have a common theme without specifying their concrete classes

**Programmatic Example**

Translating the kingdom example above. First of all we have some interfaces and implementation for the objects in the kingdom

```
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
  static final String DESCRIPTION = "This is the Elven castle!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfKing implements King {
  static final String DESCRIPTION = "This is the Elven king!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfArmy implements Army {
  static final String DESCRIPTION = "This is the Elven Army!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

// Orcish implementations similarly...

```

Then we have the abstraction and implementations for the kingdom factory

```
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

Now we have our abstract factory that lets us make family of related objects i.e. Elven kingdom factory creates Elven castle, king and army etc.

```
KingdomFactory factory = new ElfKingdomFactory();
Castle castle = factory.createCastle();
King king = factory.createKing();
Army army = factory.createArmy();

castle.getDescription();  // Output: This is the Elven castle!
king.getDescription(); // Output: This is the Elven king!
army.getDescription(); // Output: This is the Elven Army!
```

## Applicability
Use the Abstract Factory pattern when

* a system should be independent of how its products are created, composed and represented
* a system should be configured with one of multiple families of products
* a family of related product objects is designed to be used together, and you need to enforce this constraint
* you want to provide a class library of products, and you want to reveal just their interfaces, not their implementations
* the lifetime of the dependency is conceptually shorter than the lifetime of the consumer.
*	you need a run-time value to construct a particular dependency
*	you want to decide which product to call from a family at runtime.
*	you need to supply one or more parameters only known at run-time before you can resolve a dependency.

## Use Cases:	

*	Selecting to call the appropriate implementation of FileSystemAcmeService or DatabaseAcmeService or NetworkAcmeService at runtime.
*	Unit test case writing becomes much easier

## Consequences:

*	Dependency injection in java hides the service class dependencies that can lead to runtime errors that would have been caught at compile time.

## Real world examples

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
