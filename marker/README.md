---
layout: pattern
title: Marker Interface
folder: marker
permalink: /patterns/marker/
categories: Design
tags:
 - Java
 - Difficulty-Beginner
---

## Intent
Using empy interfaces as markers to distinguish special treated objects.

![alt text](./marker/etc/MarkerDiagram.png "Marker Interface")

## Applicability
Use the Marker Interface pattern when

* you want to identify the special objects from normal objects
* define a type that is implemented by instances of the marked class, marker annotations can not do that

## Real world examples

* [javase.7.docs.api.java.io.Serializable](https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html)
* [javase.7.docs.api.java.lang.Cloneable](https://docs.oracle.com/javase/7/docs/api/java/lang/Cloneable.html)

## Credits

* [Effective Java 2nd Edition by Joshua Bloch](https://www.amazon.com/Effective-Java-2nd-Joshua-Bloch/dp/0321356683)
