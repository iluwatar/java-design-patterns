---
layout: pattern
title: Hexagonal Architecture
folder: hexagonal
permalink: /patterns/hexagonal/
pumlid: HSTB4W8X30N0g-W1XkozpPD90LO8L3wEnzUTk-xxq2fvSfhSUiJs1v7XAcr4psSwMrqQh57gcZGaBmICNdZZEDb7qsCZWasT9lm7wln1MmeXZlfVIPjbvvGl
categories: Architectural
tags:
 - Java
 - Difficulty-Expert
---

## Also known as
* Ports and Adapters
* Clean Architecture
* Onion Architecture

## Intent
Allow an application to equally be driven by users, programs, automated test or batch scripts, and to be developed and tested in isolation from its eventual run-time devices and databases.

![Hexagonal Architecture class diagram](./etc/hexagonal.png)

## Applicability
Use Hexagonal Architecture pattern when

* it is important that the application is fully testable
* you use Domain Driven Design methodology and/or Microservices architectural style

## Tutorials
* [Build Maintainable Systems With Hexagonal Architecture](http://java-design-patterns.com/blog/build-maintainable-systems-with-hexagonal-architecture/)

## Real world examples

* [Apache Isis](https://isis.apache.org/)

## Credits

* [Alistair Cockburn - Hexagonal Architecture](http://alistair.cockburn.us/Hexagonal+architecture)
