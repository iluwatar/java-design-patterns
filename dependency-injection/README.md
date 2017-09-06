---
layout: pattern
title: Dependency Injection
folder: dependency-injection
permalink: /patterns/dependency-injection/
pumlid: RSdB3SCW303GLPe1mFTkunWhSGG6-PEesxS3zFQajubIpyPf_NL6B7y363xra3XpJsUZgS4QbUO0wVbWeC65DvR6BeUMXH5iwZ3GVu36YxMnqgU8NamXKu63_aPD6tNbw5y0
categories: Behavioral
tags:
 - Java
 - Difficulty-Beginner
---

## Intent
Dependency Injection is a software design pattern in which one or
more dependencies (or services) are injected, or passed by reference, into a
dependent object (or client) and are made part of the client's state. The
pattern separates the creation of a client's dependencies from its own
behavior, which allows program designs to be loosely coupled and to follow the
inversion of control and single responsibility principles.

![alt text](./etc/dependency-injection.png "Dependency Injection")

## Applicability
Use the Dependency Injection pattern when

* when you need to remove knowledge of concrete implementation from object
* to enable unit testing of classes in isolation using mock objects or stubs
