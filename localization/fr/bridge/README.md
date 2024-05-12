---
title: Bridge
category: Structural
language: fr
tag:
 - Gang of Four
---

## Également connu sous le nom de

Handle/Body

## Intention

Découpler une abstraction de son implémentation afin que les deux puissent varier indépendamment.

## Explication

Exemple concret

> Considérons que vous avez une arme avec différents enchantements, et que vous êtes censé permettre de mélanger 
> différentes armes avec différents enchantements. Que feriez-vous ? Créer plusieurs copies de chaque arme pour chacun
> des enchantements ou simplement créer un enchantement séparé et l'appliquer à l'arme en fonction des besoins ? 
> Le pattron de concepetion Bridge pattern vous permet de faire la deuxième solution.

En clair

> Bridge pattern consiste à préférer la composition à l'héritage. Les détails de la mise en œuvre(implémentation) 
> sont transférés d'une hiérarchie à un autre objet ayant une hiérarchie distincte.

Wikipedia dit

> Bridge pattern est un patron de conception utilisé en génie logiciel qui vise à "découpler une abstraction de sa 
> mise en œuvre(son implémention) de manière à ce que les deux puissent varier de manière indépendante"

**Exemple de programme**

Servons nous de notre exemple d'arme ci-dessus. Nous avons ici la hiérarchie `Weapon` :

```java
public interface Weapon {
  void wield();
  void swing();
  void unwield();
  Enchantment getEnchantment();
}

public class Sword implements Weapon {

  private final Enchantment enchantment;

  public Sword(Enchantment enchantment) {
    this.enchantment = enchantment;
  }

  @Override
  public void wield() {
    LOGGER.info("The sword is wielded.");
    enchantment.onActivate();
  }

  @Override
  public void swing() {
    LOGGER.info("The sword is swinged.");
    enchantment.apply();
  }

  @Override
  public void unwield() {
    LOGGER.info("The sword is unwielded.");
    enchantment.onDeactivate();
  }

  @Override
  public Enchantment getEnchantment() {
    return enchantment;
  }
}

public class Hammer implements Weapon {

  private final Enchantment enchantment;

  public Hammer(Enchantment enchantment) {
    this.enchantment = enchantment;
  }

  @Override
  public void wield() {
    LOGGER.info("The hammer is wielded.");
    enchantment.onActivate();
  }

  @Override
  public void swing() {
    LOGGER.info("The hammer is swinged.");
    enchantment.apply();
  }

  @Override
  public void unwield() {
    LOGGER.info("The hammer is unwielded.");
    enchantment.onDeactivate();
  }

  @Override
  public Enchantment getEnchantment() {
    return enchantment;
  }
}
```

Voici la hiérarchie des enchantements distincts :

```java
public interface Enchantment {
  void onActivate();
  void apply();
  void onDeactivate();
}

public class FlyingEnchantment implements Enchantment {

  @Override
  public void onActivate() {
    LOGGER.info("The item begins to glow faintly.");
  }

  @Override
  public void apply() {
    LOGGER.info("The item flies and strikes the enemies finally returning to owner's hand.");
  }

  @Override
  public void onDeactivate() {
    LOGGER.info("The item's glow fades.");
  }
}

public class SoulEatingEnchantment implements Enchantment {

  @Override
  public void onActivate() {
    LOGGER.info("The item spreads bloodlust.");
  }

  @Override
  public void apply() {
    LOGGER.info("The item eats the soul of enemies.");
  }

  @Override
  public void onDeactivate() {
    LOGGER.info("Bloodlust slowly disappears.");
  }
}
```

Voici les deux hiérarchies en action :

```java
LOGGER.info("The knight receives an enchanted sword.");
var enchantedSword = new Sword(new SoulEatingEnchantment());
enchantedSword.wield();
enchantedSword.swing();
enchantedSword.unwield();

LOGGER.info("The valkyrie receives an enchanted hammer.");
var hammer = new Hammer(new FlyingEnchantment());
hammer.wield();
hammer.swing();
hammer.unwield();
```

Voici la sortie de la console.

```
The knight receives an enchanted sword.
The sword is wielded.
The item spreads bloodlust.
The sword is swung.
The item eats the soul of enemies.
The sword is unwielded.
Bloodlust slowly disappears.
The valkyrie receives an enchanted hammer.
The hammer is wielded.
The item begins to glow faintly.
The hammer is swung.
The item flies and strikes the enemies finally returning to owner's hand.
The hammer is unwielded.
The item's glow fades.
```

## Diagramme de classes

![alt text](../../../bridge/etc/bridge.urm.png "Bridge class diagram")

## Application

Utilisez Bridge pattern lorsque

* Vous souhaitez éviter un lien permanent entre une abstraction et son implémentation. Cela peut être le cas, par exemple, lorsque l'implémentation doit être sélectionnée ou changée au moment de l'exécution.
* Les abstractions et leurs implémentations doivent être extensibles par des classes filles. Dans ce cas, Bridge pattern vous permet de combiner les différentes abstractions et implémentations et de les étendre indépendamment.
* Les changements dans l'implémentation d'une abstraction ne doivent pas avoir d'impact sur les clients, c'est-à-dire que leur code ne doit pas être recompilé.
* Vous avez une prolifération de classes. Une telle hiérarchie de classes indique la nécessité de diviser un objet en deux parties. Rumbaugh utilise le terme de "généralisations imbriquées" pour désigner de telles hiérarchies de classes.
* Vous souhaitez partager une implémentation entre plusieurs objets (peut-être en utilisant le comptage de références), et ce fait doit être caché au client. Un exemple simple est la classe String de Coplien, dans laquelle plusieurs objets peuvent partager la même représentation d'une chaîne de caractères.

## Tutoriel

* [Bridge Pattern Tutorial](https://www.journaldev.com/1491/bridge-design-pattern-java)

## Crédits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
