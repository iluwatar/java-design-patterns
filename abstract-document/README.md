---
layout: pattern
title: Abstract Document
folder: abstract-document
permalink: /patterns/abstract-document/
categories: Structural
language: en
tags: 
 - Extensibility
---

## Intent

Use dynamic properties and achieve flexibility of untyped languages while keeping type-safety. 

## Explanation

The Abstract Document pattern enables handling additional, non-static properties. This pattern
uses concept of traits to enable type safety and separate properties of different classes into
set of interfaces.

Real world example

>  Consider a car that consists of multiple parts. However we don't know if the specific car really has all the parts, or just some of them. Our cars are dynamic and extremely flexible.

In plain words

> Abstract Document pattern allows attaching properties to objects without them knowing about it.

Wikipedia says

> An object-oriented structural design pattern for organizing objects in loosely typed key-value stores and exposing 
the data using typed views. The purpose of the pattern is to achieve a high degree of flexibility between components 
in a strongly typed language where new properties can be added to the object-tree on the fly, without losing the 
support of type-safety. The pattern makes use of traits to separate different properties of a class into different 
interfaces.

**Programmatic Example**

Let's first define the base classes `Document` and `AbstractDocument`. They basically make the object hold a property
map and any amount of child objects.

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
Next we define an enum `Property` and a set of interfaces for type, price, model and parts. This allows us to create
static looking interface to our `Car` class.

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

Now we are ready to introduce the `Car`.

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

And finally here's how we construct and use the `Car` in a full example.

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

## Class diagram

![alt text](./etc/abstract-document.png "Abstract Document Traits and Domain")

## Applicability

Use the Abstract Document Pattern when

* There is a need to add new properties on the fly
* You want a flexible way to organize domain in tree like structure
* You want more loosely coupled system

## Credits

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)
