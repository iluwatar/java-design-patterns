---
layout: pattern
title: Data Access Object
folder: dao
permalink: /patterns/dao/
categories: Architectural
tags: Java
---

**Intent:** Object provides an abstract interface to some type of database or
other persistence mechanism.

![alt text](./etc/dao.png "Data Access Object")

**Applicability:** Use the Data Access Object in any of the following situations

* when you want to consolidate how the data layer is accessed
* when you want to avoid writing multiple data retrieval/persistence layers
