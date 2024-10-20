---
title: Dependency Injection
shortTitle: Dependency Injection
category: Creational
language: es
tag:
  - Decoupling
---

## Propósito

La inyección de dependencias es un patrón de diseño de software en el que una o más dependencias (o servicios)
se inyectan, o se pasan por referencia, a un objeto dependiente (o cliente) y pasan a formar parte del estado del
cliente. El patrón separa la creación de las dependencias de un cliente de su propio comportamiento,
lo que permite que los diseños de los programas estén poco acoplados y sigan los principios de inversión de control y
responsabilidad única.

## Explicación

Ejemplo del mundo real

> Al viejo mago le gusta llenar su pipa y fumar tabaco de vez en cuando. Sin embargo, no quiere depender de una sola
> marca de tabaco, sino que le gusta poder disfrutar de todas ellas de manera intercambiable.

En palabras sencillas

> La Inyección de Dependencias separa la creación de las dependencias del cliente de su propio comportamiento.

Wikipedia dice

> En ingeniería de software, la inyección de dependencias es una técnica en la que un objeto recibe otros objetos de los
> que depende. Estos otros objetos se llaman dependencias.

**Ejemplo programático**

Presentemos primero la interfaz tabaco `Tobacco` y las marcas concretas.

```java
@Slf4j
public abstract class Tobacco {

  public void smoke(Wizard wizard) {
    LOGGER.info("{} smoking {}", wizard.getClass().getSimpleName(),
        this.getClass().getSimpleName());
  }
}

public class SecondBreakfastTobacco extends Tobacco {
}

public class RivendellTobacco extends Tobacco {
}

public class OldTobyTobacco extends Tobacco {
}
```

A continuación se muestra la interfaz `Wizard` y su implementación.

```java
public interface Wizard {

  void smoke();
}

public class AdvancedWizard implements Wizard {

  private final Tobacco tobacco;

  public AdvancedWizard(Tobacco tobacco) {
    this.tobacco = tobacco;
  }

  @Override
  public void smoke() {
    tobacco.smoke(this);
  }
}
```

Y por último podemos demostrar lo fácil que es darle al viejo mago cualquier marca de tabaco `Tobacco`.

```java
    var advancedWizard = new AdvancedWizard(new SecondBreakfastTobacco());
    advancedWizard.smoke();
```

## Diagrama de Clases

![alt text](./etc/dependency-injection.png "Dependency Injection")

## Aplicabilidad

Utilice el patrón de Inyección de Dependencia cuando:

* Cuando necesites eliminar el conocimiento de la implementación concreta del objeto.
* Para permitir pruebas unitarias de clases de forma aislada utilizando objetos simulados o stubs.

## Créditos

* [Dependency Injection Principles, Practices, and Patterns](https://www.amazon.com/gp/product/161729473X/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=161729473X&linkId=57079257a5c7d33755493802f3b884bd)
* [Clean Code: A Handbook of Agile Software Craftsmanship](https://www.amazon.com/gp/product/0132350882/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0132350882&linkCode=as2&tag=javadesignpat-20&linkId=2c390d89cc9e61c01b9e7005c7842871)
* [Java 9 Dependency Injection: Write loosely coupled code with Spring 5 and Guice](https://www.amazon.com/gp/product/1788296257/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=1788296257&linkId=4e9137a3bf722a8b5b156cce1eec0fc1)
* [Google Guice: Agile Lightweight Dependency Injection Framework](https://www.amazon.com/gp/product/1590599977/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1590599977&linkId=3b10c90b7ba480a1b7777ff38000f956)
