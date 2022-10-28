---
layout: pattern
title: Value Object
folder: value-object
permalink: /patterns/value-object/
categories: Creational
language: en
tags:
 - Instantiation
---

## Intent
Provide objects which follow value semantics rather than reference semantics.
This means value objects' equality are not based on identity. Two value objects are
equal when they have the same value, not necessarily being the same object.

## Class diagram
![alt text](./etc/value-object.png "Value Object")

## Applicability
Use the Value Object when

* You need to measure the objects' equality based on the objects' value

## Real world examples

* [java.util.Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
* [java.time.LocalDate](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html)
* [joda-time, money, beans](http://www.joda.org/)

## Credits

* [Patterns of Enterprise Application Architecture](http://www.martinfowler.com/books/eaa.html)
* [VALJOs - Value Java Objects : Stephen Colebourne's blog](http://blog.joda.org/2014/03/valjos-value-java-objects.html)
* [Value Object : Wikipedia](https://en.wikipedia.org/wiki/Value_object)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
