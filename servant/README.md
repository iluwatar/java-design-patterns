---
title: Servant
category: Structural
language: en
tag:
    - Decoupling
    - Interface
    - Messaging
    - Object composition
    - Resource management
---

## Also known as

* Helper

## Intent

The Servant pattern is used to perform specific operations for a group of objects without changing the classes of the elements on which it operates.

## Explanation

Real-world example

> In a restaurant, a waiter (Servant) serves multiple tables (objects) by taking orders, delivering food, and handling payments. The waiter provides these common services without altering the fundamental nature of the tables or customers, allowing the restaurant to efficiently manage customer service while keeping the roles of the tables and customers unchanged.

In plain words

> The Servant pattern centralizes common functionality for a group of classes, enabling decoupled and reusable operations without altering the classes themselves.

Wikipedia says

> In software engineering, the servant pattern defines an object used to offer some functionality to a group of classes without defining that functionality in each of them. A Servant is a class whose instance (or even just class) provides methods that take care of a desired service, while objects for which (or with whom) the servant does something, are taken as parameters.

**Programmatic Example**

The Servant design pattern is a behavioral design pattern that defines a class that provides some sort of service to a group of classes. This pattern is particularly useful when these classes lack some common functionality that can't be added to the superclass. The Servant class brings this common functionality to a group of classes.

In the provided code, we have a `Servant` class that provides services to the `Royalty` class. The `Servant` class has methods like `feed()`, `giveWine()`, and `giveCompliments()` which are services provided to the `Royalty` class.

Here is the `Servant` class:

```java
public class Servant {

    public String name;

    public Servant(String name) {
        this.name = name;
    }

    public void feed(Royalty r) {
        r.getFed();
    }

    public void giveWine(Royalty r) {
        r.getDrink();
    }

    public void giveCompliments(Royalty r) {
        r.receiveCompliments();
    }

    public boolean checkIfYouWillBeHanged(List<Royalty> tableGuests) {
        return tableGuests.stream().allMatch(Royalty::getMood);
    }
}
```

The `Royalty` class is an interface that is implemented by the classes that use the services of the `Servant`. Here is a simplified version of the `Royalty` interface:

```java
public interface Royalty {
    void getFed();

    void getDrink();

    void receiveCompliments();

    void changeMood();

    boolean getMood();
}
```

The `App` class uses the `Servant` to provide services to the `Royalty` objects. Here is a simplified version of the `App` class:

```java
public class App {

    private static final Servant jenkins = new Servant("Jenkins");
    private static final Servant travis = new Servant("Travis");

    public static void main(String[] args) {
        scenario(jenkins, 1);
        scenario(travis, 0);
    }

    public static void scenario(Servant servant, int compliment) {
        var k = new King();
        var q = new Queen();

        var guests = List.of(k, q);

        servant.feed(k);
        servant.feed(q);
        servant.giveWine(k);
        servant.giveWine(q);
        servant.giveCompliments(guests.get(compliment));

        guests.forEach(Royalty::changeMood);

        if (servant.checkIfYouWillBeHanged(guests)) {
            LOGGER.info("{} will live another day", servant.name);
        } else {
            LOGGER.info("Poor {}. His days are numbered", servant.name);
        }
    }
}
```

Running the application produces:

```
09:10:38.795 [main] INFO com.iluwatar.servant.App -- Jenkins will live another day
09:10:38.797 [main] INFO com.iluwatar.servant.App -- Poor Travis. His days are numbered
```

In this example, the `Servant` class provides services to the `Royalty` objects. The `Servant` class doesn't know about the specific implementation of the `Royalty` objects, it only knows that it can provide certain services to them. This is a good example of the Servant design pattern.

## Applicability

* Use the Servant pattern when you need to provide a common functionality to a group of classes without polluting their class definitions.
* Suitable when the operations performed on the objects are not the primary responsibility of the objects themselves.

## Known Uses

* In GUI applications to handle operations like rendering or hit-testing which are common across different UI components.
* In games where various entities (like players, enemies, or items) need common behavior such as movement or collision detection.
* Logging or auditing functionalities that are required across multiple business objects.

## Consequences

Benefits:

* Promotes code reuse and separation of concerns by decoupling the operations from the objects they operate on.
* Reduces code duplication by centralizing the shared functionality.

Trade-offs:

* Can lead to an increase in the number of classes, potentially making the system harder to understand.
* May introduce tight coupling between the Servant and the classes it serves if not designed carefully.

## Related Patterns

* [Adapter](https://java-design-patterns.com/patterns/adapter/): The Servant pattern is similar to the Adapter pattern in that both provide a way to work with classes without modifying them, but the Servant pattern focuses on providing additional behavior to multiple classes rather than adapting one interface to another.
* [Facade](https://java-design-patterns.com/patterns/facade/): Both patterns provide a simplified interface to a set of functionalities, but the Servant pattern is typically used for adding functionalities to a group of classes, while the Facade pattern hides the complexities of a subsystem.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): The Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. The Servant pattern can be used in conjunction with the Strategy pattern to define operations that apply to multiple classes.
* View Helper: The View Helper pattern is related as it also centralizes common functionality, but it focuses on separating presentation logic from business logic in web applications.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Pattern-Oriented Software Architecture Volume 1: A System of Patterns](https://amzn.to/3xZ1ELU)
