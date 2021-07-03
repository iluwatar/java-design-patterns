---
layout: pattern
title: Specification
folder: specification
permalink: /patterns/specification/zh
categories: Behavioral
language: zh
tags:
 - Data access
---

## 又称为
过滤器、标准


## 目的
规范模式将如何匹配候选对象的语句与候选对象分开
它与之匹配。除了它在选择方面的用处之外，它对于
验证和按订单生产。

＃＃ 解释

真实世界的例子

> 有一大堆不同的生物，我们经常需要选择其中的一些子集。我们可以
> 编写我们的搜索规范，例如“可以飞行的生物”、“重于 500 的生物
> 公斤”，或作为其他搜索规范的组合，然后将其提供给该方
> 将执行过滤。

用简单的话说

> 规范模式允许我们将搜索条件与执行搜索的对象分开
> 搜索。

维基百科说

> 在计算机编程中，规范模式是一种特殊的软件设计模式，
> 可以通过使用布尔值将业务规则链接在一起来重新组合业务规则
> 逻辑。

**程序示例**

如果我们从上面看我们的生物池示例，我们有一组具有特定属性的生物
特性。这些属性可以是预定义的有限集的一部分（此处由
枚举大小、运动和颜色）；但它们也可以是连续值（例如
生物）。在这种情况下，使用我们所说的“参数化规范”更合适，
当 Creature 被实例化时，属性值可以作为参数给出，允许
更大的灵活性。第三种选择是使用组合预定义和/或参数化的属性
布尔逻辑，允许近乎无限的选择可能性（这称为“复合
规范”，见下文。每种方法的优缺点在最后的表格中详细说明
本文件的。

```java
public interface Creature {
  String getName();
  Size getSize();
  Movement getMovement();
  Color getColor();
  Mass getMass();
}
```

And `Dragon` implementation looks like this.

```java
public class Dragon extends AbstractCreature {

  public Dragon() {
    super("Dragon", Size.LARGE, Movement.FLYING, Color.RED, new Mass(39300.0));
  }
}
```

现在我们想要选择它们的一些子集，我们使用选择器。为了选择会飞的生物，我们
应该使用`MovementSelector`。

```java
public class MovementSelector extends AbstractSelector<Creature> {

  private final Movement movement;

  public MovementSelector(Movement m) {
    this.movement = m;
  }

  @Override
  public boolean test(Creature t) {
    return t.getMovement().equals(movement);
  }
}
```

On the other hand, when selecting creatures heavier than a chosen amount, we use
`MassGreaterThanSelector`。
```java
public class MassGreaterThanSelector extends AbstractSelector<Creature> {

  private final Mass mass;

  public MassGreaterThanSelector(double mass) {
    this.mass = new Mass(mass);
  }

  @Override
  public boolean test(Creature t) {
    return t.getMass().greaterThan(mass);
  }
}
```

有了这些构建块，我们可以按如下方式搜索红色生物：
```java
    var redCreatures = creatures.stream().filter(new ColorSelector(Color.RED))
      .collect(Collectors.toList());
```

但是我们也可以像这样使用我们的参数化选择器：
```java
    var heavyCreatures = creatures.stream().filter(new MassGreaterThanSelector(500.0)
      .collect(Collectors.toList());
```

我们的第三个选择是将多个选择器组合在一起。执行特殊搜索
生物（定义为红色、飞行和不小）可以按如下方式完成：
```java
    var specialCreaturesSelector = 
      new ColorSelector(Color.RED).and(new MovementSelector(Movement.FLYING)).and(new SizeSelector(Size.SMALL).not());

    var specialCreatures = creatures.stream().filter(specialCreaturesSelector)
      .collect(Collectors.toList());
```
**更多关于复合规格**

在 Composite Specification 中，我们将通过组合创建 `AbstractSelector` 的自定义实例
使用三个基本逻辑运算符的其他选择器（称为“叶子”）。这些是在
`ConjunctionSelector`、`DisjunctionSelector` 和 `NegationSelector`。
```java
public abstract class AbstractSelector<T> implements Predicate<T> {

  public AbstractSelector<T> and(AbstractSelector<T> other) {
    return new ConjunctionSelector<>(this, other);
  }

  public AbstractSelector<T> or(AbstractSelector<T> other) {
    return new DisjunctionSelector<>(this, other);
  }

  public AbstractSelector<T> not() {
    return new NegationSelector<>(this);
  }
}
```

```java
public class ConjunctionSelector<T> extends AbstractSelector<T> {

  private final List<AbstractSelector<T>> leafComponents;

  @SafeVarargs
  ConjunctionSelector(AbstractSelector<T>... selectors) {
    this.leafComponents = List.of(selectors);
  }

  /**
   * Tests if *all* selectors pass the test.
   */
  @Override
  public boolean test(T t) {
    return leafComponents.stream().allMatch(comp -> (comp.test(t)));
  }
}
```

现在剩下要做的就是创建叶子选择器（无论是硬编码的还是参数化的）
尽可能通用，我们将能够实例化 ``AbstractSelector`` 类
结合任意数量的选择器，如上例所示。不过我们应该小心，因为这很容易
组合多个逻辑运算符时出错；尤其要注意
操作的优先级。一般来说，复合规范是编写更多内容的好方法
可重用的代码，因为不需要为每个过滤操作创建一个 Selector 类。反而，
我们只是“在现场”创建了一个 ``AbstractSelector`` 的实例，使用通用的“叶子”
选择器和一些基本的布尔逻辑。

**不同方法的比较**
| Pattern | Usage | Pros | Cons |
|---|---|---|---|
| Hard-Coded Specification | Selection criteria are few and known in advance | + Easy to implement | - Inflexible |
| | | + Expressive |
| Parameterized Specification | Selection criteria are a large range of values (e.g. mass, speed,...) | + Some flexibility | - Still requires special-purpose classes |
| Composite Specification | There are a lot of selection criteria that can be combined in multiple ways, hence it is not feasible to create a class for each selector | + Very flexible, without requiring many specialized classes | - Somewhat more difficult to comprehend |
| | | + Supports logical operations | - You still need to create the base classes used as leaves |

## 类图

![alt text](./etc/specification.png "Specification")

## 适用性
使用规范模式时

* 您需要根据某些条件选择对象的子集，并在不同时间刷新选择。
* 您需要检查是否只有合适的对象用于某个角色（验证）。

## 相关模式

* 存储库

## 鸣谢

* [Martin Fowler - Specifications](http://martinfowler.com/apsupp/spec.pdf)
