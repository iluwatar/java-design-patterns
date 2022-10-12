---
layout: pattern
title: Data Mapper
folder: data-mapper
permalink: /patterns/data-mapper/
categories: Architectural
language: en
tags:
- Decoupling
---

## Intent
A layer of mappers that moves data between objects and a database while keeping them independent of each other and the mapper itself

This layer consists of one or more mappers (or data access objects) that perform data transfer. The scope of mapper implementations varies.
A generic mapper will handle many different domain entity types, a dedicated mapper will handle one or a few.

## Explanation

Real-world example
In plain words
Wikipedia says
Programmatic example

## Class diagram
![alt text](./etc/data-mapper.png "Data Mapper")

## Applicability
Use the Data Mapper in any of the following situations

* when you want to decouple data objects from DB access layer
* when you want to write multiple data retrieval/persistence implementations

## Known uses
* [SqlSession.getMapper()](https://mybatis.org/mybatis-3/java-api.html)

## Consequences

## Related patterns
* [Active Record Pattern](https://en.wikipedia.org/wiki/Active_record_pattern)
* [Object–relational Mapping](https://en.wikipedia.org/wiki/Object%E2%80%93relational_mapping)

## Credits
* [Data Mapper](http://richard.jp.leguen.ca/tutoring/soen343-f2010/tutorials/implementing-data-mapper/)