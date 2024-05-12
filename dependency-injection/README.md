---
title: Dependency Injection
category: Structural
language: en
tag:
    - Decoupling
    - Dependency management
    - Inversion of control
---

## Also known as

* Inversion of Control (IoC)
* Dependency Inversion

## Intent

Dependency Injection is a software design pattern in which one or more dependencies (or services) are injected, or passed by reference, into a dependent object (or client) and are made part of the client's state. The pattern separates the creation of a client's dependencies from its own behavior, which allows program designs to be loosely coupled and to follow the [Inversion of Control](https://java-design-patterns.com/principles/#inversion-of-control) and [Single Responsibility](https://java-design-patterns.com/principles/#single-responsibility-principle) principles.

## Explanation

Real world example

> The old wizard likes to fill his pipe and smoke tobacco once in a while. However, he doesn't want to depend on a single tobacco brand only but likes to be able to enjoy them all interchangeably.

In plain words

> Dependency Injection separates creation of client's dependencies from its own behavior.

Wikipedia says

> In software engineering, dependency injection is a technique in which an object receives other objects that it depends on. These other objects are called dependencies.

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
var advancedWizard=new AdvancedWizard(new SecondBreakfastTobacco());
advancedWizard.smoke();
```

## Class diagram

![Dependency Injection](./etc/dependency-injection.png "Dependency Injection")

## Applicability

* When aiming to reduce the coupling between classes and increase the modularity of the application.
* In scenarios where the object creation process is complex or should be separated from the class usage.
* In applications requiring easier unit testing by allowing dependencies to be mocked or stubbed.
* Within frameworks or libraries that manage object lifecycles and dependencies, such as Spring or Jakarta EE (formerly Java EE).

## Known Uses

* Frameworks like Spring, Jakarta EE, and Google Guice use DI extensively to manage component lifecycles and dependencies.
* Desktop and web applications that require flexible architecture with easily interchangeable components.

## Consequences

Benefits:

* Enhances modularity and separation of concerns.
* Simplifies unit testing by allowing for easy mocking of dependencies.
* Increases flexibility and maintainability by promoting loose coupling.

Trade-offs:

* Can introduce complexity in the configuration, especially in large projects.
* Might increase the learning curve for developers unfamiliar with DI patterns or frameworks.
* Requires careful management of object lifecycles and scopes.

## Related Patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/) and [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Used to create instances that the DI mechanism will inject.
* [Service Locator](https://java-design-patterns.com/patterns/service-locator/): An alternative to DI for locating services or components, though it does not decouple the lookup process as effectively as DI.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Often used in conjunction with DI to provide a single instance of a service across the application.

## Credits

* [Spring in Action](https://amzn.to/4asnpSG)
* [Dependency Injection: Design patterns using Spring and Guice](https://amzn.to/4aMyHkI)
* [Java Design Pattern Essentials](https://amzn.to/3xtPPxa)
* [Pro Java EE Spring Patterns: Best Practices and Design Strategies Implementing Java EE Patterns with the Spring Framework](https://amzn.to/3J6Teoh)
* [Dependency Injection Principles, Practices, and Patterns](https://www.amazon.com/gp/product/161729473X/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=161729473X&linkId=57079257a5c7d33755493802f3b884bd)
* [Clean Code: A Handbook of Agile Software Craftsmanship](https://www.amazon.com/gp/product/0132350882/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0132350882&linkCode=as2&tag=javadesignpat-20&linkId=2c390d89cc9e61c01b9e7005c7842871)
* [Java 9 Dependency Injection: Write loosely coupled code with Spring 5 and Guice](https://www.amazon.com/gp/product/1788296257/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=1788296257&linkId=4e9137a3bf722a8b5b156cce1eec0fc1)
* [Google Guice: Agile Lightweight Dependency Injection Framework](https://www.amazon.com/gp/product/1590599977/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1590599977&linkId=3b10c90b7ba480a1b7777ff38000f956)
