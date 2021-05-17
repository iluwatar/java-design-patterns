---  
layout: pattern  
title: Update Method
folder: update-method  
permalink: /patterns/update-method/  
categories: Behavioral
tags:  
 - Game programming
---  
  
## Intent  
Update method pattern simulates a collection of independent objects by telling each to process one frame of behavior at a time.

## Explanation
The game world maintains a collection of objects. Each object implements an update method that simulates one frame of the object’s behavior. Each frame, the game updates every object in the collection.

To learn more about how the game loop runs and when the update methods are invoked, please refer to Game Loop Pattern.

## Class diagram
![alt text](./etc/update-method.urm.png "Update Method pattern class diagram")

## Applicability  
If the Game Loop pattern is the best thing since sliced bread, then the Update Method pattern is its butter. A wide swath of games featuring live entities that the player interacts with use this pattern in some form or other. If the game has space marines, dragons, Martians, ghosts, or athletes, there’s a good chance it uses this pattern.

However, if the game is more abstract and the moving pieces are less like living actors and more like pieces on a chessboard, this pattern is often a poor fit. In a game like chess, you don’t need to simulate all of the pieces concurrently, and you probably don’t need to tell the pawns to update themselves every frame.

Update methods work well when:

- Your game has a number of objects or systems that need to run simultaneously.
- Each object’s behavior is mostly independent of the others.
- The objects need to be simulated over time.

## Credits  
  
* [Game Programming Patterns - Update Method](http://gameprogrammingpatterns.com/update-method.html)
