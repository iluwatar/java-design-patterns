---
title: Factory Method
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

Virtual Constructor

## Intent

Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.

## Explanation

Real-world example

> Blacksmith manufactures weapons. Elves require Elvish weapons and orcs require Orcish weapons. Depending on the customer at hand the right type of blacksmith is summoned.

In plain words

> It provides a way to delegate the instantiation logic to child classes.

Wikipedia says

> In class-based programming, the factory method pattern is a creational pattern that uses factory methods to deal with the problem of creating objects without having to specify the exact class of the object that will be created. This is done by creating objects by calling a factory method — either specified in an interface and implemented by child classes, or implemented in a base class and optionally overridden by derived classes—rather than by calling a constructor.

**Programmatic Example**

Taking our blacksmith example above. First of all, we have a `Blacksmith` interface and some implementations for it:

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
Blacksmith blacksmith = new OrcBlacksmith();
Weapon weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
LOGGER.info("{} manufactured {}", blacksmith, weapon);
weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
LOGGER.info("{} manufactured {}", blacksmith, weapon);

blacksmith = new ElfBlacksmith();
weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
LOGGER.info("{} manufactured {}", blacksmith, weapon);
weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
LOGGER.info("{} manufactured {}", blacksmith, weapon);
```

Program output:
```
The orc blacksmith manufactured an orcish spear
The orc blacksmith manufactured an orcish axe
The elf blacksmith manufactured an elven spear
The elf blacksmith manufactured an elven axe
```

## Class diagram

![alt text](./etc/factory-method.urm.png "Factory Method pattern class diagram")

## Applicability

Use the Factory Method pattern when:

* Class cannot anticipate the class of objects it must create.
* Class wants its subclasses to specify the objects it creates.
* Classes delegate responsibility to one of several helper subclasses, and you want to localize the knowledge of which helper subclass is the delegate.

## Known uses

* [java.util.Calendar](http://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
* [java.util.ResourceBundle](http://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
* [java.text.NumberFormat](http://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
* [java.nio.charset.Charset](http://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
* [java.net.URLStreamHandlerFactory](http://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html#createURLStreamHandler-java.lang.String-)
* [java.util.EnumSet](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of-E-)
* [javax.xml.bind.JAXBContext](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--)
* Frameworks that run application components, configured dynamically at runtime.

## Consequences

Benefits:

* Provides hooks for subclasses, creating flexibility in code.
* Connects parallel class hierarchies.
* Eliminates the need to bind application-specific classes into the code. The code only deals with the product interface; hence it can work with any user-defined concrete product classes.

Trade-offs:

* Can complicate the code by requiring the addition of new subclasses to implement the extended factory methods.

## Related Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Factory methods are often called within Abstract Factory patterns.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): A factory method that returns a new instance of a class that is a clone of a prototype class.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0Rk5y)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/3UpTLrG)
* [Patterns of Enterprise Application Architecture](https://amzn.to/4b2ZxoM)
