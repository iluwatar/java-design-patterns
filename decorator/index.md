---
layout: pattern
title: Decorator
folder: decorator
permalink: /patterns/decorator/
categories: Structural
tags: Java
---

**Intent:** Attach additional responsibilities to an object dynamically.
Decorators provide a flexible alternative to subclassing for extending
functionality.

![alt text](./etc/decorator_1.png "Decorator")

**Applicability:** Use Decorator

* to add responsibilities to individual objects dynamically and transparently, that is, without affecting other objects
* for responsibilities that can be withdrawn
* when extension by subclassing is impractical. Sometimes a large number of independent extensions are possible and would produce an explosion of subclasses to support every combination. Or a class definition may be hidden or otherwise unavailable for subclassing
