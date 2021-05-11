---
layout: pattern
title: Front Controller
folder: front-controller
permalink: /patterns/front-controller/
categories: Structural
language: en
tags:
 - Decoupling
---

## Intent
Introduce a common handler for all requests for a web site. This
way we can encapsulate common functionality such as security,
internationalization, routing and logging in a single place.

## Class diagram
![alt text](./etc/front-controller.png "Front Controller")

## Applicability
Use the Front Controller pattern when

* you want to encapsulate common request handling functionality in single place
* you want to implements dynamic request handling i.e. change routing without modifying code
* make web server configuration portable, you only need to register the handler web server specific way

## Real world examples

* [Apache Struts](https://struts.apache.org/)

## Credits

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Presentation Tier Patterns](http://www.javagyan.com/tutorials/corej2eepatterns/presentation-tier-patterns)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
