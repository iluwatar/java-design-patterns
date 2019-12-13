---
layout: pattern
title: Model-View-Controller
folder: model-view-controller
permalink: /patterns/model-view-controller/
categories: Architectural
tags:
 - Decoupling
---

## Intent
Separate the user interface into three interconnected components:
the model, the view and the controller. Let the model manage the data, the view
display the data and the controller mediate updating the data and redrawing the
display.

## Class diagram
![alt text](./etc/model-view-controller.png "Model-View-Controller")

## Applicability
Use the Model-View-Controller pattern when

* You want to clearly separate the domain data from its user interface representation

## Credits

* [Trygve Reenskaug - Model-view-controller](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
* [J2EE Design Patterns](http://www.amazon.com/J2EE-Design-Patterns-William-Crawford/dp/0596004273/ref=sr_1_2)
* [Patterns of Enterprise Application Architecture](http://www.amazon.com/Patterns-Enterprise-Application-Architecture-Martin/dp/0321127420)
