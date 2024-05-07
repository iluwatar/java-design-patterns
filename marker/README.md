---
title: Marker Interface
category: Structural
language: en
tag:
    - Encapsulation
    - Interface
    - Polymorphism
---

## Also known as

* Marker
* Tagging Interface

## Intent

The Marker Interface pattern is used to convey metadata about a class in a type-safe manner. Interfaces in Java that have no method declarations are known as marker interfaces. They are used to indicate that a class implementing such an interface possesses some special behavior or capability.

## Explanation

Real-world example

> Consider a scenario in a library system where certain books are rare and need special handling procedures, such as restricted check-outs or specific storage conditions. Analogous to the Marker Interface pattern, we could have a marker interface called `RareBook`. Books in the library catalog that implement this interface are flagged as requiring special treatment but don't necessarily have different methods from other books.
> 
> When a library staff member processes transactions or handles storage, the system checks if a book implements the `RareBook` interface. If it does, the system automatically enforces rules like "Do not allow check-outs for more than three days" or "Store in a temperature-controlled environment." This use of the marker interface effectively communicates special requirements without altering how books are generally managed, serving simply as a marker for special conditions.

In plain words

> The Marker Interface design pattern uses empty interfaces to signal or define certain properties and behaviors of objects in a type-safe way, without requiring specific method implementations.

Wikipedia says

> The marker interface pattern is a design pattern in computer science, used with languages that provide run-time type information about objects. It provides a means to associate metadata with a class where the language does not have explicit support for such metadata.
> 
> To use this pattern, a class implements a marker interface (also called tagging interface) which is an empty interface, and methods that interact with instances of that class test for the existence of the interface. Whereas a typical interface specifies functionality (in the form of method declarations) that an implementing class must support, a marker interface need not do so. The mere presence of such an interface indicates specific behavior on the part of the implementing class. Hybrid interfaces, which both act as markers and specify required methods, are possible but may prove confusing if improperly used.

**Programmatic example**

The Marker Interface design pattern is a design pattern in computer science that is used with languages that provide run-time type information about objects. It provides a means to associate metadata with a class where the language does not have explicit support for such metadata.

In the given code, the `Permission` interface acts as a marker interface. Classes that implement this interface are marked as having special permissions. Let's break down the code to understand how this pattern is implemented.

First, we define the `Permission` interface. This interface doesn't have any methods, making it a marker interface.

```java
public interface Permission {
  // This is a marker interface and does not contain any methods
}
```

Next, we have two classes `Guard` and `Thief` that represent different types of characters in our application. The `Guard` class implements the `Permission` interface, indicating that objects of this class have special permissions.

```java
public class Guard implements Permission {
  public void enter() {
    // Implementation of enter method
  }
}
```

On the other hand, the `Thief` class does not implement the `Permission` interface, indicating that objects of this class do not have special permissions.

```java
public class Thief {
  public void steal() {
    // Implementation of steal method
  }

  public void doNothing() {
    // Implementation of doNothing method
  }
}
```

In the `main` method of the `App` class, we create instances of `Guard` and `Thief`. We then use the `instanceof` operator to check if these objects implement the `Permission` interface. If an object implements the `Permission` interface, it is allowed to perform certain actions. If not, it is restricted from performing those actions.

```java
public class App {
  public static void main(String[] args) {
    final var logger = LoggerFactory.getLogger(App.class);
    var guard = new Guard();
    var thief = new Thief();

    if (guard instanceof Permission) {
      guard.enter();
    } else {
      logger.info("You have no permission to enter, please leave this area");
    }

    if (thief instanceof Permission) {
      thief.steal();
    } else {
      thief.doNothing();
    }
  }
}
```

In this way, the Marker Interface pattern allows us to associate metadata (in this case, special permissions) with a class in a type-safe manner.

## Class diagram

![Marker Interface](./etc/MarkerDiagram.png "Marker Interface")

## Applicability

Marker interfaces are applicable in scenarios where you want to impose a special behavior or capability on a class, but don't want to force the class to define specific methods. This pattern is commonly used to indicate that a class conforms to a particular contract without needing to implement methods.

## Known uses

* java.io.Serializable: Classes that implement this interface are capable of being serialized by the Java runtime.
* java.lang.Cloneable: Classes that implement this interface can be cloned using the clone method in Java.

## Consequences

Benefits:

* Enables type checking at compile time, allowing developers to use polymorphism to write cleaner and more flexible code.
* Allows the addition of metadata to classes without altering their actual behavior.

Trade-offs:

* Can lead to empty interfaces in the codebase, which some may consider as not clean or clear in purpose.
* Does not enforce any method implementations, which can lead to runtime errors if not properly handled.

## Credits

* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
