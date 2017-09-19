---
layout: pattern
title: Unit Of Work
folder: unit-of-work
permalink: /patterns/unit-of-work/
pumlid: 
categories: Architectural
tags:
 - Java
 - KISS
 - YAGNI
 - Difficulty-Beginner
---

## Intent
When a business transaction is completed all the these updates are sent as one 
  big unit of work to be persisted in a database in one go so as to minimize database trips. 

![alt text](etc/unit-of-work.urm.png "unit-of-work")

## Applicability
Use the Unit Of Work pattern when

* The client is asking to optimize the time taken for database transactions.
* When you want to boost the performance to get database records.
* You want reduce number of database calls.

## Credits

* [Design Pattern - Unit Of Work Pattern](https://www.codeproject.com/Articles/581487/Unit-of-Work-Design-Pattern)
* [Unit Of Work](https://martinfowler.com/eaaCatalog/unitOfWork.html)
