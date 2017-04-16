---
layout: pattern
title: Data Bus
folder: data-bus
permalink: /patterns/data-bus/

categories: Architectural
tags:
 - Java
 - Difficulty-Intermediate
---

## Intent

Allows send of messages/events between components of an application
without them needing to know about each other. They only need to know
about the type of the message/event being sent.

![data bus pattern uml diagram](./etc/data-bus.urm.png "Data Bus pattern")

## Applicability
Use Data Bus pattern when

* you want your components to decide themselves which messages/events they want to receive
* you want to have many-to-many communication
* you want your components to know nothing about each other

## Related Patterns
Data Bus is similar to

* Mediator pattern with Data Bus Members deciding for themselves if they want to accept any given message
* Observer pattern but supporting many-to-many communication
* Publish/Subscribe pattern with the Data Bus decoupling the publisher and the subscriber
