---
title: Decorator
category: Structural
language: en
tag:
 - Gang of Four
 - Extensibility
---

## ~로 알려져 있는

Wrapper

## 의도

객체에 동적으로 추가적인 책임을 부여합니다. 데코레이터(Decorater)는 기능 확장을 위한 서브 클래스를 만드는 것의
유연한 대안을 제공합니다.

## 설명

실제 예제

> 근처 언덕에 성난 트롤이 살고 있습니다. 보통은 맨손으로 다니지만 가끔씩은 무기를 가지고 있습니다.
> 트롤을 무장시키기 위해 새로운 트롤을 만들 필요 없이 적절한 무기로 트롤을 장식(decorate)하면 됩니다.

쉽게 설명하면

> 데코레이터(Decorator) 패턴은 객체를 데코레이터(Decorater) 클래스의 객체로 감싸 런타임 시, 
> 객체의 동작을 동적으로 변경할 수 있도록 합니다.

Wikipedia에 의하면

> 객체 지향 프로그래밍에서, 데코레이터(Decorater)은 다음과 같은 동작을 허용하는 디자인 패턴입니다.
> 정적, 동적 상관없이 개별 객체에 동작을 추가할 수 있는 디자인 패턴입니다.
> 동작에 영향을 주지 않고 개별 객체에 동작을 추가할 수 있는 디자인 패턴입니다.
> 데코레이터(Decorater) 패턴은 종종 단일 책임 원칙(SRP)을 준수하는데 유용합니다.
> 고유한 관심 영역을 가진 클래스 간에 기능을 나눌 수 있기 때문에 개방 폐쇄 원칙(OCP)를 준수하는데 유용합니다.
> 클래스의 기능을 수정하지 않고 확장할 수 있기 때문입니다.

**프로그램 코드 예제**

트롤을 예로 들어보겠습니다. 먼저, 'Troll' 인터페이스를 구현하는 'SimpleTroll'이 있습니다.

```java
public interface Troll {
  void attack();
  int getAttackPower();
  void fleeBattle();
}

@Slf4j
public class SimpleTroll implements Troll {

  @Override
  public void attack() {
    LOGGER.info("The troll tries to grab you!");
  }

  @Override
  public int getAttackPower() {
    return 10;
  }

  @Override
  public void fleeBattle() {
    LOGGER.info("The troll shrieks in horror and runs away!");
  }
}
```

다음으로, 트롤을 위한 곤봉을 추가하겠습니다. 데코레이터(Decorater)를 사용해 동적으로 추가할 수 있습니다.

```java
@Slf4j
public class ClubbedTroll implements Troll {

  private final Troll decorated;

  public ClubbedTroll(Troll decorated) {
    this.decorated = decorated;
  }

  @Override
  public void attack() {
    decorated.attack();
    LOGGER.info("The troll swings at you with a club!");
  }

  @Override
  public int getAttackPower() {
    return decorated.getAttackPower() + 10;
  }

  @Override
  public void fleeBattle() {
    decorated.fleeBattle();
  }
}
```

트롤이 실제로 동작하는 모습입니다.

```java
// simple troll
LOGGER.info("A simple looking troll approaches.");
var troll = new SimpleTroll();
troll.attack();
troll.fleeBattle();
LOGGER.info("Simple troll power: {}.\n", troll.getAttackPower());

// change the behavior of the simple troll by adding a decorator
LOGGER.info("A troll with huge club surprises you.");
var clubbedTroll = new ClubbedTroll(troll);
clubbedTroll.attack();
clubbedTroll.fleeBattle();
LOGGER.info("Clubbed troll power: {}.\n", clubbedTroll.getAttackPower());
```

프로그램 실행 결과:

```java
A simple looking troll approaches.
The troll tries to grab you!
The troll shrieks in horror and runs away!
Simple troll power: 10.

A troll with huge club surprises you.
The troll tries to grab you!
The troll swings at you with a club!
The troll shrieks in horror and runs away!
Clubbed troll power: 20.
```

## 클래스 다이어그램

![alt text](./etc/decorator.urm.png "Decorator pattern class diagram")

## 적용 가능성

다음과 같은 경우 데코레이터(Decorator) 패턴을 사용합니다.:

* 다른 객체에 영향을 주지 않으면서 개별 객체에 동적으로 투명하게 책임을 추가할 수 있습니다.
* 철회될 수 있는 책임들의 경우에 사용합니다.
* 서브클래스를 통한 확장이 실용적이지 않을 경우에 사용합니다. 때로 많은 수의 독립적인 확장은 모든 조합을 지원하기 위해 서브클래스가 폭발적으로 늘어날 수 있습니다.
  또는 클래스 정의가 숨겨져 있거나 서브클래스 생성이 불가능한 경우에 사용합니다.

## 튜토리얼

* [Decorator Pattern Tutorial](https://www.journaldev.com/1540/decorator-design-pattern-in-java-example)

## 실제 사례

 * [java.io.InputStream](http://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html), [java.io.OutputStream](http://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html),
 [java.io.Reader](http://docs.oracle.com/javase/8/docs/api/java/io/Reader.html), [java.io.Writer](http://docs.oracle.com/javase/8/docs/api/java/io/Writer.html)
 * [java.util.Collections#synchronizedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedCollection-java.util.Collection-)
 * [java.util.Collections#unmodifiableXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-)
 * [java.util.Collections#checkedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#checkedCollection-java.util.Collection-java.lang.Class-)


## 크레딧

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
