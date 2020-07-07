---
layout: pattern
title: Unit Of Work
folder: unit-of-work
permalink: /patterns/unit-of-work/

categories: Architectural
tags:
 - Data access
---

## Intent
When a business transaction is completed, all the these updates are sent as one 
  big unit of work to be persisted in a database in one go so as to minimize database trips. 

## Class diagram
![alt text](etc/unit-of-work.urm.png "unit-of-work")

## Applicability
Use the Unit Of Work pattern when

* To optimize the time taken for database transactions.
* To send changes to database as a unit of work which ensures atomicity of the transaction.
* To reduce number of database calls.

## Credits

* [Design Pattern - Unit Of Work Pattern](https://www.codeproject.com/Articles/581487/Unit-of-Work-Design-Pattern)
* [Unit Of Work](https://martinfowler.com/eaaCatalog/unitOfWork.html)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
