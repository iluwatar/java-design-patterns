---
layout: pattern
title: Association Table Mapping
folder: association-table-mapping
permalink: /patterns/association-table-mapping/
categories: Persistence Tier
tags:
 - Java
 - Difficulty-Intermediate
 - Spring
---

## Intent
The Association Table Mapping is pattern used for mapping many-to-many relationship between two classes into relational database. The relationship is represented as additional table containing two keys. The first key references to book and the second to author. 

![alt text](./etc/association-table-mapping-java.png "java")
![alt text](./etc/association-table-mapping-database.png "database")

## Applicability
Use the Association Table Mapping pattern when

* you have many-to-many association between objects, which you want to map into database

## Credits

* [Association Table Mapping](http://martinfowler.com/eaaCatalog/associationTableMapping.html)
