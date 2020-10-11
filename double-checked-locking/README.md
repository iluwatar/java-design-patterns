---
layout: pattern
title: Double Checked Locking
folder: double-checked-locking
permalink: /patterns/double-checked-locking/
categories: Idiom
tags:
 - Performance
---

## Intent
Reduce the overhead of acquiring a lock by first testing the
locking criterion (the "lock hint") without actually acquiring the lock. Only
if the locking criterion check indicates that locking is required does the
actual locking logic proceed.

## Class diagram
![alt text](./etc/double_checked_locking_1.png "Double Checked Locking")

## Applicability
Use the Double Checked Locking pattern when

* there is concurrent access in object creation, e.g. singleton, where you want to create a single instance of the same class and checking if it's null or not maybe be enough when there are two or more threads that check if the instance is null or not.
* there is concurrent access on a method where the method's behavior changes according to some constraints and these constraint change within this method.
