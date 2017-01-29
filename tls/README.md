---
layout: pattern
title: Thread Local Storage
folder: tls
permalink: /patterns/tls/
pumlid: 
categories: Concurrency
tags:
 - Java
 - Difficulty-Intermediate
---

## Intent
Securing variables global to a thread, i.e. class variables if a Callable object, 
against being spoiled by other threads using the same instance of the Callable object

![alt text](./etc/tls.png "Thread Local Storage")

## Applicability
Use the Thread Local Storage in any of the following situations

* when you use class variables in your Callable Object that are not read-only and you use the same Callable instance in more than one thread running in parallel
* when you use static variables in your Callable Object that are not read-only and more than one instances of the Callable may run in parallel threads.
