---
layout: pattern
title: Composite
folder: composite
permalink: /patterns/composite/
categories: Structural
tags:
 - Gang of Four
---

## Intent
Compose objects into tree structures to represent part-whole
hierarchies. Composite lets clients treat individual objects and compositions
of objects uniformly.

## Explanation

Real world example

> Every sentence is composed of words which are in turn composed of characters. Each of these objects is printable and they can have something printed before or after them like sentence always ends with full stop and word always has space before it

In plain words

> Composite pattern lets clients treat the individual objects in a uniform manner.

Wikipedia says

> In software engineering, the composite pattern is a partitioning design pattern. The composite pattern describes that a group of objects is to be treated in the same way as a single instance of an object. The intent of a composite is to "compose" objects into tree structures to represent part-whole hierarchies. Implementing the composite pattern lets clients treat individual objects and compositions uniformly.

**Programmatic Example**

Taking our sentence example from above. Here we have the base class and different printable types

```java
public abstract class LetterComposite {

  private List<LetterComposite> children = new ArrayList<>();

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

  private char character;

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

Then we have a messenger to carry messages

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

And then it can be used as

```java
var orcMessage = new Messenger().messageFromOrcs();
orcMessage.print(); // Where there is a whip there is a way.
var elfMessage = new Messenger().messageFromElves();
elfMessage.print(); // Much wind pours from your mouth.
```

## Class diagram
![alt text](./etc/composite.urm.png "Composite class diagram")

## Applicability
Use the Composite pattern when

* you want to represent part-whole hierarchies of objects
* you want clients to be able to ignore the difference between compositions of objects and individual objects. Clients will treat all objects in the composite structure uniformly

## Real world examples

* [java.awt.Container](http://docs.oracle.com/javase/8/docs/api/java/awt/Container.html) and [java.awt.Component](http://docs.oracle.com/javase/8/docs/api/java/awt/Component.html)
* [Apache Wicket](https://github.com/apache/wicket) component tree, see [Component](https://github.com/apache/wicket/blob/91e154702ab1ff3481ef6cbb04c6044814b7e130/wicket-core/src/main/java/org/apache/wicket/Component.java) and [MarkupContainer](https://github.com/apache/wicket/blob/b60ec64d0b50a611a9549809c9ab216f0ffa3ae3/wicket-core/src/main/java/org/apache/wicket/MarkupContainer.java)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
