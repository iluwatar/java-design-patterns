---
layout: pattern
title: Strategy
folder: strategy
permalink: /patterns/strategy/ko
categories: Behavioral
language: ko
tags:
 - Gang of Four
---

## 동의어

정책(Policy) 패턴

## 의도

알고리즘군을 정의하고 각 알고리즘을 캡슐화하고 상호 변경 가능하게 만듭니다. 전략(Strategy) 패턴을 사용하면 알고리즘이 이를 사용하는 클라이언트와 독립적일 수 있습니다.

## 설명

실제 예제

> 드래곤을 사냥하는 것은 위험한 일입니다. 경험이 쌓이면 쉬워집니다. 베테랑 사냥꾼들은 서로 다른 유형의 드래곤에 대해 서로 다른 전투 전략을 개발했습니다.

평범한 말로는

> 전략(Strategy) 패턴을 사용하면 런타임에 가장 적합한 알고리즘을 선택할 수 있습니다.   

Wikipedia는

> 컴퓨터 프로그래밍에서 전략 패턴(정책 패턴이라고도 함)은 런타임에 알고리즘을 선택할 수 있는 행동 소프트웨어 디자인 패턴입니다.

**프로그래밍 예**

먼저 드래곤 사냥 전략 인터페이스와 그 구현을 살펴봅니다.

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

그리고 여기 상대하는 드래곤에 따라 자신의 전투 전략을 선택할 수 있는 강력한 드래곤 슬레이어가 있습니다.

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

마지막으로 여기 드래곤 슬레이어가 행동합니다.

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
```

프로그램 출력:

```
    Green dragon spotted ahead!
    With your Excalibur you sever the dragon's head!
    Red dragon emerges.
    You shoot the dragon with the magical crossbow and it falls dead on the ground!
    Black dragon lands before you.
    You cast the spell of disintegration and the dragon vaporizes in a pile of dust!    
```

또한 Java 8의 새로운 기능인 Lambda Expressions은 구현을 위한 또 다른 접근 방식을 제공합니다:

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

그리고 여기 드래곤 슬레이어가 행동합니다.

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

프로그램 출력은 위와 동일합니다.

## 클래스 다이어그램

![alt text](./etc/strategy_urm.png "Strategy")

## 적용 가능성

다음과 같은 경우 전략(Strategy) 패턴을 사용합니다.

* 비슷한 클래스들이 동작 만 다른 경우가 많이 있습니다. 전략 패턴은 여러 동작 중 하나를 클래스로 구성하는 방법을 제공합니다.
* 알고리즘의 다양한 변형이 필요합니다. 예를 들어 다양한 공간 / 시간 절충을 반영하는 알고리즘을 정의할 수 있습니다. 이러한 변형이 알고리즘의 클래스 계층 구조로 구현될 때 전략 패턴을 사용할 수 있습니다.
* 알고리즘은 클라이언트가 알 필요 없는 데이터를 사용합니다. 전략 패턴을 사용하여 복잡한 알고리즘 별 데이터 구조가 노출되지 않도록 합니다.
* 클래스는 많은 동작을 정의하며 이러한 동작은 작업에서 여러 조건문으로 나타납니다. 많은 조건부 대신 관련 조건부 분기를 자체 전략 클래스로 이동하세요.

## 튜토리얼 

* [전략 패턴 튜토리얼](https://www.journaldev.com/1754/strategy-design-pattern-in-java-example-tutorial)

## 크레딧

* [디자인 패턴: 재사용 가능한 객체 지향 소프트웨어의 요소](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [자바의 함수형 프로그래밍: Java 8 Lambda 표현식의 강력한 기능 활용](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [헤드 퍼스트 디자인 패턴: 두뇌 친화적인 가이드](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [패턴으로 리팩토링](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
