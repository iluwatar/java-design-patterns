---
layout: pattern
title: Front Controller
folder: front-controller
permalink: /patterns/front-controller/
categories: Presentation Tier
tags: Java
---

**Intent:** Introduce a common handler for all requests for a web site. This
way we can encapsulate common functionality such as security,
internationalization, routing and logging in a single place.

![alt text](./etc/front-controller.png "Front Controller")

**Applicability:** Use the Front Controller pattern when

* you want to encapsulate common request handling functionality in single place
* you want to implements dynamic request handling i.e. change routing without modifying code
* make web server configuration portable, you only need to register the handler web server specific way

**Real world examples:** 

* [Apache Struts](https://struts.apache.org/)
