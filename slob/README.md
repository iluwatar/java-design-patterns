---
title: Serialized LOB
category: Structural
language: en
tag:
  - Object Instantiation
---

## Intent

Object models often contain complicated graphs of small objects. Much of the
information in these structures isn’t in the objects but in the links between
them. Objects don’t have to be persisted as table rows related to each other.
Another form of persistence is serialization, where a whole graph of objects is
written out as a single large object (LOB) in a table.

## Class diagram

![alt text](./etc/slob.urm.png "Serialized LOB")

## Applicability

Serialized LOB isn’t considered as often as it might be. XML makes it much more
attractive since it yields a easy-to-implement textual approach. Its main disadvantage is that you can’t query the
structure using SQL.
SQL extensions appear to get at XML data within a field, but that’s still not the same (or portable).
This pattern works best when you can chop out a piece of the object model and use it to represent the LOB. Think of a
LOB as a way to take a bunch of
objects that aren’t likely to be queried from any SQL route outside the application. This graph can then be hooked into
the SQL schema.

## Credits

