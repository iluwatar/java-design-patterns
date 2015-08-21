---
layout: pattern
title: Service Layer
folder: service-layer
permalink: /patterns/service-layer/
categories: Architectural
tags: Java
---

**Intent:** Service Layer is an abstraction over domain logic. Typically
applications require multiple kinds of interfaces to the data they store and
logic they implement: data loaders, user interfaces, integration gateways, and
others. Despite their different purposes, these interfaces often need common
interactions with the application to access and manipulate its data and invoke
its business logic. The Service Layer fulfills this role.

![alt text](./etc/service-layer.png "Service Layer")

**Applicability:** Use the Service Layer pattern when

* you want to encapsulate domain logic under API
* you need to implement multiple interfaces with common logic and data
