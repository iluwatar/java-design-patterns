---
title: Pipeline
shortTitle: Pipeline
category: Behavioral
language: es
tag:
 - Decoupling
---

## Propósito

Permite procesar los datos en una serie de etapas, introduciendo una entrada inicial y pasando la salida procesada para que la utilicen las etapas siguientes.

## Explicación

El patrón Pipeline utiliza etapas ordenadas para procesar una secuencia de valores de entrada. Cada tarea implementada está representada por una etapa de la tubería. Puede pensar en las canalizaciones como algo similar a las líneas de ensamblaje de una fábrica, donde cada elemento de la línea de ensamblaje se construye por etapas. El artículo parcialmente ensamblado pasa de una etapa de ensamblaje a otra. Las salidas de la cadena de montaje se producen en el mismo orden que las entradas.

Ejemplo del mundo real

> Supongamos que queremos pasar una cadena a una serie de etapas de filtrado y convertirla como una matriz char en la última etapa.

En palabras sencillas

> El patrón pipeline es una cadena de montaje donde los resultados parciales pasan de una etapa a otra.

Wikipedia dice

> En ingeniería de software, un pipeline consiste en una cadena de elementos de procesamiento (procesos, hilos, coroutines, funciones, etc.), dispuestos de forma que la salida de cada elemento es la entrada del siguiente; el nombre es por analogía a un pipeline físico.

**Ejemplo programático**

Las etapas de nuestro pipeline se llaman `Handler`s.
```java
interface Handler<I, O> {
  O process(I input);
}
```

En nuestro ejemplo de procesamiento de cadenas tenemos 3 `Handler`s concretos diferentes.

```java
class RemoveAlphabetsHandler implements Handler<String, String> {
  ...
}

class RemoveDigitsHandler implements Handler<String, String> {
  ...
}

class ConvertToCharArrayHandler implements Handler<String, char[]> {
  ...
}
```

Aquí está el `Pipeline` que recogerá y ejecutará los handlers uno a uno.

```java
class Pipeline<I, O> {

  private final Handler<I, O> currentHandler;

  Pipeline(Handler<I, O> currentHandler) {
    this.currentHandler = currentHandler;
  }

  <K> Pipeline<I, K> addHandler(Handler<O, K> newHandler) {
    return new Pipeline<>(input -> newHandler.process(currentHandler.process(input)));
  }

  O execute(I input) {
    return currentHandler.process(input);
  }
}
```

Y aquí está la `Pipeline` en acción procesando la cadena.

```java
    var filters = new Pipeline<>(new RemoveAlphabetsHandler())
        .addHandler(new RemoveDigitsHandler())
        .addHandler(new ConvertToCharArrayHandler());
    filters.execute("GoYankees123!");
```

## Diagrama de clases

![alt text](./etc/pipeline.urm.png "Diagrama de clases del patrón Pipeline ")

## Aplicabilidad

Utilice el patrón Pipeline cuando desee

* Ejecutar etapas individuales que produzcan un valor final.
* Añadir legibilidad a secuencias complejas de operaciones proporcionando un constructor fluido como interfaz.
* Mejorar la comprobabilidad del código, ya que las etapas probablemente harán una sola cosa, cumpliendo con el [Principio de Responsabilidad Única (SRP)](https://java-design-patterns.com/principles/#single-responsibility-principle)

## Usos conocidos

* [java.util.Stream](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
* [Maven Build Lifecycle](http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
* [Functional Java](https://github.com/functionaljava/functionaljava)

## Patrones relacionados

* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain-of-responsibility/)

## Créditos

* [The Pipeline Pattern — for fun and profit](https://medium.com/@aaronweatherall/the-pipeline-pattern-for-fun-and-profit-9b5f43a98130)
* [The Pipeline design pattern (in Java)](https://medium.com/@deepakbapat/the-pipeline-design-pattern-in-java-831d9ce2fe21)
* [Pipelines | Microsoft Docs](https://docs.microsoft.com/en-us/previous-versions/msp-n-p/ff963548(v=pandp.10))
