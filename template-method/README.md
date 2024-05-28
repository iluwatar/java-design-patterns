---
title: Template method
category: Behavioral
language: en
tag:
    - Abstraction
    - Code simplification
    - Decoupling
    - Extensibility
    - Gang of Four
    - Inheritance
    - Object composition
    - Polymorphism
    - Reusability
---

## Intent

Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.

## Explanation

Real-world example

> A real-world analogy for the Template Method pattern can be seen in the preparation of a cup of tea or coffee. The overall process (algorithm) is the same: boil water, brew the beverage, pour into cup, and add condiments. However, the specific steps of brewing the beverage differ. For tea, you steep the tea leaves in hot water, while for coffee, you brew ground coffee beans. The Template Method pattern encapsulates the invariant steps of the process (boiling water, pouring, adding condiments) in a base class, while allowing subclasses to define the specific brewing steps, thus ensuring the overall structure of making a hot drink is consistent while allowing customization where needed.

In plain words

> Template Method pattern outlines the general steps in the parent class and lets the concrete child implementations define the details. 

Wikipedia says

> In object-oriented programming, the template method is one of the behavioral design patterns identified by Gamma et al. in the book Design Patterns. The template method is a method in a superclass, usually an abstract superclass, and defines the skeleton of an operation in terms of a number of high-level steps. These steps are themselves implemented by additional helper methods in the same class as the template method.

**Programmatic Example**

Our programmatic example is about thieves and stealing. The general steps in stealing an item are the same. First, you pick the target, next you confuse him somehow and finally, you steal the item. However, there are many ways to implement these steps.

Let's first introduce the template method class `StealingMethod` along with its concrete implementations `SubtleMethod` and `HitAndRunMethod`. To make sure that subclasses don’t override the template method, the template method (in our case method `steal`) should be declared `final`, otherwise the skeleton defined in the base class could be overridden in subclasses.

```java
@Slf4j
public abstract class StealingMethod {

  protected abstract String pickTarget();

  protected abstract void confuseTarget(String target);

  protected abstract void stealTheItem(String target);

  public final void steal() {
    var target = pickTarget();
    LOGGER.info("The target has been chosen as {}.", target);
    confuseTarget(target);
    stealTheItem(target);
  }
}
```

```java
@Slf4j
public class SubtleMethod extends StealingMethod {

  @Override
  protected String pickTarget() {
    return "shop keeper";
  }

  @Override
  protected void confuseTarget(String target) {
    LOGGER.info("Approach the {} with tears running and hug him!", target);
  }

  @Override
  protected void stealTheItem(String target) {
    LOGGER.info("While in close contact grab the {}'s wallet.", target);
  }
}
```

```java
@Slf4j
public class HitAndRunMethod extends StealingMethod {

  @Override
  protected String pickTarget() {
    return "old goblin woman";
  }

  @Override
  protected void confuseTarget(String target) {
    LOGGER.info("Approach the {} from behind.", target);
  }

  @Override
  protected void stealTheItem(String target) {
    LOGGER.info("Grab the handbag and run away fast!");
  }
}
```

Here's the halfling thief class containing the template method.

```java
public class HalflingThief {

  private StealingMethod method;

  public HalflingThief(StealingMethod method) {
    this.method = method;
  }

  public void steal() {
    method.steal();
  }

  public void changeMethod(StealingMethod method) {
    this.method = method;
  }
}
```

And finally, we show how the halfling thief utilizes the different stealing methods.

```java
public static void main(String[] args) {
    var thief = new HalflingThief(new HitAndRunMethod());
    thief.steal();
    thief.changeMethod(new SubtleMethod());
    thief.steal();
}
```

The program output:

```
11:06:01.721 [main] INFO com.iluwatar.templatemethod.StealingMethod -- The target has been chosen as old goblin woman.
11:06:01.723 [main] INFO com.iluwatar.templatemethod.HitAndRunMethod -- Approach the old goblin woman from behind.
11:06:01.723 [main] INFO com.iluwatar.templatemethod.HitAndRunMethod -- Grab the handbag and run away fast!
11:06:01.723 [main] INFO com.iluwatar.templatemethod.StealingMethod -- The target has been chosen as shop keeper.
11:06:01.723 [main] INFO com.iluwatar.templatemethod.SubtleMethod -- Approach the shop keeper with tears running and hug him!
11:06:01.723 [main] INFO com.iluwatar.templatemethod.SubtleMethod -- While in close contact grab the shop keeper's wallet.
```

## Applicability

The Template Method pattern should be used

* To implement the invariant parts of an algorithm once and leave it up to subclasses to implement the behavior that can vary
* When common behavior among subclasses should be factored and localized in a common class to avoid code duplication. This is a good example of "refactoring to generalize" as described by Opdyke and Johnson. You first identify the differences in the existing code and then separate the differences into new operations. Finally, you replace the differing code with a template method that calls one of these new operations
* To control subclasses extensions. You can define a template method that calls "hook" operations at specific points, thereby permitting extensions only at those points

## Tutorials

* [Template Method Design Pattern In Java (DigitalOcean)](https://www.digitalocean.com/community/tutorials/template-method-design-pattern-in-java)

## Known uses

* Java's AbstractList and AbstractSet classes in the Collections Framework use the Template Method pattern to define common algorithms for list and set operations.
* Frameworks like JUnit use Template Method to define the setup and teardown process in test cases.

## Consequences

Benefits:

* Promotes code reuse by defining invariant parts of an algorithm in a base class.
* Simplifies code maintenance by encapsulating common behavior in one place.
* Enhances flexibility by allowing subclasses to override specific steps of an algorithm.

Trade-offs:

* Can lead to an increase in the number of classes, making the system more complex.
* Requires careful design to ensure that the steps exposed to subclasses are useful and meaningful.

## Related Patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Often used with Template Method to create objects needed for specific steps of the algorithm.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): While Template Method defines the skeleton of an algorithm and lets subclasses implement specific steps, the Strategy Pattern defines a family of algorithms and makes them interchangeable.
* [Subclass Sandbox](https://java-design-patterns.com/patterns/subclass-sandbox/): Complements Template Method by ensuring that subclasses can safely override specific steps of an algorithm without causing unintended side effects.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
