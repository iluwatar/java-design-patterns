---
title: Iterator
category: Behavioral
language: it
tag:
 - Gang of Four
---

## Anche conosciuto come

Cursor

## Intento
Fornire un modo per accedere agli elementi di un oggetto aggregato in modo sequenziale senza esporre la sua rappresentazione sottostante.

## Spiegazione

Esempio del mondo reale

> Lo scrigno del tesoro contiene una serie di oggetti magici. Ci sono diversi tipi di oggetti, come anelli,
> pozioni e armi. Gli oggetti possono essere esaminati per tipo utilizzando un iteratore fornito dalla cassa
> del tesoro.

In parole semplici

> I contenitori possono fornire un'interfaccia di iterazione agnostica alla rappresentazione per consentire l'accesso agli
> elementi.

Wikipedia dice

> Nella programmazione orientata agli oggetti, il pattern iterator è un design pattern in cui un iteratore viene
> utilizzato per attraversare un contenitore e accedere agli elementi del contenitore stesso. _(Testo tradotto dalla voce Iterator Pattern da Wikipedia in lingua inglese)._

**Esempio di codice**

La classe principale nel nostro esempio è `TreasureChest`, che contiene gli oggetti.

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

Ecco la classe `Item`:

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

L'interfaccia `Iterator` è estremamente semplice.

```java
public interface Iterator<T> {

  boolean hasNext();

  T next();
}
```

Nell'esempio seguente, si iterano gli oggetti di tipo anello che si trovano nel forziere.

```java
var itemIterator = TREASURE_CHEST.iterator(ItemType.RING);
while (itemIterator.hasNext()) {
  LOGGER.info(itemIterator.next().toString());
}
```

Output del programma:

```java
Ring of shadows
Ring of armor
```

## Diagramma delle classi

![alt text](../../../iterator/etc/iterator_1.png "Iterator")

## Applicabilità

Usa il pattern Iterator

* Per accedere ai contenuti di un oggetto aggregato senza esporre la sua rappresentazione interna.
* Per supportare più attraversamenti di oggetti aggregati.
* Per fornire un'interfaccia uniforme per l'attraversamento di diverse strutture aggregate.

## Tutorial

* [How to Use Iterator?](http://www.tutorialspoint.com/java/java_using_iterator.htm)

## Usi noti

* [java.util.Iterator](http://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)
* [java.util.Enumeration](http://docs.oracle.com/javase/8/docs/api/java/util/Enumeration.html)

## Collegamenti esterni

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
