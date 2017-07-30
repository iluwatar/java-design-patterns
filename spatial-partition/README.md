---
layout: pattern
title: Spatial partition
folder: spatial.partition
permalink: /patterns/spatial.partition/
categories: Structural
tags:
 - Java
 - Difficulty-Intermediate 
---

## Intent
Store them in a spatial data structure that organizes the objects by their positions.
This data structure lets you efficiently query for objects at or near a location. 
When an object’s position changes, update the spatial data structure so that it can 
continue to find the object.

## Applicability
Use the Spatial partition pattern when

* when you have a set of objects that each have some kind of position and that you 
are doing enough queries to find objects by location that your performance is suffering

## Credits
* [Kristof Szoke - Spatial partition]
