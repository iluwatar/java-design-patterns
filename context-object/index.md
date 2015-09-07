---
layout: pattern
title: Composite
folder: composite
permalink: /patterns/context-object/
categories: Structural
tags: Java
---

**Intent:** Decouples, application data and logic from protocol/presentation-technology
e.g. HttpSession and HttpRequest

![alt text](./etc/context-object.png "Context Object")

**Applicability:** Use the Context-object pattern when

* you want to avoid using protocol-specific system information outside of its relevant context
* you want to share common information between multiple layers of your application

**Real world examples:**

* [CORBA Object Request Brokers (ORBs)](https://en.wikipedia.org/wiki/Object_request_broker)
