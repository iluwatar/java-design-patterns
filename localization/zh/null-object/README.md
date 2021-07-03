---
layout: pattern
title: Null Object
folder: null-object
permalink: /patterns/null-object/zh
categories: Behavioral
language: zh
tags:
 - Extensibility
---

## 目的
在大多数面向对象的语言中，例如 Java 或 C#，引用可能为空。这些参考资料需要
在调用任何方法之前检查以确保它们不为空，因为方法通常
不能在空引用上调用。而不是使用空引用来传达不存在
对象（例如，一个不存在的客户），使用一个实现预期的对象
接口，但其方法体为空。这种方法相对于工作默认值的优势
实现是空对象是非常可预测的并且没有副作用：它什么都不做。

## 解释

真实世界的例子

> 我们正在从节点构建一个二叉树。有普通节点和“空”节点。穿越
> 树通常不会导致错误，因此我们在必要时使用空对象模式。

简单来说

> 空对象模式优雅地处理“空”对象。

维基百科说

> 在面向对象的计算机编程中，空对象是没有引用值或
> 具有定义的中性（“空”）行为。空对象设计模式描述了这样的用途
> 对象及其行为（或缺乏行为）。

**程序示例**

这是 Node 接口的定义。

```java
public interface Node {

  String getName();

  int getTreeSize();

  Node getLeft();

  Node getRight();

  void walk();
}
```

我们有两个 Node 的实现。正常实现 `NodeImpl` 和 `NullNode`
空节点。

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

然后我们可以无误地构造和遍历二叉树，如下所示。
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

## 类图

![alt text](./etc/null-object.png "Null Object")

## 适用性
在以下情况下使用空对象模式

* 您希望避免显式空检查并保持算法优雅且易于阅读。

## 鸣谢
* [Pattern Languages of Program Design 3](https://www.amazon.com/gp/product/0201310112/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201310112&linkCode=as2&tag=javadesignpat-20&linkId=7372ffb8a4e39a3bb10f199b89aef921)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
