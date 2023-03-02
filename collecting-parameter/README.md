---
title: Collecting Parameter 
category: Idiom
language: en
tag:
- Generic
---

## Name
Collecting Parameter

## Intent
To store the collaborative result of numerous methods within a collection.

## Explanation
### Real-world example
Within a large corporate building, there exists a global printer queue that is a collection of all the printing jobs
that are currently pending. Various floors contain different models of printers, each having a different printing
policy. We must construct a program that can continually add appropriate printing jobs to a collection, which is called the *collecting parameter*.

### In plain words
Instead of having one giant method that contains numerous policies for collecting information into a variable, we can
create numerous smaller functions that each take parameter, and append new information. We can pass the parameter to
all of these smaller functions and by the end, we will have what we wanted originally. This time, the code is cleaner
and easier to understand. Because the larger function has been broken down, the code is also easier to modify as changes
are localised to the smaller functions.

### Wikipedia says
In the Collecting Parameter idiom a collection (list, map, etc.) is passed repeatedly as a parameter to a method which adds items to the collection.

### Programmatic example
Coding our example from above, we may use the collection `result` as a collecting parameter. The following restrictions
are implemented:
- If an A4 paper is coloured, it must also be single-sided. All other non-coloured papers are accepted
- A3 papers must be non-coloured and single-sided
- A2 papers must be single-page, single-sided, and non-coloured

```java
package com.iluwatar.collectingparameter;
import java.util.LinkedList;
import java.util.Queue;
public class App {
  static PrinterQueue printerQueue = PrinterQueue.getInstance();

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    /*
      Initialising the printer queue with jobs
    */
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A4, 5, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A3, 2, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A2, 5, false, false));

    /*
      This variable is the collecting parameter.
    */    
    var result = new LinkedList<PrinterItem>();

    /* 
     * Using numerous sub-methods to collaboratively add information to the result collecting parameter
     */
    addA4Papers(result);
    addA3Papers(result);
    addA2Papers(result);
  }
}
```

We use the `addA4Paper`, `addA3Paper`, and `addA2Paper` methods to populate the `result` collecting parameter with the
appropriate print jobs as per the policy described previously. The three policies are encoded below,

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
        var isColouredAndSingleSided = nextItem.isColour && !nextItem.isDoubleSided;
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
        var isNotColouredAndSingleSided = !nextItem.isColour && !nextItem.isDoubleSided;
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

Each method takes a collecting parameter as an argument. It then adds elements, taken from a global variable,
to this collecting parameter if each element satisfies a given criteria. These methods can have whatever policy the client desires.

In this programmatic example, three print jobs are added to the queue. Only the first two print jobs should be added to
the collecting parameter as per the policy. The elements of the `result` variable after execution are,

| paperSize | pageCount | isDoubleSided | isColour |
|-----------|-----------|---------------|----------|
| A4        | 5         | false         | false    |
| A3        | 2         | false         | false    |

which is what we expected.

## Class diagram
![alt text](./etc/collecting-parameter.urm.png "Collecting Parameter")

## Applicability
Use the Collecting Parameter design pattern when
- you want to return a collection or object that is the collaborative result of several methods
- You want to simplify a method that accumulates data as the original method is too complex

## Tutorials
Tutorials for this method are found in:
- [Refactoring To Patterns](http://www.tarrani.net/RefactoringToPatterns.pdf)  by Joshua Kerivsky
- [Smalltalk Best Practice Patterns](https://ptgmedia.pearsoncmg.com/images/9780134769042/samplepages/013476904X.pdf)  by Kent Beck

## Known uses
Joshua Kerivsky gives a real-world example in his book 'Refactoring to Patterns'. He gives an example of using the
Collecting Parameter Design Pattern to create a `toString()` method for an XML tree. Without using this design pattern,
this would require a bulky function with conditionals and concatenation that would worsen code readability. Such a method
can be broken down into smaller methods, each appending their own set of information to the collecting parameter.

See this in [Refactoring To Patterns](http://www.tarrani.net/RefactoringToPatterns.pdf).

## Consequences
Pros:
- Makes code more readable
- Avoids 'linkages', where numerous methods reference the same global variable
- Increases maintainability by decomposing larger functions

Cons:
- May increase code length
- Adds 'layers' of methods

## Related patterns
- [Compose Methods](https://www.geeksforgeeks.org/composite-design-pattern/)

## Credits
Following books were used:
- [Refactoring To Patterns](http://www.tarrani.net/RefactoringToPatterns.pdf)  by Joshua Kerivsky
- [Smalltalk Best Practice Patterns](https://ptgmedia.pearsoncmg.com/images/9780134769042/samplepages/013476904X.pdf)  by Kent Beck
Sites:
- [Wiki](https://wiki.c2.com/?CollectingParameter)
