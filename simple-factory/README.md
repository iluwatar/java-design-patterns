---
layout: pattern
title: Simple Factory
folder: simple-factory
permalink: /patterns/simple-factory/
categories: Creational
tags:
 - Gang of Four
---

## Also known as

* Factory
* Static Factory Method

## Intent

Providing a static method encapsulated in a class called factory, in order to hide the implementation logic and makes client code focus on usage rather then initialization new objects.

## Explanation

Real world example

> Lets say we have a web application connected to SQLServer, but now we want to switch to Oracle. To do so without modifying existing source code, we need to implements Simple Factory pattern, in which a static method can be invoked to create connection to a given database.

Wikipedia says

> Factory is an object for creating other objects – formally a factory is a function or method that returns objects of a varying prototype or class.

**Programmatic Example**

We have an interface "Car" and tow implementations "Ford" and "Ferrari".

```java
/**
 * Car interface.
 */
public interface Car {
  
  public String getDescription();
  
}

/**
 * Ford implementation.
 */
public class Ford implements Car {

  static final String DESCRIPTION = "This is Ford.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

/**
 * Ferrari implementation.
 */
public class Ferrari implements Car {
   
  static final String DESCRIPTION = "This is Ferrari.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
```

Then we have the static method "getCar" to create car objects encapsulated in the factory class "CarSimpleFactory".

```java
/**
 * Factory of cars.
 */
public class CarSimpleFactory {
  
  /**
   * Enumeration for different types of cars.
   */
  static enum CarType {
    FORD, FERRARI
  }
  
  /**
   * Factory method takes as parameter a car type and initiate the appropriate class.
   */
  public static Car getCar(CarType type) {
    switch (type) {
      case FORD: return new Ford();
      case FERRARI: return new Ferrari();
      default: throw new IllegalArgumentException("Model not supported.");
    }
  }
}
```

Now on the client code we can create differentes types of cars(Ford or Ferrari) using the factory class.

```java
Car car1 = CarSimpleFactory.getCar(CarSimpleFactory.CarType.FORD);
Car car2 = CarSimpleFactory.getCar(CarSimpleFactory.CarType.FERRARI);
LOGGER.info(car1.getDescription());
LOGGER.info(car2.getDescription());
```

Program output:

```java
This is Ford.
This Ferrari.
```
## Applicability

Use the Simple Factory pattern when you only care about the creation of a object, not how to create and manage it.

## Disadvantages:

The code becomes more complicated than it should be. 

## Related patterns

[Factory Method](https://java-design-patterns.com/patterns/factory-method/)
[Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)
[Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/)


