---
layout: pattern
title: Tolerant Reader
folder: tolerant-reader
permalink: /patterns/tolerant-reader/
categories: Integration
language: en
tags:
 - Decoupling
---

## Intent

Tolerant Reader is an integration pattern that helps creating robust communication systems. The idea 
is to be as tolerant as possible when reading data from another service. This way, when the 
communication schema changes, the readers must not break.

## Explanation

Real world example

> We are persisting rainbowfish objects to file and later on they need to be restored. What makes it 
> problematic is that rainbowfish data structure is versioned and evolves over time. New version of 
> rainbowfish needs to be able to restore old versions as well.     

In plain words

> Tolerant Reader pattern is used to create robust communication mechanisms between services. 

[Robustness Principle](https://java-design-patterns.com/principles/#robustness-principle) says

> Be conservative in what you do, be liberal in what you accept from others.

**Programmatic Example**

Here's the versioned `RainbowFish`. Notice how the second version introduces additional properties.

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

Next we introduce the `RainbowFishSerializer`. This is the class that implements the Tolerant Reader 
pattern.

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

And finally here's the full example in action.

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

Program output:

```
fishV1 name=Zed age=10 length=11 weight=12
deserializedFishV1 name=Zed age=10 length=11 weight=12
fishV2 name=Scar age=5 length=12 weight=15 sleeping=true hungry=true angry=true
deserializedFishV2 name=Scar age=5 length=12 weight=15
```

## Class diagram

![alt text](./etc/tolerant_reader_urm.png "Tolerant Reader")

## Applicability

Use the Tolerant Reader pattern when

* The communication schema can evolve and change and yet the receiving side should not break

## Credits

* [Martin Fowler - Tolerant Reader](http://martinfowler.com/bliki/TolerantReader.html)
* [Service Design Patterns: Fundamental Design Solutions for SOAP/WSDL and RESTful Web Services](https://www.amazon.com/gp/product/032154420X/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=032154420X&linkId=94f9516e747ac2b449a959d5b096c73c)
