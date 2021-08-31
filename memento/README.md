---
layout: pattern
title: Memento
folder: memento
permalink: /patterns/memento/
categories: Behavioral
language: en
tags:
 - Gang of Four
---

## Also known as

Token

## Intent

Without violating encapsulation, capture and externalize an object's internal state so that the 
object can be restored to this state later.

在不违反封装的情况下，捕获并外部化对象的内部状态，以便稍后可以将对象恢复到此状态。

## Explanation

Real-world example

> We are working on an astrology application where we need to analyze star properties over time. We 
> are creating snapshots of star states using the Memento pattern.
> 
> 我们正在研究一个占星学应用程序，我们需要分析恒星随时间的特性。我们正在使用Memento模式创建恒星状态的快照。

In plain words

> Memento pattern captures object internal state making it easy to store and restore objects in any 
> point of time.
> 
> Memento模式捕获对象的内部状态，便于在任何时间点存储和恢复对象。

Wikipedia says

> The memento pattern is a software design pattern that provides the ability to restore an object to 
> its previous state (undo via rollback).
> 
> memento模式是一种软件设计模式，它能够将对象恢复到以前的状态(通过回滚撤消)。

**Programmatic Example**

Let's first define the types of stars we are capable to handle.

让我们首先定义我们能够处理的恒星类型。

```java
public enum StarType {
  SUN("sun"),
  RED_GIANT("red giant"),
  WHITE_DWARF("white dwarf"),
  SUPERNOVA("supernova"),
  DEAD("dead star");
  ...
}
```

Next, let's jump straight to the essentials. Here's the `Star` class along with the mementos that we 
need to manipulate. Especially pay attention to `getMemento` and `setMemento` methods.

接下来，让我们直入主题。这是Star类以及我们需要操作的纪念品。特别要注意getMemento和setMemento方法。

```java
public interface StarMemento {
}

public class Star {

  private StarType type;
  private int ageYears;
  private int massTons;

  public Star(StarType startType, int startAge, int startMass) {
    this.type = startType;
    this.ageYears = startAge;
    this.massTons = startMass;
  }

  public void timePasses() {
    ageYears *= 2;
    massTons *= 8;
    switch (type) {
      case RED_GIANT:
        type = StarType.WHITE_DWARF;
        break;
      case SUN:
        type = StarType.RED_GIANT;
        break;
      case SUPERNOVA:
        type = StarType.DEAD;
        break;
      case WHITE_DWARF:
        type = StarType.SUPERNOVA;
        break;
      case DEAD:
        ageYears *= 2;
        massTons = 0;
        break;
      default:
        break;
    }
  }

  StarMemento getMemento() {
    var state = new StarMementoInternal();
    state.setAgeYears(ageYears);
    state.setMassTons(massTons);
    state.setType(type);
    return state;
  }

  void setMemento(StarMemento memento) {
    var state = (StarMementoInternal) memento;
    this.type = state.getType();
    this.ageYears = state.getAgeYears();
    this.massTons = state.getMassTons();
  }

  @Override
  public String toString() {
    return String.format("%s age: %d years mass: %d tons", type.toString(), ageYears, massTons);
  }

  private static class StarMementoInternal implements StarMemento {

    private StarType type;
    private int ageYears;
    private int massTons;

    // setters and getters ->
    ...
  }
}
```

And finally here's how we use the mementos to store and restore star states.

最后是我们如何使用这些纪念品来存储和恢复星州。

```java
    var states = new Stack<>();
    var star = new Star(StarType.SUN, 10000000, 500000);
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    while (states.size() > 0) {
      star.setMemento(states.pop());
      LOGGER.info(star.toString());
    }
```

Program output:

```
sun age: 10000000 years mass: 500000 tons
red giant age: 20000000 years mass: 4000000 tons
white dwarf age: 40000000 years mass: 32000000 tons
supernova age: 80000000 years mass: 256000000 tons
dead star age: 160000000 years mass: 2048000000 tons
supernova age: 80000000 years mass: 256000000 tons
white dwarf age: 40000000 years mass: 32000000 tons
red giant age: 20000000 years mass: 4000000 tons
sun age: 10000000 years mass: 500000 tons
```

## Class diagram

![alt text](./etc/memento.png "Memento")

## Applicability

Use the Memento pattern when

* A snapshot of an object's state must be saved so that it can be restored to that state later, and
  必须保存对象状态的快照，以便稍后可以将其恢复到该状态
* A direct interface to obtaining the state would expose implementation details and break the 
object's encapsulation
  获取状态的直接接口将公开实现细节，并破坏对象的封装

## Known uses

* [java.util.Date](http://docs.oracle.com/javase/8/docs/api/java/util/Date.html)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
