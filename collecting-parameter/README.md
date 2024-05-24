---
title: Collecting Parameter
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

## Intent

Aims to simplify methods that collect information by passing a single collection object through various method calls, allowing them to add results to this collection rather than each method creating its own collection.

## Explanation

Real-world example

> Imagine a scenario in a restaurant where a waiter needs to take an order from a customer. Instead of noting down each item separately (e.g., appetizer, main course, dessert, drink), the waiter uses an order form that collects all the items into a single document. This order form simplifies the communication between the waiter and the kitchen staff by aggregating all the details into one place. Similarly, in software, the Collecting Parameter pattern aggregates multiple parameters into a single object, streamlining method calls and improving code readability and maintainability.

In plain words

> The Collecting Parameter pattern simplifies method calls by encapsulating multiple parameters into a single object.

Wikipedia says

> In the Collecting Parameter idiom a collection (list, map, etc.) is passed repeatedly as a parameter to a method which adds items to the collection.

**Programmatic Example**

Within a large corporate building, there exists a global printer queue that is a collection of all the printing jobs that are currently pending. Various floors contain different models of printers, each having a different printing policy. We must construct a program that can continually add appropriate printing jobs to a collection, which is called the collecting parameter.

Coding our example from above, we may use the collection `result` as a collecting parameter. The following restrictions are implemented:

* If an A4 paper is coloured, it must also be single-sided. All other non-coloured papers are accepted
* A3 papers must be non-coloured and single-sided
* A2 papers must be single-page, single-sided, and non-coloured

```java
public class App {
    
    static PrinterQueue printerQueue = PrinterQueue.getInstance();

    public static void main(String[] args) {
        //  Initialising the printer queue with jobs
        printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A4, 5, false, false));
        printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A3, 2, false, false));
        printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A2, 5, false, false));

        // This variable is the collecting parameter.
        var result = new LinkedList<PrinterItem>();

         // Using numerous sub-methods to collaboratively add information to the result collecting parameter
        addA4Papers(result);
        addA3Papers(result);
        addA2Papers(result);
    }
}
```

We use the `addA4Paper`, `addA3Paper`, and `addA2Paper` methods to populate the `result` collecting parameter with the appropriate print jobs as per the policy described previously. The three policies are encoded below,

```java
public class App {
    
    static PrinterQueue printerQueue = PrinterQueue.getInstance();

    /**
     * Adds A4 document jobs to the collecting parameter according to some policy that can be whatever the client
     * (the print center) wants.
     *
     * @param printerItemsCollection the collecting parameter
     */
    public static void addA4Papers(Queue<PrinterItem> printerItemsCollection) {
    /*
      Iterate through the printer queue, and add A4 papers according to the correct policy to the collecting parameter,
      which is 'printerItemsCollection' in this case.
     */
        for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
            if (nextItem.paperSize.equals(PaperSizes.A4)) {
                var isColouredAndSingleSided =
                        nextItem.isColour && !nextItem.isDoubleSided;
                if (isColouredAndSingleSided) {
                    printerItemsCollection.add(nextItem);
                } else if (!nextItem.isColour) {
                    printerItemsCollection.add(nextItem);
                }
            }
        }
    }

    /**
     * Adds A3 document jobs to the collecting parameter according to some policy that can be whatever the client
     * (the print center) wants. The code is similar to the 'addA4Papers' method. The code can be changed to accommodate
     * the wants of the client.
     *
     * @param printerItemsCollection the collecting parameter
     */
    public static void addA3Papers(Queue<PrinterItem> printerItemsCollection) {
        for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
            if (nextItem.paperSize.equals(PaperSizes.A3)) {

                // Encoding the policy into a Boolean: the A3 paper cannot be coloured and double-sided at the same time
                var isNotColouredAndSingleSided =
                        !nextItem.isColour && !nextItem.isDoubleSided;
                if (isNotColouredAndSingleSided) {
                    printerItemsCollection.add(nextItem);
                }
            }
        }
    }

    /**
     * Adds A2 document jobs to the collecting parameter according to some policy that can be whatever the client
     * (the print center) wants. The code is similar to the 'addA4Papers' method. The code can be changed to accommodate
     * the wants of the client.
     *
     * @param printerItemsCollection the collecting parameter
     */
    public static void addA2Papers(Queue<PrinterItem> printerItemsCollection) {
        for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
            if (nextItem.paperSize.equals(PaperSizes.A2)) {

                // Encoding the policy into a Boolean: the A2 paper must be single page, single-sided, and non-coloured.
                var isNotColouredSingleSidedAndOnePage =
                        nextItem.pageCount == 1 &&
                                !nextItem.isDoubleSided
                                && !nextItem.isColour;
                if (isNotColouredSingleSidedAndOnePage) {
                    printerItemsCollection.add(nextItem);
                }
            }
        }
    }
}
```

Each method takes a collecting parameter as an argument. It then adds elements, taken from a global variable, to this collecting parameter if each element satisfies a given criteria. These methods can have whatever policy the client desires.

In this programmatic example, three print jobs are added to the queue. Only the first two print jobs should be added to the collecting parameter as per the policy. The elements of the `result` variable after execution are,

| paperSize | pageCount | isDoubleSided | isColour |
|-----------|-----------|---------------|----------|
| A4        | 5         | false         | false    |
| A3        | 2         | false         | false    |

which is what we expected.

## Class diagram

![Collecting Parameter](./etc/collecting-parameter.urm.png "Collecting Parameter")

## Applicability

* Use when a method needs to accept a large number of parameters, making the method signature unwieldy.
* Use when the same group of parameters is passed to multiple methods, reducing redundancy and potential errors.
* Use to improve the readability and maintainability of the code.

## Tutorials

* [Refactoring To Patterns (Joshua Kerivsky)](http://www.tarrani.net/RefactoringToPatterns.pdf)
* [Smalltalk Best Practice Patterns (Kent Beck)](https://ptgmedia.pearsoncmg.com/images/9780134769042/samplepages/013476904X.pdf)

## Known uses

* Use when a method needs to accept a large number of parameters, making the method signature unwieldy.
* Use when the same group of parameters is passed to multiple methods, reducing redundancy and potential errors.
* Use to improve the readability and maintainability of the code.

## Consequences

Benefits:

* Improves code readability by reducing the number of parameters in method signatures.
* Facilitates the reuse of parameter sets across different methods.
* Enhances maintainability by centralizing the parameter structure.

Trade-offs:

* Introduces an additional class, which may increase complexity if not managed properly.
* Can lead to over-generalization if the parameter object becomes too large or unwieldy.

## Related patterns

* [Command](https://java-design-patterns.com/patterns/command/): Commands may utilize Collecting Parameter to aggregate results from multiple operations executed by the command objects.
* [Composite](https://java-design-patterns.com/patterns/composite/): Can be used in tandem with Collecting Parameter when dealing with hierarchical structures, allowing results to be collected across a composite structure.
* [Visitor](https://java-design-patterns.com/patterns/visitor/): Often used together, where Visitor handles traversal and operations on a structure, and Collecting Parameter accumulates the results.

## Credits

* [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/4aApLP0)
* [Refactoring: Improving the Design of Existing Code](https://amzn.to/3TVEgaB)
* [Collecting Parameter (WikiWikiWeb)](https://wiki.c2.com/?CollectingParameter)
