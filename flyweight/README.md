---
layout: pattern
title: Flyweight
folder: flyweight
permalink: /patterns/flyweight/
pumlid: HSV94S8m3030Lg20M7-w4OvYAoCh7Xtnq3ty-Eq-MQlaJcdow17JNm26gpIEdkzqidffa4Qfrm2MN1XeSEADsqxEJRU94MJgCD1_W4C-YxZr08hwNqaRPUQGBm00
categories: Structural
tags:
 - Java
 - Gang Of Four
 - Difficulty-Intermediate
 - Performance
---

## Intent
Use sharing to support large numbers of fine-grained objects
efficiently.

![alt text](./etc/flyweight_1.png "Flyweight")

## Applicability
The Flyweight pattern's effectiveness depends heavily on how
and where it's used. Apply the Flyweight pattern when all of the following are
true

* an application uses a large number of objects
* storage costs are high because of the sheer quantity of objects
* most object state can be made extrinsic
* many groups of objects may be replaced by relatively few shared objects once extrinsic state is removed
* the application doesn't depend on object identity. Since flyweight objects may be shared, identity tests will return true for conceptually distinct objects.

## Real world examples

* [java.lang.Integer#valueOf(int)](http://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#valueOf%28int%29) and similarly for Byte, Character and other wrapped types.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
