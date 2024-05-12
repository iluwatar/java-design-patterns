---
title: Composite
category: Structural
language: es
tag:
  - Gang of Four
  - Object composition
  - Recursion
---

## También conocido como

* Object Tree
* Composite Structure

## Propósito

Componga objetos en estructuras de árbol para representar jerarquías parte-todo. Composite permite a los clientes tratar
objetos individuales y composiciones de objetos de manera uniforme.

## Explanation

Real-world example

> Cada frase se compone de palabras que a su vez se componen de caracteres. Cada uno de estos objetos es imprimible y
> puede tener algo impreso antes o después de ellos, como la frase siempre termina con punto final y la palabra siempre
> tiene espacio antes de ella.

En pocas palabras

> El patrón compuesto permite a los clientes tratar uniformemente los objetos individuales.

Wikipedia dice

> En ingeniería de software, el patrón compuesto es un patrón de diseño de partición. El patrón compuesto describe que
> un grupo de objetos debe ser tratado de la misma manera que una única instancia de un objeto. La intención de un
> compuesto es "componer" objetos en estructuras de árbol para representar jerarquías parte-todo. La implementación del
> patrón de composición permite a los clientes tratar los objetos individuales y las composiciones de manera uniforme.

**Ejemplo programático**

Tomando nuestro ejemplo anterior. Aquí tenemos la clase base `LetterComposite` y los diferentes tipos
imprimibles `Letter`, `Word` y `Sentence`.

```java
public abstract class LetterComposite {

    private final List<LetterComposite> children = new ArrayList<>();

    public void add(LetterComposite letter) {
        children.add(letter);
    }

    public int count() {
        return children.size();
    }

    protected void printThisBefore() {
    }

    protected void printThisAfter() {
    }

    public void print() {
        printThisBefore();
        children.forEach(LetterComposite::print);
        printThisAfter();
    }
}

public class Letter extends LetterComposite {

    private final char character;

    public Letter(char c) {
        this.character = c;
    }

    @Override
    protected void printThisBefore() {
        System.out.print(character);
    }
}

public class Word extends LetterComposite {

    public Word(List<Letter> letters) {
        letters.forEach(this::add);
    }

    public Word(char... letters) {
        for (char letter : letters) {
            this.add(new Letter(letter));
        }
    }

    @Override
    protected void printThisBefore() {
        System.out.print(" ");
    }
}

public class Sentence extends LetterComposite {

    public Sentence(List<Word> words) {
        words.forEach(this::add);
    }

    @Override
    protected void printThisAfter() {
        System.out.print(".");
    }
}
```

Entonces tenemos un mensajero para llevar mensajes:

```java
public class Messenger {

    LetterComposite messageFromOrcs() {

        var words = List.of(
                new Word('W', 'h', 'e', 'r', 'e'),
                new Word('t', 'h', 'e', 'r', 'e'),
                new Word('i', 's'),
                new Word('a'),
                new Word('w', 'h', 'i', 'p'),
                new Word('t', 'h', 'e', 'r', 'e'),
                new Word('i', 's'),
                new Word('a'),
                new Word('w', 'a', 'y')
        );

        return new Sentence(words);

    }

    LetterComposite messageFromElves() {

        var words = List.of(
                new Word('M', 'u', 'c', 'h'),
                new Word('w', 'i', 'n', 'd'),
                new Word('p', 'o', 'u', 'r', 's'),
                new Word('f', 'r', 'o', 'm'),
                new Word('y', 'o', 'u', 'r'),
                new Word('m', 'o', 'u', 't', 'h')
        );

        return new Sentence(words);

    }

}
```

Y entonces se puede utilizar como:

```java
var messenger=new Messenger();

        LOGGER.info("Message from the orcs: ");
        messenger.messageFromOrcs().print();

        LOGGER.info("Message from the elves: ");
        messenger.messageFromElves().print();
```

La salida de la consola:

```
Message from the orcs: 
 Where there is a whip there is a way.
Message from the elves: 
 Much wind pours from your mouth.
```

## Diagrama de clases

![alt text](./etc/composite.urm.png "Diagrama de clases compuestas")

## Aplicabilidad

Utilice el patrón Composite cuando

* Desea representar jerarquías parciales de objetos.
* Desea que los clientes puedan ignorar la diferencia entre composiciones de objetos y objetos individuales. Los
  clientes tratarán todos los objetos de la estructura compuesta de manera uniforme.

## Usos conocidos

* Interfaces gráficas de usuario donde los componentes pueden contener otros componentes (por ejemplo, paneles que
  contienen botones, etiquetas, otros paneles).
* Representaciones de sistemas de archivos donde los directorios pueden contener archivos y otros directorios.
* Estructuras organizativas en las que un departamento puede contener subdepartamentos y empleados.
* [java.awt.Container](http://docs.oracle.com/javase/8/docs/api/java/awt/Container.html)
  y [java.awt.Component](http://docs.oracle.com/javase/8/docs/api/java/awt/Component.html)
* Árbol de componentes [Apache Wicket](https://github.com/apache/wicket),
  ver [Component](https://github.com/apache/wicket/blob/91e154702ab1ff3481ef6cbb04c6044814b7e130/wicket-core/src/main/java/org/apache/wicket/Component.java)
  y [MarkupContainer](https://github.com/apache/wicket/blob/b60ec64d0b50a611a9549809c9ab216f0ffa3ae3/wicket-core/src/main/java/org/apache/wicket/MarkupContainer.java)

## Consecuencias

Ventajas:

* Simplifica el código cliente, ya que puede tratar estructuras compuestas y objetos individuales de manera uniforme.
* Facilita la adición de nuevos tipos de componentes, ya que no es necesario modificar el código existente.

Contrapartidas:

* Puede hacer que el diseño sea demasiado general. Puede ser difícil restringir los componentes de un compuesto.
* Puede dificultar la restricción de los tipos de componentes de un compuesto.

## Patrones relacionados

* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): Composite puede usar Flyweight para compartir
  instancias de componentes entre varios composites.
* [Iterador](https://java-design-patterns.com/patterns/iterator/): Puede ser utilizado para atravesar estructuras
  Composite.
* [Visitante](https://java-design-patterns.com/patterns/visitor/): Puede aplicar una operación sobre una estructura
  Composite.

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/3xoLAmi)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3vBKXWb)
