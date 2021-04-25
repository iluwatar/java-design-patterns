---
layout: pattern
title: Table Module
folder: table-module
permalink: /patterns/table-module/
categories: Behavioral
tags:
 - Data access
---
## Intent
Table Module organizes domain logic with one class per table in the database, and a single instance of a class contains the various procedures that will act on the data.

## Explanation

Real world example

> When dealing with a room booking system, we need some operations on the room table. We can use the table module pattern in this scenario. We can create a class named RoomTableModule and initialize a instance of that class to handle the business logic for all rows in the room table.

In plain words

> A single instance that handles the business logic for all rows in a database table or view.


## Class diagram
![](./etc/table-module.urm.png "table module")

## Applicability

Use the Table Module Pattern when

- Domain logic is simple and data is in tabular form.
- The application only use few shared common table-oriented data structure.

## Related patterns

-  Transaction Script

- Domain Model

## Credits

* [Table Module Pattern](http://wiki3.cosc.canterbury.ac.nz/index.php/Table_module_pattern)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321127420&linkId=18acc13ba60d66690009505577c45c04)
* [Architecture patterns: domain model and friends](https://inviqa.com/blog/architecture-patterns-domain-model-and-friends)

