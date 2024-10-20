---  
title: Update Method
shortTitle: Update Method
category: Behavioral
language: es
tag:  
 - Game programming
---  
  
## Propósito
El patrón de método de actualización simula una colección de objetos independientes indicando a cada uno que procese un fotograma de comportamiento a la vez.

## Explicación
El mundo del juego mantiene una colección de objetos. Cada objeto implementa un método de actualización que simula un fotograma del comportamiento del objeto. En cada fotograma, el juego actualiza cada objeto de la colección.

Para obtener más información sobre cómo se ejecuta el bucle de juego y cuándo se invocan los métodos de actualización, consulte Patrón de bucle de juego.

## Diagrama de clases
![alt text](./etc/update-method.urm.png "Update Method pattern class diagram")

## Aplicabilidad
Si el patrón Game Loop es lo mejor desde el pan rebanado, entonces el patrón Update Method es su mantequilla. Una amplia gama de juegos con entidades vivas con las que el jugador interactúa utilizan este patrón de una forma u otra. Si el juego tiene marines espaciales, dragones, marcianos, fantasmas o atletas, es muy probable que utilice este patrón.

Sin embargo, si el juego es más abstracto y las piezas móviles se parecen menos a actores vivos y más a piezas de un tablero de ajedrez, este patrón no suele encajar. En un juego como el ajedrez, no necesitas simular todas las piezas concurrentemente, y probablemente no necesites decirle a los peones que se actualicen a sí mismos en cada frame.

Los métodos de actualización funcionan bien cuando:

- Su juego tiene un número de objetos o sistemas que necesitan ejecutarse simultáneamente.
- El comportamiento de cada objeto es independiente de los demás.
- Los objetos necesitan ser simulados en el tiempo.

## Créditos 
  
* [Game Programming Patterns - Update Method](http://gameprogrammingpatterns.com/update-method.html)
