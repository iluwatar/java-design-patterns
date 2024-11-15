---
title: Collecting Parameter
shortTitle: Collecting Parameter
category: Behavioral
language: es
tag:
    - Accumulation
    - Generic
---

## También conocido como

* Collector
* Accumulator

## Propósito

Su objetivo es simplificar los métodos que recopilan información pasando un único objeto de colección a través de varias llamadas a métodos, permitiéndoles añadir resultados a esta colección en lugar de que cada método cree su propia colección.

## Explicación

### Ejemplo del mundo real

Dentro de un gran edificio corporativo, existe una cola de impresión global que es una colección de todos los trabajos de impresión que están actualmente pendientes. Las diferentes plantas contienen diferentes modelos de impresoras, cada una con una política de impresión diferente. Debemos construir un programa que pueda añadir continuamente trabajos de impresión apropiados a una colección, que se llama el *parámetro de recogida*.

### En palabras sencillas

En lugar de tener un método gigante que contenga numerosas políticas para recoger información en una variable, podemos crear numerosas funciones más pequeñas que tomen cada parámetro, y añadan nueva información. Podemos pasar el parámetro a todas estas funciones más pequeñas y al final, tendremos lo que queríamos originalmente. Esta vez, el código es más limpio y fácil de entender. Debido a que la función más grande se ha dividido, el código también es más fácil de modificar ya que los cambios se localizan en las funciones más pequeñas.

### Wikipedia dice

En el modismo de Parámetros de Recolección una colección (lista, mapa, etc.) se pasa repetidamente como parámetro a un método que añade elementos a la colección.

### Ejemplo programático

Codificando nuestro ejemplo anterior, podemos utilizar la colección `resultado` como parámetro recolector. Se implementan las siguientes restricciones:

- Si un papel A4 es de color, también debe ser de una sola cara. Se aceptan todos los demás papeles no coloreados.
- El papel A3 no debe ser de color y debe ser de una sola cara.
- El papel A2 debe ser de una sola página, a una cara y sin colorear.

```java
package com.iluwatar.collectingparameter;

import java.util.LinkedList;
import java.util.Queue;

public class App {
    static PrinterQueue printerQueue = PrinterQueue.getInstance();

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
    /*
      Initialising the printer queue with jobs
    */
        printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A4, 5, false, false));
        printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A3, 2, false, false));
        printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A2, 5, false, false));

    /*
      This variable is the collecting parameter.
    */
        var result = new LinkedList<PrinterItem>();

        /*
         * Using numerous sub-methods to collaboratively add information to the result collecting parameter
         */
        addA4Papers(result);
        addA3Papers(result);
        addA2Papers(result);
    }
}
```

Utilizamos los métodos `addA4Paper`, `addA3Paper` y `addA2Paper` para rellenar el parámetro de recogida `result` con los trabajos de impresión adecuados según la política descrita anteriormente. Las tres políticas se codifican a continuación,

```java
public class App {
    static PrinterQueue printerQueue = PrinterQueue.getInstance();

    /**
     * Adds A4 document jobs to the collecting parameter according to some policy that can be whatever the client
     * (the print center) wants.
     *
     * @param printerItemsCollection the collecting parameter
     */
    public static void addA4Papers(Queue<PrinterItem> printerItemsCollection) {
    /*
      Iterate through the printer queue, and add A4 papers according to the correct policy to the collecting parameter,
      which is 'printerItemsCollection' in this case.
     */
        for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
            if (nextItem.paperSize.equals(PaperSizes.A4)) {
                var isColouredAndSingleSided =
                        nextItem.isColour && !nextItem.isDoubleSided;
                if (isColouredAndSingleSided) {
                    printerItemsCollection.add(nextItem);
                } else if (!nextItem.isColour) {
                    printerItemsCollection.add(nextItem);
                }
            }
        }
    }

    /**
     * Adds A3 document jobs to the collecting parameter according to some policy that can be whatever the client
     * (the print center) wants. The code is similar to the 'addA4Papers' method. The code can be changed to accommodate
     * the wants of the client.
     *
     * @param printerItemsCollection the collecting parameter
     */
    public static void addA3Papers(Queue<PrinterItem> printerItemsCollection) {
        for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
            if (nextItem.paperSize.equals(PaperSizes.A3)) {

                // Encoding the policy into a Boolean: the A3 paper cannot be coloured and double-sided at the same time
                var isNotColouredAndSingleSided =
                        !nextItem.isColour && !nextItem.isDoubleSided;
                if (isNotColouredAndSingleSided) {
                    printerItemsCollection.add(nextItem);
                }
            }
        }
    }

    /**
     * Adds A2 document jobs to the collecting parameter according to some policy that can be whatever the client
     * (the print center) wants. The code is similar to the 'addA4Papers' method. The code can be changed to accommodate
     * the wants of the client.
     *
     * @param printerItemsCollection the collecting parameter
     */
    public static void addA2Papers(Queue<PrinterItem> printerItemsCollection) {
        for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
            if (nextItem.paperSize.equals(PaperSizes.A2)) {

                // Encoding the policy into a Boolean: the A2 paper must be single page, single-sided, and non-coloured.
                var isNotColouredSingleSidedAndOnePage =
                        nextItem.pageCount == 1 &&
                                !nextItem.isDoubleSided
                                && !nextItem.isColour;
                if (isNotColouredSingleSidedAndOnePage) {
                    printerItemsCollection.add(nextItem);
                }
            }
        }
    }
}
```

Cada método toma como argumento un parámetro de recogida. A continuación, añade elementos, tomados de una variable global, a este parámetro de recogida si cada elemento satisface un criterio determinado. Estos métodos pueden tener la política que desee el cliente.

En este ejemplo de programación, se añaden tres trabajos de impresión a la cola. Sólo los dos primeros trabajos de impresión deben añadirse al parámetro de recogida según la política. Los elementos de la variable `result` después de la ejecución son,

| paperSize | pageCount | isDoubleSided | isColour |
|-----------|-----------|---------------|----------|
| A4        | 5         | false         | false    |
| A3        | 2         | false         | false    |

que es lo que esperábamos.

## Diagrama de clases

![alt text](./etc/collecting-parameter.urm.png "Collecting Parameter")

## Aplicabilidad

Utilice el patrón de diseño Recopilación de parámetros cuando

- Cuando múltiples métodos producen una colección de resultados y quieres agregar estos resultados de una manera unificada.
- En escenarios donde reducir el número de colecciones creadas por métodos puede mejorar la eficiencia de memoria y el rendimiento.
- Al refactorizar métodos grandes que realizan varias tareas, incluida la recopilación de resultados de varias operaciones.

## Tutoriales

Los tutoriales para este método se encuentran en:

- [Refactoring To Patterns](http://www.tarrani.net/RefactoringToPatterns.pdf) por Joshua Kerivsky
- [Smalltalk Best Practice Patterns](https://ptgmedia.pearsoncmg.com/images/9780134769042/samplepages/013476904X.pdf) por Kent Beck

## Usos conocidos

Joshua Kerivsky da un ejemplo real en su libro 'Refactoring to Patterns'. Da un ejemplo de uso del patrón de diseño "Collecting Parameter" para crear un método `toString()` para un árbol XML. Sin utilizar este patrón de diseño, esto requeriría una función voluminosa con condicionales y concatenación que empeoraría la legibilidad del código. Un método de este tipo puede dividirse en métodos más pequeños, cada uno de los cuales añade su propio conjunto de información al parámetro de recogida. Véase esto en [Refactoring To Patterns](http://www.tarrani.net/RefactoringToPatterns.pdf).

Otros ejemplos son:

- Agregar mensajes de error o fallos de validación en un proceso de validación complejo.
- Recopilar elementos o información mientras se recorre una estructura de datos compleja.
- Refactorización de funcionalidades de informes complejas en las que varias partes de un informe se generan mediante métodos diferentes.

## Consecuencias

Ventajas:

- Reduce la duplicación de código centralizando el manejo de las colecciones en un único lugar.
- Mejora la claridad y la capacidad de mantenimiento al hacer explícito dónde y cómo se recogen los resultados.
- Mejora el rendimiento al minimizar la creación y gestión de múltiples objetos de recopilación.

Desventajas:

- Aumenta el acoplamiento entre el invocador y los métodos invocados, ya que deben ponerse de acuerdo sobre la colección a utilizar.
- Puede introducir efectos secundarios en los métodos si no se gestionan con cuidado, ya que los métodos ya no son autónomos en su gestión de resultados.

## Patrones relacionados

- [Composite](https://java-design-patterns.com/patterns/composite/): Puede utilizarse junto con Collecting Parameter cuando se trabaja con estructuras jerárquicas, permitiendo que los resultados se recojan a través de una estructura compuesta.
- [Visitante](https://java-design-patterns.com/patterns/visitor/): A menudo se utiliza conjuntamente, donde Visitor se encarga de recorrer y realizar operaciones en una estructura, y Collecting Parameter acumula los resultados.
- [Comando](https://java-design-patterns.com/patterns/command/): Los comandos pueden utilizar el parámetro de recopilación para agregar resultados de varias operaciones ejecutadas por los objetos de comando.

## Créditos

- [Refactoring To Patterns](http://www.tarrani.net/RefactoringToPatterns.pdf) by Joshua Kerivsky
- [Smalltalk Best Practice Patterns](https://ptgmedia.pearsoncmg.com/images/9780134769042/samplepages/013476904X.pdf) by Kent Beck
- [Wiki](https://wiki.c2.com/?CollectingParameter)
- [Refactoring: Improving the Design of Existing Code](https://amzn.to/3TVEgaB)
- [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/4aApLP0)
