---
title: Private Class Data
category: Idiom
language: zh
tag:
 - Data access
---

## 目的

私有类数据设计模式试图通过限制属性的可见性来减少属性的暴露。 通过将它们封装在单个Data对象中，可以减少类属性的数量。

## 解释

真实世界例子

> 想象一下你在为家人做晚餐炖汤。你想阻止家庭成员在你烹饪时偷偷品尝菜品，否则后面可能东西不够吃了。

通俗的说

> 私有类数据模式通过将数据与使用它的方法分离到维护数据状态的类中，从而防止了对不可变数据的操纵。

维基百科说

> 私有类数据是计算机编程中的一种设计模式，用于封装类属性及其操作。

**程序示例**

使用上面炖汤的例子。 首先我们有 `炖汤`类 ，它的属性没有被私有类数据保护，从而使炖菜的成分对类方法易变。

```java
public class Stew {
  private static final Logger LOGGER = LoggerFactory.getLogger(Stew.class);
  private int numPotatoes;
  private int numCarrots;
  private int numMeat;
  private int numPeppers;
  public Stew(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {
    this.numPotatoes = numPotatoes;
    this.numCarrots = numCarrots;
    this.numMeat = numMeat;
    this.numPeppers = numPeppers;
  }
  public void mix() {
    LOGGER.info("Mixing the stew we find: {} potatoes, {} carrots, {} meat and {} peppers",
        numPotatoes, numCarrots, numMeat, numPeppers);
  }
  public void taste() {
    LOGGER.info("Tasting the stew");
    if (numPotatoes > 0) {
      numPotatoes--;
    }
    if (numCarrots > 0) {
      numCarrots--;
    }
    if (numMeat > 0) {
      numMeat--;
    }
    if (numPeppers > 0) {
      numPeppers--;
    }
  }
}
```

现在，我们有了` ImmutableStew`类，其中的数据受`StewData`类保护。 现在，其中的方法无法处理`ImmutableStew`类的数据。

```java
public class StewData {
  private final int numPotatoes;
  private final int numCarrots;
  private final int numMeat;
  private final int numPeppers;
  public StewData(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {
    this.numPotatoes = numPotatoes;
    this.numCarrots = numCarrots;
    this.numMeat = numMeat;
    this.numPeppers = numPeppers;
  }
  public int getNumPotatoes() {
    return numPotatoes;
  }
  public int getNumCarrots() {
    return numCarrots;
  }
  public int getNumMeat() {
    return numMeat;
  }
  public int getNumPeppers() {
    return numPeppers;
  }
}
public class ImmutableStew {
  private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableStew.class);
  private final StewData data;
  public ImmutableStew(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {
    data = new StewData(numPotatoes, numCarrots, numMeat, numPeppers);
  }
  public void mix() {
    LOGGER
        .info("Mixing the immutable stew we find: {} potatoes, {} carrots, {} meat and {} peppers",
            data.getNumPotatoes(), data.getNumCarrots(), data.getNumMeat(), data.getNumPeppers());
  }
}
```

让我们尝试创建每个类的实例并调用其方法：

```java
var stew = new Stew(1, 2, 3, 4);
stew.mix();   // Mixing the stew we find: 1 potatoes, 2 carrots, 3 meat and 4 peppers
stew.taste(); // Tasting the stew
stew.mix();   // Mixing the stew we find: 0 potatoes, 1 carrots, 2 meat and 3 peppers
var immutableStew = new ImmutableStew(2, 4, 3, 6);
immutableStew.mix();  // Mixing the immutable stew we find: 2 potatoes, 4 carrots, 3 meat and 6 peppers
```

## 类图

![alt text](./etc/private-class-data.png "Private Class Data")

## 适用性

在以下情况下使用私有类数据模式

* 您要阻止对类数据成员的写访问。
