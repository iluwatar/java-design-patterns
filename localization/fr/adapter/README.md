---
title: Adapter
category: Structural
language: fr
tag:
 - Gang of Four
---

## Également connu sous le nom de
Wrapper

## Intention
Convertir l'interface d'une classe en une autre interface attendue par les clients. Adapter permet à des classes de travailler ensemble
qui ne pourraient pas fonctionner autrement en raison d'interfaces incompatibles.

## Explication

Exemple concrêt

> Supposons que vous avez des photos sur votre carte mémoire et vous devez les transférer sur votre ordinateur. Pour ce faire, vous avez besoin d'un adaptateur qui est compatible aux ports de votre ordinateur pour vous permettre de connecter votre carte memoire à l'ordinateur. dans ce cas le lecteur de carte est un adapteur.
> Un autre exemple serait le fameux adaptateur électrique ; une fiche à trois branches ne peut pas être branchée sur une prise à deux branches, elle doit utiliser un adaptateur électrique qui la rend compatible avec les prises à deux branches.
> Un autre exemple serait celui d'un traducteur qui traduirait les mots prononcés par une personne à une autre.

En clair

> Le patron adapter permet d'envelopper un objet autrement incompatible dans un adaptateur afin de le rendre compatible avec une autre classe.

Wikipedia dit

> En génie logiciel, le pattron de conception  adapter est un pattron de conception logicielle qui permet d'utiliser l'interface d'une classe existante comme une autre interface. Il est souvent utilisé pour faire fonctionner des classes existantes avec d'autres sans modifier leur code source.

**Exemple de programme**

Prenons l'exemple d'un capitaine qui ne peut utiliser que des bateaux à rames et qui ne peut pas du tout naviguer.

Tout d'abord, nous avons les interfaces `RowingBoat` et `FishingBoat`

```java
public interface RowingBoat {
  void row();
}

@Slf4j
public class FishingBoat {
  public void sail() {
    LOGGER.info("The fishing boat is sailing");
  }
}
```

Et le capitaine s'attend à ce qu'une implémentation de l'interface `RowingBoat` soit capable de se déplacer

```java
public class Captain {

  private final RowingBoat rowingBoat;
  // default constructor and setter for rowingBoat
  public Captain(RowingBoat rowingBoat) {
    this.rowingBoat = rowingBoat;
  }

  public void row() {
    rowingBoat.row();
  }
}
```

Supposons maintenant que les pirates arrivent et que notre capitaine doive s'échapper, mais qu'il ne dispose que d'un bateau de pêche. Nous devons créer un adaptateur qui permette au capitaine de faire fonctionner le bateau de pêche avec ses compétences de rameur.

```java
@Slf4j
public class FishingBoatAdapter implements RowingBoat {

  private final FishingBoat boat;

  public FishingBoatAdapter() {
    boat = new FishingBoat();
  }

  @Override
  public void row() {
    boat.sail();
  }
}
```

Et maintenant, `Captain` peut utiliser le `FishingBoat` pour échapper aux pirates.

```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## Diagramme des classes
![alt text](../../../adapter/etc/adapter.urm.png "Adapter class diagram")

## Application
Utiliser le pattron de conception adapter lorsque

* Vous souhaitez utiliser une classe existante, mais son interface ne correspond pas à celle dont vous avez besoin
* Vous souhaitez créer une classe réutilisable qui coopère avec des classes non liées ou imprévues, c'est-à-dire des classes qui n'ont pas nécessairement d'interfaces compatibles.
* Vous devez utiliser plusieurs sous-classes existantes, mais il n'est pas pratique d'adapter leur interface en les sous-classant toutes. Un adaptateur d'objet peut adapter l'interface de sa classe mère.
* La plupart des applications utilisant des bibliothèques tierces utilisent des adaptateurs comme couche intermédiaire entre l'application et la bibliothèque tierce afin de découpler l'application de la bibliothèque. Si une autre bibliothèque doit être utilisée, il suffit de créer un adaptateur pour la nouvelle bibliothèque sans avoir à modifier le code de l'application.

## Tutoriels

* [Dzone](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Refactoring Guru](https://refactoring.guru/design-patterns/adapter/java/example)
* [Baeldung](https://www.baeldung.com/java-adapter-pattern)

## Conséquences
Les adaptateurs de classe et d'objet présentent différents avantages. Un adaptateur de classe :

*	Adapte l'adaptateur à la cible en s'engageant dans une classe concrète d'adaptateur. Par conséquent, un adaptateur de classe ne fonctionnera pas si nous voulons adapter une classe et toutes ses sous-classes.
*	Permet à l'adaptateur de surcharger certains comportements de l'adaptaté puisque l'adaptateur est une sous-classe de l'adaptaté.
*	N'introduit qu'un seul objet, et aucune indirection de pointeur supplémentaire n'est nécessaire pour accéder à l'adaptateur.

Un adaptateur d'objet :

*	Permet à un seul adaptateur de travailler avec de nombreux adaptatés, c'est-à-dire l'adaptateur lui-même et toutes ses sous-classes (le cas échéant). L'adaptateur peut également ajouter des fonctionnalités à tous les adaptatés en même temps.
*	Rend plus difficile la modification du comportement de l'adapté. Il faudra créer des sous classes de l'adapta et faire en sorte que l'adaptateur se réfère à la sous-classe plutôt qu'à l'adapté lui-même.


## Utilisations connues

* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)


## Crédits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
