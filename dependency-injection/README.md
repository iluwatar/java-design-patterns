---
title: "Dependency Injection Pattern in Java: Boosting Maintainability with Loose Coupling"
shortTitle: Dependency Injection
description: "Learn about the Dependency Injection design pattern. Explore its benefits, real-world examples, class diagrams, and best practices for implementation in Java."
category: Creational
language: en
tag:
  - Decoupling
  - Dependency management
  - Inversion of control
---

## Also known as

* Inversion of Control (IoC)
* Dependency Inversion

## Intent of Dependency Injection Design Pattern

To decouple the creation of object dependencies from their usage, allowing for more flexible and testable code.

## Detailed Explanation of Dependency Injection Pattern with Real-World Examples

Real-world example

> Imagine a high-end restaurant where the chef needs various ingredients to prepare dishes. Instead of the chef personally going to different suppliers for each ingredient, a trusted supplier delivers all the required fresh ingredients daily. This allows the chef to focus on cooking without worrying about sourcing the ingredients.
>
> In the Dependency Injection design pattern, the trusted supplier acts as the "injector," providing the necessary dependencies (ingredients) to the chef (object). The chef can then use these dependencies without knowing where they came from, ensuring a clean separation between the creation and use of dependencies. This setup enhances efficiency, flexibility, and maintainability in the kitchen, much like in a software system.

In plain words

> Dependency Injection separates the creation of the client's dependencies from its own behavior.

Wikipedia says

> In software engineering, dependency injection is a technique in which an object receives other objects that it depends on. These other objects are called dependencies.

## Programmatic Example of Dependency Injection Pattern in Java

The old wizard likes to fill his pipe and smoke tobacco once in a while. However, he doesn't want to depend on a single tobacco brand only but likes to be able to enjoy them all interchangeably.

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

Finally, we can show how easy it is to give the old wizard any brand of tobacco.

```java
public static void main(String[] args) {
    var simpleWizard = new SimpleWizard();
    simpleWizard.smoke();

    var advancedWizard = new AdvancedWizard(new SecondBreakfastTobacco());
    advancedWizard.smoke();

    var advancedSorceress = new AdvancedSorceress();
    advancedSorceress.setTobacco(new SecondBreakfastTobacco());
    advancedSorceress.smoke();

    var injector = Guice.createInjector(new TobaccoModule());
    var guiceWizard = injector.getInstance(GuiceWizard.class);
    guiceWizard.smoke();
}
```

The program output:

```
11:54:05.205 [main] INFO com.iluwatar.dependency.injection.Tobacco -- SimpleWizard smoking OldTobyTobacco
11:54:05.207 [main] INFO com.iluwatar.dependency.injection.Tobacco -- AdvancedWizard smoking SecondBreakfastTobacco
11:54:05.207 [main] INFO com.iluwatar.dependency.injection.Tobacco -- AdvancedSorceress smoking SecondBreakfastTobacco
11:54:05.308 [main] INFO com.iluwatar.dependency.injection.Tobacco -- GuiceWizard smoking RivendellTobacco
```

## Detailed Explanation of Dependency Injection Pattern with Real-World Examples

![Dependency Injection](./etc/dependency-injection.png "Dependency Injection")

## When to Use the Dependency Injection Pattern in Java

* When aiming to reduce the coupling between classes and increase the modularity of the application.
* In scenarios where the object creation process is complex or should be separated from the class usage.
* In applications requiring easier unit testing by allowing dependencies to be mocked or stubbed.
* Within frameworks or libraries that manage object lifecycles and dependencies, such as Spring or Jakarta EE (formerly Java EE).

## Real-World Applications of Dependency Injection Pattern in Java

* Frameworks like Spring, Jakarta EE, and Google Guice use Dependency Injection (DI) extensively to manage component lifecycles and dependencies.
* Desktop and web applications that require flexible architecture with easily interchangeable components.

## Benefits and Trade-offs of Dependency Injection Pattern

Benefits:

* Enhances modularity and separation of concerns.
* Simplifies unit testing by allowing for easy mocking of dependencies.
* Increases flexibility and maintainability by promoting loose coupling.

Trade-offs:

* Can introduce complexity in the configuration, especially in large projects.
* Might increase the learning curve for developers unfamiliar with Dependency Injection patterns or frameworks.
* Requires careful management of object lifecycles and scopes.

## Related Java Design Patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/) and [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Used to create instances that the DI mechanism will inject.
* [Service Locator](https://java-design-patterns.com/patterns/service-locator/): An alternative to DI for locating services or components, though it does not decouple the lookup process as effectively as DI.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Often used in conjunction with DI to provide a single instance of a service across the application.

## References and Credits

* [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/3wRnjp5)
* [Dependency Injection: Design patterns using Spring and Guice](https://amzn.to/4aMyHkI)
* [Dependency Injection Principles, Practices, and Patterns](https://amzn.to/4aupmxe)
* [Google Guice: Agile Lightweight Dependency Injection Framework](https://amzn.to/4bTDbX0)
* [Java 9 Dependency Injection: Write loosely coupled code with Spring 5 and Guice](https://amzn.to/4ayCtxp)
* [Java Design Pattern Essentials](https://amzn.to/3xtPPxa)
* [Pro Java EE Spring Patterns: Best Practices and Design Strategies Implementing Java EE Patterns with the Spring Framework](https://amzn.to/3J6Teoh)
* [Spring in Action](https://amzn.to/4asnpSG)
