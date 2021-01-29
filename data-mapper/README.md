---
layout: pattern
title: Data Mapper
folder: data-mapper
permalink: /patterns/data-mapper/
categories: Architectural
tags:
 - Decoupling
---

## Intent
A layer of mappers that moves data between objects and a database while keeping them independent of each other and the mapper itself

## Class diagram
![alt text](./etc/data-mapper.png "Data Mapper")

## Applicability
Use the Data Mapper in any of the following situations

* when you want to decouple data objects from DB access layer 
* when you want to write multiple data retrieval/persistence implementations

## Credits

* [Data Mapper](http://richard.jp.leguen.ca/tutoring/soen343-f2010/tutorials/implementing-data-mapper/)
