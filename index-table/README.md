---
layout: pattern
title: Index Table
folder: index-table
permalink: /patterns/index-table/

# both categories and tags are Yaml Lists
# you can either just pick one or write a list with '-'s
# usable categories and tags are listed here: https://github.com/iluwatar/java-design-patterns/blob/gh-pages/_config.yml
categories: creational # categories of the pattern
tags: # tags of the pattern
 - Java
 - Performance
---

## Also known as
Secondary Index

## Intent
Create indexes over the fields in data stores that are frequently referenced by query criteria. 
This pattern can improve query performance by allowing applications to more quickly locate the data to retrieve from a data store.

## Explanation
Many data stores organize the data for a collection of entities by using the primary key.
An application can use this key to locate and retrieve data.
While the primary key is valuable for queries that fetch data based on the value of this key, an application might not be able to use the primary key if it needs to retrieve data based on some other field, so you can create an index table using secondary index.
A secondary index is a separate data structure that is organized by one or more non-primary (secondary) key fields, and it indicates where the data for each indexed value is stored. The items in a secondary index are typically sorted by the value of the secondary keys to enable fast lookup of data. These indexes are usually maintained automatically by the database management system.

## Applicability
Use the Index Table pattern when

* a system includes plenty of query operations, and this could be a slow process.
* you want to improve query performance.