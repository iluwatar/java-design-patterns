---
title: "Collecting Parameter Pattern in Java: Mastering Efficient Parameter Handling"
shortTitle: Collecting Parameter
description: "Discover how the Collecting Parameter design pattern simplifies Java method calls by aggregating multiple parameters into a single collection object. Enhance code readability and maintainability with practical examples and real-world applications."
category: Behavioral
language: en
tag:
  - Accumulation
  - Data processing
  - Data transfer
  - Generic
---

## Also known as

* Collector
* Accumulator

## Intent of Collecting Parameter Design Pattern

The Collecting Parameter pattern in Java design patterns aims to simplify method calls by aggregating multiple parameters into a single collection object. This pattern is particularly effective for methods that collect information by passing a single collection object through various method calls. Each method can then add results to this collection, instead of creating its own collection. This approach enhances code readability and maintainability, optimizing the process of information collection in Java programming.

## Detailed Explanation of Collecting Parameter Pattern with Real-World Examples

Real-world example

> In software development, the Collecting Parameter pattern provides significant benefits by optimizing method calls and improving code maintainability.
> 
> Imagine a scenario in a restaurant where a waiter needs to take an order from a customer. Instead of noting down each item separately (e.g., appetizer, main course, dessert, drink), the waiter uses an order form that collects all the items into a single document. This order form simplifies the communication between the waiter and the kitchen staff by aggregating all the details into one place. Similarly, in software, the Collecting Parameter pattern aggregates multiple parameters into a single object, streamlining method calls and improving code readability and maintainability.

In plain words

> The Collecting Parameter pattern simplifies method calls by encapsulating multiple parameters into a single object.

Wikipedia says

> In the Collecting Parameter idiom a collection (list, map, etc.) is passed repeatedly as a parameter to a method which adds items to the collection.

## Programmatic Example of Collecting Parameter Pattern in Java

Within a large corporate building, there exists a global printer queue that is a collection of all the printing jobs that are currently pending. Various floors contain different models of printers, each having a different printing policy. We must construct a program that can continually add appropriate printing jobs to a collection, which is called the collecting parameter.

The following business rules are implemented:

* If an A4 paper is coloured, it must also be single-sided. All other non-coloured papers are accepted
* A3 papers must be non-coloured and single-sided
* A2 papers must be single-page, single-sided, and non-coloured

Let's see the implementation first and explain afterward.

```java
public class App {
  static PrinterQueue printerQueue = PrinterQueue.getInstance();

  public static void main(String[] args) {
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A4, 5, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A3, 2, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A2, 5, false, false));

    var result = new LinkedList<PrinterItem>();

    addValidA4Papers(result);
    addValidA3Papers(result);
    addValidA2Papers(result);
  }

  public static void addValidA4Papers(Queue<PrinterItem> printerItemsCollection) {
    for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
      if (nextItem.paperSize.equals(PaperSizes.A4)) {
        var isColouredAndSingleSided = nextItem.isColour && !nextItem.isDoubleSided;
        if (isColouredAndSingleSided || !nextItem.isColour) {
          printerItemsCollection.add(nextItem);
        }
      }
    }
  }

  public static void addValidA3Papers(Queue<PrinterItem> printerItemsCollection) {
    for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
      if (nextItem.paperSize.equals(PaperSizes.A3)) {
        var isNotColouredAndSingleSided = !nextItem.isColour && !nextItem.isDoubleSided;
        if (isNotColouredAndSingleSided) {
          printerItemsCollection.add(nextItem);
        }
      }
    }
  }

  public static void addValidA2Papers(Queue<PrinterItem> printerItemsCollection) {
    for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
      if (nextItem.paperSize.equals(PaperSizes.A2)) {
        var isNotColouredSingleSidedAndOnePage = nextItem.pageCount == 1 && !nextItem.isDoubleSided
                && !nextItem.isColour;
        if (isNotColouredSingleSidedAndOnePage) {
          printerItemsCollection.add(nextItem);
        }
      }
    }
  }
}
```

This `App` class is the main entry point of the application. It uses the Collecting Parameter design pattern to filter print jobs based on certain policies.

1. **Initialization**: The `printerQueue` is initialized with three print jobs of different paper sizes (A4, A3, A2).

2. **Creating the Collecting Parameter**: A `LinkedList` named `result` is created to store the print jobs that meet the policy requirements.

3. **Adding Valid Jobs to the Collecting Parameter**: The `addValidA4Papers`, `addValidA3Papers`, and `addValidA2Papers` methods are called. These methods iterate over the `printerQueue` and add the print jobs that meet the policy requirements to the `result` list.

The `result` list, which is the collecting parameter, accumulates the valid print jobs as it is passed from method to method. This is the essence of the Collecting Parameter design pattern.

Utilizing the Collecting Parameter pattern in Java design patterns leads to more efficient method calls and improved overall code structure.

## When to Use the Collecting Parameter Pattern in Java

This pattern is useful for managing parameters in Java coding practices, ensuring efficient code refactoring and enhanced readability.

* Use when a method needs to accept a large number of parameters, making the method signature unwieldy.
* Use when the same group of parameters is passed to multiple methods, reducing redundancy and potential errors.
* Use to improve the readability and maintainability of the code.

## Collecting Parameter Pattern Java Tutorials

* [Refactoring To Patterns (Joshua Kerivsky)](http://www.tarrani.net/RefactoringToPatterns.pdf)
* [Smalltalk Best Practice Patterns (Kent Beck)](https://ptgmedia.pearsoncmg.com/images/9780134769042/samplepages/013476904X.pdf)

## Real-World Applications of Collecting Parameter Pattern in Java

* Use when a method needs to accept a large number of parameters, making the method signature unwieldy.
* Use when the same group of parameters is passed to multiple methods, reducing redundancy and potential errors.
* Use to improve the readability and maintainability of the code.

## Benefits and Trade-offs of Collecting Parameter Pattern

Benefits:

* Improves code readability by reducing the number of parameters in method signatures.
* Facilitates the reuse of parameter sets across different methods.
* Enhances maintainability by centralizing the parameter structure.

Trade-offs:

* Introduces an additional class, which may increase complexity if not managed properly.
* Can lead to over-generalization if the parameter object becomes too large or unwieldy.

## Related Java Design Patterns

* [Command](https://java-design-patterns.com/patterns/command/): Commands may utilize Collecting Parameter to aggregate results from multiple operations executed by the command objects.
* [Composite](https://java-design-patterns.com/patterns/composite/): Can be used in tandem with Collecting Parameter when dealing with hierarchical structures, allowing results to be collected across a composite structure.
* [Visitor](https://java-design-patterns.com/patterns/visitor/): Often used together, where Visitor handles traversal and operations on a structure, and Collecting Parameter accumulates the results.

## References and Credits

* [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/4aApLP0)
* [Refactoring: Improving the Design of Existing Code](https://amzn.to/3TVEgaB)
* [Collecting Parameter (WikiWikiWeb)](https://wiki.c2.com/?CollectingParameter)
