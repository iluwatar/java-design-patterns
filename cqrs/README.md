---
layout: pattern
title: CQRS
folder: cqrs
permalink: /patterns/cqrs/
pumlid: 
categories: Architectural
tags:
 - Java
 - Difficulty-Intermediate
---

## Intent
CQRS Command Query Responsibility Segregation - Seperate the query side from the command side.

![alt text](./etc/cqrs.png "CQRS")

## Applicability
Use the CQRS pattern when

* you want to scale the queries and commands independently.
* you want to use different data models for queries and commands. Useful when dealing with complex domains.
* you want to use architectures like event sourcing or task based UI.

## Credits

* [Greg Young - CQRS, Task Based UIs, Event Sourcing agh!](http://www.amazon.com/Effective-Java-Edition-Joshua-Bloch/dp/0321356683)
* [Martin Fowler - CQRS](http://codebetter.com/gregyoung/2010/02/16/cqrs-task-based-uis-event-sourcing-agh/)
* [Oliver Wolf - CQRS for Great Good](https://www.youtube.com/watch?v=Ge53swja9Dw)
