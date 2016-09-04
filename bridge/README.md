---
layout: pattern
title: Bridge
folder: bridge
permalink: /patterns/bridge/
pumlid: BSR14SCm20J0Lf82BFxf1akCJ4R26ZZYzkE7zxLljJgoIVfu7S2A3v7pLRhYo3r3l9u6CPHwJjAH5uETllpZhKbejsqn86v1a-CExQwj2mdgqv8-oyev_W00
categories: Structural
tags:
 - Java
 - Gang Of Four
 - Difficulty-Intermediate
---

## Also known as
Handle/Body

## Intent
Decouple an abstraction from its implementation so that the two can
vary independently.

![alt text](./etc/bridge.png "Bridge")

## Applicability
Use the Bridge pattern when

* you want to avoid a permanent binding between an abstraction and its implementation. This might be the case, for example, when the implementation must be selected or switched at run-time.
* both the abstractions and their implementations should be extensible by subclassing. In this case, the Bridge pattern lets you combine the different abstractions and implementations and extend them independently
* changes in the implementation of an abstraction should have no impact on clients; that is, their code should not have to be recompiled.
* you have a proliferation of classes. Such a class hierarchy indicates the need for splitting an object into two parts. Rumbaugh uses the term "nested generalizations" to refer to such class hierarchies
* you want to share an implementation among multiple objects (perhaps using reference counting), and this fact should be hidden from the client. A simple example is Coplien's String class, in which multiple objects can share the same string representation.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
