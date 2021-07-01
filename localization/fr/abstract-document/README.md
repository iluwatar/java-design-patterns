---
layout : pattern
titre : Document abstrait
dossier : abstract-document
permalien : /patterns/abstract-document/
catégories : Structurel
langue : fr
tags :
- Extensibility
---

## Objectif

Utiliser des propriétés dynamiques et atteindre la flexibilité des langages non typés tout en gardant la sécurité des types.

## Explication

Le motif Abstract Document permet de gérer des propriétés supplémentaires, non statiques. Ce pattern
utilise le concept de traits pour permettre la sécurité des types et séparer les propriétés de différentes classes en
ensemble d'interfaces.

Exemple concret

> Considérons une voiture composée de plusieurs pièces. Cependant, nous ne savons pas si la voiture en question possède réellement toutes les pièces, ou seulement certaines d'entre elles. Nos voitures sont dynamiques et extrêmement flexibles.

En clair

> Le modèle de document abstrait permet d'attacher des propriétés aux objets sans qu'ils le sachent.

Selon Wikipedia

> Un modèle de conception structurelle orienté objet pour organiser des objets dans des magasins clé-valeur faiblement typés et exposer les données à l'aide de vues typées.
données à l'aide de vues typées. L'objectif de ce modèle est d'atteindre un haut degré de flexibilité entre les composants.
dans un langage fortement typé où de nouvelles propriétés peuvent être ajoutées à l'arbre d'objets à la volée, sans perdre le support de la sécurité de type.
support de la sécurité de type. Le patron utilise des traits pour séparer les différentes propriétés d'une classe en différentes interfaces.
interfaces.

**Exemple programmatique**

Définissons d'abord les classes de base `Document` et `AbstractDocument`. Elles font en sorte que l'objet contienne une propriété
et un nombre quelconque d'objets enfants.

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
Ensuite, nous définissons un enum `Property` et un ensemble d'interfaces pour le type, le prix, le modèle et les pièces. Cela nous permet de créer
une interface d'apparence statique pour notre classe `Car`.
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

Nous sommes maintenant prêts à présenter la `Car`.

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

Et enfin, voici comment nous construisons et utilisons la `Car` dans un exemple complet.

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

## Diagramme de classes

![alt text](../../../abstract-document/etc/abstract-document.png "Abstract Document Traits and Domain")

## Usage

Utilisez le modèle de document abstrait lorsque

* Il est nécessaire d'ajouter de nouvelles propriétés à la volée.
* Vous voulez un moyen flexible d'organiser le domaine dans une structure arborescente.
* Vous voulez un système plus souple

## Credits

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)
