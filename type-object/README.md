---
title: "Type Object Pattern in Java: Enhancing Flexibility with Dynamic Class Definitions"
shortTitle: Type Object
description: "Discover how the Type Object Pattern in Java allows for dynamic and flexible class creation without altering existing code. Ideal for developers looking to understand and apply this powerful design pattern in real-world Java applications."
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

## Intent of Type Object Design Pattern

Allow creation of flexible and extensible sets of related types.

## Detailed Explanation of Type Object Pattern with Real-World Examples

Real-world example

> An analogous real-world example of the Type Object pattern can be seen in a role-playing game (RPG) character customization system. In such a game, players can choose from various character classes like Warrior, Mage, and Archer, each with its unique set of abilities and attributes. The Type Object pattern allows the game to define these character classes and their behaviors dynamically. Instead of hardcoding the details of each class, the game uses a flexible system where new character types can be added or existing ones modified without changing the underlying game logic. This extensibility lets the developers introduce new character classes through updates or expansions, keeping the game fresh and engaging for players.

In plain words

> Explore how the Java Type Object pattern enables dynamic creation and management of flexible and extensible sets of related classes, ideal for Java developers seeking modularity without modifying existing codebase.

gameprogrammingpatterns.com says

> Define a type object class and a typed object class. Each type object instance represents a different logical type. Each typed object stores a reference to the type object that describes its type.

## Programmatic Example of Type Object Pattern in Java

The Type Object pattern is a design pattern that allows for the creation of flexible and reusable objects by creating a class with a field that represents the 'type' of the object. This design pattern proves invaluable for scenarios where anticipated Java types are undefined upfront, or when modifications or additions are required, ensuring efficient Java development without frequent recompilations.

In the provided code, the Type Object pattern is implemented in a mini candy-crush game. The game has many different candies, which may change over time as the game is upgraded.

Let's break down the key components of this implementation:

1. **Candy Class**: This class represents the 'type' object in this pattern. Each `Candy` has a `name`, `parent`, `points`, and `type`. The `type` is an enum that can be either `CRUSHABLE_CANDY` or `REWARD_FRUIT`.

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
      var givenTime = 50; //50ms
      var toWin = 500; //points
      var pointsWon = 0;
      var numOfRows = 3;
      var start = System.currentTimeMillis();
      var end = System.currentTimeMillis();
      var round = 0;
      while (pointsWon < toWin && end - start < givenTime) {
          round++;
          var pool = new CellPool(numOfRows * numOfRows + 5);
          var cg = new CandyGame(numOfRows, pool);
          if (round > 1) {
              LOGGER.info("Refreshing..");
          } else {
              LOGGER.info("Starting game..");
          }
          cg.printGameStatus();
          end = System.currentTimeMillis();
          cg.round((int) (end - start), givenTime);
          pointsWon += cg.totalPoints;
          end = System.currentTimeMillis();
      }
      LOGGER.info("Game Over");
      if (pointsWon >= toWin) {
          LOGGER.info("" + pointsWon);
          LOGGER.info("You win!!");
      } else {
          LOGGER.info("" + pointsWon);
          LOGGER.info("Sorry, you lose!");
      }
  }
}
```

Let's break down what happens in `App` class.

1. The `main` method is the entry point of the application. It starts by initializing several variables:
  - `givenTime` is set to 50 milliseconds. This is the time limit for the game.
  - `toWin` is set to 500 points. This is the target score to win the game.
  - `pointsWon` is initialized to 0. This variable keeps track of the total points won so far.
  - `numOfRows` is set to 3. This is the number of rows in the game grid.
  - `start` and `end` are both set to the current system time in milliseconds. These variables are used to track the elapsed time.
  - `round` is initialized to 0. This variable keeps track of the current round number.

2. The game enters a loop that continues until either the player has won enough points (`pointsWon >= toWin`) or the time limit has been reached (`end - start < givenTime`).

3. At the start of each round, a new `CellPool` and `CandyGame` are created. The `CellPool` is initialized with a size based on the number of cells in the game grid (`numOfRows * numOfRows + 5`). The `CandyGame` is initialized with the number of rows and the `CellPool`.

4. If it's not the first round, a message "Refreshing.." is logged. If it is the first round, a message "Starting game.." is logged.

5. The current game status is printed by calling `cg.printGameStatus()`.

6. The `end` time is updated to the current system time.

7. The game round is played by calling `cg.round((int) (end - start), givenTime)`. The elapsed time and the time limit are passed as arguments.

8. The points won in the round are added to the total points.

9. The `end` time is updated again to the current system time.

10. After the loop, a "Game Over" message is logged.

11. If the total points won is greater than or equal to the target score, a winning message is logged. Otherwise, a losing message is logged.

This is a simplified version of a game similar to Candy Crush, where the player tries to score as many points as possible within a given time limit. The game is played in rounds, and the player's score and the elapsed time are tracked throughout the game.

Console output:

```
14:36:14.453 [main] INFO com.iluwatar.typeobject.App -- Starting game..
14:36:14.455 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame --        cherry       |
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame --        mango        |
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame --      orange gum     |
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame --      orange gum     |
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame --   purple popsicle   |
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame --   purple popsicle   |
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame --   green jellybean   |
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame --   green jellybean   |
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame --        mango        |
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.458 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.459 [main] INFO com.iluwatar.typeobject.CandyGame -- +20 points!
...
...
...
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --   green jellybean   |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --        cherry       |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --   green jellybean   |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --   green jellybean   |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --   green jellybean   |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --        mango        |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --      orange gum     |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --   purple popsicle   |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --      orange gum     |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- +20 points!
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --      orange gum     |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --        cherry       |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --   green jellybean   |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --   purple popsicle   |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --   green jellybean   |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --        mango        |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --      orange gum     |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --   purple popsicle   |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame --      orange gum     |
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.CandyGame -- 
14:36:14.465 [main] INFO com.iluwatar.typeobject.App -- Game Over
14:36:14.465 [main] INFO com.iluwatar.typeobject.App -- 660
14:36:14.465 [main] INFO com.iluwatar.typeobject.App -- You win!!
```

In this implementation, the Type Object pattern allows for the flexible creation of `Candy` objects. The type of each candy is determined at runtime by parsing a JSON file, which makes it easy to add, modify, or remove candy types without having to recompile the code.

## When to Use the Type Object Pattern in Java

This pattern can be used when:

* Use when you need to create an extensible set of related classes without modifying existing code.
* Ideal for scenarios where types and their behaviors need to be defined at runtime or in a flexible manner.
* Suitable for situations where the number of types is large and may change over time.
* The difference between the different 'types' of objects is the data, not the behaviour.

## Type Object Pattern Java Tutorials

* [Types as Objects Pattern (Jon Pearce)](http://www.cs.sjsu.edu/~pearce/modules/patterns/analysis/top.htm)

## Real-World Applications of Type Object Pattern in Java

* Java Collections Framework: Utilizing various collection types like List, Set, and Map.
* Graphics Libraries: Defining different shapes with specific properties and behaviors.
* Game Development: Creating different types of characters or items with unique attributes and behaviors.

## Benefits and Trade-offs of Type Object Pattern

Benefits:

* Increases flexibility and extensibility of the code.
* Simplifies the addition of new types without modifying existing code.
* Enhances code readability by organizing related behaviors and properties.

Trade-offs:

* Can increase complexity if not managed properly.
* May lead to performance overhead due to dynamic type checking and handling.

## Related Java Design Patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Often used in conjunction with Type Object to create instances of the types.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Similar in that it defines a family of algorithms or behaviors, but focuses more on interchangeable behaviors.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Can be used to create new instances by copying existing ones, supporting dynamic and flexible type creation.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Type Object (Game Programming Patterns)](http://gameprogrammingpatterns.com/type-object.html)
