---
layout: pattern
title: Poison Pill
folder: poison-pill
permalink: /patterns/poison-pill/
categories: Other
tags:
 - Java
 - Difficulty-Intermediate
 - Reactive
---

## Intent
Poison Pill is known predefined data item that allows to provide
graceful shutdown for separate distributed consumption process.

![alt text](./etc/poison-pill.png "Poison Pill")

## Applicability
Use the Poison Pill idiom when

* need to send signal from one thread/process to another to terminate

## Real world examples

* [akka.actor.PoisonPill](http://doc.akka.io/docs/akka/2.1.4/java/untyped-actors.html)
