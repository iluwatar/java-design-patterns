---
layout: pattern
title: Composite Entity
folder: composite-entity
permalink: /patterns/composite-entity/
categories: Structural
tags:
 - Enterprise Integration Pattern
---

## 含义

复合实体模式用于对一组相关联的持久化对象进行建模、描述和管理，用于取代对这组对象描述为单独粒度的实体。

## 解释

现实例子

> 对于一个控制台对象，需要管理许多接口功能。通过使用复合实体模式，将消息对象、信号对象等依赖性对象组合在一起，直接使用单个对象对其进行控制。

简单地说

> 复合实体模式允许使用一个统一对象来管理一组相互关联的对象

**编程示例**

我们需要一个通用的解决方案来解决上述的控制台问题。我们引入了以下的通用复合对象。

```java
public abstract class DependentObject<T> {

  T data;

  public void setData(T message) {
    this.data = message;
  }

  public T getData() {
    return data;
  }
}

public abstract class CoarseGrainedObject<T> {

  DependentObject<T>[] dependentObjects;

  public void setData(T... data) {
    IntStream.range(0, data.length).forEach(i -> dependentObjects[i].setData(data[i]));
  }

  public T[] getData() {
    return (T[]) Arrays.stream(dependentObjects).map(DependentObject::getData).toArray();
  }
}

```

专用的 `console` 复合实体继承自这个基类，如下所示。

```java
public class MessageDependentObject extends DependentObject<String> {

}

public class SignalDependentObject extends DependentObject<String> {

}

public class ConsoleCoarseGrainedObject extends CoarseGrainedObject<String> {

  @Override
  public String[] getData() {
    super.getData();
    return new String[]{
        dependentObjects[0].getData(), dependentObjects[1].getData()
    };
  }

  public void init() {
    dependentObjects = new DependentObject[]{
        new MessageDependentObject(), new SignalDependentObject()};
  }
}

public class CompositeEntity {

  private final ConsoleCoarseGrainedObject console = new ConsoleCoarseGrainedObject();

  public void setData(String message, String signal) {
    console.setData(message, signal);
  }

  public String[] getData() {
    return console.getData();
  }
}
```

现在我们使用 `console` 复合实体来进行消息对象、信号对象的分配。

```java
var console = new CompositeEntity();
console.init();
console.setData("No Danger", "Green Light");
Arrays.stream(console.getData()).forEach(LOGGER::info);
console.setData("Danger", "Red Light");
Arrays.stream(console.getData()).forEach(LOGGER::info);
```

## 类图

![alt text](../../composite-entity/etc/composite_entity.urm.png "Composite Entity Pattern")

## 适用场景

复合实体模式适用于以下场景：

* 你想要通过一个对象来管理多个依赖对象，已调整对象之间的细化程度。同时将依赖对象的生命周期托管到这个粗粒度的复合实体对象。
## 引用

* [Composite Entity Pattern in wikipedia](https://en.wikipedia.org/wiki/Composite_entity_pattern)