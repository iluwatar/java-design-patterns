---
layout: pattern
title: Balking
folder: balking
permalink: /patterns/balking/
categories: Concurrency
tags:
 - Java
 - Difficulty-Beginner
---

## Intent
Balking Pattern is used to prevent an object from executing certain code if it is an
incomplete or inappropriate state

![alt text](./etc/balking.png "Balking")

## Applicability
Use the Balking pattern when

* you want to invoke an action on an object only when it is in a particular state
* objects are generally only in a state that is prone to balking temporarily
but for an unknown amount of time

## Related patterns
* Guarded Suspension Pattern
* Double Checked Locking Pattern
