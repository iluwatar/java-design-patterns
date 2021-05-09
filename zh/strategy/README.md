---
layout: pattern
title: Strategy
folder: strategy
permalink: /patterns/strategy/zh
categories: Behavioral
language: zh
tags:
 - Gang of Four
---

## 又被称为
政策（方针）模式

## 目的

定义一个家族算法，并封装好其中每一个，使它们可以互相替换。策略模式使算法的变化独立于使用它的客户。

## 解释

现实世界例子

> 屠龙是一项危险的职业。有经验将会使它变得简单。经验丰富的屠龙者对不同类型的龙有不同的战斗策略。       

直白点说

> 策略模式允许在运行时选择最匹配的算法。

维基百科上说

> 在程序编程领域，策略模式（又叫政策模式）是一种启用在运行时选择算法的行为型软件设计模式。

**编程实例**

让我们先介绍屠龙的策略模式接口和它的实现。

```java
@FunctionalInterface
public interface DragonSlayingStrategy {

  void execute();
}

@Slf4j
public class MeleeStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("With your Excalibur you sever the dragon's head!");
  }
}

@Slf4j
public class ProjectileStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("You shoot the dragon with the magical crossbow and it falls dead on the ground!");
  }
}

@Slf4j
public class SpellStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("You cast the spell of disintegration and the dragon vaporizes in a pile of dust!");
  }
}
```

现在有一个强力的屠龙者要基于上面的组件来选择他的战斗策略。

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

最后是屠龙者的行动。

```java
    LOGGER.info("Green dragon spotted ahead!");
    var dragonSlayer = new DragonSlayer(new MeleeStrategy());
    dragonSlayer.goToBattle();
    LOGGER.info("Red dragon emerges.");
    dragonSlayer.changeStrategy(new ProjectileStrategy());
    dragonSlayer.goToBattle();
    LOGGER.info("Black dragon lands before you.");
    dragonSlayer.changeStrategy(new SpellStrategy());
    dragonSlayer.goToBattle();
    
    // Green dragon spotted ahead!
    // With your Excalibur you sever the dragon's head!
    // Red dragon emerges.
    // You shoot the dragon with the magical crossbow and it falls dead on the ground!
    // Black dragon lands before you.
    // You cast the spell of disintegration and the dragon vaporizes in a pile of dust!    
```

## 类图
![alt text](../../strategy/etc/strategy_1.png "Strategy")

## 应用
使用策略模式当

* 许多相关的类只是行为不同。策略模式提供了一种为一种类配置多种行为的能力。
* 你需要一种算法的不同变体。比如，你可能定义反应不用时间空间权衡的算法。当这些算法的变体使用类的层次结构来实现时就可以使用策略模式。
* 一个算法使用的数据客户不应该对其知晓。使用策略模式来避免暴露复杂的，特定于算法的数据结构。
* 一个类定义了许多行为，这些行为在其操作中展现为多个条件语句。移动相关的条件分支到它们分别的策略类中来代替这些条件语句。

## 教学

* [Strategy Pattern Tutorial](https://www.journaldev.com/1754/strategy-design-pattern-in-java-example-tutorial)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
