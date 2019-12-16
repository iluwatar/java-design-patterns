---
layout: pattern
title: Data Locality
folder: data-locality
permalink: /patterns/data-locality/
categories: Behavioral
tags:
 - Game programming
 - Performance
---

## Intent
Accelerate memory access by arranging data to take advantage of CPU caching.

Modern CPUs have caches to speed up memory access. These can access memory adjacent to recently accessed memory much quicker. Take advantage of that to improve performance by increasing data locality keeping data in contiguous memory in the order that you process it.

## Class diagram
![alt text](./etc/data-locality.urm.png "Data Locality pattern class diagram")

## Applicability

* Like most optimizations, the first guideline for using the Data Locality pattern is when you have a performance problem.
* With this pattern specifically, you’ll also want to be sure your performance problems are caused by cache misses.

## Real world example

* The [Artemis](http://gamadu.com/artemis/) game engine is one of the first and better-known frameworks that uses simple IDs for game entities.

## Credits

* [Game Programming Patterns Optimization Patterns: Data Locality](http://gameprogrammingpatterns.com/data-locality.html)