---
layout: post
title: Hexagonal Architecture in Practise
author: ilu
---

![Nut]({{ site.url }}{{ site.baseurl }}/assets/nut-small.jpg)

Layered architecture is a well known pattern that organizes application into layers each having their specific purpose. The database layer takes care of data transactions, the business layer in responsible for business logic and the presentation layer deals with the user input.
The layered architecture implements so called separation of concerns principle which leads to more maintainable applications. Changes to one area in the software are not propagated to other areas.

![Layers]({{ site.url }}{{ site.baseurl }}/assets/layers.png)

This way of building applications can be considered simple and effective. Moreover, we can see that the heart of the application is the centermost business logic layer. When implemented thoughtfully, the business logic layer can function without the real presentation layer or database.

hexagonal architecture
- originated from layered architecture
- 2-dimensional picture
- domain in the middle, see domain driven design
- primary ports drive the application
- secondary ports are used by the domain
- adapters are implementations of the ports
- clean architecture, onion architecture
- fully testable systems that can be driven by users, programs, batch scripts equally in isolation of database
- naked objects

lottery system implementation
- description what the system does
- drawing with domain, ports and adapters
- implement each part
