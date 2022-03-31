---
layout: pattern
title: Object Parent
folder: object-parent
permalink: /patterns/object-parent/
categories: Creational
language: en
tags:
 - Instantiation
---

## Object Parent
Define a factory of immutable content with separated builder and factory interfaces.

## Class diagram
![alt text](./etc/object-parent.png "Object Parent")

## Applicability
Use the Object Parent pattern when

* You want consistent objects over several tests
* You want to reduce code for creation of objects in tests
* Every test should run with fresh data

## Credits

* [Answer by David Brown](http://stackoverflow.com/questions/923319/what-is-an-objectmother) to the stackoverflow question: [What is an ObjectMother?](http://stackoverflow.com/questions/923319/what-is-an-objectmother)
* [c2wiki - Object Mother](http://c2.com/cgi/wiki?ObjectMother)
* [Nat Pryce - Test Data Builders: an alternative to the Object Mother pattern](http://www.natpryce.com/articles/000714.html)
