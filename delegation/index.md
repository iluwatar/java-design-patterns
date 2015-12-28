---
layout: pattern
title: Delegation
folder: delegation
permalink: /patterns/delegation/
categories: Behavioral
tags:
 - Java
 - Difficulty-Beginner
---

**Also known as:** Proxy Chains

**Intent:** It is a technique where an object expresses certain behavior to the outside but in 
reality delegates responsibility for implementing that behaviour to an associated object. 

![alt text](./etc/delegation.png "Delegate")

**Applicability:** Use the Delegate pattern in order to achieve the following

* Reduce the coupling of methods to their class
* Components that behave identically, but realize that this situation can change in the future.

**Credits**

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
