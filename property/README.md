---
title: "Property Pattern in Java: Enhancing Flexibility with Dynamic Attribute Management"
shortTitle: Property
description: "Explore how the Property design pattern facilitates dynamic property management in Java objects, enabling runtime modifications without altering class structure. Ideal for developers looking to enhance flexibility and maintainability in their code."
category: Behavioral
language: en
tag:
  - Abstraction
  - Encapsulation
  - Interface
  - Object composition
  - Polymorphism
---

## Also known as

* Dynamic Properties
* Property Bag

## Intent of Property Design Pattern

The Property design pattern in Java allows dynamic addition, removal, or modification of object properties, offering a flexible solution for developers to customize object attributes at runtime.

## Detailed Explanation of Property Pattern with Real-World Examples

Real-world example

> Imagine a custom burger ordering system at a restaurant. Each burger starts with a basic configuration (bun, patty), but customers can add or remove various ingredients (cheese, lettuce, tomato, sauces) as they wish. The restaurant's ordering system uses the Property design pattern to handle this flexibility. Each burger object dynamically updates its list of properties (ingredients) based on customer choices, allowing for a wide variety of custom burgers without needing a fixed class structure for every possible combination. This ensures the system can adapt to any new ingredient without altering the core burger class.

In plain words

> Define and manage a dynamic set of properties for an object, allowing customization without altering its structure.

## Programmatic Example of Property Pattern in Java

The Property design pattern, also known as Prototype inheritance, is a pattern that allows objects to be created from other objects, forming object hierarchies. This pattern is particularly useful when you want to create a new object that is a slight variation of an existing object.

In the given code, the Property pattern is used to create different types of characters, each with their own unique properties.

```java
// The Character class represents a character in a game. It has a type and a set of properties.
public class Character {
  private Type type;
  private Map<Stats, Integer> properties;

  // The Character can be created with a type and a prototype. The new character will have the same properties as the prototype.
  public Character(Type type, Character prototype) {
    this.type = type;
    this.properties = new HashMap<>(prototype.properties);
  }

  // Properties can be added or modified using the set method.
  public void set(Stats stat, int value) {
    properties.put(stat, value);
  }

  // Properties can be removed using the remove method.
  public void remove(Stats stat) {
    properties.remove(stat);
  }

  // The has method checks if a property is present.
  public boolean has(Stats stat) {
    return properties.containsKey(stat);
  }

  // The get method retrieves the value of a property.
  public Integer get(Stats stat) {
    return properties.get(stat);
  }
}

// The Stats enum represents the different properties a character can have.
public enum Stats {
  STRENGTH, AGILITY, ARMOR, ATTACK_POWER, INTELLECT, SPIRIT, RAGE, ENERGY
}

// The Type enum represents the different types of characters.
public enum Type {
  MAGE, WARRIOR, ROGUE
}
```

In the `main` method, we create a prototype character and then create different types of characters based on the prototype:

```java
public static void main(String[] args) {
  // Create a prototype character with default properties
  var charProto = new Character();
  charProto.set(Stats.STRENGTH, 10);
  charProto.set(Stats.AGILITY, 10);
  charProto.set(Stats.ARMOR, 10);
  charProto.set(Stats.ATTACK_POWER, 10);

  // Create a mage character based on the prototype and add mage-specific properties
  var mageProto = new Character(Type.MAGE, charProto);
  mageProto.set(Stats.INTELLECT, 15);
  mageProto.set(Stats.SPIRIT, 10);

  // Create a warrior character based on the prototype and add warrior-specific properties
  var warProto = new Character(Type.WARRIOR, charProto);
  warProto.set(Stats.RAGE, 15);
  warProto.set(Stats.ARMOR, 15); // boost default armor for warrior

  // Create a rogue character based on the prototype and add rogue-specific properties
  var rogueProto = new Character(Type.ROGUE, charProto);
  rogueProto.set(Stats.ENERGY, 15);
  rogueProto.set(Stats.AGILITY, 15); // boost default agility for rogue

  // Create specific characters based on the prototypes
  var mag = new Character("Player_1", mageProto);
  var warrior = new Character("Player_2", warProto);
  var rogue = new Character("Player_3", rogueProto);
}
```

Program output:

```
08:27:52.567 [main] INFO com.iluwatar.property.App -- Player: Player_1
Character type: MAGE
Stats:
  - AGILITY:10
  - STRENGTH:10
  - ATTACK_POWER:10
  - ARMOR:8
  - INTELLECT:15
  - SPIRIT:10

08:27:52.569 [main] INFO com.iluwatar.property.App -- Player: Player_2
Character type: WARRIOR
Stats:
  - AGILITY:10
  - STRENGTH:10
  - ATTACK_POWER:10
  - ARMOR:15
  - RAGE:15

08:27:52.569 [main] INFO com.iluwatar.property.App -- Player: Player_3
Character type: ROGUE
Stats:
  - AGILITY:15
  - STRENGTH:10
  - ATTACK_POWER:10
  - ARMOR:10
  - ENERGY:15

08:27:52.569 [main] INFO com.iluwatar.property.App -- Player: Player_4
Character type: ROGUE
Stats:
  - AGILITY:15
  - STRENGTH:10
  - ATTACK_POWER:12
  - ARMOR:10
  - ENERGY:15
```

This way, we can easily create new characters with different properties without having to create a new class for each type of character.

## When to Use the Property Pattern in Java

Use the Property pattern when

* When you need to manage a flexible set of properties without altering the class structure.
* When properties need to be added or removed dynamically at runtime.
* When different instances of a class need different properties.

## Real-World Applications of Property Pattern in Java

* Configurations in applications where different entities require different sets of configurable parameters.
* Game development where game entities (like characters or objects) need various attributes that can change during gameplay.
* User profile management systems where user profiles can have dynamic attributes.

## Benefits and Trade-offs of Property Pattern

Benefits:

Employing the Property design pattern enhances

* Flexibility: Allows for the dynamic addition, removal, and modification of properties.
* Decoupling: Reduces dependencies between classes and their properties.
* Ease of Use: Simplifies the management of properties in large systems.

Trade-offs:

* Performance Overhead: Dynamic property management can introduce runtime overhead.
* Complexity: May increase the complexity of the code, making it harder to maintain and understand.
* Type Safety: Reduces type safety since properties are often managed as generic key-value pairs.

## Related Java Design Patterns

* [Composite](https://java-design-patterns.com/patterns/composite/): Composite allows a tree structure of objects where each node can be a complex or simple object. Property pattern can be seen as a flattened version, managing properties without hierarchy.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Both patterns enhance an object's behavior, but the Property pattern focuses on adding properties dynamically, while the Decorator adds responsibilities.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Like the Property pattern, the Strategy pattern allows dynamic behavior changes, but Strategy is about changing the algorithm used by an object.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
