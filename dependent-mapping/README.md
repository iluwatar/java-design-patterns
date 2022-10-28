---
layout: pattern
title: Dependent Mapping
folder: dependent-mapping
permalink: /patterns/dependent-mapping/
categories: Behavioral
language: en
tags:
 - Database
 - consistency
---

## Also known as
* IsDirty pattern

## Intent
Has one class perform the database mapping for a child class.

## Class diagram
![alt text](./etc/dependent-mapping.png "Dependent Mapping")

## Applicability
Some objects naturally appear in the context of other objects. 
Whenever you update the base album, 
you can update the tracks in the album. 
If these tracks are not referenced by any other table in the database, 
you can simplify the mapping process by having the album mapper 
perform track mapping and treat this mapping as a related mapping.

## Credits

* [Dependent Mapping](https://www.martinfowler.com/eaaCatalog/dependentMapping.html)
* [Dependent Mapping Pattern](https://www.sourcecodeexamples.net/2018/05/dependent-mapping-pattern.html)
