---
layout: pattern
title: Lazy Loading
folder: lazy-loading
permalink: /patterns/lazy-loading/
categories: Other
tags: Java
---

**Intent:** Lazy loading is a design pattern commonly used to defer
initialization of an object until the point at which it is needed. It can
contribute to efficiency in the program's operation if properly and
appropriately used.

![alt text](./etc/lazy-loading.png "Lazy Loading")

**Applicability:** Use the Lazy Loading idiom when

* eager loading is expensive or the object to be loaded might not be needed at all

**Real world examples:**

* JPA annotations @OneToOne, @OneToMany, @ManyToOne, @ManyToMany and fetch = FetchType.LAZY
