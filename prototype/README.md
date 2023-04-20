---
title: Prototype
category: Creational
language: en
tag: 
 - Gang Of Four
 - Instantiation
---

## Intent

Specify the kinds of objects to create using a prototypical instance, and create new objects by 
copying this prototype.

指定使用原型实例创建的对象类型，并通过复制该原型创建新对象。

## Explanation

First, it should be noted that the Prototype pattern is not used to gain performance benefits. It's only 
used for creating new objects from prototype instances.

首先，应该注意到Prototype模式并不是用来获得性能优势的。它只用于从原型实例创建新对象。

Real-world example

> Remember Dolly? The sheep that was cloned! Let's not get into the details but the key point here is 
> that it is all about cloning.
> 
> 还记得多莉?克隆的那只羊!让我们不进入细节，但关键是，这是关于克隆。

In plain words

> Create an object based on an existing object through cloning.
> 
> 通过克隆，在已有对象的基础上创建对象。

Wikipedia says

> The prototype pattern is a creational design pattern in software development. It is used when the 
> type of objects to create is determined by a prototypical instance, which is cloned to produce new 
> objects.
> 
> 原型模式是软件开发中的一种创造性设计模式。当要创建的对象类型由原型实例确定时，将使用该实例，原型实例将被克隆以生成新对象。

In short, it allows you to create a copy of an existing object and modify it to your needs, instead 
of going through the trouble of creating an object from scratch and setting it up.

简而言之，它允许您创建现有对象的副本，并根据需要修改它，而不必从头创建对象并进行设置。

**Programmatic Example**

In Java, the prototype pattern is recommended to be implemented as follows. First, create an
interface with a method for cloning objects. In this example, `Prototype` interface accomplishes
this with its `copy` method.

在Java中，建议将原型模式实现如下。首先，创建一个带有用于克隆对象的方法的接口。在这个例子中，“Prototype”接口通过它的“copy”方法来实现这一点。

```java
public abstract class Prototype<T> implements Cloneable {
    @SneakyThrows
    public T copy() {
        return (T) super.clone();
    }
}
```

Our example contains a hierarchy of different creatures. For example, let's look at `Beast` and
`OrcBeast` classes.

我们的例子包含了不同生物的层次结构。例如，让我们看看' Beast '和' OrcBeast '类。

```java
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class Beast extends Prototype<Beast> {

  public Beast(Beast source) {
  }

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
  public String toString() {
    return "Orcish wolf attacks with " + weapon;
  }

}
```

We don't want to go into too many details, but the full example contains also base classes `Mage`
and `Warlord` and there are specialized implementations for those for elves in addition to orcs.

我们不想透露太多细节，但是完整的例子还包含了基类“法师”和“军阀”，除了兽人之外，精灵还有专门的实现。

To take full advantage of the prototype pattern, we create `HeroFactory` and `HeroFactoryImpl`
classes to produce different kinds of creatures from proto　types.

为了充分利用原型模式，我们创建了“HeroFactory”和“HeroFactoryImpl”类来从原型中生成不同种类的生物。

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

Now, we are able to show the full prototype pattern in action producing new creatures by cloning
existing instances.

现在，我们可以展示完整的原型模式，通过克隆现有实例生成新的生物。

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

Here's the console output from running the example.

```
Elven mage helps in cooking
Elven warlord helps in cleaning
Elven eagle helps in protecting
Orcish mage attacks with axe
Orcish warlord attacks with sword
Orcish wolf attacks with laser
```

## Class diagram

![alt text](./etc/prototype.urm.png "Prototype pattern class diagram")

## Applicability
适用性

Use the Prototype pattern when a system should be independent of how its products are created, 
composed, represented and

当系统应该独立于产品的创建、组成和表示方式时，请使用Prototype模式

* When the classes to instantiate are specified at run-time, for example, by dynamic loading.
* 当在运行时指定要实例化的类时，例如，通过动态加载。
* To avoid building a class hierarchy of factories that parallels the class hierarchy of products.
* 避免构建与产品类层次结构相似的工厂类层次结构。
* When instances of a class can have one of only a few different combinations of state. It may be 
more convenient to install a corresponding number of prototypes and clone them rather than 
instantiating the class manually, each time with the appropriate state.
* 当类的实例只有几种不同的状态组合之一时。安装相应数量的原型并克隆它们可能比手动实例化类更方便，每次都使用适当的状态。
* When object creation is expensive compared to cloning.
* 当创建对象比克隆开销更大时。

## Known uses
已知使用

* [java.lang.Object#clone()](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone%28%29)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
