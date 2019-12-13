---
layout: pattern
title: Mute Idiom
folder: mute-idiom
permalink: /patterns/mute-idiom/
categories: Idiom
tags: 
 - Decoupling
---

## Intent
Provide a template to suppress any exceptions that either are declared but cannot occur or should only be logged;
while executing some business logic. The template removes the need to write repeated `try-catch` blocks.

## Class diagram
![alt text](./etc/mute-idiom.png "Mute Idiom")

## Applicability
Use this idiom when

* an API declares some exception but can never throw that exception eg. ByteArrayOutputStream bulk write method.
* you need to suppress some exception just by logging it, such as closing a resource.

## Credits

* [JOOQ: Mute Design Pattern](http://blog.jooq.org/2016/02/18/the-mute-design-pattern/)
