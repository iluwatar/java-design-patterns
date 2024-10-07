---
title: "Patron de conception 'Builder' en Java: Créer des objets personnalisés avec clarté"
shortTitle: Builder
description: "Découvrez le patron de conception Builder en Java, un puissant modèle de création qui simplifie la construction d'objets. Apprenez à séparer la construction d'un objet complexe de sa représentation avec des exemples pratiques et des cas d'utilisation."
category: Creational
language: fr
tag:
  - Gang of Four
  - Instantiation
  - Object composition
---

## Intention du Patron de conception Builder

Le patron de conception Builder en Java, un modèle de création fondamental, permet de construire des objets complexes étape par étape. Il sépare la construction d'un objet complexe de sa représentation afin que le même processus de construction puisse créer différentes représentations.

## Explication détaillée du patron de conception Builder avec des exemples réels

Exemple réel

> Le patron de conception Builder en Java est particulièrement utile dans des scénarios où la création d'un objet nécessite de nombreux paramètres.
> 
> Imaginez que vous créez un sandwich personnalisable dans un restaurant. Le patron de conception Builder dans ce contexte impliquerait un `SandwichBuilder` qui vous permet de spécifier chaque composant du sandwich, comme le type de pain, de viande, de fromage, de légumes, et de condiments. Au lieu de savoir comment assembler le sandwich de zéro, vous utilisez le `SandwichBuilder` pour ajouter chaque composant désiré étape par étape, assurant que vous obtenez exactement le sandwich que vous souhaitez. Cette séparation entre la construction et la représentation finale du produit garantit que le même processus de construction peut produire différents types de sandwiches selon les composants spécifiés.

En termes simples

> Il vous permet de créer différentes versions d'un objet tout en évitant la "pollution" des constructeurs. Utile lorsque plusieurs variantes d'un objet sont possibles, ou lorsqu'il y a beaucoup d'étapes impliquées dans la création d'un objet.

Wikipedia dit

> Le modèle Builder est un modèle de conception de création d'objets qui vise à trouver une solution au problème des constructeurs à paramètres multiples, aussi connu sous le nom d'antipattern du constructeur télescopique.

Avec cela à l'esprit, expliquons ce qu'est l'antipattern du constructeur télescopique. À un moment donné, nous avons tous rencontré un constructeur comme celui-ci:

```java
public Hero(Profession profession,String name,HairType hairType,HairColor hairColor,Armor armor,Weapon weapon){
    // Assignation des valeurs
}
```

Comme vous pouvez le voir, le nombre de paramètres du constructeur peut rapidement devenir écrasant, ce qui rend difficile de comprendre leur agencement. De plus, cette liste de paramètres pourrait continuer à s'allonger si vous décidez d'ajouter plus d'options à l'avenir. Cela s'appelle l'antipattern du constructeur télescopique.

## Exemple programmatique du Modèle Builder en Java

Dans cet exemple du patron de conception Builder en Java, nous construisons différents types d'objets `Hero` avec des attributs variés.

Imaginez un générateur de personnages pour un jeu de rôle. La solution la plus simple est de laisser l'ordinateur générer le personnage pour vous. Cependant, si vous préférez sélectionner manuellement les détails du personnage comme la profession, le genre, la couleur des cheveux, etc., la création de personnage devient un processus étape par étape qui se termine une fois toutes les sélections effectuées.

Une approche plus logique est d'utiliser le modèle Builder. D'abord, considérons le `Hero` que nous voulons créer:

```java
public final class Hero {
    private final Profession profession;
    private final String name;
    private final HairType hairType;
    private final HairColor hairColor;
    private final Armor armor;
    private final Weapon weapon;

    private Hero(Builder builder) {
        this.profession = builder.profession;
        this.name = builder.name;
        this.hairColor = builder.hairColor;
        this.hairType = builder.hairType;
        this.weapon = builder.weapon;
        this.armor = builder.armor;
    }
}
````

Ensuite, nous avons le `Builder`:

```java
  public static class Builder {
    private final Profession profession;
    private final String name;
    private HairType hairType;
    private HairColor hairColor;
    private Armor armor;
    private Weapon weapon;

    public Builder(Profession profession, String name) {
        if (profession == null || name == null) {
            throw new IllegalArgumentException("profession and name can not be null");
        }
        this.profession = profession;
        this.name = name;
    }

    public Builder withHairType(HairType hairType) {
        this.hairType = hairType;
        return this;
    }

    public Builder withHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
        return this;
    }

    public Builder withArmor(Armor armor) {
        this.armor = armor;
        return this;
    }

    public Builder withWeapon(Weapon weapon) {
        this.weapon = weapon;
        return this;
    }

    public Hero build() {
        return new Hero(this);
    }
}
```

Ensuite, il peut être utilisé comme suit:

```java
  public static void main(String[] args) {

    var mage = new Hero.Builder(Profession.MAGE, "Riobard")
            .withHairColor(HairColor.BLACK)
            .withWeapon(Weapon.DAGGER)
            .build();
    LOGGER.info(mage.toString());

    var warrior = new Hero.Builder(Profession.WARRIOR, "Amberjill")
            .withHairColor(HairColor.BLOND)
            .withHairType(HairType.LONG_CURLY).withArmor(Armor.CHAIN_MAIL).withWeapon(Weapon.SWORD)
            .build();
    LOGGER.info(warrior.toString());

    var thief = new Hero.Builder(Profession.THIEF, "Desmond")
            .withHairType(HairType.BALD)
            .withWeapon(Weapon.BOW)
            .build();
    LOGGER.info(thief.toString());
}
```

Sortie du programme:

```
16:28:06.058 [main] INFO com.iluwatar.builder.App -- This is a mage named Riobard with black hair and wielding a dagger.
16:28:06.060 [main] INFO com.iluwatar.builder.App -- This is a warrior named Amberjill with blond long curly hair wearing chain mail and wielding a sword.
16:28:06.060 [main] INFO com.iluwatar.builder.App -- This is a thief named Desmond with bald head and wielding a bow.
```

## Diagramme de classe du Modèle Builder

![Builder](./etc/builder.urm.png "Diagramme de classe du patron Builder")

## Quand utiliser le patron de conception Builder en Java

Utilisez le modèle Builder lorsque

* Le patron de conception Builder est idéal pour les applications Java nécessitant la création d'objets complexes.
* L'algorithme pour créer un objet complexe doit être indépendant des parties qui composent l'objet et de la façon dont elles sont assemblées.
* Le processus de construction doit permettre différentes représentations de l'objet construit.
* Il est particulièrement utile lorsqu'un produit nécessite beaucoup d'étapes pour être créé et lorsque ces étapes doivent être exécutées dans un ordre spécifique.

## Tutoriels Java sur le Modèle Builder


* [Patron de conception Builder (DigitalOcean)](https://www.journaldev.com/1425/builder-design-pattern-in-java)
* [Builder (Refactoring Guru)](https://refactoring.guru/design-patterns/builder)
* [Exploring Joshua Bloch’s Builder design pattern in Java (Java Magazine)](https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java)

## Applications réelles du Modèle Builder en Java

* StringBuilder en Java pour construire des chaînes de caractères.
* java.lang.StringBuffer utilisé pour créer des objets chaîne modifiables.
* Java.nio.ByteBuffer ainsi que des tampons similaires comme FloatBuffer, IntBuffer, et d'autres.
* javax.swing.GroupLayout.Group#addComponent()
* Divers constructeurs d'IHM dans les IDE qui créent des composants d'interface utilisateur.
* Toutes les implémentations de [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [Constructeurs Apache Camel](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
* [Apache Commons Option.Builder](https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/Option.Builder.html)

## Avantages et inconvénients du Modèle Builder

Avantages:

* Plus de contrôle sur le processus de construction par rapport à d'autres modèles de création.
* Permet de construire des objets étape par étape, de différer les étapes de construction ou d'exécuter des étapes de manière récursive.
* Peut construire des objets qui nécessitent un assemblage complexe de sous-objets. Le produit final est détaché des parties qui le composent ainsi que de leur processus d'assemblage.
* Principe de responsabilité unique. Vous pouvez isoler le code de construction complexe de la logique métier du produit.

Inconvénients:

* La complexité générale du code peut augmenter car le modèle nécessite la création de plusieurs nouvelles classes.
* Peut augmenter l'utilisation de la mémoire en raison de la nécessité de créer plusieurs objets builder.

## Patrons de conception Java liés

* [Fabrique abstraite (Abstract Factory)](https://java-design-patterns.com/patterns/abstract-factory/): Peut être utilisé en conjonction avec le patron de conception Builder pour construire des parties d'un objet complexe.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Les builders créent souvent des objets à partir d'un prototype.
* [Step Builder](https://java-design-patterns.com/patterns/step-builder/): Il s'agit d'une variation du modèle Builder qui génère un objet complexe en utilisant une approche étape par étape. Le modèle Step Builder est un bon choix lorsque vous avez besoin de construire un objet avec un grand nombre de paramètres optionnels, et que vous souhaitez éviter l'antipattern du constructeur télescopique.

## Références et crédits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
