---
title: Singleton
category: Creational
language: en
tag:
    - Gang of Four
    - Instantiation
    - Lazy initialization
    - Resource management
---

## Also known as

* Single Instance

## Intent

Ensure a class only has one instance, and provide a global point of access to it.

## Explanation

Real-world example

> A real-world analogy for the Singleton pattern is a government issuing a passport. In a country, each citizen can only be issued one valid passport at a time. The passport office ensures that no duplicate passports are issued to the same person. Whenever a citizen needs to travel, they must use this single passport, which serves as the unique, globally recognized identifier for their travel credentials. This controlled access and unique instance management in the real world mirrors how the Singleton pattern controls the instantiation of a class in software.

In plain words

> Ensures that only one object of a particular class is ever created.

Wikipedia says

> In software engineering, the singleton pattern is a software design pattern that restricts the instantiation of a class to one object. This is useful when exactly one object is needed to coordinate actions across the system.

**Programmatic Example**

Joshua Bloch, Effective Java 2nd Edition p.18

> A single-element enum type is the best way to implement a singleton

```java
public enum EnumIvoryTower {
  INSTANCE
}
```

Then in order to use:

```java
    var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
    var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
    LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
    LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);
```

The console output

```
enumIvoryTower1=com.iluwatar.singleton.EnumIvoryTower@1221555852
enumIvoryTower2=com.iluwatar.singleton.EnumIvoryTower@1221555852
```

## Applicability

Use the Singleton pattern when

* There must be exactly one instance of a class, and it must be accessible to clients from a well-known access point
* When the sole instance should be extensible by subclassing, and clients should be able to use an extended instance without modifying their code

## Known uses

* The logging class
* Configuration classes in many applications
* Connection pools
* File manager
* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)

## Consequences

Benefits:

* Controlled access to the single instance.
* Reduced namespace pollution.
* Allows refinement of operations and representation.
* Permits a variable number of instances (more than one, if desired).
* More flexible than class operations.

Trade-offs:

* Difficult to test due to global state.
* Potentially more complex lifecycle management.
* Can introduce bottlenecks if used in a concurrent context without careful synchronization.

## Related Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Often used to ensure a class only has one instance.
* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Singleton pattern can be implemented using a Factory Method to encapsulate the creation logic.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Avoids the need to create instances, can work alongside Singleton to manage unique instances.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
