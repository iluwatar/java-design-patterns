---
layout: pattern
title: Prototype
folder: prototype
permalink: /patterns/prototype/zh
categories: Creational
language: zh
tags: 
 - Gang Of Four
 - Instantiation
---

## 意图

使用原型实例指定要创建的对象种类，并通过以下方式创建新对象
复制这个原型。

## 解释

首先，应该注意原型模式不是为了获得性能优势。这只是
用于从原型实例创建新对象。

真实世界的例子

> 还记得多莉吗？被克隆的羊！让我们不要进入细节，但这里的关键点是
> 一切都与克隆有关。

简单来说

> 通过克隆基于现有对象创建对象。

维基百科说

> 原型模式是软件开发中的一种创造型设计模式。它用于当
> 要创建的对象类型由原型实例决定，该实例被克隆以产生新的
> 对象。

简而言之，它允许您创建现有对象的副本并根据需要对其进行修改，而不是
经历从头开始创建对象并设置它的麻烦。

**程序示例**

在 Java 中，建议按如下方式实现原型模式。首先，创建一个
与克隆对象的方法的接口。在这个例子中，`Prototype` 接口完成
这与其`copy`方法。

```java
public interface Prototype {
  Object copy();
}
```

我们的示例包含不同生物的层次结构。例如，让我们看看 `Beast` 和
`OrcBeast` 类。
```java
@EqualsAndHashCode
@NoArgsConstructor
public abstract class Beast implements Prototype {

  public Beast(Beast source) {
  }

  @Override
  public abstract Beast copy();
}

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class OrcBeast extends Beast {

  private final String weapon;

  public OrcBeast(OrcBeast orcBeast) {
    super(orcBeast);
    this.weapon = orcBeast.weapon;
  }

  @Override
  public OrcBeast copy() {
    return new OrcBeast(this);
  }

  @Override
  public String toString() {
    return "Orcish wolf attacks with " + weapon;
  }
}
```

我们不想讨论太多细节，但完整的示例还包含基类 `Mage`
和`Warlord`，除了兽人之外，还有针对精灵的专门实现。

为了充分利用原型模式，我们创建了 `HeroFactory` 和 `HeroFactoryImpl`
类以从原型产生不同种类的生物。
```java
public interface HeroFactory {
  
  Mage createMage();
  Warlord createWarlord();
  Beast createBeast();
}

@RequiredArgsConstructor
public class HeroFactoryImpl implements HeroFactory {

  private final Mage mage;
  private final Warlord warlord;
  private final Beast beast;

  public Mage createMage() {
    return mage.copy();
  }

  public Warlord createWarlord() {
    return warlord.copy();
  }

  public Beast createBeast() {
    return beast.copy();
  }
}
```

现在，我们能够展示通过克隆产生新生物的完整原型模式
现有实例。

```java
    var factory = new HeroFactoryImpl(
        new ElfMage("cooking"),
        new ElfWarlord("cleaning"),
        new ElfBeast("protecting")
    );
    var mage = factory.createMage();
    var warlord = factory.createWarlord();
    var beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());

    factory = new HeroFactoryImpl(
        new OrcMage("axe"),
        new OrcWarlord("sword"),
        new OrcBeast("laser")
    );
    mage = factory.createMage();
    warlord = factory.createWarlord();
    beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());
```

控制台输出

```
Elven mage helps in cooking
Elven warlord helps in cleaning
Elven eagle helps in protecting
Orcish mage attacks with axe
Orcish warlord attacks with sword
Orcish wolf attacks with laser
```

## 类图

![alt text](./etc/prototype.urm.png "Prototype pattern class diagram")

## 适用性
当系统应该独立于其产品的创建方式时，使用原型模式，
组成、代表和

* 在运行时指定要实例化的类时，例如通过动态加载。
* 避免构建与产品类层次结构平行的工厂类层次结构。
* 当一个类的实例可以具有仅有的几种不同状态组合中的一种时。它可能是
  更方便地安装相应数量的原型并克隆它们而不是
  手动实例化类，每次都具有适当的状态。
* 与克隆相比，创建对象的成本更高。

## 已知用途

* [java.lang.Object#clone()](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone%28%29)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
