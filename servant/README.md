---
layout: pattern
title: Servant
folder: servant
permalink: /patterns/servant/
pumlid: DSkn4O0m20NGLNG0G-ys63cDbv0SV7HzRUnUy-QYkSOkONKwWU4haV6JZe8pjd2nt1MYIBatAZKU1XjTVFEoYvT3by60c3erzW_qdPiL9CY_KrXB8rfz0G00
categories: Structural
tags:
 - Java
 - Difficulty-Beginner
---

## Intent
Servant is used for providing some behavior to a group of classes.
Instead of defining that behavior in each class - or when we cannot factor out
this behavior in the common parent class - it is defined once in the Servant.

![alt text](./etc/servant-pattern.png "Servant")

## Applicability
Use the Servant pattern when

* when we want some objects to perform a common action and don't want to define this action as a method in every class.

## Credits
* [Let's Modify the Objects-First Approach into Design-Patterns-First](http://edu.pecinovsky.cz/papers/2006_ITiCSE_Design_Patterns_First.pdf)
