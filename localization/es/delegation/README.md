---
title: Delegation
category: Structural
language: es
tag:
  - Decoupling
---

## También conocido como

Proxy Pattern

## Propósito

Es una técnica en la que un objeto expresa cierto comportamiento al exterior pero en realidad delega la responsabilidad
de implementar ese comportamiento en un objeto asociado.

## Explanation

Real-world example

> Imaginemos que tenemos aventureros que luchan contra monstruos con diferentes armas dependiendo de sus habilidades y
> destrezas. Debemos ser capaces de equiparles con diferentes sin tener que modificar su código fuente para cada una de
> ellas. El patrón de delegación lo hace posible delegando el trabajo dinámico a un objeto específico que implementa una
> interfaz con métodos relevantes.

Wikipedia dice

> En programación orientada a objetos, la delegación se refiere a la evaluación de un miembro (propiedad o método) de un
> objeto (el receptor) en el contexto de otro objeto original (el emisor). La delegación puede hacerse explícitamente,
> pasando el objeto emisor al objeto receptor, lo que puede hacerse en cualquier lenguaje orientado a objetos; o
> implícitamente, mediante las reglas de búsqueda de miembros del lenguaje, lo que requiere soporte del lenguaje para la
> función.

**Ejemplo programático**

Tenemos una interfaz `Printer` y tres implementaciones `CanonPrinter`, `EpsonPrinter` y `HpPrinter`.

```java
public interface Printer {
  void print(final String message);
}

@Slf4j
public class CanonPrinter implements Printer {
  @Override
  public void print(String message) {
    LOGGER.info("Canon Printer : {}", message);
  }
}

@Slf4j
public class EpsonPrinter implements Printer {
  @Override
  public void print(String message) {
    LOGGER.info("Epson Printer : {}", message);
  }
}

@Slf4j
public class HpPrinter implements Printer {
  @Override
  public void print(String message) {
    LOGGER.info("HP Printer : {}", message);
  }
}
```

El `PrinterController` puede ser utilizado como un `Printer` delegando cualquier trabajo manejado por este
a un objeto que la implemente.

```java
public class PrinterController implements Printer {
  
  private final Printer printer;
  
  public PrinterController(Printer printer) {
    this.printer = printer;
  }
  
  @Override
  public void print(String message) {
    printer.print(message);
  }
}
```

Now on the client code printer controllers can print messages differently depending on the
object they're delegating that work to.

```java
private static final String MESSAGE_TO_PRINT = "hello world";

var hpPrinterController = new PrinterController(new HpPrinter());
var canonPrinterController = new PrinterController(new CanonPrinter());
var epsonPrinterController = new PrinterController(new EpsonPrinter());

hpPrinterController.print(MESSAGE_TO_PRINT);
canonPrinterController.print(MESSAGE_TO_PRINT);
epsonPrinterController.print(MESSAGE_TO_PRINT)
```

Salida del programa:

```java
HP Printer : hello world
Canon Printer : hello world
Epson Printer : hello world
```

## Diagrama de clases

![alt text](./etc/delegation.png "Delegate")

## Aplicabilidad

Utilice el patrón Delegate para conseguir lo siguiente

* Reducir el acoplamiento de los métodos a su clase
* Componentes que se comportan de forma idéntica, pero teniendo en cuenta que esta situación puede cambiar en el futuro.

## Créditos

* [Delegate Pattern: Wikipedia ](https://en.wikipedia.org/wiki/Delegation_pattern)
* [Proxy Pattern: Wikipedia ](https://en.wikipedia.org/wiki/Proxy_pattern)
