---
title: Acyclic Visitor
category: Behavioral
language: fr
tag:
 - Extensibility
---

## Intention

Permettre l'ajout de nouvelles fonctions à des hiérarchies de classes existantes sans affecter ces hiérarchies et sans
créer de cycles de dépendance gênants inhérents au pqtron de conception  GoF visitor.

## Explication

Exemple concret

> Nous disposons d'une hiérarchie de classes de modems. Les modems de cette hiérarchie doivent être visités 
> par un algorithme externe basé sur des critères de filtrage (s'agit-il d'un modem compatible Unix ou DOS).

En clair

> Le visiteur acyclique permet d'ajouter des fonctions à des hiérarchies de classes existantes sans modifier ces dernières.

[WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor) dit

> Le modèle du visiteur acyclique permet d'ajouter de nouvelles fonctions aux hiérarchies de classes existantes
> sans affecter ces hiérarchies et sans créer les cycles de dépendance inhérents au patron de conception GangOfFour visitor.

**Exemple de Programme**

Ci-dessous la hierarchie `Modem`.

```java
public abstract class Modem {
  public abstract void accept(ModemVisitor modemVisitor);
}

public class Zoom extends Modem {
  ...
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof ZoomVisitor) {
      ((ZoomVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Only ZoomVisitor is allowed to visit Zoom modem");
    }
  }
}

public class Hayes extends Modem {
  ...
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof HayesVisitor) {
      ((HayesVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Only HayesVisitor is allowed to visit Hayes modem");
    }
  }
}
```

Ensuite, nous introduisons la hiérarchie `ModemVisitor`.

```java
public interface ModemVisitor {
}

public interface HayesVisitor extends ModemVisitor {
  void visit(Hayes hayes);
}

public interface ZoomVisitor extends ModemVisitor {
  void visit(Zoom zoom);
}

public interface AllModemVisitor extends ZoomVisitor, HayesVisitor {
}

public class ConfigureForDosVisitor implements AllModemVisitor {
  ...
  @Override
  public void visit(Hayes hayes) {
    LOGGER.info(hayes + " used with Dos configurator.");
  }
  @Override
  public void visit(Zoom zoom) {
    LOGGER.info(zoom + " used with Dos configurator.");
  }
}

public class ConfigureForUnixVisitor implements ZoomVisitor {
  ...
  @Override
  public void visit(Zoom zoom) {
    LOGGER.info(zoom + " used with Unix configurator.");
  }
}
```

Enfin, voici les visitors en action.

```java
    var conUnix = new ConfigureForUnixVisitor();
    var conDos = new ConfigureForDosVisitor();
    var zoom = new Zoom();
    var hayes = new Hayes();
    hayes.accept(conDos);
    zoom.accept(conDos);
    hayes.accept(conUnix);
    zoom.accept(conUnix);   
```

Sortie du programme :

```
    // Hayes modem used with Dos configurator.
    // Zoom modem used with Dos configurator.
    // Only HayesVisitor is allowed to visit Hayes modem
    // Zoom modem used with Unix configurator.
```

## Diagramme de classes

![alt text](../../../acyclic-visitor/etc/acyclic-visitor.png "Acyclic Visitor")

## Application

Ce patron de conception peut être utilisé:

* Pour ajouter ne nouvelle fonction à une hiérarchie existante sans avoir à modifier ou à affecter cette hiérarchie.
* Lorsqu'il existe des fonctions qui opèrent sur une hiérarchie, mais qui n'appartiennent pas à la hiérarchie elle-même. Par exemple, le problème ConfigureForDOS / ConfigureForUnix / ConfigureForX.
* Lorsque vous devez éffectuer des opérations très différentes sur un objet en fonction de son type.
* Lorsque la hiérarchie des classes visitées sera fréquemment étendue avec de nouveaux dérivés de la classe Element.
*Lorsque la recompilation, l'interconnexion, le réessai ou la redistribution des dérivés de Element sont très coûteux.

## Tutoriel

* [Acyclic Visitor Pattern Example](https://codecrafter.blogspot.com/2012/12/the-acyclic-visitor-pattern.html)

## Consequences

Le bon côté:

* Pas de cycles de dépendance entre les hiérarchies de classes.
* Il n'est pas nécessaire de recompiler tous les visiteurs si un nouveau visiteur est ajouté.
* Ne provoque pas d'échec de compilation chez les visiteurs existants si la hiérarchie des classes a un nouveau membre.

Le mauvais : 

* viole le [principe de substitution de Liskov](https://java-design-patterns.com/principles/#liskov-substitution-principle) en montrant qu'il peut accepter tous les visiteurs alors qu'il ne s'intéresse en réalité qu'à certains d'entre eux.
* Une hiérarchie parallèle de visiteurs doit être créée pour tous les membres de la hiérarchie des classes visitables.

## Patron de conception associé

* [Pqtron de conception Visitor](https://java-design-patterns.com/patterns/visitor/)

## Crédits

* [Acyclic Visitor by Robert C. Martin](http://condor.depaul.edu/dmumaugh/OOT/Design-Principles/acv.pdf)
* [Acyclic Visitor in WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor)
