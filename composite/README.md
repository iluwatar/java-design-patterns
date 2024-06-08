---
title: "Composite Pattern in Java: Building Flexible Tree Structures"
shortTitle: Composite
description: "Explore the Composite Design Pattern in Java. Learn how to compose objects into tree structures to represent part-whole hierarchies, making it easier to treat individual objects and compositions uniformly. Ideal for graphical user interfaces, file systems, and organizational structures."
category: Structural
language: en
tag:
  - Decoupling
  - Gang of Four
  - Object composition
  - Recursion
---

## Also known as

* Object Tree
* Composite Structure

## Intent of Composite Design Pattern

Compose objects into tree structures to represent part-whole hierarchies. The Composite Design Pattern lets clients treat individual objects and compositions of objects uniformly.

## Detailed Explanation of Composite Pattern with Real-World Examples

Real-world example

> In a real-world example, consider a company with a complex organizational structure. The company consists of various departments, each of which can contain sub-departments, and ultimately individual employees. The Composite Design Pattern can be used to represent this structure. Each department and employee are treated as a node in a tree structure, where departments can contain other departments or employees, but employees are leaf nodes with no children. This allows the company to perform operations uniformly, such as calculating total salaries or printing the organizational chart, by treating individual employees and entire departments in the same way.

In plain words

> The Composite Design Pattern lets clients uniformly treat individual objects and compositions of objects.

Wikipedia says

> In software engineering, the composite pattern is a partitioning design pattern. The composite pattern describes that a group of objects is to be treated in the same way as a single instance of an object. The intent of a composite is to "compose" objects into tree structures to represent part-whole hierarchies. Implementing the composite pattern lets clients treat individual objects and compositions uniformly.

## Programmatic Example of Composite Pattern in Java

Every sentence is composed of words which are in turn composed of characters. Each of these objects are printable, and they can have something printed before or after them like sentence always ends with full stop and word always has space before it.

Here we have the base class `LetterComposite` and the different printable types `Letter`, `Word` and `Sentence`.

```java
public abstract class LetterComposite {

    private final List<LetterComposite> children = new ArrayList<>();

    public void add(LetterComposite letter) {
        children.add(letter);
    }

    public int count() {
        return children.size();
    }

    protected void printThisBefore() {
    }

    protected void printThisAfter() {
    }

    public void print() {
        printThisBefore();
        children.forEach(LetterComposite::print);
        printThisAfter();
    }
}

public class Letter extends LetterComposite {

    private final char character;

    public Letter(char c) {
        this.character = c;
    }

    @Override
    protected void printThisBefore() {
        System.out.print(character);
    }
}

public class Word extends LetterComposite {

    public Word(List<Letter> letters) {
        letters.forEach(this::add);
    }

    public Word(char... letters) {
        for (char letter : letters) {
            this.add(new Letter(letter));
        }
    }

    @Override
    protected void printThisBefore() {
        System.out.print(" ");
    }
}

public class Sentence extends LetterComposite {

    public Sentence(List<Word> words) {
        words.forEach(this::add);
    }

    @Override
    protected void printThisAfter() {
        System.out.print(".");
    }
}
```

Then we have a messenger to carry messages:

```java
public class Messenger {

    LetterComposite messageFromOrcs() {

        var words = List.of(
                new Word('W', 'h', 'e', 'r', 'e'),
                new Word('t', 'h', 'e', 'r', 'e'),
                new Word('i', 's'),
                new Word('a'),
                new Word('w', 'h', 'i', 'p'),
                new Word('t', 'h', 'e', 'r', 'e'),
                new Word('i', 's'),
                new Word('a'),
                new Word('w', 'a', 'y')
        );

        return new Sentence(words);

    }

    LetterComposite messageFromElves() {

        var words = List.of(
                new Word('M', 'u', 'c', 'h'),
                new Word('w', 'i', 'n', 'd'),
                new Word('p', 'o', 'u', 'r', 's'),
                new Word('f', 'r', 'o', 'm'),
                new Word('y', 'o', 'u', 'r'),
                new Word('m', 'o', 'u', 't', 'h')
        );

        return new Sentence(words);

    }

}
```

And then it can be used as:

```java
  public static void main(String[] args) {

    var messenger = new Messenger();

    LOGGER.info("Message from the orcs: ");
    messenger.messageFromOrcs().print();

    LOGGER.info("Message from the elves: ");
    messenger.messageFromElves().print();
}
```

The console output:

```
20:43:54.801 [main] INFO com.iluwatar.composite.App -- Message from the orcs: 
 Where there is a whip there is a way.
20:43:54.803 [main] INFO com.iluwatar.composite.App -- Message from the elves: 
 Much wind pours from your mouth.
```

## When to Use the Composite Pattern in Java

Use the Composite pattern when

* You want to represent part-whole hierarchies of objects.
* You want clients to be able to ignore the difference between compositions of objects and individual objects. Clients will treat all objects in the composite structure uniformly.

## Real-World Applications of Composite Pattern in Java

* Graphical user interfaces where components can contain other components (e.g., panels containing buttons, labels, other panels).
* File system representations where directories can contain files and other directories.
* Organizational structures where a department can contain sub-departments and employees.
* [java.awt.Container](http://docs.oracle.com/javase/8/docs/api/java/awt/Container.html) and [java.awt.Component](http://docs.oracle.com/javase/8/docs/api/java/awt/Component.html)
* [Apache Wicket](https://github.com/apache/wicket) component tree, see [Component](https://github.com/apache/wicket/blob/91e154702ab1ff3481ef6cbb04c6044814b7e130/wicket-core/src/main/java/org/apache/wicket/Component.java) and [MarkupContainer](https://github.com/apache/wicket/blob/b60ec64d0b50a611a9549809c9ab216f0ffa3ae3/wicket-core/src/main/java/org/apache/wicket/MarkupContainer.java)

## Benefits and Trade-offs of Composite Pattern

Benefits:

* Simplifies client code, as it can treat composite structures and individual objects uniformly.
* Makes it easier to add new kinds of components, as existing code doesn't need to be changed.

Trade-offs:

* Can make the design overly general. It might be difficult to restrict the components of a composite.
* Can make it harder to restrict the types of components in a composite.

## Related Java Design Patterns

* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): Composite can use Flyweight to share component instances among several composites.
* [Iterator](https://java-design-patterns.com/patterns/iterator/): Can be used to traverse Composite structures.
* [Visitor](https://java-design-patterns.com/patterns/visitor/): Can apply an operation over a Composite structure.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/3xoLAmi)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3vBKXWb)
