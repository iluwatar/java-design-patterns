---
layout: pattern
title: Business Delegate
folder: business-delegate
permalink: /patterns/business-delegate/
categories: Structural
tags:
 - Decoupling
---

## Intent
The Business Delegate pattern adds an abstraction layer between
presentation and business tiers. By using the pattern we gain loose coupling
between the tiers and encapsulate knowledge about how to locate, connect to,
and interact with the business objects that make up the application.

## Class diagram
![alt text](./etc/business-delegate.png "Business Delegate")

## Applicability
Use the Business Delegate pattern when

* you want loose coupling between presentation and business tiers
* you want to orchestrate calls to multiple business services
* you want to encapsulate service lookups and service calls

## Credits

* [J2EE Design Patterns](http://www.amazon.com/J2EE-Design-Patterns-William-Crawford/dp/0596004273/ref=sr_1_2)
