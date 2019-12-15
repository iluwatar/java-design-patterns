---
layout: pattern
title: Marker Interface
folder: marker
permalink: /patterns/marker/
categories: Structural
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

* [Effective Java 2nd Edition by Joshua Bloch](https://www.amazon.com/Effective-Java-2nd-Joshua-Bloch/dp/0321356683)
