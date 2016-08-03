---
layout: pattern
title: Front Controller
folder: front-controller
permalink: /patterns/front-controller/
categories: Presentation Tier
tags:
 - Java
 - Difficulty-Intermediate
---

## Intent
Introduce a common handler for all requests for a web site. This
way we can encapsulate common functionality such as security,
internationalization, routing and logging in a single place.

![alt text](./etc/front-controller.png "Front Controller")

## Applicability
Use the Front Controller pattern when

* you want to encapsulate common request handling functionality in single place
* you want to implements dynamic request handling i.e. change routing without modifying code
* make web server configuration portable, you only need to register the handler web server specific way

## Real world examples

* [Apache Struts](https://struts.apache.org/)

## Credits

* [J2EE Design Patterns](http://www.amazon.com/J2EE-Design-Patterns-William-Crawford/dp/0596004273/ref=sr_1_2)
* [Presentation Tier Patterns](http://www.javagyan.com/tutorials/corej2eepatterns/presentation-tier-patterns)
* [Patterns of Enterprise Application Architecture](http://www.amazon.com/Patterns-Enterprise-Application-Architecture-Martin/dp/0321127420)
