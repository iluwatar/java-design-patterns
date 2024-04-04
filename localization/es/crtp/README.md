---
title: Curiously Recurring Template Pattern
language: es
category: Structural
tag:
- Extensibility
- Instantiation
---

## Nombre / clasificación

Curiously Recurring Template Pattern

## También conocido como

Recursive Type Bound, Recursive Generic

## Propósito

Permitir que los componentes derivados hereden ciertas funcionalidades de un componente base que sean compatibles con el tipo derivado.

## Explicación

Un ejemplo real

> Para una promoción de artes marciales mixtas que esté planificando un evento, es crucial asegurarse de que los combates se organicen entre atletas de la misma categoría de peso. Así se evitan los enfrentamientos entre luchadores de tallas muy diferentes, como un peso pesado contra un peso gallo.

En pocas palabras

> Hacer que ciertos métodos dentro de un tipo acepten argumentos específicos de sus subtipos.

Wikipedia dice

> El patrón de plantilla curiosamente recurrente (CRTP) es un modismo, originalmente en C++, en el que una clase X deriva de una instanciación de plantilla de clase usando la propia X como argumento de plantilla.

**Ejemplo programático**

Definamos la interfaz genérica Fighter

```java
public interface Fighter<T> {

  void fight(T t);

}
```

La clase `MMAFighter` se utiliza para crear luchadores que se distinguen por su categoría de peso.

```java
public class MmaFighter<T extends MmaFighter<T>> implements Fighter<T> {

  private final String name;
  private final String surname;
  private final String nickName;
  private final String speciality;

  public MmaFighter(String name, String surname, String nickName, String speciality) {
    this.name = name;
    this.surname = surname;
    this.nickName = nickName;
    this.speciality = speciality;
  }

  @Override
  public void fight(T opponent) {
    LOGGER.info("{} is going to fight against {}", this, opponent);
  }

  @Override
  public String toString() {
    return name + " \"" + nickName + "\" " + surname;
  }
```

Los siguientes son algunos subtipos de `MmaFighter`

```java
class MmaBantamweightFighter extends MmaFighter<MmaBantamweightFighter> {

  public MmaBantamweightFighter(String name, String surname, String nickName, String speciality) {
    super(name, surname, nickName, speciality);
  }

}

public class MmaHeavyweightFighter extends MmaFighter<MmaHeavyweightFighter> {

  public MmaHeavyweightFighter(String name, String surname, String nickName, String speciality) {
    super(name, surname, nickName, speciality);
  }

}
```

Un luchador puede enfrentarse a un oponente de la misma categoría de peso, si el oponente es de una categoría de peso diferente
se produce un error.

```java
MmaBantamweightFighter fighter1 = new MmaBantamweightFighter("Joe", "Johnson", "The Geek", "Muay Thai");
MmaBantamweightFighter fighter2 = new MmaBantamweightFighter("Ed", "Edwards", "The Problem Solver", "Judo");
fighter1.fight(fighter2); // This is fine

MmaHeavyweightFighter fighter3 = new MmaHeavyweightFighter("Dave", "Davidson", "The Bug Smasher", "Kickboxing");
MmaHeavyweightFighter fighter4 = new MmaHeavyweightFighter("Jack", "Jackson", "The Pragmatic", "Brazilian Jiu-Jitsu");
fighter3.fight(fighter4); // This is fine too

fighter1.fight(fighter3); // This will raise a compilation error
```

## Diagrama de clases

![alt text](./etc/crtp.png "Diagrama de clases CRTP")

## Aplicabilidad

Utilice el Patrón de Plantilla Curiosamente Recurrente cuando

* Tienes conflictos de tipos al encadenar métodos en una jerarquía de objetos
* Desea utilizar un método de clase parametrizado que pueda aceptar subclases de la clase como argumentos, permitiendo que se aplique a objetos que heredan de la clase
* Desea que ciertos métodos funcionen sólo con instancias del mismo tipo, por ejemplo, para lograr la comparabilidad mutua.

## Tutoriales

* [El blog de NuaH](https://nuah.livejournal.com/328187.html)
* Respuesta de Yogesh Umesh Vaity a [¿Qué significa "Recursive type bound" en Generics?](https://stackoverflow.com/questions/7385949/what-does-recursive-type-bound-in-generics-mean)

## Usos conocidos

* [java.lang.Enum](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html)

## Créditos

* [How do I decrypt "Enum<E extends Enum\<E>>"?](http://www.angelikalanger.com/GenericsFAQ/FAQSections/TypeParameters.html#FAQ106)
* Chapter 5 Generics, Item 30 in [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
