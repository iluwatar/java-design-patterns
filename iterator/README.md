---
layout: pattern
title: Iterator
folder: iterator
permalink: /patterns/iterator/
categories: Behavioral
tags:
 - Gang of Four
---

## Also known as
Cursor

## Intent
Provide a way to access the elements of an aggregate object
sequentially without exposing its underlying representation.

## Class diagram
![alt text](./etc/iterator_1.png "Iterator")

## Applicability
Use the Iterator pattern

* to access an aggregate object's contents without exposing its internal representation
* to support multiple traversals of aggregate objects
* to provide a uniform interface for traversing different aggregate structures

## Real world examples

* [java.util.Iterator](http://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)
* [java.util.Enumeration](http://docs.oracle.com/javase/8/docs/api/java/util/Enumeration.html)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
