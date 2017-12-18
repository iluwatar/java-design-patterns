---
layout: pattern
title: CQRS
folder: cqrs
permalink: /patterns/cqrs/
categories: Architectural
tags:
 - Java
 - Difficulty-Intermediate
---

## Intent
CQRS Command Query Responsibility Segregation - Separate the query side from the command side.

![alt text](./etc/cqrs.png "CQRS")

## Applicability
Use the CQRS pattern when

* you want to scale the queries and commands independently.
* you want to use different data models for queries and commands. Useful when dealing with complex domains.
* you want to use architectures like event sourcing or task based UI.

## Credits

* [Greg Young - CQRS, Task Based UIs, Event Sourcing agh!](http://codebetter.com/gregyoung/2010/02/16/cqrs-task-based-uis-event-sourcing-agh/)
* [Martin Fowler - CQRS](https://martinfowler.com/bliki/CQRS.html)
* [Oliver Wolf - CQRS for Great Good](https://www.youtube.com/watch?v=Ge53swja9Dw)
