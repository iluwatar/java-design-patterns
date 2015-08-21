---
layout: pattern
title: Flyweight
folder: flyweight
permalink: /patterns/flyweight/
categories: Structural
tags: Java
---

**Intent:** Use sharing to support large numbers of fine-grained objects
efficiently.

![alt text](./etc/flyweight_1.png "Flyweight")

**Applicability:** The Flyweight pattern's effectiveness depends heavily on how
and where it's used. Apply the Flyweight pattern when all of the following are
true
* an application uses a large number of objects
* storage costs are high because of the sheer quantity of objects
* most object state can be made extrinsic
* many groups of objects may be replaced by relatively few shared objects once extrinsic state is removed
* the application doesn't depend on object identity. Since flyweight objects may be shared, identity tests will return true for conceptually distinct objects.

**Real world examples:**

* [java.lang.Integer#valueOf(int)](http://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#valueOf%28int%29)
