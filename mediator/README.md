---
layout: pattern
title: Mediator
folder: mediator
permalink: /patterns/mediator/
categories: Behavioral
language: en
tags:
 - Gang Of Four
 - Decoupling
---

## Intent

Define an object that encapsulates how a set of objects interact. Mediator promotes loose coupling 
by keeping objects from referring to each other explicitly, and it lets you vary their interaction 
independently.

定义一个对象来封装一组对象如何交互。Mediator通过防止对象显式地相互引用来促进松耦合，并且它允许您独立地改变它们的交互。

## Explanation

Real-world example

> Rogue, wizard, hobbit, and hunter have decided to join their forces and travel in the same
> party. To avoid coupling each member with each other, they use the party interface to
> communicate with each other.
> 
> 盗贼、巫师、霍比特人和猎人决定加入他们的部队，并在同一个队伍中旅行。为了避免每个成员之间的耦合，它们使用party接口来相互通信。

In plain words

> Mediator decouples a set of classes by forcing their communications flow through a mediating
> object.
> 
> Mediator通过强制类的通信流通过中介对象来解耦一组类。

Wikipedia says

> In software engineering, the mediator pattern defines an object that encapsulates how a set of 
> objects interact. This pattern is considered to be a behavioral pattern due to the way it can 
> alter the program's running behavior. In object-oriented programming, programs often consist of 
> many classes. Business logic and computation are distributed among these classes. However, as 
> more classes are added to a program, especially during maintenance and/or refactoring, the 
> problem of communication between these classes may become more complex. This makes the program 
> harder to read and maintain. Furthermore, it can become difficult to change the program, since 
> any change may affect code in several other classes. With the mediator pattern, communication 
> between objects is encapsulated within a mediator object. Objects no longer communicate directly 
> with each other, but instead communicate through the mediator. This reduces the dependencies 
> between communicating objects, thereby reducing coupling.
> 
> 在软件工程中，中介模式定义了一个对象，该对象封装了一组对象如何交互。这种模式被认为是一种行为模式，因为它可以改变程序的运行行为。
> 在面向对象编程中，程序通常由许多类组成。业务逻辑和计算分布在这些类中。然而，随着越来越多的类被添加到程序中，特别是在维护和/或重构期间，
> 这些类之间的通信问题可能会变得更加复杂。这使得程序更难阅读和维护。此外，更改程序会变得很困难，因为任何更改都可能影响其他几个类中的代码。
> 使用中介模式，对象之间的通信封装在中介对象中。对象之间不再直接通信，而是通过中介进行通信。这减少了通信对象之间的依赖关系，从而减少了耦合。

**Programmatic Example**

In this example, the mediator encapsulates how a set of objects interact. Instead of referring to 
each other directly they use the mediator interface.

在本例中，中介封装了一组对象如何交互。它们使用中介接口，而不是直接相互引用。

The party members `Rogue`, `Wizard`, `Hobbit`, and `Hunter` all inherit from the `PartyMemberBase`
implementing the `PartyMember` interface.

团队成员Rogue、Wizard、Hobbit和Hunter都继承自实现PartyMember接口的PartyMemberBase。

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
// Wizard, Hobbit, and Hunter 实现方式相似
```

Our mediator system consists of `Party` interface and its implementation.

我们的mediator系统由Party接口以及其实现组成。

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

Here's a demo showing the mediator pattern in action.

下面的demo演示了中介者模式的作用。

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

Here's the console output from running the example.

下面是运行示例的控制台输出。

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

## Class diagram

![alt text](./etc/mediator_1.png "Mediator")

## Applicability
应用

Use the Mediator pattern when
以下情况使用中介者模式

* A set of objects communicate in well-defined but complex ways. The resulting interdependencies are unstructured and difficult to understand
* 一组对象以定义良好但复杂的方式进行通信。由此产生的相互依赖关系是非结构化的，难以理解
* Reusing an object is difficult because it refers to and communicates with many other objects
* 重用一个对象很困难，因为它引用许多其他对象并与之通信
* A behavior that's distributed between several classes should be customizable without a lot of subclassing
* 分布在多个类之间的行为应该是可定制的，无需大量子类化

## Known uses

* All scheduleXXX() methods of [java.util.Timer](http://docs.oracle.com/javase/8/docs/api/java/util/Timer.html)
* [java.util.concurrent.Executor#execute()](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executor.html#execute-java.lang.Runnable-)
* submit() and invokeXXX() methods of [java.util.concurrent.ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* scheduleXXX() methods of [java.util.concurrent.ScheduledExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledExecutorService.html)
* [java.lang.reflect.Method#invoke()](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#invoke-java.lang.Object-java.lang.Object...-)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
