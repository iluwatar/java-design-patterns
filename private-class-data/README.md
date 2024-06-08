---
title: "Private Class Data Pattern in Java: Safeguarding Data Integrity with Encapsulation"
shortTitle: Private Class Data
description: "Explore the Private Class Data pattern in Java, ideal for enhancing data security and integrity in object-oriented programming. Learn how it prevents unintended data manipulation with encapsulation."
category: Structural
language: en
tag:
  - Abstraction
  - Encapsulation
  - Security
---

## Also known as

* Data Hiding
* Encapsulation

## Intent of Private Class Data Design Pattern

The Private Class Data design pattern in Java focuses on restricting access to the internal state of an object, enhancing security and reducing risks of data corruption through controlled method access.

## Detailed Explanation of Private Class Data Pattern with Real-World Examples

Real-world example

> A real-world analogy for the Private Class Data pattern is the way a bank protects customer account information. Just like a class with private fields, a bank keeps sensitive data such as account balances, transaction history, and personal information private and only accessible through specific methods. Customers interact with their accounts through well-defined interfaces such as ATMs or online banking portals, which enforce security and validation rules, ensuring that unauthorized access or modifications are prevented. This controlled access mechanism ensures the integrity and security of the data, similar to how Private Class Data protects and manages access to class attributes in software design.

In plain words

> Private class data pattern prevents manipulation of data that is meant to be immutable by separating the data from the methods that use it into a class that maintains the data state.

Wikipedia says

> Private class data is a design pattern in computer programming used to encapsulate class attributes and their manipulation.

## Programmatic Example of Private Class Data Pattern in Java

Imagine you are cooking a stew for your family dinner. You want to stop your family members from tasting the stew while you're still preparing it. If they do, there might not be enough stew left for dinner.

First, we have a `Stew` class where its data is not protected by private class data, making the stew's ingredient mutable to class methods. 

```java
@Slf4j
public class Stew {
    
  private int numPotatoes;
  private int numCarrots;
  private int numMeat;
  private int numPeppers;
  
  public Stew(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {
    this.numPotatoes = numPotatoes;
    this.numCarrots = numCarrots;
    this.numMeat = numMeat;
    this.numPeppers = numPeppers;
  }
  
  public void mix() {
    LOGGER.info("Mixing the stew we find: {} potatoes, {} carrots, {} meat and {} peppers",
        numPotatoes, numCarrots, numMeat, numPeppers);
  }
  
  public void taste() {
    LOGGER.info("Tasting the stew");
    if (numPotatoes > 0) {
      numPotatoes--;
    }
    if (numCarrots > 0) {
      numCarrots--;
    }
    if (numMeat > 0) {
      numMeat--;
    }
    if (numPeppers > 0) {
      numPeppers--;
    }
  }
}
```

Now, we have `ImmutableStew` class, where its data is protected by `StewData` record. The methods in `ImmutableStew` are unable to manipulate the data of the `StewData` class.

```java
public record StewData(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {}

@Slf4j
public class ImmutableStew {
    
  private final StewData data;
  
  public ImmutableStew(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {
    data = new StewData(numPotatoes, numCarrots, numMeat, numPeppers);
  }
  
  public void mix() {
    LOGGER
        .info("Mixing the immutable stew we find: {} potatoes, {} carrots, {} meat and {} peppers",
            data.getNumPotatoes(), data.getNumCarrots(), data.getNumMeat(), data.getNumPeppers());
  }
}
```

Let's try creating an instance of each class and call their methods:

```java
public static void main(String[] args) {
    // stew is mutable
    var stew = new Stew(1, 2, 3, 4);
    stew.mix();
    stew.taste();
    stew.mix();

    // immutable stew protected with Private Class Data pattern
    var immutableStew = new ImmutableStew(2, 4, 3, 6);
    immutableStew.mix();
}
```

Program output:

```
08:00:08.210 [main] INFO com.iluwatar.privateclassdata.Stew -- Mixing the stew we find: 1 potatoes, 2 carrots, 3 meat and 4 peppers
08:00:08.212 [main] INFO com.iluwatar.privateclassdata.Stew -- Tasting the stew
08:00:08.212 [main] INFO com.iluwatar.privateclassdata.Stew -- Mixing the stew we find: 0 potatoes, 1 carrots, 2 meat and 3 peppers
08:00:08.213 [main] INFO com.iluwatar.privateclassdata.ImmutableStew -- Mixing the immutable stew we find: 2 potatoes, 4 carrots, 3 meat and 6 peppers
```

## When to Use the Private Class Data Pattern in Java

Use the Private Class Data pattern when

* When you want to protect the integrity of an object’s state.
* When you need to limit the visibility of the internal data of an object to prevent unintended modification.
* In scenarios where multiple classes need to share access to some common data without exposing it directly.

## Real-World Applications of Private Class Data Pattern in Java

* Java Beans, where properties are accessed via getters and setters.
* In many Java libraries where the internal state is hidden from the user to ensure consistency and security.
* Enterprise applications where sensitive data needs to be protected from direct access.

## Benefits and Trade-offs of Private Class Data Pattern

Benefits:

* Enhanced Security: Reduces the risk of unintended data corruption by encapsulating the data.
* Ease of Maintenance: Changes to the internal representation of data do not affect external code.
* Improved Abstraction: Users interact with a simplified interface without worrying about the complexities of data management.

Trade-offs:

* Performance Overhead: Additional method calls (getters/setters) can introduce slight performance overhead.
* Complexity: May increase the complexity of the class design due to the additional layer of methods for data access.

## Related Java Design Patterns

* [Proxy](https://java-design-patterns.com/patterns/proxy/): Both patterns restrict access to the underlying object but Proxy controls access to the object itself, while Private Class Data controls access to the data.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Ensures that a class has only one instance and provides a global point of access to it; often used to manage shared data with controlled access.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Adds behavior to an object without altering its structure; can be combined with Private Class Data to manage additional state privately.

## References and Credits

* [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/3UJTZJk)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
