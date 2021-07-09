---
layout: pattern
title: Filterer
folder: filterer
permalink: /patterns/filterer/zh
description: Design pattern that helps container-like objects to return filtered version of themselves.# short meta description that shows in Google search results
language: zh
categories:
- Functional
  tags:
- Extensibility
---

## 名称/分类

过滤器

## 目的
此设计模式的目的是引入一个功能接口，该接口将添加一个
容器类对象的功能，可以轻松返回自身的过滤版本。

## 解释

真实世界的例子
> 我们正在设计一种威胁（恶意软件）检测软件，它可以分析目标系统
> 其中存在的威胁。在设计中，我们必须考虑到新的
> 可以稍后添加威胁类型。此外，还要求威胁检测
> 系统可以根据不同的标准过滤检测到的威胁（目标系统充当
> 类似容器的威胁对象）。

换句话说
> 过滤器模式是一种设计模式，可帮助类容器对象返回过滤版本
> 自己。

**Programmatic Example**

为了对上述威胁检测示例进行建模，我们引入了“Threat”和“ThreatAwareSystem”
接口。

```java
public interface Threat {
  String name();
  int id();
  ThreatType type();
}

public interface ThreatAwareSystem {
  String systemId();
  List<? extends Threat> threats();
  Filterer<? extends ThreatAwareSystem, ? extends Threat> filtered();

}
```
请注意返回“Filterer”接口实例的“filtered”方法，该方法定义为：
```java
@FunctionalInterface
public interface Filterer<G, E> {
  G by(Predicate<? super E> predicate);
}
```

用于满足系统能够根据威胁进行自我过滤的要求
特性。类容器对象（在我们的例子中为`ThreatAwareSystem`）需要有一个方法
返回一个 `Filterer` 的实例。这个辅助接口提供了协变指定一个
代表接口的子接口中逆变`Predicate`的下界
类似容器的物体。

在我们的示例中，我们将能够传递一个带有 `?扩展 Threat` 对象和
返回`？从 `Filtered::by` 方法扩展 ThreatAwareSystem`。一个简单的实现
`ThreatAwareSystem` 的：

```java
public class SimpleThreatAwareSystem implements ThreatAwareSystem {

  private final String systemId;
  private final ImmutableList<Threat> issues;

  public SimpleThreatAwareSystem(final String systemId, final List<Threat> issues) {
    this.systemId = systemId;
    this.issues = ImmutableList.copyOf(issues);
  }
  
  @Override
  public String systemId() {
    return systemId;
  }
  
  @Override
  public List<? extends Threat> threats() {
    return new ArrayList<>(issues);
  }

  @Override
  public Filterer<? extends ThreatAwareSystem, ? extends Threat> filtered() {
    return this::filteredGroup;
  }

  private ThreatAwareSystem filteredGroup(Predicate<? super Threat> predicate) {
    return new SimpleThreatAwareSystem(this.systemId, filteredItems(predicate));
  }

  private List<Threat> filteredItems(Predicate<? super Threat> predicate) {
    return this.issues.stream()
            .filter(predicate)
            .collect(Collectors.toList());
  }
}
```

`filtered` 方法被重写以通过给定的谓词过滤威胁列表。

现在，如果我们引入一个新的“威胁”接口子类型，它增加了给定概率的概率
威胁可能出现：

```java
public interface ProbableThreat extends Threat {
  double probability();
}
```

我们还可以引入一个新的界面来表示一个系统，该系统通过他们的
概率：

````java
public interface ProbabilisticThreatAwareSystem extends ThreatAwareSystem {
  @Override
  List<? extends ProbableThreat> threats();

  @Override
  Filterer<? extends ProbabilisticThreatAwareSystem, ? extends ProbableThreat> filtered();
}
````

注意我们如何覆盖 `ProbabilisticThreatAwareSystem` 中的 `filtered` 方法并指定
通过指定不同的泛型类型来不同的返回协变类型。我们的界面干净整洁
默认实现不会混乱。我们将能够过滤
`ProbabilisticThreatAwareSystem` 由 `ProbableThreat` 属性：
```java
public class SimpleProbabilisticThreatAwareSystem implements ProbabilisticThreatAwareSystem {

  private final String systemId;
  private final ImmutableList<ProbableThreat> threats;

  public SimpleProbabilisticThreatAwareSystem(final String systemId, final List<ProbableThreat> threats) {
    this.systemId = systemId;
    this.threats = ImmutableList.copyOf(threats);
  }

  @Override
  public String systemId() {
    return systemId;
  }

  @Override
  public List<? extends ProbableThreat> threats() {
    return threats;
  }

  @Override
  public Filterer<? extends ProbabilisticThreatAwareSystem, ? extends ProbableThreat> filtered() {
    return this::filteredGroup;
  }

  private ProbabilisticThreatAwareSystem filteredGroup(final Predicate<? super ProbableThreat> predicate) {
    return new SimpleProbabilisticThreatAwareSystem(this.systemId, filteredItems(predicate));
  }

  private List<ProbableThreat> filteredItems(final Predicate<? super ProbableThreat> predicate) {
    return this.threats.stream()
            .filter(predicate)
            .collect(Collectors.toList());
  }
}
```

现在，如果我们想按威胁类型过滤“ThreatAwareSystem”，我们可以这样做：

```java
Threat rootkit = new SimpleThreat(ThreatType.ROOTKIT, 1, "Simple-Rootkit");
Threat trojan = new SimpleThreat(ThreatType.TROJAN, 2, "Simple-Trojan");
List<Threat> threats = List.of(rootkit, trojan);

ThreatAwareSystem threatAwareSystem = new SimpleThreatAwareSystem("System-1", threats);

ThreatAwareSystem rootkitThreatAwareSystem = threatAwareSystem.filtered()
           .by(threat -> threat.type() == ThreatType.ROOTKIT);
```

或者，如果我们想过滤 `ProbabilisticThreatAwareSystem`：

```java
ProbableThreat malwareTroyan = new SimpleProbableThreat("Troyan-ArcBomb", 1, ThreatType.TROJAN, 0.99);
ProbableThreat rootkit = new SimpleProbableThreat("Rootkit-System", 2, ThreatType.ROOTKIT, 0.8);
List<ProbableThreat> probableThreats = List.of(malwareTroyan, rootkit);

ProbabilisticThreatAwareSystem simpleProbabilisticThreatAwareSystem =new SimpleProbabilisticThreatAwareSystem("System-1", probableThreats);

ProbabilisticThreatAwareSystem filtered = simpleProbabilisticThreatAwareSystem.filtered()
           .by(probableThreat -> Double.compare(probableThreat.probability(), 0.99) == 0);
```

## 类图

![Filterer](./etc/filterer.png "Filterer")

## 适用性

在处理使用子类型的类容器对象时，可以使用模式，而不是
可扩展类结构的参数化（泛型）。它使您能够轻松扩展过滤
类容器对象随着业务需求变化的能力。
## 教程

* [Article about Filterer pattern posted on it's author's blog](https://blog.tlinkowski.pl/2018/filterer-pattern/)
* [Application of Filterer pattern in domain of text analysis](https://www.javacodegeeks.com/2019/02/filterer-pattern-10-steps.html)

## 已知用途

其中一种用途出现在博客中
[这个链接](https://www.javacodegeeks.com/2019/02/filterer-pattern-10-steps.html)  
它介绍了如何
使用“过滤器”模式创建文本问题分析器，支持用于单元的测试用例
测试。

## 结果

优点：
* 您可以轻松地为类容器对象引入新的子类型，并为包含在其中的对象引入子类型，并且仍然能够轻松过滤这些新子类型的新属性。

缺点：
* 与泛型混合的协变返回类型有时会很棘手

## 鸣谢

* Author of the pattern : [Tomasz Linkowski](https://tlinkowski.pl/)
