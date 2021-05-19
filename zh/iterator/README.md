---
layout: pattern
title: Iterator
folder: iterator
permalink: /patterns/iterator/zh
categories: Behavioral
language: zh
tags:
 - Gang of Four
---

## 又被称为
游标

## 目的
提供一种在不暴露其基础表示的情况下顺序访问聚合对象的元素的方法。

## 解释

真实世界例子

> 百宝箱包含一组魔法物品。有多种物品，例如戒指，药水和武器。可以使用藏宝箱提供的迭代器按类型浏览商品。

通俗地说

> 容器可以提供与表示形式无关的迭代器接口，以提供对元素的访问。

维基百科说

> 在面向对象的编程中，迭代器模式是一种设计模式，其中迭代器用于遍历容器并访问容器的元素。

**程序示例**

在我们的示例中包含物品的藏宝箱是主要类。

```java
public class TreasureChest {

  private final List<Item> items;

  public TreasureChest() {
    items = List.of(
        new Item(ItemType.POTION, "Potion of courage"),
        new Item(ItemType.RING, "Ring of shadows"),
        new Item(ItemType.POTION, "Potion of wisdom"),
        new Item(ItemType.POTION, "Potion of blood"),
        new Item(ItemType.WEAPON, "Sword of silver +1"),
        new Item(ItemType.POTION, "Potion of rust"),
        new Item(ItemType.POTION, "Potion of healing"),
        new Item(ItemType.RING, "Ring of armor"),
        new Item(ItemType.WEAPON, "Steel halberd"),
        new Item(ItemType.WEAPON, "Dagger of poison"));
  }

  public Iterator<Item> iterator(ItemType itemType) {
    return new TreasureChestItemIterator(this, itemType);
  }

  public List<Item> getItems() {
    return new ArrayList<>(items);
  }
}

public class Item {

  private ItemType type;
  private final String name;

  public Item(ItemType type, String name) {
    this.setType(type);
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  public ItemType getType() {
    return type;
  }

  public final void setType(ItemType type) {
    this.type = type;
  }
}

public enum ItemType {

  ANY, WEAPON, RING, POTION

}
```

迭代器接口极度简单。

```java
public interface Iterator<T> {

  boolean hasNext();

  T next();
}
```

在以下示例中，我们遍历在宝箱中找到的戒指类型物品。

```java
var itemIterator = TREASURE_CHEST.iterator(ItemType.RING);
while (itemIterator.hasNext()) {
  LOGGER.info(itemIterator.next().toString());
}
// Ring of shadows
// Ring of armor
```

## 类图
![alt text](../../iterator/etc/iterator_1.png "Iterator")

## 适用性
以下情况使用迭代器模式

* 在不暴露其内部表示的情况下访问聚合对象的内容
* 为了支持聚合对象的多种遍历方式
* 提供一个遍历不同聚合结构的统一接口

## Java世界例子

* [java.util.Iterator](http://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)
* [java.util.Enumeration](http://docs.oracle.com/javase/8/docs/api/java/util/Enumeration.html)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
