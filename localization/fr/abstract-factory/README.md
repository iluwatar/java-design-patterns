---
layout : pattern
titre : Fabrique Abstraite
dossier : abstract-factory
permalien : /patterns/abstract-factory/
catégories : Création
langue : fr
tags :
- Gang of Four
---

## Aussi connu sous le nom de

Kit

## Objectif

Fournir une interface pour la création de familles d'objets liés ou dépendants
sans spécifier leurs classes concrètes.

## Explication

Exemple concret

> Pour créer un royaume, nous avons besoin d'objets ayant un thème commun. Le royaume elfique a besoin d'un roi elfique, d'un château elfique et d'une armée elfique, tandis que le royaume orciste a besoin d'un roi orciste, d'un château orciste et d'une armée orciste. Il existe une dépendance entre les objets du royaume.

En clair

> Une fabrique de fabriques ; une fabrique qui regroupe des fabriques individuelles mais liées/dépendantes sans spécifier leurs classes concrètes.

Selon Wikipedia

> Le modèle d'usine abstrait fournit un moyen d'encapsuler un groupe d'usines individuelles qui ont un thème commun sans spécifier leurs classes concrètes.

**Exemple programmatique**

Traduction de l'exemple du royaume ci-dessus. Tout d'abord, nous avons quelques interfaces et implémentations pour les objets dans le
royaume.

```java
public interface Castle {
    String getDescription();
}

public interface King {
    String getDescription();
}

public interface Army {
    String getDescription();
}

// Elven implementations ->
public class ElfCastle implements Castle {
    static final String DESCRIPTION = "This is the elven castle!";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
public class ElfKing implements King {
    static final String DESCRIPTION = "This is the elven king!";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
public class ElfArmy implements Army {
    static final String DESCRIPTION = "This is the elven Army!";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

// Les implémentations d'Orcish sont similaires -> ...

```

Ensuite, nous avons l'abstraction et les implémentations pour la fabrique de royaume.

```java
public interface KingdomFactory {
  Castle createCastle();
  King createKing();
  Army createArmy();
}

public class ElfKingdomFactory implements KingdomFactory {
  public Castle createCastle() {
    return new ElfCastle();
  }
  public King createKing() {
    return new ElfKing();
  }
  public Army createArmy() {
    return new ElfArmy();
  }
}

public class OrcKingdomFactory implements KingdomFactory {
  public Castle createCastle() {
    return new OrcCastle();
  }
  public King createKing() {
    return new OrcKing();
  }
  public Army createArmy() {
    return new OrcArmy();
  }
}
```

Nous avons maintenant une fabrique abstraite qui nous permet de créer une famille d'objets apparentés, par exemple, la fabrique du royaume elfique crée le château, le roi et l'armée elfiques, etc.

```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

Sortie du programme :

```java
This is the elven castle!
This is the elven king!
This is the elven Army!
```

Maintenant, nous pouvons concevoir une fabrique pour nos différentes fabriques de royaume. Dans cet exemple, nous avons créé `FactoryMaker`, responsable du retour d'une instance de `ElfKingdomFactory` ou de `OrcKingdomFactory`.  
Le client peut utiliser `FactoryMaker` pour créer la fabrique concrète désirée qui, à son tour, produira différents objets concrets (dérivés de `Army`, `King`, `Castle`).  
Dans cet exemple, nous avons également utilisé un enum pour paramétrer quel type de fabrique de royaume le client va demander.

```java
public static class FactoryMaker {

  public enum KingdomType {
    ELF, ORC
  }

  public static KingdomFactory makeFactory(KingdomType type) {
    switch (type) {
      case ELF:
        return new ElfKingdomFactory();
      case ORC:
        return new OrcKingdomFactory();
      default:
        throw new IllegalArgumentException("KingdomType not supported.");
    }
  }
}

public static void main(String[] args) {
  var app = new App();

  LOGGER.info("Elf Kingdom");
  app.createKingdom(FactoryMaker.makeFactory(KingdomType.ELF));
  LOGGER.info(app.getArmy().getDescription());
  LOGGER.info(app.getCastle().getDescription());
  LOGGER.info(app.getKing().getDescription());

  LOGGER.info("Orc Kingdom");
  app.createKingdom(FactoryMaker.makeFactory(KingdomType.ORC));
  -- similar use of the orc factory
}
```

## Diagramme de classes

![alt text](../../../abstract-factory/etc/abstract-factory.urm.png "Abstract Factory class diagram")


## Usage

Utilisez le modèle Abstract Factory lorsque

* Le système doit être indépendant de la façon dont ses produits sont créés, composés et représentés.
* Le système doit être configuré avec l'une des multiples familles de produits.
* La famille d'objets de produits connexes est conçue pour être utilisée ensemble, et vous devez faire respecter cette contrainte.
* Vous voulez fournir une bibliothèque de classes de produits, et vous voulez révéler seulement leurs interfaces, pas leurs implémentations.
* La durée de vie de la dépendance est conceptuellement plus courte que la durée de vie du consommateur.
* Vous avez besoin d'une valeur d'exécution pour construire une dépendance particulière.
* Vous voulez décider quel produit appeler à partir d'une famille au moment de l'exécution.
* Vous devez fournir un ou plusieurs paramètres connus uniquement à l'exécution avant de pouvoir résoudre une dépendance.
* Lorsque vous avez besoin de cohérence entre les produits
* Vous ne voulez pas modifier le code existant lorsque vous ajoutez de nouveaux produits ou familles de produits au programme.

Exemples de cas d'utilisation

* Sélection de l'appel à l'implémentation appropriée de FileSystemAcmeService ou DatabaseAcmeService ou NetworkAcmeService au moment de l'exécution.
* L'écriture de cas de tests unitaires devient beaucoup plus facile
* Outils d'interface utilisateur pour différents systèmes d'exploitation

## Conséquences

* L'injection de dépendances en Java masque les dépendances des classes de service, ce qui peut entraîner des erreurs d'exécution qui auraient été détectées à la compilation.
* Alors que le modèle est excellent pour créer des objets prédéfinis, l'ajout de nouveaux objets peut être un défi.
* Le code devient plus compliqué qu'il ne devrait l'être car beaucoup de nouvelles interfaces et classes sont introduites avec le modèle.

## Tutoriels

* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## Applications connues

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## Pattern Associé

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)

