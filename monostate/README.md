---
layout: pattern
title: MonoState
folder: monostate
permalink: /patterns/monostate/
categories: Creational
language: en
tags:
 - Instantiation
---

## Also known as
Borg

## Intent
Enforces a behaviour like sharing the same state amongst all instances.

## Class diagram
![alt text](./etc/monostate.png "MonoState")

## Applicability
Use the Monostate pattern when

* The same state must be shared across all instances of a class.
* Typically this pattern might be used everywhere a Singleton might be used. Singleton usage however is not transparent, Monostate usage is.
* Monostate has one major advantage over singleton. The subclasses might decorate the shared state as they wish and hence can provide dynamically different behaviour than the base class.

## Typical Use Case

* The logging class
* Managing a connection to a database
* File manager

## Real world examples

Yet to see this.
