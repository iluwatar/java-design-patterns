---
layout: pattern
title: Identity-Field
folder: identity-field
permalink: /patterns/identity-fields/
categories: Structural
tags:
- Data access
---

## Intent
Saves a database ID field in an object to maintain an identity between an in-memory object and a database row.

## Explanation

In plain words

> It uses a java class that has one field matching the primary key in a database table.
**Programmatic Example**

We have a **person** table which contains **id**, **name**, **age**, and **gender** as columns.

| id |   name   | age | gender |
| :--------: | :--------: | :--------: | :--------: |
| 1	 | ramesh   | 28  | male   |
| 2	 | prakash  | 28  | male   |

We also have a java class **Person** which contains **id**, **name**, **age**, and **gender** as fields.

```java
class Person{
    private long id;
    private String name;
    private int age;
    private String gender;
    // standard getter and setter methods.
}
```
1. **id** column is the **primary key** in a database that is unique and distinguishes each row.
2. In the above table, there are two rows and which are uniquely identified by the primary key.
3. The relational database uses the primary key to identify one row from another.

The **id** column with datatype long represents the identity field.

In **Person** java class, the **id** field is an identity field that matches the primary key in a database table.

##Representing the Identity Field in an Object

The simplest form of Identity Field is a field that matches the type of key in the database. Thus, if you use a simple integral key, an integral field will work very nicely.

There are three basic choices to get the key:
- Get the database to auto-generate
- Use a GUID
- Generate your own

## Class diagram
![Identity-Field Class Diagram](./etc/identity-field.png "identity-field")

## Applicability
Use the Identity-Field pattern when

* There’s a mapping between objects in memory and rows in a database.