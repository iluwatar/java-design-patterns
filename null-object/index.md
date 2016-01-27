---
layout: pattern
title: Null Object
folder: null-object
permalink: /patterns/null-object/
categories: Behavioral
tags:
 - Java
 - Difficulty-Beginner
---

## Intent
In most object-oriented languages, such as Java or C#, references
may be null. These references need to be checked to ensure they are not null
before invoking any methods, because methods typically cannot be invoked on
null references. Instead of using a null reference to convey absence of an
object (for instance, a non-existent customer), one uses an object which
implements the expected interface, but whose method body is empty. The
advantage of this approach over a working default implementation is that a Null
Object is very predictable and has no side effects: it does nothing.

![alt text](./etc/null-object.png "Null Object")

## Applicability
Use the Null Object pattern when

* you want to avoid explicit null checks and keep the algorithm elegant and easy to read.

## Credits
* [Pattern Languages of Program Design](http://www.amazon.com/Pattern-Languages-Program-Design-Coplien/dp/0201607344/ref=sr_1_1)
