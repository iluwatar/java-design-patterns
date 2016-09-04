---
layout: pattern
title: Factory Kit
folder: factory-kit
permalink: /patterns/factory-kit/
pumlid: JST15i8m20N0g-W14lRU1YcsQ4BooCS-RwzBTpDNSscvQKQx7C1SDwBWi-w68--vD6Gur55bTBAM9uE3dlpcikcotSjaGCCNTLu_q8C58pxbPI25_Bzcz3gpjoy0
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
