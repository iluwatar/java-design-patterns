---
layout: pattern
title: Tolerant Reader
folder: tolerant-reader
permalink: /patterns/tolerant-reader/zh
categories: Integration
language: zh
tags:
 - Decoupling
---

## 目的
Tolerant Reader 是一种集成模式，有助于创建健壮的通信系统。想法
从另一个服务读取数据时要尽可能宽容。这样，当
沟通模式改变，读者切不可打破。

## 解释

真实世界的例子

> 我们将 Rainbowfish 对象持久保存到文件中，稍后需要恢复它们。是什么让它
> 问题在于，rainbowfish 数据结构是版本化的，并且会随着时间的推移而演变。新版本的
> Rainbowfish 也需要能够恢复旧版本。

简单来说

> Tolerant Reader 模式用于在服务之间创建健壮的通信机制。
[Robustness Principle](https://java-design-patterns.com/principles/#robustness-principle) 提到
> 做事要保守，从别人那里接受的要自由。

**程序示例**

这是版本化的“RainbowFish”。注意第二个版本是如何引入附加属性的。
```java
public class RainbowFish implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String name;
  private final int age;
  private final int lengthMeters;
  private final int weightTons;

  /**
   * Constructor.
   */
  public RainbowFish(String name, int age, int lengthMeters, int weightTons) {
    this.name = name;
    this.age = age;
    this.lengthMeters = lengthMeters;
    this.weightTons = weightTons;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public int getLengthMeters() {
    return lengthMeters;
  }

  public int getWeightTons() {
    return weightTons;
  }
}

public class RainbowFishV2 extends RainbowFish {

  private static final long serialVersionUID = 1L;

  private boolean sleeping;
  private boolean hungry;
  private boolean angry;

  public RainbowFishV2(String name, int age, int lengthMeters, int weightTons) {
    super(name, age, lengthMeters, weightTons);
  }

  /**
   * Constructor.
   */
  public RainbowFishV2(String name, int age, int lengthMeters, int weightTons, boolean sleeping,
                       boolean hungry, boolean angry) {
    this(name, age, lengthMeters, weightTons);
    this.sleeping = sleeping;
    this.hungry = hungry;
    this.angry = angry;
  }

  public boolean getSleeping() {
    return sleeping;
  }

  public boolean getHungry() {
    return hungry;
  }

  public boolean getAngry() {
    return angry;
  }
}
```

接下来我们介绍“RainbowFishSerializer”。这是实现 Tolerant Reader 的类
图案。
```java
public final class RainbowFishSerializer {

  private RainbowFishSerializer() {
  }

  public static void writeV1(RainbowFish rainbowFish, String filename) throws IOException {
    var map = Map.of(
        "name", rainbowFish.getName(),
        "age", String.format("%d", rainbowFish.getAge()),
        "lengthMeters", String.format("%d", rainbowFish.getLengthMeters()),
        "weightTons", String.format("%d", rainbowFish.getWeightTons())
    );

    try (var fileOut = new FileOutputStream(filename);
         var objOut = new ObjectOutputStream(fileOut)) {
      objOut.writeObject(map);
    }
  }

  public static void writeV2(RainbowFishV2 rainbowFish, String filename) throws IOException {
    var map = Map.of(
        "name", rainbowFish.getName(),
        "age", String.format("%d", rainbowFish.getAge()),
        "lengthMeters", String.format("%d", rainbowFish.getLengthMeters()),
        "weightTons", String.format("%d", rainbowFish.getWeightTons()),
        "angry", Boolean.toString(rainbowFish.getAngry()),
        "hungry", Boolean.toString(rainbowFish.getHungry()),
        "sleeping", Boolean.toString(rainbowFish.getSleeping())
    );

    try (var fileOut = new FileOutputStream(filename);
         var objOut = new ObjectOutputStream(fileOut)) {
      objOut.writeObject(map);
    }
  }

  public static RainbowFish readV1(String filename) throws IOException, ClassNotFoundException {
    Map<String, String> map;

    try (var fileIn = new FileInputStream(filename);
         var objIn = new ObjectInputStream(fileIn)) {
      map = (Map<String, String>) objIn.readObject();
    }

    return new RainbowFish(
        map.get("name"),
        Integer.parseInt(map.get("age")),
        Integer.parseInt(map.get("lengthMeters")),
        Integer.parseInt(map.get("weightTons"))
    );
  }
}
```

最后是完整的示例。
```java
    var fishV1 = new RainbowFish("Zed", 10, 11, 12);
    LOGGER.info("fishV1 name={} age={} length={} weight={}", fishV1.getName(),
        fishV1.getAge(), fishV1.getLengthMeters(), fishV1.getWeightTons());
    RainbowFishSerializer.writeV1(fishV1, "fish1.out");

    var deserializedRainbowFishV1 = RainbowFishSerializer.readV1("fish1.out");
    LOGGER.info("deserializedFishV1 name={} age={} length={} weight={}",
        deserializedRainbowFishV1.getName(), deserializedRainbowFishV1.getAge(),
        deserializedRainbowFishV1.getLengthMeters(), deserializedRainbowFishV1.getWeightTons());

    var fishV2 = new RainbowFishV2("Scar", 5, 12, 15, true, true, true);
    LOGGER.info(
        "fishV2 name={} age={} length={} weight={} sleeping={} hungry={} angry={}",
        fishV2.getName(), fishV2.getAge(), fishV2.getLengthMeters(), fishV2.getWeightTons(),
        fishV2.getHungry(), fishV2.getAngry(), fishV2.getSleeping());
    RainbowFishSerializer.writeV2(fishV2, "fish2.out");

    var deserializedFishV2 = RainbowFishSerializer.readV1("fish2.out");
    LOGGER.info("deserializedFishV2 name={} age={} length={} weight={}",
        deserializedFishV2.getName(), deserializedFishV2.getAge(),
        deserializedFishV2.getLengthMeters(), deserializedFishV2.getWeightTons());
```

程序输出：
```
fishV1 name=Zed age=10 length=11 weight=12
deserializedFishV1 name=Zed age=10 length=11 weight=12
fishV2 name=Scar age=5 length=12 weight=15 sleeping=true hungry=true angry=true
deserializedFishV2 name=Scar age=5 length=12 weight=15
```

## 类图

![alt text](./etc/tolerant_reader_urm.png "Tolerant Reader")

## 适用性
在以下情况下使用 Tolerant Reader 模式

* 通信模式可以演变和变化，但接收方不应中断

## 鸣谢

* [Martin Fowler - Tolerant Reader](http://martinfowler.com/bliki/TolerantReader.html)
* [Service Design Patterns: Fundamental Design Solutions for SOAP/WSDL and RESTful Web Services](https://www.amazon.com/gp/product/032154420X/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=032154420X&linkId=94f9516e747ac2b449a959d5b096c73c)
