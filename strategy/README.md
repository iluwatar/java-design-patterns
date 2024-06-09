---
title: "Strategy Pattern in Java: Streamlining Object Behaviors with Interchangeable Algorithms"
shortTitle: Strategy
description: "Explore the Strategy design pattern in Java with a detailed guide and practical examples. Learn how to implement flexible and interchangeable algorithms effectively in your Java applications for enhanced design and maintenance."
category: Behavioral
language: en
tag:
  - Decoupling
  - Extensibility
  - Gang of Four
  - Interface
  - Polymorphism
---

## Also known as

* Policy

## Intent of Strategy Design Pattern

Define a family of algorithms in Java, encapsulate each one, and make them interchangeable to enhance software development using the Strategy design pattern. Strategy lets the algorithm vary independently of the clients that use it.

## Detailed Explanation of Strategy Pattern with Real-World Examples

Real-world example

> A practical real-world example of the Strategy design pattern in Java is evident in car navigation systems, where algorithm flexibility is paramount. Different navigation algorithms (such as shortest route, fastest route, and scenic route) can be used to determine the best path from one location to another. Each algorithm encapsulates a specific strategy for calculating the route. The user (client) can switch between these algorithms based on their preferences without changing the navigation system itself. This allows for flexible and interchangeable navigation strategies within the same system. 

In plain words

> Strategy pattern allows choosing the best-suited algorithm at runtime.

Wikipedia says

> In computer programming, the strategy pattern (also known as the policy pattern) is a behavioral software design pattern that enables selecting an algorithm at runtime.

## Programmatic Example of Strategy Pattern in Java

Slaying dragons is a dangerous job. With experience, it becomes easier. Veteran dragonslayers have developed different fighting strategies against different types of dragons.

Let's explore how to implement the `DragonSlayingStrategy` interface in Java, demonstrating various Strategy pattern applications.

```java
@FunctionalInterface
public interface DragonSlayingStrategy {

  void execute();
}
```

```java
@Slf4j
public class MeleeStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("With your Excalibur you sever the dragon's head!");
  }
}
```

```java
@Slf4j
public class ProjectileStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("You shoot the dragon with the magical crossbow and it falls dead on the ground!");
  }
}
```

```java
@Slf4j
public class SpellStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("You cast the spell of disintegration and the dragon vaporizes in a pile of dust!");
  }
}
```

And here is the mighty `DragonSlayer`, who can pick his fighting strategy based on the opponent.

```java
public class DragonSlayer {

  private DragonSlayingStrategy strategy;

  public DragonSlayer(DragonSlayingStrategy strategy) {
    this.strategy = strategy;
  }

  public void changeStrategy(DragonSlayingStrategy strategy) {
    this.strategy = strategy;
  }

  public void goToBattle() {
    strategy.execute();
  }
}
```

Finally, here's the `DragonSlayer` in action.

```java
@Slf4j
public class App {

  private static final String RED_DRAGON_EMERGES = "Red dragon emerges.";
  private static final String GREEN_DRAGON_SPOTTED = "Green dragon spotted ahead!";
  private static final String BLACK_DRAGON_LANDS = "Black dragon lands before you.";

  public static void main(String[] args) {
    // GoF Strategy pattern
    LOGGER.info(GREEN_DRAGON_SPOTTED);
    var dragonSlayer = new DragonSlayer(new MeleeStrategy());
    dragonSlayer.goToBattle();
    LOGGER.info(RED_DRAGON_EMERGES);
    dragonSlayer.changeStrategy(new ProjectileStrategy());
    dragonSlayer.goToBattle();
    LOGGER.info(BLACK_DRAGON_LANDS);
    dragonSlayer.changeStrategy(new SpellStrategy());
    dragonSlayer.goToBattle();

    // Java 8 functional implementation Strategy pattern
    LOGGER.info(GREEN_DRAGON_SPOTTED);
    dragonSlayer = new DragonSlayer(
        () -> LOGGER.info("With your Excalibur you severe the dragon's head!"));
    dragonSlayer.goToBattle();
    LOGGER.info(RED_DRAGON_EMERGES);
    dragonSlayer.changeStrategy(() -> LOGGER.info(
        "You shoot the dragon with the magical crossbow and it falls dead on the ground!"));
    dragonSlayer.goToBattle();
    LOGGER.info(BLACK_DRAGON_LANDS);
    dragonSlayer.changeStrategy(() -> LOGGER.info(
        "You cast the spell of disintegration and the dragon vaporizes in a pile of dust!"));
    dragonSlayer.goToBattle();

    // Java 8 lambda implementation with enum Strategy pattern
    LOGGER.info(GREEN_DRAGON_SPOTTED);
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.MELEE_STRATEGY);
    dragonSlayer.goToBattle();
    LOGGER.info(RED_DRAGON_EMERGES);
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.PROJECTILE_STRATEGY);
    dragonSlayer.goToBattle();
    LOGGER.info(BLACK_DRAGON_LANDS);
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.SPELL_STRATEGY);
    dragonSlayer.goToBattle();
  }
}
```

Program output:

```
13:06:36.631 [main] INFO com.iluwatar.strategy.App -- Green dragon spotted ahead!
13:06:36.634 [main] INFO com.iluwatar.strategy.MeleeStrategy -- With your Excalibur you sever the dragon's head!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- Red dragon emerges.
13:06:36.634 [main] INFO com.iluwatar.strategy.ProjectileStrategy -- You shoot the dragon with the magical crossbow and it falls dead on the ground!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- Black dragon lands before you.
13:06:36.634 [main] INFO com.iluwatar.strategy.SpellStrategy -- You cast the spell of disintegration and the dragon vaporizes in a pile of dust!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- Green dragon spotted ahead!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- With your Excalibur you severe the dragon's head!
13:06:36.634 [main] INFO com.iluwatar.strategy.App -- Red dragon emerges.
13:06:36.635 [main] INFO com.iluwatar.strategy.App -- You shoot the dragon with the magical crossbow and it falls dead on the ground!
13:06:36.635 [main] INFO com.iluwatar.strategy.App -- Black dragon lands before you.
13:06:36.635 [main] INFO com.iluwatar.strategy.App -- You cast the spell of disintegration and the dragon vaporizes in a pile of dust!
13:06:36.635 [main] INFO com.iluwatar.strategy.App -- Green dragon spotted ahead!
13:06:36.637 [main] INFO com.iluwatar.strategy.LambdaStrategy -- With your Excalibur you severe the dragon's head!
13:06:36.637 [main] INFO com.iluwatar.strategy.App -- Red dragon emerges.
13:06:36.637 [main] INFO com.iluwatar.strategy.LambdaStrategy -- You shoot the dragon with the magical crossbow and it falls dead on the ground!
13:06:36.637 [main] INFO com.iluwatar.strategy.App -- Black dragon lands before you.
13:06:36.637 [main] INFO com.iluwatar.strategy.LambdaStrategy -- You cast the spell of disintegration and the dragon vaporizes in a pile of dust!
```

## When to Use the Strategy Pattern in Java

Use the Strategy pattern when:

* You need to use different variants of an algorithm within an object and want to switch algorithms at runtime.
* There are multiple related classes that differ only in their behavior.
* An algorithm uses data that clients shouldn't know about.
* A class defines many behaviors and these appear as multiple conditional statements in its operations.

## Strategy Pattern Java Tutorials

* [Strategy Pattern Tutorial (DigitalOcean)](https://www.digitalocean.com/community/tutorials/strategy-design-pattern-in-java-example-tutorial)

## Real-World Applications of Strategy Pattern in Java

* Java's `java.util.Comparator` interface is a common example of the Strategy pattern.
* In GUI frameworks, layout managers (such as those in Java's AWT and Swing) are strategies.

## Benefits and Trade-offs of Strategy Pattern

Benefits:

* Families of related algorithms are reused.
* An alternative to subclassing for extending behavior.
* Avoids conditional statements for selecting desired behavior.
* Allows clients to choose algorithm implementation.

Trade-offs:

* Clients must be aware of different Strategies.
* Increase in the number of objects.

## Related Java Design Patterns

* [Decorator](https://java-design-patterns.com/patterns/decorator/): Enhances an object without changing its interface but is more concerned with responsibilities than algorithms.
* [State](https://java-design-patterns.com/patterns/state/): Similar in structure but used to represent state-dependent behavior rather than interchangeable algorithms.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Functional Programming in Java](https://amzn.to/3JUIc5Q)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
