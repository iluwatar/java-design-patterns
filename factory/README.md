---
title: "Factory Pattern in Java: Streamlining Object Creation"
shortTitle: Factory
description: "Learn the Factory Design Pattern in Java with detailed examples and explanations. Understand how to create flexible and scalable code using the Factory Pattern. Ideal for developers looking to improve their object-oriented design skills."
category: Creational
language: en
tag:
  - Abstraction
  - Encapsulation
  - Gang of Four
  - Instantiation
  - Polymorphism
---

## Intent of Factory Design Pattern

The Factory Design Pattern in Java is a creational pattern that defines an interface for creating an object but allows subclasses to alter the type of objects that will be created. This pattern promotes flexibility and scalability in your codebase.

## Detailed Explanation of Factory Pattern with Real-World Examples

Real-world example

> Imagine a scenario in a bakery where different types of cakes are made using a Factory Design Pattern. The bakery's `CakeFactory` handles the creation process, allowing easy addition of new cake types without altering the core cake-making process. The `CakeFactory` can produce various types of cakes such as chocolate cake, vanilla cake, and strawberry cake. Instead of the bakery staff manually selecting ingredients and following specific recipes for each type of cake, they use the `CakeFactory` to handle the process. The customer simply requests a cake type, and the `CakeFactory` determines the appropriate ingredients and recipe to use, then creates the specific type of cake. This setup allows the bakery to easily add new cake types without modifying the core cake-making process, promoting flexibility and scalability.

Wikipedia says

> Factory is an object for creating other objects – formally a factory is a function or method that returns objects of a varying prototype or class.

## Programmatic Example of Factory Pattern in Java

Imagine an alchemist who is about to manufacture coins. The alchemist must be able to create both gold and copper coins and switching between them must be possible without modifying the existing source code. The factory pattern makes it possible by providing a static construction method which can be called with relevant parameters.

In Java, you can implement the Factory Pattern by defining an interface `Coin` and its implementations `GoldCoin` and `CopperCoin`. The `CoinFactory` class provides a static method `getCoin` to create coin objects based on the type.

```java
public interface Coin {
  String getDescription();
}
```

```java
public class GoldCoin implements Coin {

  static final String DESCRIPTION = "This is a gold coin.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
```

```java
public class CopperCoin implements Coin {
   
  static final String DESCRIPTION = "This is a copper coin.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
```

Enumeration below represents types of coins that we support (`GoldCoin` and `CopperCoin`).

```java
@RequiredArgsConstructor
@Getter
public enum CoinType {

  COPPER(CopperCoin::new),
  GOLD(GoldCoin::new);

  private final Supplier<Coin> constructor;
}
```

Then we have the static method `getCoin` to create coin objects encapsulated in the factory class `CoinFactory`.

```java
public class CoinFactory {

  public static Coin getCoin(CoinType type) {
    return type.getConstructor().get();
  }
}
```

Now, in the client code, we can generate various types of coins using the factory class.

```java
public static void main(String[] args) {
    LOGGER.info("The alchemist begins his work.");
    var coin1 = CoinFactory.getCoin(CoinType.COPPER);
    var coin2 = CoinFactory.getCoin(CoinType.GOLD);
    LOGGER.info(coin1.getDescription());
    LOGGER.info(coin2.getDescription());
}
```

Program output:

```
06:19:53.530 [main] INFO com.iluwatar.factory.App -- The alchemist begins his work.
06:19:53.533 [main] INFO com.iluwatar.factory.App -- This is a copper coin.
06:19:53.533 [main] INFO com.iluwatar.factory.App -- This is a gold coin.
```

## When to Use the Factory Pattern in Java

* Use the Factory Design Pattern in Java when the class does not know beforehand the exact types and dependencies of the objects it needs to create.
* When a method returns one of several possible classes that share a common super class and wants to encapsulate the logic of which object to create.
* The pattern is commonly used when designing frameworks or libraries to give the best flexibility and isolation from concrete class types.

## Real-World Applications of Factory Pattern in Java

* [java.util.Calendar#getInstance()](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
* [java.util.ResourceBundle#getBundle()](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
* [java.text.NumberFormat#getInstance()](https://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
* [java.nio.charset.Charset#forName()](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
* [java.net.URLStreamHandlerFactory#createURLStreamHandler(String)](https://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html) (returns different singleton objects, depending on a protocol)
* [java.util.EnumSet#of()](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of(E))
* [javax.xml.bind.JAXBContext#createMarshaller()](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--) and other similar methods.
* JavaFX uses Factory patterns for creating various UI controls tailored to the specifics of the user's environment.

## Benefits and Trade-offs of Factory Pattern

Benefits:

* Implementing the Factory Pattern in your Java application reduces coupling between the implementation and the classes it uses.
* Supports the [Open/Closed Principle](https://java-design-patterns.com/principles/#open-closed-principle), as the system can introduce new types without changing existing code.

Trade-offs:

* The code can become more complicated due to the introduction of multiple additional classes.
* Overuse can make the code less readable if the underlying complexity of the object creation is low or unnecessary.

## Related Java Design Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Can be considered a kind of Factory that works with groups of products.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Often used in conjunction with Factory to ensure that a class has only one instance.
* [Builder](https://java-design-patterns.com/patterns/builder/): Separates the construction of a complex object from its representation, similar to how factories manage instantiation.
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/): Is a factory of immutable content with separated builder and factory interfaces.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0Rk5y)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/3UpTLrG)
