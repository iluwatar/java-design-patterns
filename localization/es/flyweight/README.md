---
title: Flyweight
category: Structural
language: es
tag:
  - Gang of Four
  - Performance
---

## Propósito

Utilice la compartición para dar soporte a un gran número de objetos finos de forma eficiente.

## Explicación

Un ejemplo real

> La tienda del alquimista tiene estanterías llenas de pociones mágicas. Muchas de las pociones son las mismas, por lo
> que no es necesario crear un nuevo objeto para cada una de ellas. En su lugar, una instancia de objeto puede
> representar
> múltiples elementos de la estantería para que la huella de memoria siga siendo pequeña.

En pocas palabras

> Se utiliza para minimizar el uso de memoria o los gastos computacionales compartiendo todo lo posible con objetos
> similares.

Wikipedia dice

> En programación informática, flyweight es un patrón de diseño de software. Un flyweight es un objeto que minimiza el
> uso de memoria compartiendo tantos datos como sea posible con otros objetos similares; es una forma de utilizar
> objetos
> en grandes cantidades cuando una simple representación repetida utilizaría una cantidad inaceptable de memoria.

**Ejemplo programático**

Traduciendo nuestro ejemplo de la tienda de alquimia de arriba. En primer lugar, tenemos diferentes tipos de
pociones `Potion`: `HealingPotion`, `HolyWaterPotion` e `InvisibilityPotion`:

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

Luego la clase Flyweight `PotionFactory`, que es la fábrica para crear pociones.

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
        case HEALING -> {
          potion = new HealingPotion();
          potions.put(type, potion);
        }
        case HOLY_WATER -> {
          potion = new HolyWaterPotion();
          potions.put(type, potion);
        }
        case INVISIBILITY -> {
          potion = new InvisibilityPotion();
          potions.put(type, potion);
        }
        default -> {
        }
      }
    }
    return potion;
  }
}
```

`AlchemistShop` contiene dos estantes de pociones mágicas. Las pociones se crean utilizando la antes
mencionada `PotionFactory`.

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

En nuestro escenario, un valiente visitante entra en la tienda del alquimista y se bebe todas las pociones.

```java
// create the alchemist shop with the potions
var alchemistShop = new AlchemistShop();
// a brave visitor enters the alchemist shop and drinks all the potions
alchemistShop.drinkPotions();
```

Salida del programa:

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

## Diagrama de clases

![alt text](./etc/flyweight.urm.png "Flyweight pattern class diagram")

## Aplicabilidad

La eficacia del patrón Flyweight depende en gran medida de cómo y dónde se utilice. Aplique el patrón
Flyweight cuando se cumplan todas las condiciones siguientes:

* Una aplicación utiliza un gran número de objetos.
* Los costes de almacenamiento son altos debido a la gran cantidad de objetos.
* La mayor parte del estado de los objetos puede hacerse extrínseco.
* Muchos grupos de objetos pueden ser reemplazados por relativamente pocos objetos compartidos una vez que el estado extrínseco
  extrínseco.
* La aplicación no depende de la identidad de los objetos. Dado que los objetos flyweight pueden ser compartidos, las pruebas de identidad
  devolverán verdadero para objetos conceptualmente distintos.

## Usos conocidos

* [java.lang.Integer#valueOf(int)](http://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#valueOf%28int%29) y
  de forma similar para Byte, Carácter y otros tipos envueltos (Wrappers).

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
