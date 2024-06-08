---
title: "Model-View-Controller Pattern in Java: Streamlining Java Web Development"
shortTitle: Model-View-Controller (MVC)
description: "Learn about the Model-View-Controller (MVC) design pattern in Java, including its benefits, real-world examples, use cases, and how to implement it effectively in your applications."
category: Architectural
language: en
tag:
  - Architecture
  - Client-server
  - Decoupling
  - Layered architecture
  - Presentation
---

## Also known as

* MVC

## Intent of Model-View-Controller Design Pattern

To separate an application into three interconnected components (Model, View, Controller), enabling modular development of each part independently, enhancing maintainability and scalability. Model-View-Controller (MVC) design pattern is widely used in Java applications for web development and user interface separation.

## Detailed Explanation of Model-View-Controller Pattern with Real-World Examples

Real-world example

> Consider ICU room in a hospital displaying patient health information on devices taking input from sensors. The display shows data received from the controller, which updates from the sensor model. This exemplifies the MVC design pattern in a real-world Java application.

In plain words

> MVC separates the business logic from user interface by mediating Controller between Model & View.

Wikipedia says

> Model–view–controller (MVC) is commonly used for developing user interfaces that divide the related program logic into three interconnected elements. This is done to separate internal representations of information from the ways information is presented to and accepted from the user.

## Programmatic Example of Model-View-Controller Pattern in Java

Consider following `GiantModel` model class that provides the health, fatigue & nourishment information.

```java
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiantModel {

  private Health health;
  private Fatigue fatigue;
  private Nourishment nourishment;

  @Override
  public String toString() {
    return String.format("The giant looks %s, %s and %s.", health, fatigue, nourishment);
  }
}
```

`GiantView` class to display received patient data.

```java
public class GiantView {

  public void displayGiant(GiantModel giant) {
    LOGGER.info(giant.toString());
  }
}
```

`GiantController` class takes updates from `GiantModel` and sends to `GiantView` for display.

```java
public class GiantController {

  private final GiantModel giant;
  private final GiantView view;

  public GiantController(GiantModel giant, GiantView view) {
    this.giant = giant;
    this.view = view;
  }

  @SuppressWarnings("UnusedReturnValue")
  public Health getHealth() {
    return giant.getHealth();
  }

  public void setHealth(Health health) {
    this.giant.setHealth(health);
  }

  @SuppressWarnings("UnusedReturnValue")
  public Fatigue getFatigue() {
    return giant.getFatigue();
  }

  public void setFatigue(Fatigue fatigue) {
    this.giant.setFatigue(fatigue);
  }

  @SuppressWarnings("UnusedReturnValue")
  public Nourishment getNourishment() {
    return giant.getNourishment();
  }

  public void setNourishment(Nourishment nourishment) {
    this.giant.setNourishment(nourishment);
  }

  public void updateView() {
    this.view.displayGiant(giant);
  }
}
```

This example demonstrates how the MVC pattern separates concerns in a Java application, making it easier to manage and update components independently.

## When to Use the Model-View-Controller Pattern in Java

* Used in web applications to separate data model, user interface, and user input processing.
* Suitable for applications requiring a clear separation of concerns, ensuring that the business logic, user interface, and user input are loosely coupled and independently managed, following the MVC pattern.

## Model-View-Controller Pattern Java Tutorials

* [Spring Boot Model (ZetCode)](https://zetcode.com/springboot/model/)
* [Spring MVC Tutorial (Baeldung)](https://www.baeldung.com/spring-mvc-tutorial)

## Real-World Applications of Model-View-Controller Pattern in Java

* Frameworks like Spring MVC in Java for web applications.
* Desktop applications in Java, such as those using Swing or JavaFX.

## Benefits and Trade-offs of Model-View-Controller Pattern

Benefits:

* Promotes organized code structure by separating concerns.
* Facilitates parallel development of components.
* Enhances testability due to decoupled nature.
* Easier to manage and update individual parts without affecting others.

Trade-offs:

* Increased complexity in initially setting up the architecture.
* Can lead to excessive boilerplate if not implemented correctly or for very small projects.

## Related Java Design Patterns

* [Observer](https://java-design-patterns.com/patterns/observer/): Often used in MVC where the view observes the model for changes; this is a fundamental relationship for updating the UI when the model state changes.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Controllers may use different strategies for handling user input, related through the ability to switch strategies for user input processing in Java MVC applications.
* [Composite](https://java-design-patterns.com/patterns/composite/): Views can be structured using the Composite Pattern to manage hierarchies of user interface components.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Pro Spring 5: An In-Depth Guide to the Spring Framework and Its Tools](https://amzn.to/3y9Rrwp)
* [Model-view-controller (Wikipedia)](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
