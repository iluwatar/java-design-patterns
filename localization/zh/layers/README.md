---
layout: pattern
title: Layers
folder: layers
permalink: /patterns/layers/zh
pumlformat: svg
categories: Architectural
language: zh
tags:
 - Decoupling
---

## 目的

层是一种架构模式，其中软件职责被划分为不同的
应用层。

## 解释

真实世界的例子

> 考虑一个展示婚礼装饰蛋糕等的网站。而不是网页
> 直接访问数据库，它依赖于提供此信息的服务。这
> 服务然后查询数据层以吸收所需的信息。

简单来说

> 使用层架构模式，不同的关注点位于不同的层上。视图层是
> 只对渲染感兴趣，服务层从各种来源组装所请求的数据，并且
> 数据层从数据存储中获取位。

维基百科说

> 在软件工程中，多层架构（通常称为 n 层架构）或
> 多层架构是一种客户端-服务器架构，其中展示、应用
> 处理和数据管理功能在物理上是分开的。

**程序示例**

在数据层，我们保留蛋糕构建块。 “蛋糕”由层和顶部组成。
```java
@Entity
public class Cake {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(cascade = CascadeType.REMOVE)
  private CakeTopping topping;

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  private Set<CakeLayer> layers;
}
```

服务层提供了`CakeBakingService`，可以方便地访问蛋糕的不同方面。
```java
public interface CakeBakingService {

  void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException;

  List<CakeInfo> getAllCakes();

  void saveNewTopping(CakeToppingInfo toppingInfo);

  List<CakeToppingInfo> getAvailableToppings();

  void saveNewLayer(CakeLayerInfo layerInfo);

  List<CakeLayerInfo> getAvailableLayers();
}
```

在顶部，我们的 `View` 负责渲染蛋糕。
```java
public interface View {

  void render();

}

@Slf4j
public class CakeViewImpl implements View {

  private final CakeBakingService cakeBakingService;

  public CakeViewImpl(CakeBakingService cakeBakingService) {
    this.cakeBakingService = cakeBakingService;
  }

  public void render() {
    cakeBakingService.getAllCakes().forEach(cake -> LOGGER.info(cake.toString()));
  }
}
```

## 类图

![alt text](./etc/layers.png "Layers")

## 适用性

在以下情况下使用 Layers 架构

* 您希望将软件职责清楚地划分为程序的不同部分。
* 您希望防止更改在整个应用程序中传播。
* 您想让您的应用程序更易于维护和测试。

## 鸣谢

* [Pattern Oriented Software Architecture Volume 1: A System of Patterns](https://www.amazon.com/gp/product/0471958697/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0471958697&linkCode=as2&tag=javadesignpat-20&linkId=e3f42d7a2a4cc8c619bbc0136b20dadb)
