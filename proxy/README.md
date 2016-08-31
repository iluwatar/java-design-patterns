---
layout: pattern
title: Proxy
folder: proxy
permalink: /patterns/proxy/
pumlid: 9SR13OCm30NGLM00udktCS62eCI9x6yesrEfx_Jcehd69c5rEe3X7oBZE-q5HwpXOhahH95oRrHgt0msEldYPHClkow30J5rQko_qB3-VKYG_qjXBOrezGK0
categories: Structural
tags:
 - Java
 - Gang Of Four
 - Difficulty-Beginner
---

## Also known as
Surrogate

## Intent
Provide a surrogate or placeholder for another object to control
access to it.

![alt text](./etc/proxy_1.png "Proxy")

## Applicability
Proxy is applicable whenever there is a need for a more
versatile or sophisticated reference to an object than a simple pointer. Here
are several common situations in which the Proxy pattern is applicable

* a remote proxy provides a local representative for an object in a different address space.
* a virtual proxy creates expensive objects on demand.
* a protection proxy controls access to the original object. Protection proxies are useful when objects should have different access rights.

## Typical Use Case

* control access to another object
* lazy initialization
* implement logging
* facilitate network connection
* to count references to an object

## Real world examples

* [java.lang.reflect.Proxy](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
* [Apache Commons Proxy](https://commons.apache.org/proper/commons-proxy/)
* Mocking frameworks Mockito, Powermock, EasyMock

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
