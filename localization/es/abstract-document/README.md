---
title: Abstract Document
category: Structural
language: es
tag:
  - Extensibility
---

## Propósito

Usar propiedades dinámicas y conseguir la flexibilidad de los lenguajes no tipados manteniendo la seguridad de tipos.

## Explicación

El uso del patrón Abstract Document permite gestionar propiedades no estáticas adicionales. Este patrón usa el concepto
de atributos para permitir seguridad de tipos y propiedades separadas de diferentes clases en un grupo de interfaces.

Ejemplo del mundo real

> Toma como ejemplo un coche que está formado por muchas partes. Sin embargo, no sabemos si el coche tiene todas las
> partes o solo una parte de ellas. Nuestros coches son dinámicos y extremadamente flexibles.

Dicho de otra forma

> El patrón Abstract Document permite añadir propiedades a objetos sin que estos sean conscientes de ello.

Según Wikipedia

> Un patrón de diseño estructural orientado a objetos para organizar objetos en contenedores clave-valor vagamente
> tipados y exponiendo los datos usando vistas tipadas. El propósito del patrón es conseguir un alto grado de flexibilidad
> entre componentes en un lenguaje de tipado fuerte donde nuevas propiedades pueden añadirse al árbol de objetos sobre la
> marcha sin perder el soporte de la seguridad de tipos. El patrón hace uso de atributos para separar diferentes
> propiedades de una clase en distintas interfaces.

**Ejemplo Programático**

Primero definamos las clases base `Document` y `AbstractDocument`. Básicamente, hacen que el objeto contenga un mapa de
propiedades y cualquier número de objetos hijo.

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

A continuación definimos un enum `Property` y un grupo de interfaces para tipo, precio, modelo y partes. Esto nos
permite crear interfaces de apariencia estática para nuestra clase `Car`.

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

Ahora estamos listos para introducir el Coche `Car`.

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

Y finalmente asi es como construimos y usamos el Coche `Car` en un ejemplo completo.

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

## Diagrama de clases

![alt text](./etc/abstract-document.png "Abstract Document Traits and Domain")

## Aplicación

Usar el patrón Abstract Document cuando

* Existe la necesidad de añadir propiedades sobre la marcha.
* Quieres una manera flexible de organizar el dominio en una estructura similar a un árbol.
* Quieres un sistema menos acoplado.

## Créditos

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)
