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

## Explanation

Real world example


In plain words


Wikipedia says
>A Data Mapper is a Data Access Layer that performs bidirectional transfer of data between a persistent data store 
> (often a relational database) and an in-memory data representation (the domain layer). The goal of the pattern is to 
> keep the in-memory representation and the persistent data store independent of each other and the data mapper itself. 
> This is useful when one needs to model and enforce strict business processes on the data in the domain layer that do 
> not map neatly to the persistent data store. 



## Class diagram
![alt text](./etc/data-mapper.png "Data Mapper")

## Applicability
Use the Data Mapper in any of the following situations

* when you want to decouple data objects from DB access layer 
* when you want to write multiple data retrieval/persistence implementations

## Credits

* [Data Mapper](http://richard.jp.leguen.ca/tutoring/soen343-f2010/tutorials/implementing-data-mapper/)
