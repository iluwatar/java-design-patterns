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

* When the application needs to be independent of any frameworks
* When it is important that the application highly maintainable and fully testable

## Real world examples

* [Apache Isis](https://isis.apache.org/)

## Credits

* [Alistair Cockburn - Hexagonal Architecture](http://alistair.cockburn.us/Hexagonal+architecture)
