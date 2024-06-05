---
title: Object Mother
category: Testing
language: en
tag:
    - Code simplification
    - Instantiation
    - Isolation
    - Testing
---

## Also known as

* Object Builder
* Test Data Builder

## Intent

The Object Mother pattern simplifies the creation of objects for testing purposes, ensuring that test cases are clear and maintainable by centralizing the logic needed to instantiate objects in a consistent state.

## Explanation

Real-world example

> Imagine you're developing a Java application for a travel agency. In your system, there are different types of travelers, such as tourists, business travelers, and travel agents, each with specific attributes and behaviors. To perform thorough testing, you need to create and manipulate these traveler objects in various contexts. The Object Mother Pattern can help you generate consistent and predefined traveler objects for testing purposes, ensuring that your tests are based on known, reliable data.

In plain words

> The Object Mother Pattern is a design pattern used in Java to simplify the creation of objects with specific configurations, especially for testing. Instead of manually constructing objects with varying properties for each test case, you create a dedicated "Object Mother" class or method that produces these objects with predefined settings. This ensures that you have consistent and predictable test data, making your tests more reliable and easier to manage.

wiki.c2.com says

> Object Mother starts with the factory pattern, by delivering prefabricated test-ready objects via a simple method call. It moves beyond the realm of the factory by
> 1. facilitating the customization of created objects,
> 2. providing methods to update the objects during the tests, and
> 3. if necessary, deleting the object from the database at the completion of the test.

**Programmatic example**

The Object Mother is a design pattern that aims to provide an easy way to create objects for testing purposes. It encapsulates the logic for building instances of complex objects in one place, making it easier to maintain and reuse across multiple tests.

First, we have the `King` class. This class represents a king with certain behaviors and states. The king can be drunk or sober, happy or unhappy. The king can also flirt with a queen, which may affect his happiness.

```java
public class King implements Royalty {
  boolean isDrunk = false;
  boolean isHappy = false;

  @Override
  public void makeDrunk() {
    isDrunk = true;
  }

  @Override
  public void makeSober() {
    isDrunk = false;
  }

  @Override
  public void makeHappy() {
    isHappy = true;
  }

  @Override
  public void makeUnhappy() {
    isHappy = false;
  }

  public boolean isHappy() {
    return isHappy;
  }

  public void flirt(Queen queen) {
    var flirtStatus = queen.getFlirted(this);
    if (!flirtStatus) {
      this.makeUnhappy();
    } else {
      this.makeHappy();
    }
  }
}
```

The `RoyaltyObjectMother` class is where the Object Mother pattern is implemented. This class provides static methods to create different types of `King` and `Queen` objects. These methods encapsulate the logic for creating these objects, making it easier to create them in a consistent way across multiple tests.

```java
class RoyaltyObjectMother {

  static King createDrunkKing() {
    var king = new King();
    king.makeDrunk();
    return king;
  }

  static King createHappyKing() {
    var king = new King();
    king.makeHappy();
    return king;
  }

  // Other methods to create different types of King and Queen objects...
}
```

In the `RoyaltyObjectMotherTest` class, we can see how the Object Mother pattern is used to create objects for testing. The `RoyaltyObjectMother` class is used to create `King` and `Queen` objects in different states, which are then used in the tests.

```java
class RoyaltyObjectMotherTest {

  @Test
  void unsuccessfulKingFlirt() {
    var soberUnhappyKing = RoyaltyObjectMother.createSoberUnhappyKing();
    var flirtyQueen = RoyaltyObjectMother.createFlirtyQueen();
    soberUnhappyKing.flirt(flirtyQueen);
    assertFalse(soberUnhappyKing.isHappy());
  }

  // Other tests...
}
```

In this way, the Object Mother pattern simplifies the creation of objects for testing, making the tests easier to read and maintain.

## Applicability

Use the Object Mother pattern when

* Creating complex objects with numerous fields for unit tests.
* You need to reuse a standard set of objects across multiple tests.
* Test setup is becoming cumbersome and repetitive.

## Tutorials

* [What is an ObjectMother? (Stack Overflow)](http://stackoverflow.com/questions/923319/what-is-an-objectmother)
* [Object Mother (c2wiki)](http://c2.com/cgi/wiki?ObjectMother)
* [Test Data Builders: an alternative to the Object Mother pattern (Nat Pryce)](http://www.natpryce.com/articles/000714.html)

## Known Uses

* In unit testing frameworks to create test fixtures.
* In enterprise applications to generate standard domain objects required across multiple test cases.
* In open-source projects like Apache Commons and Spring Framework for test object creation.

## Consequences

Benefits:

* Code Simplification: Reduces boilerplate code in tests, making tests more readable and easier to maintain.
* Isolation: Ensures test data setup is isolated from test logic, enhancing clarity.
* Consistency: Provides a consistent way to create objects, reducing the likelihood of errors in test setup.

Trade-offs:

* Maintenance: Requires maintaining the Object Mother class itself, which can grow complex over time.
* Overhead: May introduce additional layers of abstraction that could complicate understanding for new developers.

## Related Patterns

* [Builder](https://java-design-patterns.com/patterns/builder/): Both patterns deal with object creation. The Object Mother is often simpler and used specifically in a testing context, whereas the Builder Pattern is more general-purpose.
* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Similar in the sense of centralizing object creation logic. The Object Mother is specifically aimed at tests, while Factory Method is used more broadly in application code.

## Credits

* [Growing Object-Oriented Software, Guided by Tests](https://amzn.to/4dGfIuk)
* [xUnit Test Patterns: Refactoring Test Code](https://amzn.to/4dHGDpm)
