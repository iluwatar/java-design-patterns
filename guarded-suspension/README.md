---
title: Guarded Suspension
category: Concurrency
language: en
tags:
 - Decoupling
---

## Intent
Use Guarded suspension pattern to handle a situation when you want to execute a method on object which is not in a proper state.

## Class diagram
![Guarded Suspension diagram](./etc/guarded-suspension.png)

## Applicability
Use Guarded Suspension pattern when the developer knows that the method execution will be blocked for a finite period of time

## Related patterns

* Balking 
