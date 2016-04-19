---
layout: post
title: Hexagonal Architecture in Practise
author: ilu
---

![Nut]({{ site.url }}{{ site.baseurl }}/assets/nut-small.jpg)

Layered architecture is a well known pattern that organizes application into layers each having their specific purpose. The database layer takes care of data transactions, the business layer in responsible for business logic and the presentation layer deals with the user input.
The layered architecture implements so called separation of concerns principle which leads to more maintainable applications. Changes to one area in the software are not propagated to other areas.

![Layers]({{ site.url }}{{ site.baseurl }}/assets/layers.png)

This way of building applications can be considered simple and effective. Moreover, we can see that the heart of the application is the centermost business logic layer. When implemented thoughtfully, the business logic layer can function without a real presentation layer or database.

Hexagonal architecture builds upon this concept. The main objective of the architecture is to create fully testable systems that can be driven equally by users, programs and batch scripts in isolation of database. 

At the heart of the application is the business logic. It answers the question what does this application do? But the business logic alone may not be very useful. Something has to drive this application, call the business logic methods. These drivers we call the primary ports. Also, the business logic has its dependencies. For example, there may be a data storage module that business logic calls upon to retrieve and update data. These dependencies are called the secondary ports of the application.

The ports may have one or more implementations. For example there may be a mock database for testing and a real database for running the application. The port implementations are called adapters. Thus the alias name Ports and Adapters for Hexagonal Architecture. Other names describing the same concept are Clean Architecture and Onion Architecture.

The name for the pattern comes from the following hexagonal drawing.

![Hexagon]({{ site.url }}{{ site.baseurl }}/assets/hexagon.png)

The diagram shows how the domain is in the middle surrounded by ports on each side of hexagon. The actual amount of ports does not have to be exactly six, it can be less or it can be more depending on the application needs. One of the implementations of Hexagonal architecture can be considered Naked objects pattern that is applied in Apache Isis framework.

lottery system implementation
- description what the system does
- drawing with domain, ports and adapters
- implement each part
