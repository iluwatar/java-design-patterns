---
layout: pattern
title: Multiton
folder: multiton
permalink: /patterns/multiton/
categories: Creational
tags:
 - Java
 - Difficulty-Beginner
---

## Also known as
Registry

## Intent
Ensure a class only has limited number of instances, and provide a
global point of access to them.

![alt text](./etc/multiton.png "Multiton")

## Applicability
Use the Multiton pattern when

* there must be specific number of instances of a class, and they must be accessible to clients from a well-known access point
