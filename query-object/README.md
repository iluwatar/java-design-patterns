---
layout: pattern
title: Query Object Pattern
folder: query-object
permalink: /patterns/query-object/
categories: Integration
tags:
 - Data access
---

## Intent
Query Object Pattern allows data sources to handle various types of customized query by instantiating objects containing different query conditions instead of implementing each types of queries by adding a fixed method in data accessing middleware (In reality, it can be data repositories provided by ORM or a raw database interface like connection through JDBC) without revealing the schema of it to users. 

## Class diagram
![alt text](./etc/query_object.PNG "Query Object Pattern")

## Applicability
Use this design pattern when

* Your data access middleware might encounter various types of queries.
* You need to allow users to perform queries without knowing the database schema through your middleware.
