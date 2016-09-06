---
layout: pattern
title: Data Mapper
folder: data-mapper
permalink: /patterns/data-mapper/
pumlid: JShB3OGm303HLg20nFVjnYGM1CN6ycTfVtFSsnjfzY5jPgUqkLqHwXy0mxUU8wuyqidQ8q4IjJqCO-QBWGOtVh5qyd5AKOmW4mT6Nu2-ZiAekapH_hkcSTNa-GC0
categories: Persistence Tier
tags:
 - Java
 - Difficulty-Beginner
---

## Intent
A layer of mappers that moves data between objects and a database while keeping them independent of each other and the mapper itself

![alt text](./etc/data-mapper.png "Data Mapper")

## Applicability
Use the Data Mapper in any of the following situations

* when you want to decouple data objects from DB access layer 
* when you want to write multiple data retrieval/persistence implementations

## Credits

* [Data Mapper](http://richard.jp.leguen.ca/tutoring/soen343-f2010/tutorials/implementing-data-mapper/)
