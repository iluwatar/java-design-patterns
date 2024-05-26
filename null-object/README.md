---
title: Null Object
category: Behavioral
language: en
tag:
    - Code simplification
    - Decoupling
    - Polymorphism
---

## Also known as

* Active Nothing
* Stub

## Intent

To provide a default behavior for an object, avoiding the need for null checks and enhancing code readability.

## Explanation

Real-world example

> A real-world analogy for the Null Object pattern can be found in the context of customer service. Imagine a customer service system where there are different types of support representatives: human agents and automated bots. When a customer request is received, the system can assign it to a human agent or, if no agents are available, to an automated bot. If neither human agents nor automated bots are available, the system assigns the request to a "Null Representative."
>
> The Null Representative is a placeholder that does nothing but ensures that the system doesn't crash or raise errors due to the absence of a support representative. It provides default responses like "Your request is being processed" without any actual processing, thereby maintaining system stability and avoiding the need for null checks throughout the codebase.       

In plain words

> Null Object pattern handles "empty" objects gracefully.   

Wikipedia says

> In object-oriented computer programming, a null object is an object with no referenced value or with defined neutral ("null") behavior. The null object design pattern describes the uses of such objects and their behavior (or lack thereof).

**Programmatic Example**

We are building a binary tree from nodes. There are ordinary nodes and "empty" nodes. Traversing the tree normally should not cause errors, so we use null object pattern where necessary.

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

We have two implementations of `Node`. The normal implementation `NodeImpl` and `NullNode` for empty nodes.

```java
@Slf4j
public class NodeImpl implements Node {

  private final String name;
  private final Node left;
  private final Node right;

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
var root = new NodeImpl("1", new NodeImpl("11", new NodeImpl("111", NullNode.getInstance(), NullNode.getInstance()), NullNode.getInstance()),
        new NodeImpl("12", NullNode.getInstance(), new NodeImpl("122", NullNode.getInstance(), NullNode.getInstance())));
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

## Applicability

* When you need to provide a default behavior in place of a null object.
* To simplify the client code by eliminating null checks.
* When a default action is preferable to handling a null reference.

## Known Uses

* Logging systems where a NullLogger can be used to avoid null checks.
* Collections that use a NullIterator to handle empty collections gracefully.
* GUI systems where a NullComponent can be used to represent a component that does nothing.

## Consequences

Benefits:

* Eliminates the need for null checks, reducing the risk of NullPointerException.
* Simplifies the client code and enhances readability.
* Promotes the use of polymorphism by handling default behavior through a common interface.

Trade-offs:

* May introduce additional classes, potentially increasing the overall complexity of the system.
* The default behavior might mask potential issues that would otherwise be caught by explicit null handling.

## Related Patterns

* [Strategy](https://java-design-patterns.com/patterns/strategy/): Null Object can be seen as a special case of the Strategy Pattern where the strategy is to do nothing.
* [State](https://java-design-patterns.com/patterns/state/): Similar in that both patterns can handle different states or behaviors; Null Object is like a state that does nothing.
* [Factory](https://java-design-patterns.com/patterns/factory/): Often used to provide Null Objects in place of actual objects.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Pattern Languages of Program Design 3](https://amzn.to/3UZkRF6)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
* [Refactoring: Improving the Design of Existing Code](https://amzn.to/3UJ7etA)
