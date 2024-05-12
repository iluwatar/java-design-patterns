---
title: Property
category: Creational
language: en
tag:
 - Instantiation
---

## Intent
Create hierarchy of objects and new objects using already existing
objects as parents.

## Explanation

Real-world example

> In the mystical land of "Elandria", adventurers can harness the power of ancient relics to customize their abilities. Each relic represents a unique property or skill. As adventurers explore, they discover and integrate new relics, dynamically enhancing their skills based on the relics they possess.

> Consider a modern software used in designing and customizing smartphones. Designers can choose from a variety of components such as processor type, camera specs, battery capacity, and more. Each component represents a property of the smartphone. As technology evolves and new components become available, designers can seamlessly add or replace properties to create a unique smartphone configuration without redefining the core design structure.

In plain words

> Define and manage a dynamic set of properties for an object, allowing customization without altering its structure.

**Programmatic Example**
```java
import java.util.HashMap;
import java.util.Map;

// Enumeration for possible properties or statistics a character can have
enum Stats {
    AGILITY, ATTACK_POWER, ARMOR, INTELLECT, SPIRIT, FURY, RAGE;
}

// Enumeration for different types or classes of characters
enum Type {
    WARRIOR, MAGE, ROGUE;
}

// Interface defining prototype operations on a character
interface Prototype {
    Integer get(Stats stat);
    boolean has(Stats stat);
    void set(Stats stat, Integer value);
    void remove(Stats stat);
}

// Implementation of the Character class
class Character implements Prototype {
    private String name;
    private Type type;
    private Map<Stats, Integer> properties = new HashMap<>();

    public Character() {}

    public Character(Type type, Prototype prototype) {
        this.type = type;
        for (Stats stat : Stats.values()) {
            if (prototype.has(stat)) {
                this.set(stat, prototype.get(stat));
            }
        }
    }

    public Character(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public Integer get(Stats stat) {
        return properties.get(stat);
    }

    @Override
    public boolean has(Stats stat) {
        return properties.containsKey(stat);
    }

    @Override
    public void set(Stats stat, Integer value) {
        properties.put(stat, value);
    }

    @Override
    public void remove(Stats stat) {
        properties.remove(stat);
    }

    @Override
    public String toString() {
        return "Character{name='" + name + "', type=" + type + ", properties=" + properties + '}';
    }
}

// Main class to demonstrate the pattern
public class PropertyPatternDemo {
    public static void main(String[] args) {
        // Create a prototype character
        Character prototypeWarrior = new Character("Proto Warrior", Type.WARRIOR);
        prototypeWarrior.set(Stats.ATTACK_POWER, 10);
        prototypeWarrior.set(Stats.ARMOR, 15);

        // Create a new character using the prototype
        Character newWarrior = new Character(Type.WARRIOR, prototypeWarrior);
        newWarrior.set(Stats.AGILITY, 5);

        System.out.println(prototypeWarrior);
        System.out.println(newWarrior);
    }
}
```

Program output:

```
Character{name='Proto Warrior', type=WARRIOR, properties={ARMOR=15, ATTACK_POWER=10}}
Character{name='null', type=WARRIOR, properties={ARMOR=15, AGILITY=5, ATTACK_POWER=10}}
```

## Class diagram
![alt text](./etc/property.png "Property")

## Applicability
Use the Property pattern when

* When you like to have objects with dynamic set of fields and prototype inheritance

## Real world examples

* [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Inheritance_and_the_prototype_chain) prototype inheritance
