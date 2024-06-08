---
title: "Factory Method Pattern in Java: Enhancing Flexibility with Polymorphic Manufacturing"
shortTitle: Factory Method
description: "Learn about the Factory Method pattern in Java. Explore examples, uses, benefits, and how it enhances code flexibility and maintenance."
category: Creational
language: en
tag:
  - Encapsulation
  - Gang of Four
  - Instantiation
  - Object composition
  - Polymorphism
---

## Also known as

* Virtual Constructor

## Intent of Factory Method Design Pattern

Define an interface for creating an object using the Factory Method Pattern, but let subclasses decide which class to instantiate. This creational design pattern lets a class defer instantiation to subclasses, enhancing code flexibility and maintenance.

## Detailed Explanation of Factory Method Pattern with Real-World Examples

Real-world example

> Imagine a logistics company that needs to deliver different types of packages: standard, express, and oversized. The company has a central system that processes delivery requests but does not know the specifics of how each package type is handled. To manage this, the company uses a Factory Method pattern.
>
> In this setup, there is a central `DeliveryRequest` class with a method `createPackage()`. This method is overridden in subclasses like `StandardDelivery`, `ExpressDelivery`, and `OversizedDelivery`, each of which knows how to create and manage the respective package type. This way, the central system can handle delivery requests without needing to know the details of how each package type is created and processed, allowing for flexibility and easier maintenance.

In plain words

> It provides a way to delegate the instantiation logic to child classes.

Wikipedia says

> In class-based programming, the factory method pattern is a creational pattern that uses factory methods to deal with the problem of creating objects without having to specify the exact class of the object that will be created. This is done by creating objects by calling a factory method — either specified in an interface and implemented by child classes, or implemented in a base class and optionally overridden by derived classes—rather than by calling a constructor.

## Programmatic Example of Factory Method Pattern in Java

The Factory Method approach is pivotal in Java Design Patterns for achieving flexible and maintainable code as we see in the following example.

Blacksmith manufactures weapons. Elves require Elvish weapons and orcs require Orcish weapons. Depending on the customer at hand the right type of blacksmith is summoned.

First of all, we have a `Blacksmith` interface and some implementations for it:

```java
public interface Blacksmith {
  Weapon manufactureWeapon(WeaponType weaponType);
}

public class ElfBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return ELFARSENAL.get(weaponType);
  }
}

public class OrcBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return ORCARSENAL.get(weaponType);
  }
}
```

When the customers come, the correct type of blacksmith is summoned and requested weapons are manufactured:

```java
public static void main(String[] args) {

    Blacksmith blacksmith = new OrcBlacksmith();
    Weapon weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
    LOGGER.info(MANUFACTURED, blacksmith, weapon);
    weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
    LOGGER.info(MANUFACTURED, blacksmith, weapon);

    blacksmith = new ElfBlacksmith();
    weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
    LOGGER.info(MANUFACTURED, blacksmith, weapon);
    weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
    LOGGER.info(MANUFACTURED, blacksmith, weapon);
}
```

Program output:
```
06:40:07.269 [main] INFO com.iluwatar.factory.method.App -- The orc blacksmith manufactured an orcish spear
06:40:07.271 [main] INFO com.iluwatar.factory.method.App -- The orc blacksmith manufactured an orcish axe
06:40:07.272 [main] INFO com.iluwatar.factory.method.App -- The elf blacksmith manufactured an elven spear
06:40:07.272 [main] INFO com.iluwatar.factory.method.App -- The elf blacksmith manufactured an elven axe
```

## When to Use the Factory Method Pattern in Java

Use the Factory Method Pattern in Java when:

* Class cannot anticipate the class of objects it must create.
* Class wants its subclasses to specify the objects it creates.
* Classes delegate responsibility to one of several helper subclasses, and you want to localize the knowledge of which helper subclass is the delegate.

## Real-World Applications of Factory Method Pattern in Java

* [java.util.Calendar](http://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
* [java.util.ResourceBundle](http://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
* [java.text.NumberFormat](http://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
* [java.nio.charset.Charset](http://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
* [java.net.URLStreamHandlerFactory](http://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html#createURLStreamHandler-java.lang.String-)
* [java.util.EnumSet](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of-E-)
* [javax.xml.bind.JAXBContext](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--)
* Frameworks that run application components, configured dynamically at runtime.

## Benefits and Trade-offs of Factory Method Pattern

Benefits:

* The Factory Method Pattern provides hooks for subclasses, enhancing code flexibility and maintainability.
* Connects parallel class hierarchies.
* Eliminates the need to bind application-specific classes into the code. The code only deals with the product interface; hence it can work with any user-defined concrete product classes.

Trade-offs:

* Can complicate the code by requiring the addition of new subclasses to implement the extended factory methods.

## Related Java Design Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Factory methods are often called within Abstract Factory patterns.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): A factory method that returns a new instance of a class that is a clone of a prototype class.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0Rk5y)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/3UpTLrG)
* [Patterns of Enterprise Application Architecture](https://amzn.to/4b2ZxoM)
