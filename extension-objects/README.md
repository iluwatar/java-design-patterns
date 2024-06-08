---
title: "Extension Objects Pattern in Java: Enhancing Object Functionality Flexibly"
shortTitle: Extension Objects
description: "Learn about the Extension Objects Design Pattern in Java. Understand its purpose, benefits, and implementation with examples to enhance your software design."
category: Structural
language: en
tag:
  - Encapsulation
  - Extensibility
  - Object composition
  - Polymorphism
---

## Also known as

* Interface Extensions

## Intent of Extension Objects Design Pattern

The Extension Objects pattern allows for the flexible extension of an object's behavior without modifying its structure, by attaching additional objects that can dynamically add new functionality.

## Detailed Explanation of Extension Objects Pattern with Real-World Examples

Real-world example

> An analogous real-world example of the Extension Objects design pattern can be found in modular kitchen appliances. Consider a base blender unit to which different attachments can be added, such as a food processor, juicer, or grinder. Each attachment adds new functionality to the blender without altering the base unit itself. Users can dynamically switch between different functionalities based on their current needs, making the blender highly versatile and adaptable to various tasks. This mirrors the Extension Objects pattern in software, where new functionalities are added to an object dynamically and contextually, enhancing flexibility and reuse.

In plain words

> The Extension Objects pattern is used to dynamically add functionality to objects without modifying their core classes. It is a behavioural design pattern used for adding new functionality to existing classes and objects within a program. This pattern provides programmers with the ability to extend/modify class functionality without having to refactor existing source code.

Wikipedia says

> In object-oriented computer programming, an extension objects pattern is a design pattern added to an object after the original object was compiled. The modified object is often a class, a prototype or a type. Extension object patterns are features of some object-oriented programming languages. There is no syntactic difference between calling an extension method and calling a method declared in the type definition.

## Programmatic Example of Extension Objects Pattern in Java

The Extension Objects pattern allows for the flexible extension of an object's behavior without modifying its structure, by attaching additional objects that can dynamically add new functionality.

In this Java implementation, we have three types of units: `SoldierUnit`, `SergeantUnit`, and `CommanderUnit`. Each unit can have extensions that provide additional functionality. The extensions are `SoldierExtension`, `SergeantExtension`, and `CommanderExtension`.

The `Unit` class is the base class for all units. It has a method `getUnitExtension` that returns an extension object based on the extension name.

```java
public abstract class Unit {
  private String name;

  protected Unit(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public abstract UnitExtension getUnitExtension(String extensionName);
}
```

The `UnitExtension` interface is the base interface for all extensions. Each specific extension will implement this interface.

```java
public interface UnitExtension {
  String getName();
}
```

The `SoldierUnit` class is a specific type of unit. It overrides the `getUnitExtension` method to return a `SoldierExtension` object.

```java
public class SoldierUnit extends Unit {
  public SoldierUnit(String name) {
    super(name);
  }

  @Override
  public UnitExtension getUnitExtension(String extensionName) {
    if ("SoldierExtension".equals(extensionName)) {
      return new SoldierExtension(this);
    }
    return null;
  }
}
```

The `SoldierExtension` class is a specific type of extension. It implements the `UnitExtension` interface and provides additional functionality for the `SoldierUnit`.

```java
public class SoldierExtension implements UnitExtension {
  private SoldierUnit unit;

  public SoldierExtension(SoldierUnit unit) {
    this.unit = unit;
  }

  @Override
  public String getName() {
    return "SoldierExtension";
  }

  public void soldierReady() {
    // additional functionality for SoldierUnit
  }
}
```

In the `main` application, we create different types of units and check for each unit to have an extension. If the extension exists, we call the specific method on the extension object.

```java
public class App {
  public static void main(String[] args) {
    var soldierUnit = new SoldierUnit("SoldierUnit1");
    var sergeantUnit = new SergeantUnit("SergeantUnit1");
    var commanderUnit = new CommanderUnit("CommanderUnit1");

    checkExtensionsForUnit(soldierUnit);
    checkExtensionsForUnit(sergeantUnit);
    checkExtensionsForUnit(commanderUnit);
  }

  private static void checkExtensionsForUnit(Unit unit) {
    var extension = "SoldierExtension";
    Optional.ofNullable(unit.getUnitExtension(extension))
        .map(e -> (SoldierExtension) e)
        .ifPresentOrElse(SoldierExtension::soldierReady, () -> System.out.println(unit.getName() + " without " + extension));
  }
}
```

This produces the following console output.

```
22:58:03.779 [main] INFO concreteextensions.Soldier -- [Soldier] SoldierUnit1 is ready!
22:58:03.781 [main] INFO App -- SoldierUnit1 without SergeantExtension
22:58:03.782 [main] INFO App -- SoldierUnit1 without CommanderExtension
22:58:03.782 [main] INFO App -- SergeantUnit1 without SoldierExtension
22:58:03.783 [main] INFO concreteextensions.Sergeant -- [Sergeant] SergeantUnit1 is ready!
22:58:03.783 [main] INFO App -- SergeantUnit1 without CommanderExtension
22:58:03.783 [main] INFO App -- CommanderUnit1 without SoldierExtension
22:58:03.783 [main] INFO App -- CommanderUnit1 without SergeantExtension
22:58:03.783 [main] INFO concreteextensions.Commander -- [Commander] CommanderUnit1 is ready!
```

This example demonstrates how the Extension Objects pattern allows for the flexible extension of an object's behavior without modifying its structure.

## Detailed Explanation of Extension Objects Pattern with Real-World Examples

![Extension_objects](./etc/extension_obj.png "Extension objects")

## When to Use the Extension Objects Pattern in Java

This pattern is applicable in scenarios where an object's functionality needs to be extended at runtime, avoiding the complications of subclassing. It's particularly useful in systems where object capabilities need to be augmented post-deployment, or where the capabilities might vary significantly across instances.

## Real-World Applications of Extension Objects Pattern in Java

* Extending services in an application server without altering existing code.
* Plugins in IDEs like IntelliJ IDEA or Eclipse to add features to the base application.
* Enabling additional features in enterprise software based on license levels.
* [OpenDoc](https://en.wikipedia.org/wiki/OpenDoc)
* [Object Linking and Embedding](https://en.wikipedia.org/wiki/Object_Linking_and_Embedding)

## Benefits and Trade-offs of Extension Objects Pattern

Benefits:

* Enhances flexibility by allowing dynamic extension of an object's capabilities.
* Promotes loose coupling between the base object and its extensions.
* Supports the [Open/Closed Principle](https://java-design-patterns.com/principles/#open-closed-principle) by keeping the object open for extension but closed for modification.

Trade-offs:

* Can increase complexity due to the management of extension objects.
* May introduce performance overhead if the interaction between objects and extensions is not efficiently designed.

## Related Java Design Patterns

* [Decorator](https://java-design-patterns.com/patterns/decorator/): Similar in intent to add responsibilities dynamically, but uses a different structure.
* [Composite](https://java-design-patterns.com/patterns/composite/): Also manages a group of objects, which can be seen as a form of extension.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Offers an alternative way to change the behavior of an object dynamically.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/4aBMuuL)
* [Pattern-Oriented Software Architecture: A System of Patterns](https://amzn.to/3Q9YOtX)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3W6IZYQ)
