---
title: Adapter
category: Structural
language: es
tag:
 - Gang of Four
---

## También conocido como
Wrapper

## Propósito
Convertir la interfaz de una clase en otra que espera el cliente. El patrón Adapter permite a clases funcionar en conjunto con otras clases con las que no podrían de otra forma por problemas de compatibilidad.

## Explicación

Ejemplo del mundo real

> Imagina que tienes unas imágenes en una tarjeta de memoria y quieres transferirlas a tu ordenador. Para transferirlas necesitas algún tipo de adaptador compatible con los puertos de tu ordenador que te permita introducir tu tarjeta. En este caso el lector de tarjetas es un adaptador (adapter).
> Otro ejemplo podría ser el famoso adaptador de corriente; un enchufe con tres patas no se puede conectar a una toma de corriente con dos agujeros, necesita un adaptador para hacerlo compatible con la toma de corriente.
> Otro ejemplo más sería un traductor traduciendo palabras de una persona para otra.

En otras palabras

> El patrón Adapter permite envolver un objeto en un adaptador para hacerlo compatible con una clase con la que sería incompatible de otra manera.

Según Wikipedia

> En ingeniería de software el patrón Adapter es un patrón de diseño de software que permite usar la interfaz de una clase existente como otra interfaz diferente. A menudo es utilizado para hacer que clases existentes trabajen con otras clases sin necesidad de modificar su código fuente.

**Ejemplo Programático**

Toma como ejemplo un capitán que solo puede usar botes de remo y no puede navegar en absoluto.

Primero tenemos las interfaces `RowingBoat` (bote de remo) y `FishingBoat` (bote de pesca).

```java
public interface RowingBoat {
  void row();
}

@Slf4j
public class FishingBoat {
  public void sail() {
    LOGGER.info("The fishing boat is sailing");
  }
}
```

Y el capitán espera una implementación de la interfaz `RowingBoat` (bote de remo) para poder moverse.

```java
public class Captain {

  private final RowingBoat rowingBoat;
  // default constructor and setter for rowingBoat
  public Captain(RowingBoat rowingBoat) {
    this.rowingBoat = rowingBoat;
  }

  public void row() {
    rowingBoat.row();
  }
}
```

Ahora supongamos que viene un grupo de piratas y nuestro capitán tiene que escapar, pero solo hay un bote de pesca. Necesitamos crear un adaptador que permita al capitán usar el bote de pesca con sus habilidades para usar botes de remo.

```java
@Slf4j
public class FishingBoatAdapter implements RowingBoat {

  private final FishingBoat boat;

  public FishingBoatAdapter() {
    boat = new FishingBoat();
  }

  @Override
  public void row() {
    boat.sail();
  }
}
```

Y ahora el `Captain` (capitán) puede usar el `FishingBoat` (bote de pesca) para escapar de los piratas.

```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## Diagrama de clases
![alt text](./etc/adapter.urm.png "Adapter class diagram")

## Aplicación
Usa el patrón Adapter cuando

* Quieres usar una clase existente y su interfaz no coincide con la que necesitas.
* Quieres crear una clase reutilizable que coopere con clases que no están relacionadas o con las que su cooperación no estaba planeada, esto es, clases que no necesariamente tienen interfaces compatibles.
* Necesitas usar varias subclases existentes, pero no es práctico adaptar su interfaz creando subclases para todas. Un adaptador puede adaptar la interfaz de la clase padre.
* Muchas aplicaciones que usan librerías de terceros usan adaptadores como capas intermedias entre la aplicación y la librería para desacoplar la aplicación de la librería. Si es necesario usar otra librería solo hace falta crear un adaptador para la nueva librería sin necesidad de modificar el código de la aplicación.

## Tutoriales

* [Dzone](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Refactoring Guru](https://refactoring.guru/design-patterns/adapter/java/example)
* [Baeldung](https://www.baeldung.com/java-adapter-pattern)

## Consecuencias
Los adaptadores de clases y objetos tienen distintas cualidades. Un adaptador de clases

*	Hace la adaptación quedando enlazado a una clase adaptada concreta. Como consecuencia un adaptador de clases no funcionará cuando queramos adaptar una clase y sus subclases.
*	Permite al adaptador modificar el comportamiento de la clase adaptada porque el adaptador es una subclase de la clase adaptada.
*	Usa un solo objeto y no es necesario usar punteros adicionales para referenciar la clase adaptada.

Un adaptador de objetos

*	Permite a un solo adaptador trabajar con varias clases, esto es, con la clase adaptada y todas sus subclases (si tiene alguna). El adaptador también puede añadir funcionalidad a todas las clases adaptadas a la vez.
*	Hace más complicado modificar el comportamiento de la clase adaptada. Sería necesario hacer una subclase de la clase a adaptar y hacer que el adaptador referencie la subclase en lugar de la clase a adaptar.


## Ejemplos del mundo real

* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)


## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
