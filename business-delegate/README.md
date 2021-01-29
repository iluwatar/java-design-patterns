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

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
