---
title: Active Record
category: Architectural  
language: en
tags:
 - Data access
---

## Intent
The active record design pattern is a way of accessing data from databases by creating an object which contains the contents of a database row.

## Explanation
The ActiveRecord object is intitialised by an ActiveDatabase object which creates a connection to an exisitng database.

Real-world example
> You want to implement an application which access and update a database from within an OOP context.
> Update the database within a program.

In plain words

> For a system which requires database storage but do not have sufficient SQL knowledge to do so. 

Wikipedia says
>The active record pattern is an approach to accessing data in a database. A database table or view is wrapped into a class. Thus, an object instance is tied to a single row in the table. After creation of an object, a new row is added to the table upon save. Any object loaded gets its information from the database. When an object is updated, the corresponding row in the table is also updated. The wrapper class implements accessor methods or properties for each column in the table or view.


**Programmatic Example**

This implementation shows what the Active Record pattern could look like in a generic context. The Active Record makes initialises `contents` and `columns` as lists of strings. A developer can create an `ActiveDatabase` object by entering the configuration details required to start the connection. Assuming there is an exsiting database the developer would then create an `ActiveRecord` object using the `ActiveDatabase` object and the `id` of the row with which they would like to create in an active context. They are able to locate the row which matches this `id` then this can be read or deleted. 

If you are adding in a new row you can just update the `contents` list which would be empty if created with an `id` which doesnt exist yet.
