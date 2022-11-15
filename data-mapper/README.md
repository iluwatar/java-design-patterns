---
title: Data Mapper
category: Architectural
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

> When accessing web resources through a browser, there is generally no need to interact with the server directly;
> the browser and the proxy server will complete the data acquisition operation, and the three will remain independent.

In plain words

> The data mapper will help complete the bi-directional transfer of persistence layer and in-memory data.

Wikipedia says

> A Data Mapper is a Data Access Layer that performs bidirectional transfer of data between a
> persistent data store (often a relational database) and an in-memory data representation (the domain layer).

Programmatic example

## Class diagram
![alt text](./etc/data-mapper.png "Data Mapper")

## Applicability
Use the Data Mapper in any of the following situations

* when you want to decouple data objects from DB access layer
* when you want to write multiple data retrieval/persistence implementations

## Tutorials

* [Spring Boot RowMapper](https://zetcode.com/springboot/rowmapper/)
* [Spring BeanPropertyRowMapper tutorial](https://zetcode.com/spring/beanpropertyrowmapper/)
* [Data Transfer Object Pattern in Java - Implementation and Mapping (Tutorial for Model Mapper & MapStruct)](https://stackabuse.com/data-transfer-object-pattern-in-java-implementation-and-mapping/)

## Known uses
* [SqlSession.getMapper()](https://mybatis.org/mybatis-3/java-api.html)

## Consequences

> Neatly mapped persistence layer data
> Data model follows the single function principle

## Related patterns
* [Active Record Pattern](https://en.wikipedia.org/wiki/Active_record_pattern)
* [Object–relational Mapping](https://en.wikipedia.org/wiki/Object%E2%80%93relational_mapping)

## Credits
* [Data Mapper](http://richard.jp.leguen.ca/tutoring/soen343-f2010/tutorials/implementing-data-mapper/)
