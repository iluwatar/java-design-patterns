---
title: Acyclic Visitor
category: Behavioral
language: es
tag:
 - Extensibility
---

## Propósito

Permitir añadir nuevas funciones a jerarquías de clases existentes sin que estas se vean afectadas, y sin crear los problemáticos círculos de dependencias que son inherentes al patrón GoF (Gang of Four) Visitor.

## Explicación

Ejemplo del mundo real

> Tenemos una jerarquía de clases módem. Los modems de esta jerarquía deben ser visitados por un algoritmo externo basándose en unos filtros (el módem es compatible con Unix o DOS).

En otras palabras

> Acyclic Visitor permite añadir funciones a jerarquías de clases existentes sin modificarlas.

[WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor) dice

> El patrón Acyclic Visitor permite que nuevas funciones sean añadidas a jerarquías de clases existentes sin afectar a las mismas, y sin crear los círculos de dependencias que son inherentes al patrón de visitante (Visitor Pattern) de GangOfFour.

**Ejemplo Programático**

Aquí tenemos la jerarquía `Modem`.

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

Después tenemos la jerarquía `ModemVisitor`.

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

Finalmente, aquí están los "visitors" en acción.

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

Output del programa:

```
    // Hayes modem used with Dos configurator.
    // Zoom modem used with Dos configurator.
    // Only HayesVisitor is allowed to visit Hayes modem
    // Zoom modem used with Unix configurator.
```

## Diagrama de clases

![alt text](./etc/acyclic-visitor.png "Acyclic Visitor")

## Aplicación

Este patrón puede ser usado:

* Cuando necesitas añadir una nueva función a una jerarquía de clases sin que esta se vea afectada o alterada.
* Cuando hay funciones que operan sobre la jerarquía, pero no pertenecen a la jerarquía como tal. Las clases ConfigureForDOS / ConfigureForUnix / ConfigureForX por ejemplo.
* Cuando necesitas ejecutar operaciones muy diferentes en un objeto dependiendo de su tipo.
* Cuando la jerarquía visitada va a ser frecuentemente extendida con derivados de la clase elemento.
* Cuando el proceso de volver a compilar, enlazar, probar o distribuir los derivados de la clase elemento es muy pesado.

## Tutoriales

* [Acyclic Visitor Pattern Example](https://codecrafter.blogspot.com/2012/12/the-acyclic-visitor-pattern.html)

## Consecuencias

Buenas:

* No hay círculos de dependencias entre las jerarquías.
* No es necesario compilar todos los visitantes si se añade uno nuevo.
* No provoca errores de compilación en visitantes existentes si la jerarquía tiene un nuevo miembro.

Malas:

* Viola el [Principio de sustitución de Liskov](https://java-design-patterns.com/principles/#liskov-substitution-principle) al mostrar que puede aceptar todos los visitantes solamente estando interesado en uno en particular.
* Hay que crear una jerarquía de visitantes paralela para todos los miembros de una jerarquía visitable.

## Patrones relacionados

* [Visitor Pattern](https://java-design-patterns.com/patterns/visitor/)

## Créditos

* [Acyclic Visitor by Robert C. Martin](http://condor.depaul.edu/dmumaugh/OOT/Design-Principles/acv.pdf)
* [Acyclic Visitor in WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor)
