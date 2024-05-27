---
title: Subclass Sandbox 
category: Behavioral
language: en
tag:
    - Abstraction
    - Code simplification
    - Decoupling
    - Extensibility
    - Game programming
    - Polymorphism
---

## Also known as

* Hook Method

## Intent

To allow subclasses to alter the core behavior of a class by providing specific implementations of certain methods while keeping the overall structure unchanged.
  
## Explanation

Real-world example

> Imagine a cooking class where the instructor provides a standard recipe structure, including steps like "prepare ingredients," "cook," and "serve." Each student follows this structure but can customize specific steps to create their unique dish. For example, one student might choose to prepare a salad, while another might prepare a stir-fry, both adhering to the same overarching recipe format. This way, the instructor ensures that all dishes are made following a consistent process, but students have the flexibility to personalize the key parts of their recipes. This mirrors the Subclass Sandbox pattern, where the core structure is defined by the superclass, and specific behaviors are customized in subclasses.

In plain words

> The Subclass Sandbox pattern allows subclasses to customize specific behaviors within a predefined algorithm structure provided by the superclass.

[gameprogrammingpatterns.com](https://gameprogrammingpatterns.com/) says

> A base class defines an abstract sandbox method and several provided operations. Marking them protected makes it clear that they are for use by derived classes. Each derived sandboxed subclass implements the sandbox method using the provided operations.

**Programmatic Example**

Suppose you want to create various superpowers in a game, where each superpower needs to move with a sound effect and spawn particles. Should you create many classes with similar methods or derive them from a base class? The Subclass Sandbox pattern enables you to handle this efficiently by deriving these classes from a common base class.

We start with the base class `Superpower`. It contains an abstract sandbox method `active` and some provided operations.

```java
public abstract class Superpower {

  protected Logger logger;

  protected abstract void activate();

  protected void move(double x, double y, double z) {
    logger.info("Move to ( " + x + ", " + y + ", " + z + " )");
  }

  protected void playSound(String soundName, int volume) {
    logger.info("Play " + soundName + " with volume " + volume);
  }

  protected void spawnParticles(String particleType, int count) {
    logger.info("Spawn " + count + " particle with type " + particleType);
  }
}
```

Next, we are able to create derived sandboxed subclass that implements the sandbox method using the provided operations. Here is the first power:

```java
public class SkyLaunch extends Superpower {

  public SkyLaunch() {
    super();
    logger = LoggerFactory.getLogger(SkyLaunch.class);
  }

  @Override
  protected void activate() {
    move(0, 0, 20);
    playSound("SKYLAUNCH_SOUND", 1);
    spawnParticles("SKYLAUNCH_PARTICLE", 100);
  }
}
```

Here is the second power.

```java
public class GroundDive extends Superpower {

  public GroundDive() {
    super();
    logger = LoggerFactory.getLogger(GroundDive.class);
  }

  @Override
  protected void activate() {
    move(0, 0, -20);
    playSound("GROUNDDIVE_SOUND", 5);
    spawnParticles("GROUNDDIVE_PARTICLE", 20);
  }
}
```

Finally, here are the superpowers in active.

```java
public static void main(String[] args) {
    LOGGER.info("Use superpower: sky launch");
    var skyLaunch = new SkyLaunch();
    skyLaunch.activate();
    LOGGER.info("Use superpower: ground dive");
    var groundDive = new GroundDive();
    groundDive.activate();
}
```

Program output:

```
13:10:23.177 [main] INFO com.iluwatar.subclasssandbox.App -- Use superpower: sky launch
13:10:23.179 [main] INFO com.iluwatar.subclasssandbox.SkyLaunch -- Move to ( 0.0, 0.0, 20.0 )
13:10:23.180 [main] INFO com.iluwatar.subclasssandbox.SkyLaunch -- Play SKYLAUNCH_SOUND with volume 1
13:10:23.180 [main] INFO com.iluwatar.subclasssandbox.SkyLaunch -- Spawn 100 particle with type SKYLAUNCH_PARTICLE
13:10:23.180 [main] INFO com.iluwatar.subclasssandbox.App -- Use superpower: ground dive
13:10:23.180 [main] INFO com.iluwatar.subclasssandbox.GroundDive -- Move to ( 0.0, 0.0, -20.0 )
13:10:23.180 [main] INFO com.iluwatar.subclasssandbox.GroundDive -- Play GROUNDDIVE_SOUND with volume 5
13:10:23.180 [main] INFO com.iluwatar.subclasssandbox.GroundDive -- Spawn 20 particle with type GROUNDDIVE_PARTICLE
```

## Applicability  

* Use when you want to create a framework that allows users to define their own behaviors by extending classes.
* Applicable in scenarios where you need to enforce a specific algorithm structure while allowing certain steps to be overridden.

## Known Uses

* Template method pattern in GUI frameworks where the framework provides the structure and the subclasses implement the specifics.
* Game development where the core game loop is defined, but specific behaviors are provided by subclassing.
* Java libraries like the `AbstractList` where core methods are defined and certain behaviors can be customized by extending classes.

## Consequences

Benefits:

* Encourages code reuse by allowing shared code in the superclass.
* Simplifies the addition of new behaviors through subclassing.
* Enhances code readability and maintainability by separating the algorithm's structure from specific implementations.

Trade-offs:

* Can lead to a large number of subclasses.
* Requires careful design to ensure that the base class is flexible enough for various extensions.
* Increases complexity in understanding the code flow due to multiple layers of inheritance.

## Related Patterns

* [Strategy](https://java-design-patterns.com/patterns/strategy/): Both involve interchangeable behaviors, but Strategy pattern uses composition over inheritance.
* [Template Method](https://java-design-patterns.com/patterns/template-method/): Similar in enforcing a structure where certain steps can be overridden by subclasses.

## Credits  

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Game Programming Patterns](https://amzn.to/3K96fOn)
* [Subclass Sandbox (Game Programming Patterns)](https://gameprogrammingpatterns.com/subclass-sandbox.html)
