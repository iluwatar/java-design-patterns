---
layout: post
title: Hexagonal Architecture in Practise
author: ilu
---

![Nut]({{ site.url }}{{ site.baseurl }}/assets/nut-small.jpg)

This blog post is about Hexagonal Architecture but to have something familiar to start with let's first talk about Layered Architecture. It is a well known architectural pattern that organizes application into layers each having their specific purpose. The database layer takes care of data transactions, the business layer is responsible for business logic and the presentation layer deals with the user input. The Layered Architecture implements so called separation of concerns principle which leads to more maintainable applications. Changes to one area in the software are not propagated to other areas.

![Layers]({{ site.url }}{{ site.baseurl }}/assets/layers.png)

This way of building applications can be considered simple and effective. But it also has several fallacies. When you see an application implemented with Layered Architecture, where is the application core? Is it the database? Maybe it's the business logic with some little things scattered over to the presentation layer. This is a typical problem with layers. There is no application core, there are just the layers and the core logic is scattered here and there.

Hexagonal Architecture tackles this issue by building the application around the core. The main objective is to create fully testable systems that can be driven equally by users, programs and batch scripts in isolation of database. 

However, the core alone may not be very useful. Something has to drive this application, call the business logic methods. These drivers we call the primary ports. Also, the core has its dependencies. For example, there may be a data storage module that the core calls upon to retrieve and update data. These dependencies are called the secondary ports of the application.

The ports may have one or more implementations. For example there may be a mock database for testing and a real database for running the application. The port implementations are called adapters. Thus the alias name Ports and Adapters for Hexagonal Architecture. Other names describing the same concept are Clean Architecture and Onion Architecture.

The name for the pattern comes from the following hexagonal drawing.

![Hexagon]({{ site.url }}{{ site.baseurl }}/assets/hexagon.png)

The diagram shows how the domain is in the middle surrounded by ports on each side of hexagon. The actual amount of ports does not have to be exactly six, it can be less or it can be more depending on the application needs. One of the implementations of Hexagonal architecture can be considered Naked Objects pattern that is applied in Apache Isis framework.

Next we will demonstrate Hexagonal Architecture by building a lottery system. The lottery system will provide two primary ports: One for the users to submit lottery tickets and another for system administrators to perform the draw.

Secondary ports consist of lottery ticket database, banking and notifications. The resulting hexagon of the system can be seen in the following diagram.

![Lottery system]({{ site.url }}{{ site.baseurl }}/assets/lottery.png)
