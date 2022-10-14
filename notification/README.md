--- 
layout: pattern 
title: com.iluwater.notification.Notification
folder: notification 
permalink: /patterns/notification/ 
categories: Behavioural
language: en 
tags:
- Decoupling
- Presentation
---

## Intent

To capture information about errors and other information that occurs in the domain layer 
which acts as the internal logic and validation tool. The notification then communicates this information 
back to the presentation for handling and displaying to the user what errors have occurred and why.

## Explanation

Real world example

> You need to register a worker for your company. The information submitted needs to be valid
> before the worker can be added to the database, and if there are any errors you need to know about them.

In plain words

> A notification is simply a way of collecting information about errors and communicating it to the user 
> so that they are aware of them

**Programatic example**

## Class diagram

![alt text](./etc/notification.urm.png "Notification")

## Applicability

Use the notification pattern when:

* You wish to communicate information about errors between the domain layer and the presentation layer. This is most applicable when a seperated presentation pattern is being used as this does not allow for direct communication between the domain and presentation.

## Related patterns

* service layer
* Data Transfer Object
* Domain Model
* Remote Facade
* Autonomous View
* Layer Supertype
* Separated Presentation

## Credits

* [Martin Fowler - com.iluwater.notification.Notification Pattern](https://martinfowler.com/eaaDev/Notification.html)