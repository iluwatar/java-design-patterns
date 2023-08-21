---
title: Type-Object
category: Behavioral
language: en
tag:
 - Game programming
 - Extensibility
---

## Intent
As explained in the book Game Programming Patterns by Robert Nystrom, type object pattern helps in

> Allowing flexible creation of new “classes” by creating a single class, each instance of which represents a different type of object

## Explanation
Real-world example
> You are working on a game with many different breeds of monsters. Each monster breed has different values for the attributes, such as attack, health, intelligence, etc. You want to create new monster breeds, or modify the attributes of an existing breed, without needing to modify the code and recompiling the game.

In plain words
> Define a type object class, and a typed object class. We give each type object instance a reference to a typed object, containing the information for that type.

**Programmatic example**

## Class diagram
![alt text](./etc/typeobjectpattern.urm.png "Type-Object pattern class diagram")

## Applicability
This pattern can be used when:

* We don’t know what types we will need up front.
* We want to be able to modify or add new types without having to recompile or change code.
* Only difference between the different 'types' of objects is the data, not the behaviour.
 
## Credits

* [Game Programming Patterns - Type Object](http://gameprogrammingpatterns.com/type-object.html)
* [Types as Objects Pattern](http://www.cs.sjsu.edu/~pearce/modules/patterns/analysis/top.htm)
