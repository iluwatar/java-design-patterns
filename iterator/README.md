---
layout: pattern
title: Iterator
folder: iterator
permalink: /patterns/iterator/
categories: Behavioral
language: en
tags:
 - Gang of Four
---

## Also known as

Cursor

## Intent
Provide a way to access the elements of an aggregate object sequentially without exposing its 
underlying representation.

## Explanation

Real world example

> Treasure chest contains a set of magical items. There multiple types of items such as rings, 
> potions and weapons. The items can be browsed by type using an iterator the treasure chest 
> provides. 

In plain words

> Containers can provide a representation agnostic iterator interface to provide access to the 
> elements. 

Wikipedia says

> In object-oriented programming, the iterator pattern is a design pattern in which an iterator is 
> used to traverse a container and access the container's elements.

**Programmatic Example**

The main class in our example is the `TreasureChest` that contains items.

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
```

Here's the `Item` class:

```java
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

The `Iterator` interface is extremely simple.

```java
public interface Iterator<T> {

  boolean hasNext();

  T next();
}
```

In the following example we iterate through the ring type items found in the chest. 

```java
var itemIterator = TREASURE_CHEST.iterator(ItemType.RING);
while (itemIterator.hasNext()) {
  LOGGER.info(itemIterator.next().toString());
}
```

Program output:

```java
Ring of shadows
Ring of armor
```

## Class diagram

![alt text](./etc/iterator_1.png "Iterator")

## Applicability

Use the Iterator pattern

* To access an aggregate object's contents without exposing its internal representation.
* To support multiple traversals of aggregate objects.
* To provide a uniform interface for traversing different aggregate structures.

## Tutorials

* [How to Use Iterator?](http://www.tutorialspoint.com/java/java_using_iterator.htm)

## Real world examples

* [java.util.Iterator](http://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)
* [java.util.Enumeration](http://docs.oracle.com/javase/8/docs/api/java/util/Enumeration.html)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
