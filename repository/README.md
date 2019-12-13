---
layout: pattern
title: Repository
folder: repository
permalink: /patterns/repository/
categories: Architectural
tags:
 - Data access
---

## Intent
Repository layer is added between the domain and data mapping
layers to isolate domain objects from details of the database access code and
to minimize scattering and duplication of query code. The Repository pattern is
especially useful in systems where number of domain classes is large or heavy
querying is utilized.

## Class diagram
![alt text](./etc/repository.png "Repository")

## Applicability
Use the Repository pattern when

* The number of domain objects is large
* You want to avoid duplication of query code
* You want to keep the database querying code in single place
* You have multiple data sources

## Real world examples

* [Spring Data](http://projects.spring.io/spring-data/)

## Credits

* [Don’t use DAO, use Repository](http://thinkinginobjects.com/2012/08/26/dont-use-dao-use-repository/)
* [Advanced Spring Data JPA - Specifications and Querydsl](https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/)
* [Repository Pattern Benefits and Spring Implementation](https://stackoverflow.com/questions/40068965/repository-pattern-benefits-and-spring-implementation)
