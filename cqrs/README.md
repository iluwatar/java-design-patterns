---
layout: pattern
title: CQRS
folder: cqrs
permalink: /patterns/cqrs/
pumlid: 7SPR4a0m3030gt00pR_RH6I8QQFouFgC_TfHb6gkd5Q7FQBx363ub4rYpoMTZKuDrYXqDX37HIuuyCPfPPTDfuuHREhGqBy0NUR0GNzAMYizMtq1
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
