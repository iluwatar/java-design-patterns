---
title: Delegation
category: Behavioral
language: en
tag:
    - Decoupling
    - Delegation
    - Object composition
---

## Also known as

* Helper
* Surrogate

## Intent

To allow an object to delegate responsibility for a task to another helper object.

## Explanation

Real-world example

> In a restaurant, the head chef delegates tasks to sous-chefs: one manages grilling, another handles salads, and a third is in charge of desserts. Each sous-chef specializes in their area, allowing the head chef to focus on overall kitchen management. This mirrors the Delegation design pattern, where a main object delegates specific tasks to helper objects, each expert in their domain.

Wikipedia says

> In object-oriented programming, delegation refers to evaluating a member (property or method) of one object (the receiver) in the context of another original object (the sender). Delegation can be done explicitly, by passing the sending object to the receiving object, which can be done in any object-oriented language; or implicitly, by the member lookup rules of the language, which requires language support for the feature.

**Programmatic Example**

Let's consider a printing example.

We have an interface `Printer` and three implementations `CanonPrinter`, `EpsonPrinter` and `HpPrinter`.

```java
public interface Printer {
    void print(final String message);
}

@Slf4j
public class CanonPrinter implements Printer {
    @Override
    public void print(String message) {
        LOGGER.info("Canon Printer : {}", message);
    }
}

@Slf4j
public class EpsonPrinter implements Printer {
    @Override
    public void print(String message) {
        LOGGER.info("Epson Printer : {}", message);
    }
}

@Slf4j
public class HpPrinter implements Printer {
    @Override
    public void print(String message) {
        LOGGER.info("HP Printer : {}", message);
    }
}
```

The `PrinterController` can be used as a `Printer` by delegating any work handled by this interface to an object implementing it.

```java
public class PrinterController implements Printer {

    private final Printer printer;

    public PrinterController(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void print(String message) {
        printer.print(message);
    }
}
```

Now on the client code printer controllers can print messages differently depending on the object they're delegating that work to.

```java
public class App {

    private static final String MESSAGE_TO_PRINT = "hello world";

    public static void main(String[] args) {
        var hpPrinterController = new PrinterController(new HpPrinter());
        var canonPrinterController = new PrinterController(new CanonPrinter());
        var epsonPrinterController = new PrinterController(new EpsonPrinter());

        hpPrinterController.print(MESSAGE_TO_PRINT);
        canonPrinterController.print(MESSAGE_TO_PRINT);
        epsonPrinterController.print(MESSAGE_TO_PRINT);
    }
}
```

Program output:

```
HP Printer:hello world
Canon Printer:hello world
Epson Printer:hello world
```

## Class diagram

![Delegate class diagram](./etc/delegation.png "Delegate")

## Applicability

* When you want to pass responsibility from one class to another without inheritance.
* To achieve composition-based reuse instead of inheritance-based.
* When you need to use several interchangeable helper classes at runtime.

## Known Uses

* Java's java.awt.event package, where listeners are often used to handle events.
* Wrapper classes in Java's Collections Framework (java.util.Collections), which delegate to other collection objects.
* In Spring Framework, delegation is used extensively in the IoC container where beans delegate tasks to other beans.

## Consequences

Benefits:

* Reduces subclassing: Objects can delegate operations to different objects and change them at runtime, reducing the need for subclassing.
* Encourages reuse: Delegation promotes the reuse of the helper object's code.
* Increases flexibility: By delegating tasks to helper objects, you can change the behavior of your classes at runtime.

Trade-offs:

* Runtime Overhead: Delegation can introduce additional layers of indirection, which may result in slight performance costs.
* Complexity: The design can become more complicated since it involves additional classes and interfaces to manage delegation.

## Related Patterns

* [Composite](https://java-design-patterns.com/patterns/composite/): Delegation can be used within a composite pattern to delegate component-specific behavior to child components.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Delegation is often used in the strategy pattern where a context object delegates tasks to a strategy object.
* https://java-design-patterns.com/patterns/proxy/: The proxy pattern is a form of delegation where a proxy object controls access to another object, which it delegates work to.

## Credits

* [Effective Java](https://amzn.to/4aGE7gX)
* [Head First Design Patterns](https://amzn.to/3J9tuaB)
* [Refactoring: Improving the Design of Existing Code](https://amzn.to/3VOcRsw)
* [Delegate Pattern: Wikipedia ](https://en.wikipedia.org/wiki/Delegation_pattern)
