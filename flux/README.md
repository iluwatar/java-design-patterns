---
layout: pattern
title: Flux
folder: flux
permalink: /patterns/flux/
categories: Presentation Tier
tags:
 - Java
 - Difficulty-Intermediate
---

## Intent
Flux eschews MVC in favor of a unidirectional data flow. When a
user interacts with a view, the view propagates an action through a central
dispatcher, to the various stores that hold the application's data and business
logic, which updates all of the views that are affected.

![alt text](./etc/flux.png "Flux")

## Applicability
Use the Flux pattern when

* you want to focus on creating explicit and understandable update paths for your application's data, which makes tracing changes during development simpler and makes bugs easier to track down and fix.

## Credits

* [Flux - Application architecture for building user interfaces](http://facebook.github.io/flux/)
