---
layout: pattern
title: Guarded Suspension
folder: guarded-suspension
permalink: /patterns/guarded-suspension/
pumlid: RScv3SCm3030LU819FRPXg5fIm552tnYPFiyjRi3RkbAaYkdoQr5JBy369vrxz7oaSv6XmPhL3e6TCaJ0msU-CAoilTToyG8DdKOw5z0GzcAlvNAN_WZSD1brBHHPmxv0000
categories: Concurrency
tags:
 - Java
 - Difficulty-Beginner
---

## Intent
Use Guareded suspension pattern to handle a situation when you want to execute a method on object which is not in a proper state.
![Guarded Suspension diagram](./etc/guarded-suspension.png)

## Applicability
Use Guareded Suspension pattern when:

* the developer knows that the method execution will be blocked for a finite period of time

