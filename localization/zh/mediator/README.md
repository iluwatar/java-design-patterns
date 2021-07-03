---
layout: pattern
title: Mediator
folder: mediator
permalink: /patterns/mediator/zh
categories: Behavioral
language: zh
tags:
 - Gang Of Four
 - Decoupling
---

## 目的
定义一个封装一组对象如何交互的对象。中介促进松耦合
通过防止对象明确地相互引用，它可以让你改变它们的交互
独立。

## 解释

真实世界的例子

> 盗贼、巫师、霍比特人和猎人决定加入他们的队伍，一起旅行
> 派对。为了避免每个成员相互耦合，他们使用派对接口来
> 相互交流。

简单来说

> 中介者通过强制他们的通信流通过中介来解耦一组类
> 对象。

维基百科说

> 在软件工程中，中介者模式定义了一个对象，该对象封装了一组
> 对象交互。这种模式被认为是一种行为模式，因为它可以
> 改变程序的运行行为。在面向对象编程中，程序通常由
> 许多课程。业务逻辑和计算分布在这些类中。然而，作为
> 更多的类被添加到程序中，特别是在维护和/或重构期间，
> 这些类之间的通信问题可能会变得更加复杂。这使得程序
> 更难阅读和维护。此外，更改程序可能变得困难，因为
> 任何更改都可能影响其他几个类中的代码。使用中介者模式，沟通
> 对象之间被封装在一个中介对象中。对象不再直接通信
> 彼此之间，而是通过中介进行交流。这减少了依赖
> 通信对象之间，从而减少耦合。

**程序示例**

在这个例子中，中介器封装了一组对象如何交互。而不是指
彼此直接使用中介接口。

党员`Rogue`、`Wizard`、`Hobbit`和`Hunter`都继承自`PartyMemberBase`
实现`PartyMember`接口。

```java
public interface PartyMember {

  void joinedParty(Party party);

  void partyAction(Action action);

  void act(Action action);
}

@Slf4j
public abstract class PartyMemberBase implements PartyMember {

  protected Party party;

  @Override
  public void joinedParty(Party party) {
    LOGGER.info("{} joins the party", this);
    this.party = party;
  }

  @Override
  public void partyAction(Action action) {
    LOGGER.info("{} {}", this, action.getDescription());
  }

  @Override
  public void act(Action action) {
    if (party != null) {
      LOGGER.info("{} {}", this, action);
      party.act(this, action);
    }
  }

  @Override
  public abstract String toString();
}

public class Rogue extends PartyMemberBase {

  @Override
  public String toString() {
    return "Rogue";
  }
}

// Wizard, Hobbit, and Hunter are implemented similarly
```

我们的中介系统由“Party”接口及其实现组成。
```java
public interface Party {

  void addMember(PartyMember member);

  void act(PartyMember actor, Action action);
}

public class PartyImpl implements Party {

  private final List<PartyMember> members;

  public PartyImpl() {
    members = new ArrayList<>();
  }

  @Override
  public void act(PartyMember actor, Action action) {
    for (var member : members) {
      if (!member.equals(actor)) {
        member.partyAction(action);
      }
    }
  }

  @Override
  public void addMember(PartyMember member) {
    members.add(member);
    member.joinedParty(this);
  }
}
```

这是一个演示，显示了正在运行的中介模式。
```java
    // create party and members
    Party party = new PartyImpl();
    var hobbit = new Hobbit();
    var wizard = new Wizard();
    var rogue = new Rogue();
    var hunter = new Hunter();

    // add party members
    party.addMember(hobbit);
    party.addMember(wizard);
    party.addMember(rogue);
    party.addMember(hunter);

    // perform actions -> the other party members
    // are notified by the party
    hobbit.act(Action.ENEMY);
    wizard.act(Action.TALE);
    rogue.act(Action.GOLD);
    hunter.act(Action.HUNT);
```

这是运行示例的控制台输出。
```
Hobbit joins the party
Wizard joins the party
Rogue joins the party
Hunter joins the party
Hobbit spotted enemies
Wizard runs for cover
Rogue runs for cover
Hunter runs for cover
Wizard tells a tale
Hobbit comes to listen
Rogue comes to listen
Hunter comes to listen
Rogue found gold
Hobbit takes his share of the gold
Wizard takes his share of the gold
Hunter takes his share of the gold
Hunter hunted a rabbit
Hobbit arrives for dinner
Wizard arrives for dinner
Rogue arrives for dinner
```

## 类图

![alt text](./etc/mediator_1.png "Mediator")
## 适用性

在以下情况下使用中介者模式

* 一组对象以定义明确但复杂的方式进行通信。由此产生的相互依赖是非结构化的，难以理解
* 重用一个对象很困难，因为它引用了许多其他对象并与之通信
* 分布在多个类之间的行为应该是可定制的，无需大量子类化

## 已知用途

* All scheduleXXX() methods of [java.util.Timer](http://docs.oracle.com/javase/8/docs/api/java/util/Timer.html)
* [java.util.concurrent.Executor#execute()](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executor.html#execute-java.lang.Runnable-)
* submit() and invokeXXX() methods of [java.util.concurrent.ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* scheduleXXX() methods of [java.util.concurrent.ScheduledExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledExecutorService.html)
* [java.lang.reflect.Method#invoke()](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#invoke-java.lang.Object-java.lang.Object...-)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
