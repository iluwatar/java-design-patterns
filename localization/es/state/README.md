---
title: State
shortTitle: State
category: Behavioral
language: es
tag:
 - Gang of Four
---

## También conocido como

Objects for States

## Propósito

Permite que un objeto altere su comportamiento cuando cambia su estado interno. El objeto parecerá
cambiar de clase.

## Explicación

Ejemplo del mundo real

> Al observar un mamut en su hábitat natural parece cambiar su comportamiento en función de la
> situación. Al principio puede parecer tranquilo, pero con el tiempo, cuando detecta una amenaza, se enfada y se vuelve peligroso para su entorno.
> peligroso para su entorno.

En palabras sencillas

> El patrón de estado permite a un objeto cambiar su comportamiento.

Wikipedia dice

> El patrón de estado es un patrón de diseño de software de comportamiento que permite a un objeto alterar su
> comportamiento cuando cambia su estado interno. Este patrón es cercano al concepto de máquinas de estado finito.
> máquinas de estado finito. El patrón de estado puede ser interpretado como un patrón de estrategia, que es capaz de cambiar una estrategia a través de invocaciones de métodos definidos por el usuario.
> estrategia a través de invocaciones de métodos definidos en la interfaz del patrón.

**Ejemplo programático**

Esta es la interfaz de estado y sus implementaciones concretas.

```java
public interface State {

  void onEnterState();

  void observe();
}

@Slf4j
public class PeacefulState implements State {

  private final Mammoth mammoth;

  public PeacefulState(Mammoth mammoth) {
    this.mammoth = mammoth;
  }

  @Override
  public void observe() {
    LOGGER.info("{} is calm and peaceful.", mammoth);
  }

  @Override
  public void onEnterState() {
    LOGGER.info("{} calms down.", mammoth);
  }
}

@Slf4j
public class AngryState implements State {

  private final Mammoth mammoth;

  public AngryState(Mammoth mammoth) {
    this.mammoth = mammoth;
  }

  @Override
  public void observe() {
    LOGGER.info("{} is furious!", mammoth);
  }

  @Override
  public void onEnterState() {
    LOGGER.info("{} gets angry!", mammoth);
  }
}
```

Y aquí está el mammoth que contiene el Estado.

```java
public class Mammoth {

  private State state;

  public Mammoth() {
    state = new PeacefulState(this);
  }

  public void timePasses() {
    if (state.getClass().equals(PeacefulState.class)) {
      changeStateTo(new AngryState(this));
    } else {
      changeStateTo(new PeacefulState(this));
    }
  }

  private void changeStateTo(State newState) {
    this.state = newState;
    this.state.onEnterState();
  }

  @Override
  public String toString() {
    return "The mammoth";
  }

  public void observe() {
    this.state.observe();
  }
}
```

He aquí el ejemplo completo de cómo se comporta el mammoth a lo largo del tiempo.

```java
    var mammoth = new Mammoth();
    mammoth.observe();
    mammoth.timePasses();
    mammoth.observe();
    mammoth.timePasses();
    mammoth.observe();
```

Salida del programa:

```java
    The mammoth gets angry!
    The mammoth is furious!
    The mammoth calms down.
    The mammoth is calm and peaceful.
```

## Diagrama de clases

![alt text](./etc/state_urm.png "State")

## Aplicabilidad

Utiliza el patrón State en cualquiera de los siguientes casos:

* El comportamiento de un objeto depende de su estado, y debe cambiar su comportamiento en tiempo de ejecución dependiendo de ese estado.
* Las operaciones tienen grandes sentencias condicionales multiparte que dependen del estado del objeto. Este estado suele estar representado por una o más constantes enumeradas. A menudo, varias operaciones contendrán esta misma estructura condicional. El patrón State coloca cada rama de la condicional en una clase separada. Esto permite tratar el estado del objeto como un objeto en sí mismo que puede variar independientemente de otros objetos.

Traducción realizada con la versión gratuita del traductor www.DeepL.com/Translator

## Usos conocidos

* [javax.faces.lifecycle.Lifecycle#execute()](http://docs.oracle.com/javaee/7/api/javax/faces/lifecycle/Lifecycle.html#execute-javax.faces.context.FacesContext-) controlled by [FacesServlet](http://docs.oracle.com/javaee/7/api/javax/faces/webapp/FacesServlet.html), the behavior is dependent on current phase of lifecycle.
* [JDiameter - Diameter State Machine](https://github.com/npathai/jdiameter/blob/master/core/jdiameter/api/src/main/java/org/jdiameter/api/app/State.java)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
