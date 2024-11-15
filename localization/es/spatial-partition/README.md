---
title: Spatial Partition
shortTitle: Spatial Partition
category: Behavioral
language: es
tag:
 - Performance
 - Game programming
---

## Propósito

Como se explica en el libro  [Game Programming Patterns](http://gameprogrammingpatterns.com/spatial-partition.html) 
por Bob Nystrom, spatial partition pattern ayuda a localizar eficazmente los objetos almacenándolos en una
estructura de datos organizada por sus posiciones.

## Explicación

Digamos que usted está construyendo un juego de guerra con cientos, o incluso miles de jugadores, que se enfrentan en el campo de batalla.
La posición de cada jugador se actualiza en cada fotograma. La forma más sencilla de manejar
todas las interacciones que tienen lugar en el campo es comprobar la posición de cada jugador con la de todos los demás.
posición de cada jugador:

```java
public void handleMeLee(Unit units[], int numUnits) {
  for (var a = 0; a < numUnits - 1; a++)
  {
    for (var b = a + 1; b < numUnits; b++)
    {
      if (units[a].position() == units[b].position())
      {
        handleAttack(units[a], units[b]);
      }
    }
  }
}
```

Esto incluirá un montón de controles innecesarios entre jugadores que están demasiado alejados como para tener alguna
influencia entre ellos. Los bucles anidados dan a esta operación una complejidad O(n^2), que tiene que ser
cada fotograma, ya que muchos de los objetos del campo pueden moverse en cada fotograma. La idea
detrás del patrón de diseño de Partición Espacial es permitir una rápida localización de los objetos utilizando una estructura de datos que está organizada por sus posiciones.
que está organizada por sus posiciones, de modo que cuando se realiza una operación como la anterior,
no es necesario cotejar la posición de cada objeto con la de todos los demás. La estructura de datos
puede utilizarse para almacenar objetos en movimiento y estáticos, aunque para seguir la pista de los objetos en movimiento,
sus posiciones tendrán que restablecerse cada vez que se muevan. Esto significaría tener que crear una nueva
instancia de la estructura de datos cada vez que un objeto se mueva, lo que consumiría memoria adicional. La dirección
estructuras de datos comunes utilizadas para este patrón de diseño son:

* Grid
* Árbol Quad
* Árbol K-d
* BSP
* Jerarquía de volúmenes límite

En nuestra implementación, utilizamos la estructura de datos Quadtree que reducirá la complejidad temporal de
O(n^2) a O(nlogn), lo que reduce significativamente los cálculos necesarios en el caso de un gran número de objetos.
significativamente en el caso de un gran número de objetos.

## Diagrama de clases

![alt text](./etc/spatial-partition.urm.png "Spatial Partition pattern class diagram")

## Aplicabilidad

Utilice el patrón Spatial Partition cuando:

* Cuando se necesita mantener un registro de un gran número de posiciones de objetos, que se actualizan cada fotograma.
* Cuando es aceptable cambiar memoria por velocidad, ya que la creación y actualización de la estructura de datos consumirá memoria extra.

## Créditos

* [Game Programming Patterns/Spatial Partition](http://gameprogrammingpatterns.com/spatial-partition.html) por Bob Nystrom
* [Quadtree tutorial](https://www.youtube.com/watch?v=OJxEcs0w_kE) por Daniel Schiffman
