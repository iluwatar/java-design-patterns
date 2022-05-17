---
layout: pattern
title: Concrete-Table-Inheritance
folder: Concrete-Table-Inheritance
permalink: /patterns/concrete-table-inheritance/
categories: Structural
tags:
- Data access
---
## Name
Concrete Table Inheritance
## Also known as
Leaf Table Inheritance
## Intent
Relational databases do not support inheritance relationships between tables. But by using classes to represent tables, we can create a hierarchy of tables.

## Explanation

In plain words

> It uses a concrete java class to represent a database table.

**Programmatic Example**

We have three tables, **bowler**, **cricketer**, and **footballer**. We extract same columns from these tables and create an inheritance hierarchy. We have four java classes, **Bowler**, **Cricketer**, **Footballer**, and **Player**, using an inheritance relationship to represent those tables.

## Class diagram
![alt text](./etc/concrete-table-inheritance.png "identity-field")

## Applicability
Use the Concrete-Table-Inheritance pattern when

* figuring out how to map inheritance.
 