---
title: Template method
category: Behavioral
language: es
tag:
 - Gang of Four
---

## Propósito

Define el esqueleto de un algoritmo en una operación, difiriendo algunos pasos a subclases. Plantilla
Método que permite a las subclases redefinir ciertos pasos de un algoritmo sin cambiar la estructura del algoritmo.
del algoritmo.

## Explicación

Ejemplo del mundo real

> Los pasos generales para robar un objeto son los mismos. Primero, eliges al objetivo, luego lo confundes...
> y finalmente, robas el objeto. Sin embargo, hay muchas maneras de implementar estos
> pasos.

En palabras sencillas

> El patrón Template Method esboza los pasos generales en la clase padre y deja que las implementaciones hijas concretas definan los detalles.

Wikipedia dice

> En programación orientada a objetos, el método de plantillas es uno de los patrones de diseño de comportamiento
> identificados por Gamma et al. en el libro Design Patterns. El método de plantilla es un método en una
> superclase, normalmente una superclase abstracta, y define el esqueleto de una operación en términos de
> una serie de pasos de alto nivel. Estos pasos son a su vez implementados por métodos de ayuda adicionales
> en la misma clase que el método de plantilla.

**Ejemplo programático**

Presentemos primero la clase de método de plantilla junto con sus implementaciones concretas.
Para asegurarse de que las subclases no sobrescriben el método de plantilla, el método de plantilla (en nuestro caso
método `steal`) debe ser declarado `final`, de lo contrario el esqueleto definido en la clase base podría
ser sobreescrito en subclases.

```java
@Slf4j
public abstract class StealingMethod {

  protected abstract String pickTarget();

  protected abstract void confuseTarget(String target);

  protected abstract void stealTheItem(String target);

  public final void steal() {
    var target = pickTarget();
    LOGGER.info("The target has been chosen as {}.", target);
    confuseTarget(target);
    stealTheItem(target);
  }
}

@Slf4j
public class SubtleMethod extends StealingMethod {

  @Override
  protected String pickTarget() {
    return "shop keeper";
  }

  @Override
  protected void confuseTarget(String target) {
    LOGGER.info("Approach the {} with tears running and hug him!", target);
  }

  @Override
  protected void stealTheItem(String target) {
    LOGGER.info("While in close contact grab the {}'s wallet.", target);
  }
}

@Slf4j
public class HitAndRunMethod extends StealingMethod {

  @Override
  protected String pickTarget() {
    return "old goblin woman";
  }

  @Override
  protected void confuseTarget(String target) {
    LOGGER.info("Approach the {} from behind.", target);
  }

  @Override
  protected void stealTheItem(String target) {
    LOGGER.info("Grab the handbag and run away fast!");
  }
}
```

Aquí está la clase ladrón halfling que contiene el método de plantilla.

```java
public class HalflingThief {

  private StealingMethod method;

  public HalflingThief(StealingMethod method) {
    this.method = method;
  }

  public void steal() {
    method.steal();
  }

  public void changeMethod(StealingMethod method) {
    this.method = method;
  }
}
```

Y por último, mostramos cómo el ladrón halfling utiliza los diferentes métodos de robo.

```java
    var thief = new HalflingThief(new HitAndRunMethod());
    thief.steal();
    thief.changeMethod(new SubtleMethod());
    thief.steal();
```

## Diagrama de clases

![alt text](./etc/template_method_urm.png "Template Method")

## Aplicabilidad

El patrón Template Method debería utilizarse

* Para implementar las partes invariantes de un algoritmo una vez y dejar que las subclases implementen el comportamiento que puede variar.
* Cuando el comportamiento común entre subclases debe ser factorizado y localizado en una clase común para evitar la duplicación de código. Este es un buen ejemplo de "refactorizar para generalizar", tal y como lo describen Opdyke y Johnson. Primero se identifican las diferencias en el código existente y luego se separan las diferencias en nuevas operaciones. Por último, se sustituye el código diferente por un método de plantilla que llama a una de estas nuevas operaciones
* Para controlar las extensiones de las subclases. Puede definir un método de plantilla que llame a operaciones "gancho" en puntos específicos, permitiendo así extensiones sólo en esos puntos

## Tutoriales

* [Template-method Pattern Tutorial](https://www.journaldev.com/1763/template-method-design-pattern-in-java)

## Usos conocidos

* [javax.servlet.GenericServlet.init](https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/GenericServlet.html#init--):
El método `GenericServlet.init(ServletConfig config)` llama al método sin parámetros `GenericServlet.init()` que está pensado para ser sobreescrito en subclases.
El método `GenericServlet.init(ServletConfig config)` es el método plantilla en este ejemplo.

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
