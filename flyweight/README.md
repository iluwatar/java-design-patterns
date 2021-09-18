---
layout: pattern
title: Flyweight
folder: flyweight
permalink: /patterns/flyweight/
categories: Structural
language: en
tags:
 - Gang of Four
 - Performance
---

## Intent

Use sharing to support large numbers of fine-grained objects efficiently.

使用共享有效地支持大量细粒度对象。

## Explanation

Real-world example

> Alchemist's shop has shelves full of magic potions. Many of the potions are the same so there is 
> no need to create a new object for each of them. Instead, one object instance can represent 
> multiple shelf items so the memory footprint remains small.
> 
> 炼金术士的店里摆满了魔法药水。许多药剂都是相同的，所以没有必要为每一种药剂创造新的对象。相反，一个对象实例可以表示多个货架项，因此内存占用仍然很小。

In plain words

> It is used to minimize memory usage or computational expenses by sharing as much as possible with 
> similar objects.
> 
> 它被用于通过与类似对象尽可能多地共享来最小化内存使用或计算开销。

Wikipedia says

> In computer programming, flyweight is a software design pattern. A flyweight is an object that 
> minimizes memory use by sharing as much data as possible with other similar objects; it is a way 
> to use objects in large numbers when a simple repeated representation would use an unacceptable 
> amount of memory.
> 
> 在计算机编程中，flyweight是一种软件设计模式。flyweight是指通过与其他类似对象共享尽可能多的数据来最小化内存使用的对象;它是一种使用大量对象的方法，当一个简单的重复表示将使用不可接受的内存数量时。

**Programmatic example**

Translating our alchemist shop example from above. First of all, we have different potion types:

从上面解释我们的炼金师商店的例子。首先，我们有不同的药剂类型:

```java
public interface Potion {
  void drink();
}

@Slf4j
public class HealingPotion implements Potion {
  @Override
  public void drink() {
    LOGGER.info("You feel healed. (Potion={})", System.identityHashCode(this));
  }
}

@Slf4j
public class HolyWaterPotion implements Potion {
  @Override
  public void drink() {
    LOGGER.info("You feel blessed. (Potion={})", System.identityHashCode(this));
  }
}

@Slf4j
public class InvisibilityPotion implements Potion {
  @Override
  public void drink() {
    LOGGER.info("You become invisible. (Potion={})", System.identityHashCode(this));
  }
}
```

Then the actual Flyweight class `PotionFactory`, which is the factory for creating potions.

然后是真正的Flyweight类“PotionFactory”，它是用于创建药剂的工厂。

```java
public class PotionFactory {

  private final Map<PotionType, Potion> potions;

  public PotionFactory() {
    potions = new EnumMap<>(PotionType.class);
  }

  Potion createPotion(PotionType type) {
    var potion = potions.get(type);
    if (potion == null) {
      switch (type) {
        case HEALING:
          potion = new HealingPotion();
          potions.put(type, potion);
          break;
        case HOLY_WATER:
          potion = new HolyWaterPotion();
          potions.put(type, potion);
          break;
        case INVISIBILITY:
          potion = new InvisibilityPotion();
          potions.put(type, potion);
          break;
        default:
          break;
      }
    }
    return potion;
  }
}
```

`AlchemistShop` contains two shelves of magic potions. The potions are created using the
aforementioned `PotionFactory`.

“AlchemistShop”里有两排魔法药水。药剂是使用前面提到的“PotionFactory”创造的。

```java
@Slf4j
public class AlchemistShop {

  private final List<Potion> topShelf;
  private final List<Potion> bottomShelf;

  public AlchemistShop() {
    var factory = new PotionFactory();
    topShelf = List.of(
        factory.createPotion(PotionType.INVISIBILITY),
        factory.createPotion(PotionType.INVISIBILITY),
        factory.createPotion(PotionType.STRENGTH),
        factory.createPotion(PotionType.HEALING),
        factory.createPotion(PotionType.INVISIBILITY),
        factory.createPotion(PotionType.STRENGTH),
        factory.createPotion(PotionType.HEALING),
        factory.createPotion(PotionType.HEALING)
    );
    bottomShelf = List.of(
        factory.createPotion(PotionType.POISON),
        factory.createPotion(PotionType.POISON),
        factory.createPotion(PotionType.POISON),
        factory.createPotion(PotionType.HOLY_WATER),
        factory.createPotion(PotionType.HOLY_WATER)
    );
  }

  public final List<Potion> getTopShelf() {
    return List.copyOf(this.topShelf);
  }

  public final List<Potion> getBottomShelf() {
    return List.copyOf(this.bottomShelf);
  }

  public void drinkPotions() {
    LOGGER.info("Drinking top shelf potions\n");
    topShelf.forEach(Potion::drink);
    LOGGER.info("Drinking bottom shelf potions\n");
    bottomShelf.forEach(Potion::drink);
  }
}
```

In our scenario, a brave visitor enters the alchemist shop and drinks all the potions.

在我们的场景中，一个勇敢的游客进入炼金师商店并喝下所有的药水。

```java
// create the alchemist shop with the potions
var alchemistShop = new AlchemistShop();
// a brave visitor enters the alchemist shop and drinks all the potions
alchemistShop.drinkPotions();
```

Program output:

```java
Drinking top shelf potions 
You become invisible. (Potion=1509514333)
You become invisible. (Potion=1509514333)
You feel strong. (Potion=739498517)
You feel healed. (Potion=125130493)
You become invisible. (Potion=1509514333)
You feel strong. (Potion=739498517)
You feel healed. (Potion=125130493)
You feel healed. (Potion=125130493)
Drinking bottom shelf potions
Urgh! This is poisonous. (Potion=166239592)
Urgh! This is poisonous. (Potion=166239592)
Urgh! This is poisonous. (Potion=166239592)
You feel blessed. (Potion=991505714)
You feel blessed. (Potion=991505714)
```

## Class diagram

![alt text](./etc/flyweight.urm.png "Flyweight pattern class diagram")

## Applicability
适用性

The Flyweight pattern's effectiveness depends heavily on how and where it's used. Apply the 
Flyweight pattern when all of the following are true:

Flyweight模式的有效性很大程度上取决于它的使用方式和使用位置。当下列所有条件都为true时，应用Flyweight模式:

* An application uses a large number of objects.
* 应用程序使用大量的对象。
* Storage costs are high because of the sheer quantity of objects.
* 由于对象的数量巨大，存储成本很高。
* Most of the object state can be made extrinsic.
* 大多数对象状态都可以是外在的。
* Many groups of objects may be replaced by relatively few shared objects once the extrinsic state 
  is removed.
* 一旦外部状态被移除，许多组对象可能会被相对较少的共享对象所替代。
* The application doesn't depend on object identity. Since flyweight objects may be shared, identity 
tests will return true for conceptually distinct objects.
* 应用程序不依赖于对象标识。由于flyweight对象可以共享，因此对于概念上不同的对象，标识测试将返回true。

## Known uses
已知使用

* [java.lang.Integer#valueOf(int)](http://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#valueOf%28int%29) and similarly for Byte, Character and other wrapped types.
* [java.lang.Integer#valueOf(int)]和类似的Byte, Character和其他包装类型。

## Credits
鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
