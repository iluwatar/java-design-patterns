---
title: Type Object
category: Creational
language: en
tag:
    - Abstraction
    - Code simplification
    - Data processing
    - Game programming
    - Extensibility
    - Instantiation
    - Object composition
    - Polymorphism
---

## Also known as

* Type Descriptor
* Type Safe Enumeration

## Intent

Allow creation of flexible and extensible sets of related types.

## Explanation

Real-world example

> An analogous real-world example of the Type Object pattern can be seen in a role-playing game (RPG) character customization system. In such a game, players can choose from various character classes like Warrior, Mage, and Archer, each with its unique set of abilities and attributes. The Type Object pattern allows the game to define these character classes and their behaviors dynamically. Instead of hardcoding the details of each class, the game uses a flexible system where new character types can be added or existing ones modified without changing the underlying game logic. This extensibility lets the developers introduce new character classes through updates or expansions, keeping the game fresh and engaging for players.

In plain words

> The Type Object pattern allows for the creation and management of flexible and extensible sets of related types dynamically, without modifying existing code.

Gameprogrammingpatterns.com says

> Define a type object class and a typed object class. Each type object instance represents a different logical type. Each typed object stores a reference to the type object that describes its type.

**Programmatic example**

The Type Object pattern is a design pattern that allows for the creation of flexible and reusable objects by creating a class with a field that represents the 'type' of the object. This pattern is useful when the types needed are not known upfront, or when there is a need to modify or add new types conveniently without recompiling repeatedly.

In the provided code, the Type Object pattern is implemented in a mini candy-crush game. The game has many different candies, which may change over time as the game is upgraded.

Let's break down the key components of this implementation:

1. **Candy Class**: This class represents the 'type' object in this pattern. Each candy has a name, parent, points, and type. The type is an enum that can be either `CRUSHABLE_CANDY` or `REWARD_FRUIT`.

```java
class Candy {
  String name;
  Candy parent;
  String parentName;
  int points;
  Type type;

  Candy(String name, String parentName, Type type, int points) {
    // constructor implementation
  }

  int getPoints() {
    // implementation
  }

  Type getType() {
    // implementation
  }

  void setPoints(int a) {
    // implementation
  }
}
```

2. **JsonParser Class**: This class is responsible for parsing the JSON file that contains the details about the candies. It creates a `Candy` object for each candy in the JSON file and stores them in a `Hashtable`.

```java
public class JsonParser {
  Hashtable<String, Candy> candies;

  JsonParser() {
    this.candies = new Hashtable<>();
  }

  void parse() throws JsonParseException {
    // implementation
  }

  void setParentAndPoints() {
    // implementation
  }
}
```

3. **Cell Class**: This class represents a cell in the game matrix. Each cell contains a candy that can be crushed. It also contains information on how crushing can be done, how the matrix is to be reconfigured, and how points are to be gained.

```java
class Cell {
  Candy candy;
  int positionX;
  int positionY;

  Cell() {
    // implementation
  }

  Cell(Candy candy, int positionX, int positionY) {
    // implementation
  }

  void crush(CellPool pool, Cell[][] cellMatrix) {
    // implementation
  }

  // other methods...
}
```

4. **CandyGame Class**: This class contains the rules for the continuation of the game.

```java
class CandyGame {
  Cell[][] cells;
  CellPool pool;
  int totalPoints;

  CandyGame(int num, CellPool pool) {
    // implementation
  }

  boolean continueRound() {
    // implementation
  }

  // other methods...
}
```

5. **CellPool Class**: This class is a pool that reuses the candy cells that have been crushed instead of creating new ones repeatedly.

```java
class CellPool {
  int pointer;
  List<Cell> pool;
  Candy[] randomCode;

  CellPool(int num) {
    // implementation
  }

  void addNewCell(Cell c) {
    // implementation
  }

  Candy[] assignRandomCandytypes() {
    // implementation
  }

  Cell getNewCell() {
    // implementation
  }
}
```

6. **App Class**: This class contains the main method that starts the game.

```java
@Slf4j
public class App {
  public static void main(String[] args) {
    // implementation
  }
}
```

In this implementation, the Type Object pattern allows for the flexible creation of `Candy` objects. The type of each candy is determined at runtime by parsing a JSON file, which makes it easy to add, modify, or remove candy types without having to recompile the code.

## Class diagram

![Type Object](./etc/typeobjectpattern.urm.png "Type Object")

## Applicability

This pattern can be used when:

* Use when you need to create an extensible set of related classes without modifying existing code.
* Ideal for scenarios where types and their behaviors need to be defined at runtime or in a flexible manner.
* Suitable for situations where the number of types is large and may change over time.
* The difference between the different 'types' of objects is the data, not the behaviour.

## Known uses

* Java Collections Framework: Utilizing various collection types like List, Set, and Map.
* Graphics Libraries: Defining different shapes with specific properties and behaviors.
* Game Development: Creating different types of characters or items with unique attributes and behaviors.

## Consequences

Benefits:

* Increases flexibility and extensibility of the code.
* Simplifies the addition of new types without modifying existing code.
* Enhances code readability by organizing related behaviors and properties.

Trade-offs:

* Can increase complexity if not managed properly.
* May lead to performance overhead due to dynamic type checking and handling.

## Related patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Often used in conjunction with Type Object to create instances of the types.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Similar in that it defines a family of algorithms or behaviors, but focuses more on interchangeable behaviors.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Can be used to create new instances by copying existing ones, supporting dynamic and flexible type creation.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Game Programming Patterns - Type Object](http://gameprogrammingpatterns.com/type-object.html)
* [Types as Objects Pattern - Jon Pearce](http://www.cs.sjsu.edu/~pearce/modules/patterns/analysis/top.htm)
