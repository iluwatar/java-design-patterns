---
layout: pattern
title: Abstract Document
folder: abstract-document
permalink: /patterns/abstract-document/zh
categories: Structural
language: zh
tags: 
 - Extensibility
---

## 目的

使用动态属性，并在保持类型安全的同时实现非类型化语言的灵活性。

## 解释

抽象文档模式使您能够处理其他非静态属性。 此模式使用特征的概念来实现类型安全，并将不同类的属性分离为一组接口。

真实世界例子

>  考虑由多个部分组成的汽车。 但是，我们不知道特定汽车是否真的拥有所有零件，或者仅仅是零件中的一部分。 我们的汽车是动态而且非常灵活的。

通俗的说

> 抽象文档模式允许在对象不知道的情况下将属性附加到对象。

维基百科说

> 面向对象的结构设计模式，用于组织松散类型的键值存储中的对象并使用类型化的视图公开数据。 该模式的目的是在强类型语言中实现组件之间的高度灵活性，在这种语言中，可以在不丢失类型安全支持的情况下，将新属性动态地添加到对象树中。 该模式利用特征将类的不同属性分成不同的接口。

**程序示例**

让我们首先定义基类`Document`和`AbstractDocument`。 它们基本上使对象拥有属性映射和任意数量的子对象。

```java
public interface Document {

  Void put(String key, Object value);

  Object get(String key);

  <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}

public abstract class AbstractDocument implements Document {

  private final Map<String, Object> properties;

  protected AbstractDocument(Map<String, Object> properties) {
    Objects.requireNonNull(properties, "properties map is required");
    this.properties = properties;
  }

  @Override
  public Void put(String key, Object value) {
    properties.put(key, value);
    return null;
  }

  @Override
  public Object get(String key) {
    return properties.get(key);
  }

  @Override
  public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
    return Stream.ofNullable(get(key))
        .filter(Objects::nonNull)
        .map(el -> (List<Map<String, Object>>) el)
        .findAny()
        .stream()
        .flatMap(Collection::stream)
        .map(constructor);
  }
  ...
}
```
接下来，我们定义一个枚举“属性”和一组类型，价格，模型和零件的接口。 这使我们能够为Car类创建静态外观的界面。

```java
public enum Property {

  PARTS, TYPE, PRICE, MODEL
}

public interface HasType extends Document {

  default Optional<String> getType() {
    return Optional.ofNullable((String) get(Property.TYPE.toString()));
  }
}

public interface HasPrice extends Document {

  default Optional<Number> getPrice() {
    return Optional.ofNullable((Number) get(Property.PRICE.toString()));
  }
}
public interface HasModel extends Document {

  default Optional<String> getModel() {
    return Optional.ofNullable((String) get(Property.MODEL.toString()));
  }
}

public interface HasParts extends Document {

  default Stream<Part> getParts() {
    return children(Property.PARTS.toString(), Part::new);
  }
}
```

现在我们准备介绍`Car`。

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

最后是完整示例中的`Car`构造和使用方式。

```java
    LOGGER.info("Constructing parts and car");

    var wheelProperties = Map.of(
        Property.TYPE.toString(), "wheel",
        Property.MODEL.toString(), "15C",
        Property.PRICE.toString(), 100L);

    var doorProperties = Map.of(
        Property.TYPE.toString(), "door",
        Property.MODEL.toString(), "Lambo",
        Property.PRICE.toString(), 300L);

    var carProperties = Map.of(
        Property.MODEL.toString(), "300SL",
        Property.PRICE.toString(), 10000L,
        Property.PARTS.toString(), List.of(wheelProperties, doorProperties));

    var car = new Car(carProperties);

    LOGGER.info("Here is our car:");
    LOGGER.info("-> model: {}", car.getModel().orElseThrow());
    LOGGER.info("-> price: {}", car.getPrice().orElseThrow());
    LOGGER.info("-> parts: ");
    car.getParts().forEach(p -> LOGGER.info("\t{}/{}/{}",
        p.getType().orElse(null),
        p.getModel().orElse(null),
        p.getPrice().orElse(null))
    );

    // Constructing parts and car
    // Here is our car:
    // model: 300SL
    // price: 10000
    // parts: 
    // wheel/15C/100
    // door/Lambo/300
```

## 类图

![alt text](../../abstract-document/etc/abstract-document.png "Abstract Document Traits and Domain")

## 适用性

使用抽象文档模式当

* 需要即时添加新属性
* 你想要一种灵活的方式来以树状结构组织域
* 你想要更宽松的耦合系统

## 鸣谢

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)
