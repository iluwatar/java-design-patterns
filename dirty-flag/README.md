---
layout: pattern
title: Dirty Flag
folder: dirty-flag
permalink: /patterns/dirty-flag/
categories: Behavioral
tags:
 - Game programming
 - Performance
---

## Intent
To avoid expensive re-acquisition of resources. The resources retain their identity, are kept in some
fast-access storage, and are re-used to avoid having to acquire them again.

## Class diagram
![alt text](./etc/dirty-flag.png "Dirty Flag")

## Applicability
Use the Dirty Flag pattern when

* Repetitious acquisition, initialization, and release of the same resource causes unnecessary performance overhead.

## Credits

* [Design Patterns: Dirty Flag](https://www.takeupcode.com/podcast/89-design-patterns-dirty-flag/)
