---
title: Trampoline
category: Behavioral
language: es
tag:
 - Performance
---

## Propósito

El patrón Trampoline se utiliza para implementar algoritmos recursivamente en Java sin volar la pila
y para intercalar la ejecución de funciones sin codificarlas juntas.

## Explicación

La recursión es una técnica frecuentemente adoptada para resolver problemas algorítmicos en un estilo de divide y vencerás.
Por ejemplo, el cálculo de la suma acumulativa de Fibonacci y los factoriales. En este tipo de
problemas, la recursividad es más directa que su homóloga de bucle. Además, la recursividad puede
necesitar menos código y parecer más concisa. Hay un dicho que dice que todo problema de recursión puede resolverse
utilizando un bucle a costa de escribir código más difícil de entender.

Sin embargo, las soluciones de tipo recursivo tienen una gran advertencia. Para cada llamada recursiva, normalmente se necesita
un valor intermedio almacenado y hay una cantidad limitada de memoria de pila disponible. Quedarse sin
memoria de pila crea un error de desbordamiento de pila y detiene la ejecución del programa.

Trampoline pattern es un truco que permite definir algoritmos recursivos en Java sin desbordar la
pila.

Ejemplo del mundo real

> Un cálculo Fibonacci recursivo sin el problema de desbordamiento de pila utilizando el patrón Trampoline.       

En palabras sencillas

> El patrón Trampoline permite la recursión sin agotar la memoria de la pila.

Wikipedia dice

> En Java, trampoline se refiere al uso de reflection para evitar el uso de clases internas, por ejemplo en
> eventos. La sobrecarga de tiempo de una llamada a reflection se intercambia por la sobrecarga de espacio de una clase interna.
> Trampolines en Java generalmente implican la creación de un GenericListener para pasar eventos a una clase externa.

**Ejemplo programático**

Esta es la implementación de `Trampoline` en Java.

Cuando se llama a `get` sobre el Trampoline devuelto, internamente se itera llamando a `jump` sobre el
siempre que la instancia concreta devuelta sea `Trampoline`, deteniéndose una vez que la instancia
instancia devuelta sea `done`.

```java
public interface Trampoline<T> {

  T get();

  default Trampoline<T> jump() {
    return this;
  }

  default T result() {
    return get();
  }

  default boolean complete() {
    return true;
  }

  static <T> Trampoline<T> done(final T result) {
    return () -> result;
  }

  static <T> Trampoline<T> more(final Trampoline<Trampoline<T>> trampoline) {
    return new Trampoline<T>() {
      @Override
      public boolean complete() {
        return false;
      }

      @Override
      public Trampoline<T> jump() {
        return trampoline.result();
      }

      @Override
      public T get() {
        return trampoline(this);
      }

      T trampoline(final Trampoline<T> trampoline) {
        return Stream.iterate(trampoline, Trampoline::jump)
            .filter(Trampoline::complete)
            .findFirst()
            .map(Trampoline::result)
            .orElseThrow();
      }
    };
  }
}
```

Uso del `Trampoline` para obtener valores Fibonacci.

```java
public static void main(String[] args) {
    LOGGER.info("Start calculating war casualties");
    var result = loop(10, 1).result();
    LOGGER.info("The number of orcs perished in the war: {}", result);
}

public static Trampoline<Integer> loop(int times, int prod) {
    if (times == 0) {
        return Trampoline.done(prod);
    } else {
        return Trampoline.more(() -> loop(times - 1, prod * times));
    }
}
```

Salida del programa:

```
19:22:24.462 [main] INFO com.iluwatar.trampoline.TrampolineApp - Start calculating war casualties
19:22:24.472 [main] INFO com.iluwatar.trampoline.TrampolineApp - The number of orcs perished in the war: 3628800
```

## Diagrama de clases

![alt text](./etc/trampoline.urm.png "Trampoline pattern class diagram")

## Aplicabilidad

Utilice el patrón Trampoline cuando:

* Para implementar funciones recursivas de cola. Este patrón permite encender una operación sin pila.
* Para intercalar la ejecución de dos o más funciones en el mismo hilo.

## Usos conocidos

* [cyclops-react](https://github.com/aol/cyclops-react)

## Créditos

* [Trampolining: a practical guide for awesome Java Developers](https://medium.com/@johnmcclean/trampolining-a-practical-guide-for-awesome-java-developers-4b657d9c3076)
* [Trampoline in java ](http://mindprod.com/jgloss/trampoline.html)
* [Laziness, trampolines, monoids and other functional amenities: this is not your father's Java](https://www.slideshare.net/mariofusco/lazine)
* [Trampoline implementation](https://github.com/bodar/totallylazy/blob/master/src/com/googlecode/totallylazy/Trampoline.java)
* [What is a trampoline function?](https://stackoverflow.com/questions/189725/what-is-a-trampoline-function)
* [Modern Java in Action: Lambdas, streams, functional and reactive programming](https://www.amazon.com/gp/product/1617293563/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617293563&linkId=ad53ae6f9f7c0982e759c3527bd2595c)
* [Java 8 in Action: Lambdas, Streams, and functional-style programming](https://www.amazon.com/gp/product/1617291994/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617291994&linkId=e3e5665b0732c59c9d884896ffe54f4f)
