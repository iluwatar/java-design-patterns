---
title: Type-Object
category: Behavioral
language: en
tag:
 - Game programming
 - Extensibility
---

# Type-Object Pattern in Java

## Explanation

In Java, the Type-Object pattern is a design pattern that encapsulates type information in an object. This pattern is particularly useful when dealing with multiple objects of the same kind, and there is a need to add new types without altering existing code.

## Intent
As explained in the book Game Programming Patterns by Robert Nystrom, type object pattern helps in

> Allowing flexible creation of new “classes” by creating a single class, each instance of which represents a different type of object

## Real World Example
Let's consider a real-world example. Say, we are working on a game which has a hero and many monsters which are going to attack the hero. These monsters have certain attributes like attack, points etc. and come in different 'breeds' like zombie or ogres. The obvious answer is to have a base Monster class which has some fields and methods, which may be overriden by subclasses like the Zombie or Ogre class. But as we continue to build the game, there may be more and more breeds of monsters added and certain attributes may need to be changed in the existing monsters too. The OOP solution of inheriting from the base class would not be an efficient method in this case.
Using the type-object pattern, instead of creating many classes inheriting from a base class, we have 1 class with a field which represents the 'type' of object. This makes the code cleaner and object instantiation also becomes as easy as parsing a json file with the object properties.

## In Plain Words

The Type-Object pattern in Java is a method to encapsulate type-specific properties and behaviors within an object. This design pattern facilitates the addition of new types without necessitating changes to existing code, thereby enhancing codebase expansion and maintenance.

## Wikipedia Says

While there isn't a specific Wikipedia entry for the Type-Object pattern, it is a commonly used technique in object-oriented programming. This pattern assists in managing objects that share similar characteristics but have different values for those characteristics. It finds widespread use in game development, where numerous types of objects (like enemies) share common behavior but have different properties.

## Programmatic Example

Consider an example involving different types of enemies in a game. Each enemy type has distinct properties like speed, health, and damage.

```java
public class EnemyType {
    private String name;
    private int speed;
    private int health;
    private int damage;
    
    public EnemyType(String name, int speed, int health, int damage) {
        this.name = name;
        this.speed = speed;
        this.health = health;
        this.damage = damage;
    }

    // getters and setters
}

public class Enemy {
    private EnemyType type;

    // Encapsulating type information in an object
    public Enemy(EnemyType type) {
        this.type = type;
    }

    // other methods
}
```

In the above example, `EnemyType` encapsulates type-specific properties (name, speed, health, damage), and `Enemy` uses an instance of `EnemyType` to define its type. This way, you can add as many enemy types as you want without modifying the `Enemy` class.

## Applicability
This pattern can be used when:

* We don’t know what types we will need up front.
* We want to be able to modify or add new types without having to recompile or change code.
* Only difference between the different 'types' of objects is the data, not the behaviour.

## Another example with class diagram
![alt text](./etc/typeobjectpattern.urm.png "Type-Object pattern class diagram")

## Credits

* [Game Programming Patterns - Type Object](http://gameprogrammingpatterns.com/type-object.html)
* [Types as Objects Pattern](http://www.cs.sjsu.edu/~pearce/modules/patterns/analysis/top.htm)
