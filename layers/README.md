---
layout: pattern
title: Layers
folder: layers
permalink: /patterns/layers/
pumlformat: svg
categories: Architectural
tags:
 - Decoupling
---

## Intent
Layers is an architectural pattern where software responsibilities are
 divided among the different layers of the application.

## Explanation

Real world example

> Consider a web site displaying decorated cakes for weddings and such. Instead of the web page directly reaching into the database, it relies on a service to deliver this information. The service then queries the data layer to assimilate the needed information.

In Plain Words

> With Layers architectural pattern different concerns reside on separate layers. View layer is interested only in rendering, service layer assembles the requested data from various sources, and data layer gets the bits from the data storage.

Wikipedia says

> In software engineering, multitier architecture (often referred to as n-tier architecture) or multilayered architecture is a client–server architecture in which presentation, application processing, and data management functions are physically separated.

**Programmatic Example**

On the data layer, we keep our cake building blocks. Cakes consist of layers and topping.

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

The service layer offers CakeBakingService for easy access to different aspects of cakes.

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

On the top we have our view responsible of rendering the cakes.

```java
public interface View {

  void render();

}

public class CakeViewImpl implements View {

  private static final Logger LOGGER = LoggerFactory.getLogger(CakeViewImpl.class);

  private CakeBakingService cakeBakingService;

  public CakeViewImpl(CakeBakingService cakeBakingService) {
    this.cakeBakingService = cakeBakingService;
  }

  public void render() {
    cakeBakingService.getAllCakes().forEach(cake -> LOGGER.info(cake.toString()));
  }
}
```

## Class diagram
![alt text](./etc/layers.png "Layers")

## Applicability
Use the Layers architecture when

* you want clearly divide software responsibilities into different parts of the program
* you want to prevent a change from propagating throughout the application
* you want to make your application more maintainable and testable

## Credits

* [Pattern Oriented Software Architecture Vol I-V](http://www.amazon.com/Pattern-Oriented-Software-Architecture-Volume-Patterns/dp/0471958697)
