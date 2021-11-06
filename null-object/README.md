---
layout: pattern
title: Null Object
folder: null-object
permalink: /patterns/null-object/
categories: Behavioral
language: en
tags:
 - Extensibility
---

## Intent

In most object-oriented languages, such as Java or C#, references may be null. These references need 
to be checked to ensure they are not null before invoking any methods, because methods typically 
cannot be invoked on null references. Instead of using a null reference to convey absence of an
object (for instance, a non-existent customer), one uses an object which implements the expected 
interface, but whose method body is empty. The advantage of this approach over a working default 
implementation is that a Null Object is very predictable and has no side effects: it does nothing.

## Explanation

Real world example

> We are building a binary tree from nodes. There are ordinary nodes and "empty" nodes. Traversing 
> the tree normally should not cause errors, so we use null object pattern where necessary.            

In plain words

> Null Object pattern handles "empty" objects gracefully.   

Wikipedia says

> In object-oriented computer programming, a null object is an object with no referenced value or 
> with defined neutral ("null") behavior. The null object design pattern describes the uses of such 
> objects and their behavior (or lack thereof).

**Programmatic Example**

Here's the definition of `Node` interface.

```java
public interface Node {

  String getName();

  int getTreeSize();

  Node getLeft();

  Node getRight();

  void walk();
}
```

We have two implementations of `Node`. The normal implementation `NodeImpl` and `NullNode` for
empty nodes.

```java
@Slf4j
public class NodeImpl implements Node {

  private final String name;
  private final Node left;
  private final Node right;

  /**
   * Constructor.
   */
  public NodeImpl(String name, Node left, Node right) {
    this.name = name;
    this.left = left;
    this.right = right;
  }

  @Override
  public int getTreeSize() {
    return 1 + left.getTreeSize() + right.getTreeSize();
  }

  @Override
  public Node getLeft() {
    return left;
  }

  @Override
  public Node getRight() {
    return right;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void walk() {
    LOGGER.info(name);
    if (left.getTreeSize() > 0) {
      left.walk();
    }
    if (right.getTreeSize() > 0) {
      right.walk();
    }
  }
}

public final class NullNode implements Node {

  private static final NullNode instance = new NullNode();

  private NullNode() {
  }

  public static NullNode getInstance() {
    return instance;
  }

  @Override
  public int getTreeSize() {
    return 0;
  }

  @Override
  public Node getLeft() {
    return null;
  }

  @Override
  public Node getRight() {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public void walk() {
    // Do nothing
  }
}
```

Then we can construct and traverse the binary tree without errors as follows.

```java
    var root = new NodeImpl("1",
            new NodeImpl("11",
                new NodeImpl("111", NullNode.getInstance(), NullNode.getInstance()),
                NullNode.getInstance()
            ),
            new NodeImpl("12",
                NullNode.getInstance(),
                new NodeImpl("122", NullNode.getInstance(), NullNode.getInstance())
            )
        );
    root.walk();
```

Program output:

```
1
11
111
12
122
```

## Class diagram

![alt text](./etc/null-object.png "Null Object")

## Applicability

Use the Null Object pattern when

* You want to avoid explicit null checks and keep the algorithm elegant and easy to read.

## Credits

* [Pattern Languages of Program Design 3](https://www.amazon.com/gp/product/0201310112/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201310112&linkCode=as2&tag=javadesignpat-20&linkId=7372ffb8a4e39a3bb10f199b89aef921)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
