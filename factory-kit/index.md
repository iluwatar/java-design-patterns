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

## Also known as
Virtual Constructor

## Intent
Define factory of immutable content with separated builder and factory interfaces.

![alt text](./etc/factory-kit_1.png "Factory Kit")

## Applicability
Use the Factory Kit pattern when

* a class can't anticipate the class of objects it must create
* you just want a new instance of custom builder instead of global one
* a class wants its subclasses to specify the objects it creates
* classes delegate responsibility to one of several helper subclasses, and you want to localize the knowledge of which helper subclass is the delegate

## Credits

* [Design Pattern Reloaded by Remi Forax: ](https://www.youtube.com/watch?v=-k2X7guaArU)
