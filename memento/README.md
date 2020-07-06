---
layout: pattern
title: Memento
folder: memento
permalink: /patterns/memento/
categories: Behavioral
tags:
 - Gang of Four
---

## Also known as
Token

## Intent
Without violating encapsulation, capture and externalize an
object's internal state so that the object can be restored to this state later.

## Class diagram
![alt text](./etc/memento.png "Memento")

## Applicability
Use the Memento pattern when

* a snapshot of an object's state must be saved so that it can be restored to that state later, and
* a direct interface to obtaining the state would expose implementation details and break the object's encapsulation

## Real world examples

* [java.util.Date](http://docs.oracle.com/javase/8/docs/api/java/util/Date.html)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
