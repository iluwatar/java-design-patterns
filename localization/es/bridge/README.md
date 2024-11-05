---
title: Bridge
shortTitle: Bridge
category: Structural
language: es
tag:
  - Gang of Four
---

## También conocido como

Handle/Body

## Intención

Desacoplar una abstracción de su implementación para que ambas puedan variar de forma independiente.

## Explicación

Real-world example

> Imagina que tienes un arma con diferentes encantamientos, y se supone que debes permitir la mezcla de diferentes armas
> con diferentes encantamientos. ¿Qué harías? ¿Crearías múltiples copias de cada una de las armas para cada uno de los
> encantamientos o simplemente crearías un encantamiento separado y lo establecerías para el arma según sea necesario?
> El
> patrón Bridge te permite hacer lo segundo.

En palabras sencillas

> El patrón Bridge se trata de preferir la composición sobre la herencia. Los detalles de implementación se empujan de
> una jerarquía a otro objeto con una jerarquía separada.

Wikipedia dice

> El patrón bridge es un patrón de diseño utilizado en ingeniería de software que tiene como objetivo "desacoplar una
> abstracción de su implementación para que las dos puedan variar independientemente"

**Ejemplo programático**

Transladando nuestro ejemplo de arma de arriba. Aquí tenemos la interfaz arma `Weapon`:

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

Aquí está la interfaz de encantamientos `Enchantment` separada:

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

Aquí están ambas interfaces en acción:

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

Aquí está la salida en consola.

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

## Diagrama de Clases

![alt text](./etc/bridge.urm.png "Bridge diagrama de clases")

## Aplicabilidad

Usa el patrón Bridge cuando

* Quieres evitar una vinculación permanente entre una abstracción y su implementación. Este podría ser el caso, por
  ejemplo, cuando la implementación debe ser seleccionada o cambiada en tiempo de ejecución.
* Tanto las abstracciones como sus implementaciones deberían ser extensibles mediante la herencia. En este caso, el
  patrón Bridge te permite combinar las diferentes abstracciones e implementaciones y extenderlas de forma
  independiente.
* Los cambios en la implementación de una abstracción no deberían tener impacto en los clientes; es decir, su código no
  debería tener que ser recompilado.
* Tienes una proliferación de clases. Tal jerarquía de clases indica la necesidad de dividir un objeto en dos partes.
  Rumbaugh usa el término "generalizaciones anidadas" para referirse a tales jerarquías de clases.
* Quieres compartir una implementación entre varios objetos (quizás usando conteo de referencias), y este hecho debería
  ser ocultado al cliente. Un ejemplo simple es la clase String de Coplien, en la que varios objetos pueden compartir la
  misma representación de cadena.

## Tutoriales

* [Bridge Pattern Tutorial](https://www.journaldev.com/1491/bridge-design-pattern-java)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)