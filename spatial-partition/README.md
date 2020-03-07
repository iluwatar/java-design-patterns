---
layout: pattern
title: Spatial Partition
folder: spatial-partition
permalink: /patterns/spatial-partition/
categories: Behavioral
tags:
 - Performance
 - Game programming
---

## Intent
As explained in the book [Game Programming Patterns](http://gameprogrammingpatterns.com/spatial-partition.html) by Bob Nystrom, spatial partition pattern helps to 

> efficiently locate objects by storing them in a data structure organized by their positions.

## Explanation
Say, you are building a war game with hundreds, or maybe even thousands of players, who are clashing on the battle field. Each player's position is getting updated every frame. The simple way to handle all interactions taking place on the field is to check each player's position against every other player's position:

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

This will include a lot of unnecessary checks between players which are too far apart to have any influence on each other. The nested loops gives this operation an O(n^2) complexity, which has to be performed every frame since many of the objects on the field may be moving each frame.
The idea behind the Spatial Partition design pattern is to enable quick location of objects using a data structure that is organised by their positions, so when performing an operation like the one above, every object's position need not be checked against all other objects' positions. The data structure can be used to store moving and static objects, though in order to keep track of the moving objects, their positions will have to be reset each time they move. This would mean having to create a new instance of the data structure each time an object moves, which would use up additional memory. The common data structures used for this design pattern are:

* Grid
* Quad tree
* k-d tree
* BSP
* Boundary volume hierarchy

In our implementation, we use the Quadtree data structure which will reduce the time complexity of finding the objects within a certain range from O(n^2) to O(nlogn), decreasing the computations required significantly in case of large number of objects.

## Class diagram
![alt text](./etc/spatial-partition.urm.png "Spatial Partition pattern class diagram")

## Applicability
This pattern can be used:

* When you need to keep track of a large number of objects' positions, which are getting updated every frame.
* When it is acceptable to trade memory for speed, since creating and updating data structure will use up extra memory.

## Credits

* [Game Programming Patterns/Spatial Partition](http://gameprogrammingpatterns.com/spatial-partition.html) by Bob Nystrom
* [Quadtree tutorial](https://www.youtube.com/watch?v=OJxEcs0w_kE) by Daniel Schiffman
