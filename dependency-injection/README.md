---
layout: pattern
title: Dependency Injection
folder: dependency-injection
permalink: /patterns/dependency-injection/
categories: Creational
language: en
tags:
 - Decoupling
---

## Intent

Dependency Injection is a software design pattern in which one or more dependencies (or services) 
are injected, or passed by reference, into a dependent object (or client) and are made part of the 
client's state. The pattern separates the creation of a client's dependencies from its own behavior, 
which allows program designs to be loosely coupled and to follow the inversion of control and single 
responsibility principles.

## Explanation

Real world example

> The old wizard likes to fill his pipe and smoke tobacco once in a while. However, he doesn't want 
> to depend on a single tobacco brand only but likes to be able to enjoy them all interchangeably.    

In plain words

> Dependency Injection separates creation of client's dependencies from its own behavior. 

Wikipedia says

> In software engineering, dependency injection is a technique in which an object receives other 
> objects that it depends on. These other objects are called dependencies.

**Programmatic Example**

Let's first introduce the `Tobacco` interface and the concrete brands.

```java
@Slf4j
public abstract class Tobacco {

  public void smoke(Wizard wizard) {
    LOGGER.info("{} smoking {}", wizard.getClass().getSimpleName(),
        this.getClass().getSimpleName());
  }
}

public class SecondBreakfastTobacco extends Tobacco {
}

public class RivendellTobacco extends Tobacco {
}

public class OldTobyTobacco extends Tobacco {
}
```

Next here's the `Wizard` class hierarchy.

```java
public interface Wizard {

  void smoke();
}

public class AdvancedWizard implements Wizard {

  private final Tobacco tobacco;

  public AdvancedWizard(Tobacco tobacco) {
    this.tobacco = tobacco;
  }

  @Override
  public void smoke() {
    tobacco.smoke(this);
  }
}
```

And lastly we can show how easy it is to give the old wizard any brand of tobacco.

```java
    var advancedWizard = new AdvancedWizard(new SecondBreakfastTobacco());
    advancedWizard.smoke();
```

## Class diagram

![alt text](./etc/dependency-injection.png "Dependency Injection")

## Applicability

Use the Dependency Injection pattern when:

* When you need to remove knowledge of concrete implementation from object.
* To enable unit testing of classes in isolation using mock objects or stubs.

## Credits

* [Dependency Injection Principles, Practices, and Patterns](https://www.amazon.com/gp/product/161729473X/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=161729473X&linkId=57079257a5c7d33755493802f3b884bd)
* [Clean Code: A Handbook of Agile Software Craftsmanship](https://www.amazon.com/gp/product/0132350882/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0132350882&linkCode=as2&tag=javadesignpat-20&linkId=2c390d89cc9e61c01b9e7005c7842871)
* [Java 9 Dependency Injection: Write loosely coupled code with Spring 5 and Guice](https://www.amazon.com/gp/product/1788296257/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=1788296257&linkId=4e9137a3bf722a8b5b156cce1eec0fc1)
* [Google Guice Tutorial: Open source Java based dependency injection framework](https://www.amazon.com/gp/product/B083P7DZ8M/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=B083P7DZ8M&linkId=04f0f902c877921e45215b624a124bfe)
