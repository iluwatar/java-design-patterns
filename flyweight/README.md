---
title: "Flyweight Pattern in Java: Maximizing Memory Efficiency with Shared Object Instances"
shortTitle: Flyweight
description: "Learn how the Flyweight design pattern optimizes memory usage in Java applications by sharing data among similar objects. Enhance performance and reduce memory footprint with practical examples and detailed explanations."
category: Structural
language: en
tag:
  - Gang of Four
  - Memory management
  - Object composition
  - Optimization
  - Performance
---

## Intent of Flyweight Design Pattern

The Flyweight design pattern in Java is crucial for optimizing memory usage and enhancing application performance. By minimizing the number of objects created, it significantly reduces the memory footprint. The primary goal of the Flyweight pattern is to share as much data as possible among similar objects, thereby improving efficiency and performance.

## Detailed Explanation of Flyweight Pattern with Real-World Examples

Real-world example

> A real-world application of the Flyweight pattern in Java can be seen in text editors like Microsoft Word or Google Docs. These applications use Flyweight to efficiently manage memory by sharing character objects, reducing the memory footprint significantly. In such applications, each character in a document could potentially be a separate object, which would be highly inefficient in terms of memory usage. Instead, the Flyweight pattern can be used to share character objects. For instance, all instances of the letter 'A' can share a single 'A' object with its intrinsic state (e.g., the shape of the character). The extrinsic state, such as the position, font, and color, can be stored separately and applied as needed. This way, the application efficiently manages memory by reusing existing objects for characters that appear multiple times.

In plain words

> It is used to minimize memory usage or computational expenses by sharing as much as possible with similar objects.

Wikipedia says

> In computer programming, flyweight is a software design pattern. A flyweight is an object that minimizes memory use by sharing as much data as possible with other similar objects; it is a way to use objects in large numbers when a simple repeated representation would use an unacceptable amount of memory.

## Programmatic Example of Flyweight Pattern in Java

Alchemist's shop has shelves full of magic potions. Many of the potions are the same so there is no need to create a new object for each of them. Instead, one object instance can represent  multiple shelf items so the memory footprint remains small.

First of all, we have different `Potion` types:

```java
public interface Potion {
  void drink();
}
```

```java
@Slf4j
public class HealingPotion implements Potion {
  @Override
  public void drink() {
    LOGGER.info("You feel healed. (Potion={})", System.identityHashCode(this));
  }
}
```

```java
@Slf4j
public class HolyWaterPotion implements Potion {
  @Override
  public void drink() {
    LOGGER.info("You feel blessed. (Potion={})", System.identityHashCode(this));
  }
}
```

```java
@Slf4j
public class InvisibilityPotion implements Potion {
  @Override
  public void drink() {
    LOGGER.info("You become invisible. (Potion={})", System.identityHashCode(this));
  }
}
```

Then the actual Flyweight class `PotionFactory`, which is the factory for creating potions.

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
        case HEALING -> potion = new HealingPotion();
        case HOLY_WATER -> potion = new HolyWaterPotion();
        case INVISIBILITY -> potion = new InvisibilityPotion();
        default -> {
        }
      }
      potions.put(type, potion);
    }
    return potion;
  }
}
```

`AlchemistShop` contains two shelves of magic potions. The potions are created using the aforementioned `PotionFactory`.

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

```java
public static void main(String[] args) {
    // create the alchemist shop with the potions
    var alchemistShop = new AlchemistShop();
    // a brave visitor enters the alchemist shop and drinks all the potions
    alchemistShop.drinkPotions();
}
```

Program output:

```
09:02:52.731 [main] INFO com.iluwatar.flyweight.AlchemistShop -- Drinking top shelf potions
09:02:52.733 [main] INFO com.iluwatar.flyweight.InvisibilityPotion -- You become invisible. (Potion=1395089624)
09:02:52.733 [main] INFO com.iluwatar.flyweight.InvisibilityPotion -- You become invisible. (Potion=1395089624)
09:02:52.733 [main] INFO com.iluwatar.flyweight.StrengthPotion -- You feel strong. (Potion=1450821318)
09:02:52.733 [main] INFO com.iluwatar.flyweight.HealingPotion -- You feel healed. (Potion=668849042)
09:02:52.733 [main] INFO com.iluwatar.flyweight.InvisibilityPotion -- You become invisible. (Potion=1395089624)
09:02:52.733 [main] INFO com.iluwatar.flyweight.StrengthPotion -- You feel strong. (Potion=1450821318)
09:02:52.733 [main] INFO com.iluwatar.flyweight.HealingPotion -- You feel healed. (Potion=668849042)
09:02:52.733 [main] INFO com.iluwatar.flyweight.HealingPotion -- You feel healed. (Potion=668849042)
09:02:52.733 [main] INFO com.iluwatar.flyweight.AlchemistShop -- Drinking bottom shelf potions
09:02:52.734 [main] INFO com.iluwatar.flyweight.PoisonPotion -- Urgh! This is poisonous. (Potion=2096057945)
09:02:52.734 [main] INFO com.iluwatar.flyweight.PoisonPotion -- Urgh! This is poisonous. (Potion=2096057945)
09:02:52.734 [main] INFO com.iluwatar.flyweight.PoisonPotion -- Urgh! This is poisonous. (Potion=2096057945)
09:02:52.734 [main] INFO com.iluwatar.flyweight.HolyWaterPotion -- You feel blessed. (Potion=1689843956)
09:02:52.734 [main] INFO com.iluwatar.flyweight.HolyWaterPotion -- You feel blessed. (Potion=1689843956)
```

## When to Use the Flyweight Pattern in Java

The Flyweight pattern's effectiveness depends heavily on how and where it's used. Apply the Flyweight pattern when all the following are true:

* The Flyweight pattern is particularly effective in Java applications that use a large number of objects.
* When storage costs are high due to the quantity of objects, Flyweight helps by sharing intrinsic data and managing extrinsic state separately.
* Most of the object state can be made extrinsic.
* Many groups of objects may be replaced by relatively few shared objects once the extrinsic state is removed.
* The application doesn't depend on object identity. Since flyweight objects may be shared, identity tests will return true for conceptually distinct objects.

## Real-World Applications of Flyweight Pattern in Java

* [java.lang.Integer#valueOf(int)](http://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#valueOf%28int%29) and similarly for Byte, Character and other wrapped types.
* Java’s String class utilizes the Flyweight pattern to manage string literals efficiently.
* GUI applications often use Flyweight for sharing objects like fonts or graphical components, thereby conserving memory and improving performance.

## Benefits and Trade-offs of Flyweight Pattern

Benefits:

* Reduces the number of instances of an object, conserving memory.
* Centralizes state management, reducing the risk of inconsistent state.

Trade-offs:

* Increases complexity by adding the management layer for shared objects.
* Potential overhead in accessing shared objects if not well implemented.

## Related Java Design Patterns

* [Composite](https://java-design-patterns.com/patterns/composite/): Often combined with Flyweight when the composites are shareable. Both are used to manage hierarchies and structures of objects.
* [State](https://java-design-patterns.com/patterns/state/): Can be used to manage state in a shared Flyweight object, distinguishing internal state (invariant) from external state (context-specific).

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
