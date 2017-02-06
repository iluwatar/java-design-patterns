---
layout: pattern
title: Singleton
folder: singleton
permalink: /patterns/singleton/
pumlid: HSV14SCm20J0Lk82BFxf1ikCh0n26ZZizfDVVhjRjwfvIhg-Bc35cyZvAQtZoYD3l4w364gTWxhcms2d3z-ydnAzsRuO4BUWmV43HRUcWcaagF-Lz55M3lq2
categories: Creational
tags:
 - Java
 - Gang Of Four
 - Difficulty-Beginner
---

## Intent
Ensure a class only has one instance, and provide a global point of
access to it.

![alt text](./etc/singleton_1.png "Singleton")

## Applicability
Use the Singleton pattern when

* there must be exactly one instance of a class, and it must be accessible to clients from a well-known access point
* when the sole instance should be extensible by subclassing, and clients should be able to use an extended instance without modifying their code

## Typical Use Case

* the logging class
* managing a connection to a database
* file manager

## Real world examples

* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)


## Consequences

* Violates Single Responsibility Principle (SRP) by controlling their own creation and lifecycle.
* Encourages using a global shared instance which prevents an object and resources used by this object from being deallocated.     
* Creates tightly coupled code that is difficult to test.
* Makes it almost impossible to subclass a Singleton.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
* [Effective Java (2nd Edition)](http://www.amazon.com/Effective-Java-Edition-Joshua-Bloch/dp/0321356683)
