---
layout: pattern
title: Command
folder: command
permalink: /patterns/command/
categories: Behavioral
language: en
tags:
 - Gang of Four
---

## Also known as

Action, Transaction

## Intent

Encapsulate a request as an object, thereby letting you parameterize clients with different
requests, queue or log requests, and support undoable operations.

## Explanation
Real world example

> There is a wizard casting spells on a goblin. The spells are executed on the goblin one by one.
> The first spell shrinks the goblin and the second makes him invisible. Then the wizard reverses
> the spells one by one. Each spell here is a command object that can be undone.

In plain words

> Storing requests as command objects allows performing an action or undoing it at a later time.

Wikipedia says

> In object-oriented programming, the command pattern is a behavioral design pattern in which an
> object is used to encapsulate all information needed to perform an action or trigger an event at
> a later time.

**Programmatic Example**

Here's the sample code with wizard and goblin. Let's start from the `Wizard` class.

```java
@Slf4j
public class Wizard {

  private final Deque<Command> undoStack = new LinkedList<>();
  private final Deque<Command> redoStack = new LinkedList<>();

  public Wizard() {}

  public void castSpell(Runnable runnable) {
    runnable.run();
    undoStack.offerLast(runnable);
  }

  public void undoLastSpell() {
    if (!undoStack.isEmpty()) {
      var previousSpell = undoStack.pollLast();
      redoStack.offerLast(previousSpell);
      previousSpell.run();
    }
  }

  public void redoLastSpell() {
    if (!redoStack.isEmpty()) {
      var previousSpell = redoStack.pollLast();
      undoStack.offerLast(previousSpell);
      previousSpell.run();
    }
  }

  @Override
  public String toString() {
    return "Wizard";
  }
}
```

Next, we have the goblin who's the target of the spells.

```java
@Slf4j
public abstract class Target {

  private Size size;

  private Visibility visibility;

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }

  @Override
  public abstract String toString();

  public void printStatus() {
    LOGGER.info("{}, [size={}] [visibility={}]", this, getSize(), getVisibility());
  }
}

public class Goblin extends Target {

  public Goblin() {
    setSize(Size.NORMAL);
    setVisibility(Visibility.VISIBLE);
  }

  @Override
  public String toString() {
    return "Goblin";
  }

  public void changeSize() {
    var oldSize = getSize() == Size.NORMAL ? Size.SMALL : Size.NORMAL;
    setSize(oldSize);
  }

  public void changeVisibility() {
    var visible = getVisibility() == Visibility.INVISIBLE
          ? Visibility.VISIBLE : Visibility.INVISIBLE;
    setVisibility(visible);
  }
}
```

Finally we have the wizard in main function who casts spell

```java
public static void main(String[] args) {
  var wizard = new Wizard();
  var goblin = new Goblin();

  // casts shrink/unshrink spell
  wizard.castSpell(goblin::changeSize);

  // casts visible/invisible spell
  wizard.castSpell(goblin::changeVisibility);

  // undo and redo casts
   wizard.undoLastSpell();
   wizard.redoLastSpell();
```

Here's the whole example in action.

```java
var wizard = new Wizard();
var goblin = new Goblin();

goblin.printStatus();
wizard.castSpell(goblin::changeSize);
goblin.printStatus();

wizard.castSpell(goblin::changeVisibility);
goblin.printStatus();

wizard.undoLastSpell();
goblin.printStatus();

wizard.undoLastSpell();
goblin.printStatus();

wizard.redoLastSpell();
goblin.printStatus();

wizard.redoLastSpell();
goblin.printStatus();
```

Here's the program output:

```java
Goblin, [size=normal] [visibility=visible]
Goblin, [size=small] [visibility=visible]
Goblin, [size=small] [visibility=invisible]
Goblin, [size=small] [visibility=visible]
Goblin, [size=normal] [visibility=visible]
Goblin, [size=small] [visibility=visible]
Goblin, [size=small] [visibility=invisible]
```

## Class diagram

![alt text](./etc/command.png "Command")

## Applicability

Use the Command pattern when you want to:

* Parameterize objects by an action to perform. You can express such parameterization in a
procedural language with a callback function, that is, a function that's registered somewhere to be
called at a later point. Commands are an object-oriented replacement for callbacks.
* Specify, queue, and execute requests at different times. A Command object can have a lifetime
independent of the original request. If the receiver of a request can be represented in an address
space-independent way, then you can transfer a command object for the request to a different process
and fulfill the request there.
* Support undo. The Command's execute operation can store state for reversing its effects in the
command itself. The Command interface must have an added un-execute operation that reverses the
effects of a previous call to execute. The executed commands are stored in a history list.
Unlimited-level undo and redo is achieved by traversing this list backwards and forwards calling
un-execute and execute, respectively.
* Support logging changes so that they can be reapplied in case of a system crash. By augmenting the
Command interface with load and store operations, you can keep a persistent log of changes.
Recovering from a crash involves reloading logged commands from disk and re-executing them with
the execute operation.
* Structure a system around high-level operations build on primitive operations. Such a structure is
common in information systems that support transactions. A transaction encapsulates a set of changes
to data. The Command pattern offers a way to model transactions. Commands have a common interface,
letting you invoke all transactions the same way. The pattern also makes it easy to extend the
system with new transactions.

## Typical Use Case

* To keep a history of requests
* Implement callback functionality
* Implement the undo functionality

## Real world examples

* [java.lang.Runnable](http://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html)
* [org.junit.runners.model.Statement](https://github.com/junit-team/junit4/blob/master/src/main/java/org/junit/runners/model/Statement.java)
* [Netflix Hystrix](https://github.com/Netflix/Hystrix/wiki)
* [javax.swing.Action](http://docs.oracle.com/javase/8/docs/api/javax/swing/Action.html)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
