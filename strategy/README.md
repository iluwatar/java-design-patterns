---
layout: pattern
title: Strategy
folder: strategy
permalink: /patterns/strategy/
categories: Behavioral
tags:
 - Gang of Four
---

## Also known as

Policy

## Intent

Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets 
the algorithm vary independently from clients that use it.

## Explanation

Real world example

> Slaying dragons is a dangerous job. With experience it becomes easier. Veteran 
> dragonslayers have developed different fighting strategies against different types of dragons.         

In plain words

> Strategy pattern allows choosing the best suited algorithm at runtime.   

Wikipedia says

> In computer programming, the strategy pattern (also known as the policy pattern) is a behavioral 
> software design pattern that enables selecting an algorithm at runtime.

**Programmatic Example**

Let's first introduce the dragon slaying strategy interface and its implementations.

```java
@FunctionalInterface
public interface DragonSlayingStrategy {
	
  String execute(Integer n);
}

public class MeleeStrategy implements DragonSlayingStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(MeleeStrategy.class);

  @Override
  public String execute(Integer n) {
    return "MeleeStrategy: With your Excalibur you cut dragon's leg "+n+" times!";
  }
}

public class ProjectileStrategy implements DragonSlayingStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectileStrategy.class);

  @Override
  public String execute(Integer n) {
    return "ProjectileStrategy: You shoot "+n+" arrows to the dragon's wings with your crossbow!";
  }
}

public class SpellStrategy implements DragonSlayingStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpellStrategy.class);

  @Override
  public String execute(Integer n) {
    return "SpellStrategy: You spend "+n+" seconds casting the explosion spell you kill the dragon!";
  }
}
```

And here is the mighty dragonslayer, who is able to pick his fighting strategy based on the 
opponent.

```java
public class DragonSlayer {

  private DragonSlayingStrategy strategy;

  public DragonSlayer(DragonSlayingStrategy strategy) {
    this.strategy = strategy;
  }

  public void changeStrategy(DragonSlayingStrategy strategy) {
    this.strategy = strategy;
  }

  public String goToBattle(Integer n) {
    return strategy.execute(n);
  }
}
```

Finally here's the dragonslayer in action.

```java
    LOGGER.info("Green dragon spotted ahead!");
    var dragonSlayer = new DragonSlayer(new MeleeStrategy());
    LOGGER.info(dragonSlayer.goToBattle(3));
    LOGGER.info("Red dragon emerges.");
    dragonSlayer.changeStrategy(new ProjectileStrategy());
    LOGGER.info(dragonSlayer.goToBattle(2));
    LOGGER.info("Black dragon lands before you.");
    dragonSlayer.changeStrategy(new SpellStrategy());
    LOGGER.info(dragonSlayer.goToBattle(5));
```

Program output:

```
    Green dragon spotted ahead!
    With your Excalibur you cut dragon's leg 3 times!
    Red dragon emerges.
    ProjectileStrategy: You shoot 2 arrows to the dragon's wings with your crossbow!
    Black dragon lands before you.
    You spend 5 seconds casting the explosion spell you kill the dragon!
```

We can achieve the same with less code with Java 8 using Lambda Functions instead of creating classes that implements the interface. In this case we would have the following code inside App.js:

```java
    LOGGER.info("************ Java 8 Strategy pattern ************");
    LOGGER.info("Elder dragon spotted ahead!");
    dragonSlayer = new DragonSlayer((n) -> {
    	return "With your Excalibur you cut dragon's leg "+n+" times!";
    });
    LOGGER.info(dragonSlayer.goToBattle(2));
    LOGGER.info("Elder dragon emerges.");
    dragonSlayer.changeStrategy((n) -> {
    	return "You shoot the dragon's wings with your crossbow "+n+" times!";
    });
    LOGGER.info(dragonSlayer.goToBattle(5));
    LOGGER.info("Elder dragon falls near you throwing flames and you fall back!");
    dragonSlayer.changeStrategy((n) -> {
    	return "You spend "+n+" seconds casting the explosion spell you kill the dragon!";
    });
    LOGGER.info(dragonSlayer.goToBattle(11));
```

What's more, the new feature Lambda Expressions in Java 8 provides another approach for the implementation:

```java
public class LambdaStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(LambdaStrategy.class);

  public enum Strategy implements DragonSlayingStrategy {
    MeleeStrategy(() -> LOGGER.info(
        "With your Excalibur you severe the dragon's head!")),
    ProjectileStrategy(() -> LOGGER.info(
        "You shoot the dragon with the magical crossbow and it falls dead on the ground!")),
    SpellStrategy(() -> LOGGER.info(
        "You cast the spell of disintegration and the dragon vaporizes in a pile of dust!"));

    private final DragonSlayingStrategy dragonSlayingStrategy;

    Strategy(DragonSlayingStrategy dragonSlayingStrategy) {
      this.dragonSlayingStrategy = dragonSlayingStrategy;
    }

    @Override
    public void execute() {
      dragonSlayingStrategy.execute();
    }
  }
}
```

And here's the dragonslayer in action.

```java
    LOGGER.info("Green dragon spotted ahead!");
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.MeleeStrategy);
    dragonSlayer.goToBattle();
    LOGGER.info("Red dragon emerges.");
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.ProjectileStrategy);
    dragonSlayer.goToBattle();
    LOGGER.info("Black dragon lands before you.");
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.SpellStrategy);
    dragonSlayer.goToBattle();
```

Program output is the same as above one.

## Class diagram

![alt text](./etc/strategy_urm.png "Strategy")

## Applicability

Use the Strategy pattern when

* Many related classes differ only in their behavior. Strategies provide a way to configure a class either one of many behaviors
* You need different variants of an algorithm. for example, you might define algorithms reflecting different space/time trade-offs. Strategies can be used when these variants are implemented as a class hierarchy of algorithms
* An algorithm uses data that clients shouldn't know about. Use the Strategy pattern to avoid exposing complex, algorithm-specific data structures
* A class defines many behaviors, and these appear as multiple conditional statements in its operations. Instead of many conditionals, move related conditional branches into their own Strategy class

## Tutorial 

* [Strategy Pattern Tutorial](https://www.journaldev.com/1754/strategy-design-pattern-in-java-example-tutorial)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
