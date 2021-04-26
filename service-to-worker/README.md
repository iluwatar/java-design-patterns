---
layout: pattern
title: Service to Worker
folder: service-to-worker
permalink: /patterns/service-to-worker/
categories: Architectural
tags: - Decoupling
---

## Intent

Combine a controller and dispatcher with views and helpers to handle client requests and prepare a dynamic presentation as the response. Controllers delegate content retrieval to helpers, which manage the population of the intermediate model for the view. A dispatcher is responsible for view management and navigation and can be encapsulated either within a controller or a separate component.

## Explanation
The system controls the execution flow and access flow of business data, creates presentation content from it, and centralizes control and improves modularity, reuse, and separation of roles.


**Example**

We modified this pattern based on a classic design patterns [Model View Controller Pattern](https://github.com/iluwatar/java-design-patterns/tree/master/model-view-controller) as the Class Diagram.

In this example, the controller directly controls the display of the View and can receive commands to control the dispatcher indirectly. The dispatcher stores different commands that can be used to modify the `model` with `action`s or to modify the display in the `view`s.

Therefore, this example leverages the Service to Worker pattern to increase functionality cohesion and improve the business logic.


## Class diagram
![alt text](./etc/service-to-worker.png "Service to Worker")

## Applicability
- For the business logic of web development, the responsibility of a dispatcher component may be to translate the logical name login into the resource name of an appropriate view, such as login.jsp, and dispatch to that view. To accomplish this translation, the dispatcher may access resources such as an XML configuration file that specifies the appropriate view to display.

## Credits
* [J2EE Design Patterns](https://www.oreilly.com/library/view/j2ee-design-patterns/0596004273/re05.html)
* [Core J2EE Patterns](http://corej2eepatterns.com/Patterns/ServiceToWorker.htm)
