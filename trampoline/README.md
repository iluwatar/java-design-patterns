---
layout: pattern
title: Trampoline
folder: trampoline
permalink: /patterns/trampoline/
categories: Behavior
tags:
 - Java
 - Difficulty-Intermediate
 - Performance
 - Recursion
---

## Intent
By representing a computation in one of 2 states 
(completed with result, or a reference to the reminder of the computation, 
something like the way a java.util.Supplier does) 
it is possible to implement algorithms recursively in Java without blowing the stack 
and to interleave the execution of functions without hard coding them together or even using threads.



## Applicability
Use the Trampoline pattern when

* For implementing tail recursive function. This pattern allows to switch on a stackless operation.
* For interleaving the execution of two or more functions on the same thread.

## Known uses(real world examples)
* Trampoline refers to using reflection to avoid using inner classes, for example in event listeners. 
The time overhead of a reflection call is traded for the space overhead of an inner class. 
Trampolines in Java usually involve the creation of a GenericListener to pass events to an outer class.


## Credits

* [Trampolining: a practical guide for awesome Java Developers](https://medium.com/@johnmcclean/trampolining-a-practical-guide-for-awesome-java-developers-4b657d9c3076)
* [Trampoline in java ](http://mindprod.com/jgloss/trampoline.html)
* [cyclops-react](https://github.com/aol/cyclops-react)

