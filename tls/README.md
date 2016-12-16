---
layout: pattern
title: Thread Local Storage
folder: tls
permalink: /patterns/tls/
pumlid: 5Sd13OGm30NHLg00uZlTc62HeCI9x6-s_ONJF6dMghd5AM5jAS3qdSZubwwA4aUuM1uAKQGyEg6CpZxSwUQ7jrEyNhfD1iJKwNql2Cr9aB-ci9vczFO7
categories: Concurrency
tags:
 - Java
 - Difficulty-Intermediate
---

## Intent
Securing variables global to a thread, i.e. class variables of the Runnable object, 
against being spoiled by other threads using the same instance of the Runnable object

![alt text](./etc/tls.png "Thread Local Storage")

## Applicability
Use the Thread Local Storage in any of the following situations

* when you use class variables in your Runnable Object that are not read-only
