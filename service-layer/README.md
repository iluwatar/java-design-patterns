---
layout: pattern
title: Service Layer
folder: service-layer
permalink: /patterns/service-layer/
pumlid: LOl93SCm3C1MQGUmzUysgY8aAcJ5q96WszVV_aW2V8gHriRb-ZWoPxm07E--Inxrhc2dqv8jEvq3HEl6H8SFNjWs3jcjJSnaju21iG3MSmbnK_mkuwJ_qij7dpNq1m00
categories: Architectural
tags:
 - Java
 - Difficulty-Intermediate
---

## Intent
Service Layer is an abstraction over domain logic. Typically
applications require multiple kinds of interfaces to the data they store and
logic they implement: data loaders, user interfaces, integration gateways, and
others. Despite their different purposes, these interfaces often need common
interactions with the application to access and manipulate its data and invoke
its business logic. The Service Layer fulfills this role.

![alt text](./etc/service-layer.png "Service Layer")

## Applicability
Use the Service Layer pattern when

* you want to encapsulate domain logic under API
* you need to implement multiple interfaces with common logic and data

## Credits

* [Martin Fowler - Service Layer](http://martinfowler.com/eaaCatalog/serviceLayer.html)
* [Patterns of Enterprise Application Architecture](http://www.amazon.com/Patterns-Enterprise-Application-Architecture-Martin/dp/0321127420)
