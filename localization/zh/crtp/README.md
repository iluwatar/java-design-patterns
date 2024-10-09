---
title: Curiously Recurring Template Pattern
shortTitle: Curiously Recurring Template Pattern
language: zh
category: Structural
tag:
- Extensibility
- Instantiation
---

## 名称/分类

Curiously Recurring Template Pattern，CRTP，奇异递归模板模式

## 别名

递归类型绑定，递归泛型

## 目的

允许派生组件从与派生类型兼容的基本组件继承某些功能。

## 解释

真实世界的例子

> 对于正在策划赛事的综合格斗推广活动来说，确保在相同重量级的运动员之间组织比赛至关重要。这样可以防止体型明显不同的拳手之间的不匹配，例如重量级拳手与雏量级拳手的对决。

用通俗的话来讲

> 使类型中的某些方法接受特定于其子类型的参数。

维基百科介绍

> 奇异递归模板模式（curiously recurring template pattern，CRTP）是C++模板编程时的一种惯用法：其中类X派生自使用X本身作为模板参数的类模板实例化。

**程序示例**

让我们来定义通用接口Fighter

```java
public interface Fighter<T> {

  void fight(T t);

}
```

MmaFighter类用于实例化按重量级别区分的拳手

``` Java
public class MmaFighter<T extends MmaFighter<T>> implements Fighter<T> {

  private final String name;
  private final String surname;
  private final String nickName;
  private final String speciality;

  public MmaFighter(String name, String surname, String nickName, String speciality) {
    this.name = name;
    this.surname = surname;
    this.nickName = nickName;
    this.speciality = speciality;
  }

  @Override
  public void fight(T opponent) {
    LOGGER.info("{} is going to fight against {}", this, opponent);
  }

  @Override
  public String toString() {
    return name + " \"" + nickName + "\" " + surname;
  }
```

以下是 MmaFighter 的一些子类型

```Java
class MmaBantamweightFighter extends MmaFighter<MmaBantamweightFighter> {

  public MmaBantamweightFighter(String name, String surname, String nickName, String speciality) {
    super(name, surname, nickName, speciality);
  }

}

public class MmaHeavyweightFighter extends MmaFighter<MmaHeavyweightFighter> {

  public MmaHeavyweightFighter(String name, String surname, String nickName, String speciality) {
    super(name, surname, nickName, speciality);
  }

}
```

允许拳手与相同重量级的对手交手，如果对手是不同重量级，则会出现错误

``` Java
MmaBantamweightFighter fighter1 = new MmaBantamweightFighter("Joe", "Johnson", "The Geek", "Muay Thai");
MmaBantamweightFighter fighter2 = new MmaBantamweightFighter("Ed", "Edwards", "The Problem Solver", "Judo");
fighter1.fight(fighter2); // This is fine

MmaHeavyweightFighter fighter3 = new MmaHeavyweightFighter("Dave", "Davidson", "The Bug Smasher", "Kickboxing");
MmaHeavyweightFighter fighter4 = new MmaHeavyweightFighter("Jack", "Jackson", "The Pragmatic", "Brazilian Jiu-Jitsu");
fighter3.fight(fighter4); // This is fine too

fighter1.fight(fighter3); // This will raise a compilation error
```

## 类图

![alt text](etc/crtp.png "CRTP class diagram")

## 适用性

在以下情况下使用CRTP

* 在对象层次结构中链接方法时存在类型冲突
* 您想使用一个参数化的类方法，该方法可以接受类的子类作为参数，从而可以应用于继承自类的对象
* 您希望某些方法仅适用于相同类型的实例，例如实现相互比较。

## 教程

* [The NuaH Blog](https://nuah.livejournal.com/328187.html)
* Yogesh Umesh Vaity answer to [What does "Recursive type bound" in Generics mean?](https://stackoverflow.com/questions/7385949/what-does-recursive-type-bound-in-generics-mean)

## 已知用途

* [java.lang.Enum](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html)

## 鸣谢

* [How do I decrypt "Enum<E extends Enum\<E>>"?](http://www.angelikalanger.com/GenericsFAQ/FAQSections/TypeParameters.html#FAQ106)
* Chapter 5 Generics, Item 30 in [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
