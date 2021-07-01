---
layout: pattern
title: Layers
folder: layers
permalink: /patterns/layers/
pumlformat: svg
categories: Architectural
language: en
tags:
 - Decoupling
---

## Intent

Layers is an architectural pattern where software responsibilities are divided among the different 
layers of the application.

## Explanation

Real world example

> Consider a web site displaying decorated cakes for weddings and such. Instead of the web page 
> directly reaching into the database, it relies on a service to deliver this information. The 
> service then queries the data layer to assimilate the needed information.

In plain words

> With Layers architectural pattern different concerns reside on separate layers. View layer is 
> interested only in rendering, service layer assembles the requested data from various sources, and 
> data layer gets the bits from the data storage.

Wikipedia says

> In software engineering, multitier architecture (often referred to as n-tier architecture) or 
> multilayered architecture is a client–server architecture in which presentation, application 
> processing, and data management functions are physically separated.

**Programmatic Example**

On the data layer, we keep our cake building blocks. `Cake` consist of layers and topping.

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

The service layer offers `CakeBakingService` for easy access to different aspects of cakes.

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

On the top we have our `View` responsible of rendering the cakes.

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

## Class diagram

![alt text](./etc/layers.png "Layers")

## Applicability

Use the Layers architecture when

* You want clearly divide software responsibilities into different parts of the program.
* You want to prevent a change from propagating throughout the application.
* You want to make your application more maintainable and testable.

## Credits

* [Pattern Oriented Software Architecture Volume 1: A System of Patterns](https://www.amazon.com/gp/product/0471958697/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0471958697&linkCode=as2&tag=javadesignpat-20&linkId=e3f42d7a2a4cc8c619bbc0136b20dadb)
