---
title: Delegation
category: Structural
language: en
tag:
 - Decoupling
---

## Also known as
Proxy Pattern

## Intent
It is a technique where an object expresses certain behavior to the outside but in 
reality delegates responsibility for implementing that behaviour to an associated object. 

## Explanation

Real-world example

> Imagine that we have adventurers who fight monsters with different weapons depending on their 
> abilities and skills. We must be able to equip them with different ones without having to 
> modify their source code for each one. The delegation pattern makes it possible by delegating
> the dynamic work to a specific object implementing an interface with relevant methods.

Wikipedia says

> In object-oriented programming, delegation refers to evaluating a member (property or method) of
> one object (the receiver) in the context of another original object (the sender). Delegation can
> be done explicitly, by passing the sending object to the receiving object, which can be done in 
> any object-oriented language; or implicitly, by the member lookup rules of the language, which 
> requires language support for the feature.

**Programmatic Example**

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
The `PrinterController` can be used as a `Printer` by delegating any work handled by this 
interface to an object implementing it.
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

Now on the client code printer controllers can print messages differently depending on the
object they're delegating that work to. 

```java
private static final String MESSAGE_TO_PRINT = "hello world";

var hpPrinterController = new PrinterController(new HpPrinter());
var canonPrinterController = new PrinterController(new CanonPrinter());
var epsonPrinterController = new PrinterController(new EpsonPrinter());

hpPrinterController.print(MESSAGE_TO_PRINT);
canonPrinterController.print(MESSAGE_TO_PRINT);
epsonPrinterController.print(MESSAGE_TO_PRINT)
```

Program output:

```java
HP Printer : hello world
Canon Printer : hello world
Epson Printer : hello world
```

## Class diagram
![alt text](./etc/delegation.png "Delegate")

## Applicability
Use the Delegate pattern in order to achieve the following

* Reduce the coupling of methods to their class
* Components that behave identically, but realize that this situation can change in the future.

## Credits

* [Delegate Pattern: Wikipedia ](https://en.wikipedia.org/wiki/Delegation_pattern)
* [Proxy Pattern: Wikipedia ](https://en.wikipedia.org/wiki/Proxy_pattern)
