---
layout: pattern
title: MonoState
folder: monostate
permalink: /patterns/monostate/
pumlid: HSV14OGm20NGLjO23FVj1YEZsGaa0nzjVxrvUszfLdlkaju_9p3ZI-HybwFXp2r3l0w364eTIgtdpM2d7r-yxXBji7Ko86v1ol60TDW8C8G4zLr9rp9J-ny0
categories: Creational
tags:
 - Java
 - Difficulty-Beginner
---

## Also known as
Borg

## Intent
Enforces a behaviour like sharing the same state amongst all instances.

![alt text](./etc/monostate.png "MonoState")

## Applicability
Use the Monostate pattern when

* The same state must be shared across all instances of a class.
* Typically this pattern might be used everywhere a Singleton might be used. Singleton usage however is not transparent, Monostate usage is.
* Monostate has one major advantage over singleton. The subclasses might decorate the shared state as they wish and hence can provide dynamically different behaviour than the base class.

## Typical Use Case

* the logging class
* managing a connection to a database
* file manager

## Real world examples

Yet to see this.
