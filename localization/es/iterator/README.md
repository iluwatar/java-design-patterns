---
title: Iterator
shortTitle: Iterator
category: Behavioral
language: es
tag:
 - Gang of Four
---

## También conocido como

Cursor

## Propósito
Proporcionar una forma de acceder a los elementos de un objeto agregado secuencialmente sin exponer su
representación subyacente.

## Explicación

Ejemplo del mundo real

> El cofre del tesoro contiene un conjunto de objetos mágicos. Hay múltiples tipos de artículos tales como anillos, 
> Los objetos pueden buscarse por tipo utilizando un iterador que proporciona 
> el cofre del tesoro.

En palabras sencillas

> Los contenedores pueden proporcionar una interfaz de iterador agnóstica de representación 
> para proporcionar acceso a los elementos.

Wikipedia dice

> En programación orientada a objetos, el patrón iterador es un patrón de diseño en el que 
> se utiliza un iterador para recorrer un contenedor y acceder a sus elementos.

**Ejemplo programático**

La clase principal de nuestro ejemplo es `TreasureChest` que contiene ítems.

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

Esta es la clase `Item`:

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

La interfaz `Iterator` es extremadamente sencilla.

```java
public interface Iterator<T> {

  boolean hasNext();

  T next();
}
```

En el siguiente ejemplo, iteramos a través de los objetos de tipo anillo encontrados en el cofre. 

```java
var itemIterator = TREASURE_CHEST.iterator(ItemType.RING);
while (itemIterator.hasNext()) {
  LOGGER.info(itemIterator.next().toString());
}
```

Salida del programa:

```java
Ring of shadows
Ring of armor
```

## Diagrama de clases

![alt text](./etc/iterator_1.png "Iterator")

## Aplicabilidad

Utilizar el patrón Iterator

* Para acceder al contenido de un objeto agregado sin exponer su representación interna.
* Para soportar múltiples recorridos de objetos agregados.
* Proporcionar una interfaz uniforme para recorrer diferentes estructuras de agregados.

## Tutoriales

* [How to Use Iterator?](http://www.tutorialspoint.com/java/java_using_iterator.htm)

## Usos conocidos

* [java.util.Iterator](http://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)
* [java.util.Enumeration](http://docs.oracle.com/javase/8/docs/api/java/util/Enumeration.html)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
