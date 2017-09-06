---
layout: pattern
title: Converter
folder: converter
permalink: /patterns/converter/
categories:
tags:
 - Java
 - Difficulty-Beginner
---

## Intent
The purpose of the Converter Pattern is to provide a generic, common way of bidirectional
conversion between corresponding types, allowing a clean implementation in which the types do not
need to be aware of each other. Moreover, the Converter Pattern introduces bidirectional collection
mapping, reducing a boilerplate code to minimum.

![alt text](./etc/converter.png "Converter Pattern")

## Applicability
Use the Converter Pattern in the following situations:

* When you have types that logically correspond which other and you need to convert entities between them
* When you want to provide different ways of types conversions depending on a context
* Whenever you introduce a DTO (Data transfer object), you will probably need to convert it into the domain equivalence

## Credits

* [Converter](http://www.xsolve.pl/blog/converter-pattern-in-java-8/)
