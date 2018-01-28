---
layout: pattern
title: Factory Kit
folder: factory-kit
permalink: /patterns/factory-kit/
categories: Creational
tags:
 - Java
 - Difficulty-Beginner
 - Functional
---

## Intent
Define a factory of immutable content with separated builder and factory interfaces.

![alt text](./etc/factory-kit.png "Factory Kit")

## Applicability
Use the Factory Kit pattern when

* a class can't anticipate the class of objects it must create
* you just want a new instance of a custom builder instead of the global one
* you explicitly want to define types of objects, that factory can build
* you want a separated builder and creator interface

## Credits

* [Design Pattern Reloaded by Remi Forax: ](https://www.youtube.com/watch?v=-k2X7guaArU)
