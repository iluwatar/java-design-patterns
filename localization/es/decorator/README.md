---
title: Decorator
shortTitle: Decorator
category: Structural
language: es
tag:
  - Gang of Four
  - Extensibility
---

## También conocido como

Wrapper

## Propósito

Adjunte responsabilidades adicionales a un objeto de forma dinámica. Los decoradores proporcionan una alternativa
flexible a la subclase para ampliar la funcionalidad.

## Explicación

Ejemplo real

> En las colinas cercanas vive un trol furioso. Normalmente va con las manos desnudas, pero a veces lleva un arma. Para
> armar al troll no es necesario crear un nuevo troll sino decorarlo dinámicamente con un arma adecuada.

En pocas palabras

> El patrón decorador permite cambiar dinámicamente el comportamiento de un objeto en tiempo de ejecución envolviéndolo
> en un objeto de una clase decoradora.

Wikipedia says

> En programación orientada a objetos, el patrón decorador es un patrón de diseño que permite añadir comportamiento a un
> objeto individual, ya sea de forma estática o dinámica, sin afectar al comportamiento de otros objetos de la misma
> clase. El patrón decorador suele ser útil para adherirse al Principio de Responsabilidad Única, ya que permite dividir
> la funcionalidad entre clases con áreas de interés únicas, así como al Principio Abierto-Cerrado, al permitir extender
> la funcionalidad de una clase sin modificarla.

**Ejemplo programático**

Tomemos el ejemplo del troll. En primer lugar tenemos un `SimpleTroll` que implementa la interfaz `Troll`:

```java
public interface Troll {
  void atacar();
  int getPoderAtaque();
  void huirBatalla();
}

@Slf4j
public class SimpleTroll implements Troll {

  @Override
  public void atacar() {
    LOGGER.info("¡El troll intenta atraparte!");
  }

  @Override
  public int getPoderAtaque() {
    return 10;
  }

  @Override
  public void huirBatalla() {
    LOGGER.info("¡El troll chilla de horror y huye!");
  }
}
```

A continuación, queremos añadir un palo para el troll. Podemos hacerlo de forma dinámica mediante el uso de un
decorador:

```java
@Slf4j
public class TrollConGarrote implements Troll {

  private final Troll decorado;

  public TrollConGarrote(Troll decorado) {
    this.decorado = decorado;
  }

  @Override
  public void atacar() {
    decorado.atacar();
    LOGGER.info("¡El troll te golpea con un garrote!");
  }

  @Override
  public int getPoderAtaque() {
    return decorado.getPoderAtaque() + 10;
  }

  @Override
  public void huirBatalla() {
    decorado.huirBatalla();
  }
}
```

Aquí está el troll en acción:

```java
// troll simple
LOGGER.info("Un troll de aspecto simple se acerca.");
var troll = new SimpleTroll();
troll.atacar();
troll.huirBatalla();
LOGGER.info("Poder del troll simple: {}.\n", troll.getPoderAtaque());

// cambia el comportamiento del troll simple agregando un decorador
LOGGER.info("Un troll con un enorme garrote te sorprende.");
var trollConGarrote = new TrollConGarrote(troll);
trollConGarrote.atacar();
trollConGarrote.huirBatalla();
LOGGER.info("Poder del troll con garrote: {}.\n", trollConGarrote.getPoderAtaque());
```

Salida del programa:

```java
Un troll de aspecto simple se acerca. 
¡El troll intenta atraparte!
¡El troll chilla de horror y huye!
Poder del troll simple: 10.

Un troll con un enorme garrote te sorprende.
¡El troll intenta atraparte!
¡El troll te golpea con un garrote!
¡El troll chilla de horror y huye!
Poder del troll con garrote: 20.
```

## Diagrama de clases

![alt text](./etc/decorator.urm.png "Decorator pattern class diagram")

## Aplicabilidad

Decorator se utiliza para:

* Añadir responsabilidades a objetos individuales de forma dinámica y transparente, es decir, sin
  afectar a otros objetos.
* Para responsabilidades que pueden ser retiradas.
* Cuando la extensión por subclase es poco práctica. A veces es posible un gran número de extensiones independientes
  son posibles y producirían una explosión de subclases para soportar cada combinación. O la definición de una clase
  puede estar oculta o no estar disponible para subclases.

## Tutoriales

* [Decorator Pattern Tutorial](https://www.journaldev.com/1540/decorator-design-pattern-in-java-example)

## Usos conocidos

* [java.io.InputStream](http://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html), [java.io.OutputStream](http://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html),
  [java.io.Reader](http://docs.oracle.com/javase/8/docs/api/java/io/Reader.html)
  y [java.io.Writer](http://docs.oracle.com/javase/8/docs/api/java/io/Writer.html)
* [java.util.Collections#synchronizedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedCollection-java.util.Collection-)
* [java.util.Collections#unmodifiableXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-)
* [java.util.Collections#checkedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#checkedCollection-java.util.Collection-java.lang.Class-)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
