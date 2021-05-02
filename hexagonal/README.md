---
layout: pattern
title: Hexagonal Architecture
folder: hexagonal
permalink: /patterns/hexagonal/
pumlformat: svg
categories: Architectural
tags:
 - Decoupling
---

## Also known as

* Ports and Adapters
* Clean Architecture
* Onion Architecture

## Intent
Allow an application to equally be driven by users, programs, automated test or batch scripts, and to be developed and tested in isolation from its eventual run-time devices and databases.

## Class diagram
![Hexagonal Architecture class diagram](./etc/hexagonal.png)

## Applicability
Use Hexagonal Architecture pattern when

* When the application needs to be independent of any frameworks
* When it is important that the application highly maintainable and fully testable

## Tutorials

* [Build Maintainable Systems With Hexagonal Architecture](http://java-design-patterns.com/blog/build-maintainable-systems-with-hexagonal-architecture/)

## Real world examples

* [Apache Isis](https://isis.apache.org/) builds generic UI and REST API directly from the underlying domain objects

## Credits

* [Alistair Cockburn - Hexagonal Architecture](http://alistair.cockburn.us/Hexagonal+architecture)
