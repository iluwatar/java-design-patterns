---
title: Iterator
shortTitle: Iterator
categories: Behavioral
language: ko
tag:
- Gang of Four
---

## 또한 ~으로 알려진

커서(Cursor) 패턴

## 의도
기반이되는 표현을 노출하지 않고 Aggregate 객체의 요소에 순차적으로 접근할 수 있는 방법을 제공합니다.

## 설명

실제 예제

> 보물상자에는 마법의 아이템이 들어 있습니다. 반지, 물약, 무기와 같은 다양한 종류의 물건들이 있습니다. 보물상자가 제공하는 반복기(iterator)를 사용하여 아이템은 유형별로 검색될 수 있을 것입니다.

쉽게 말하자면

> 컨테이너는 요소에 대한 접근을 제공하기 위해 표현에 구애받지 않는 반복기(iterator) 인터페이스를 제공할 수 있습니다.

Wikipedia에 의하면

> 반복자 패턴(iterator pattern)은 객체 지향 프로그래밍에서 반복자를 사용하여 컨테이너를 가로지르며 컨테이너의 요소들에 접근하는 디자인 패턴입니다.

**코드 예제**

예제의 메인 클래스는 아이템들을 담고있는 `보물상자(TreasureChest)` 입니다.

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

다음은 `아이템(item)` 클래스입니다. :

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

`반복자(Iterator)` 인터페이스는 아주 단순합니다.

```java
public interface Iterator<T> {

  boolean hasNext();

  T next();
}
```

다음 예제에서는 보물상자에서 발견되는 고리형 항목을 반복한다.

```java
var itemIterator = TREASURE_CHEST.iterator(ItemType.RING);
while (itemIterator.hasNext()) {
  LOGGER.info(itemIterator.next().toString());
}
```

프로그램 실행결과:

```java
Ring of shadows
Ring of armor
```

## 클래스 다이어그램

![alt text](./etc/iterator_1.png "Iterator")

## 적용 가능성

템플릿 메소드는 사용되어야합니다.

* 내부 표현을 드러내지않고 aggregate 객체의 컨텐츠에 접근하기 위해서

* aggregate 오브젝트들의 여러 번의 횡단을 지원하기 위해서

* 서로 다른 aggregate 구조들의 횡단을 위한  획일적인 인터페이스를 제공하기 위해서

## 튜토리얼

* [How to Use Iterator?](http://www.tutorialspoint.com/java/java_using_iterator.htm)

## 실제 사례

* [java.util.Iterator](http://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)
* [java.util.Enumeration](http://docs.oracle.com/javase/8/docs/api/java/util/Enumeration.html)

## 크레딧

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
