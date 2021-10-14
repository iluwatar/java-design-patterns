---
layout: pattern
title: Marker Interface
folder: marker
permalink: /patterns/marker/
categories: Structural
language: en
tags:
 - Decoupling
---

## Intent
Using empty interfaces as markers to distinguish special treated objects.

## Class diagram
![alt text](./etc/MarkerDiagram.png "Marker Interface")

## Applicability
Use the Marker Interface pattern when

* you want to identify the special objects from normal objects (to treat them differently)
* you want to mark that some object is available for certain sort of operations

## Real world examples

* [javase.8.docs.api.java.io.Serializable](https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html)
* [javase.8.docs.api.java.lang.Cloneable](https://docs.oracle.com/javase/8/docs/api/java/lang/Cloneable.html)

## Credits

* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
