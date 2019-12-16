---
layout: pattern
title: Resource Acquisition Is Initialization
folder: resource-acquisition-is-initialization
permalink: /patterns/resource-acquisition-is-initialization/
categories: Idiom
tags:
 - Data access
---

## Intent
Resource Acquisition Is Initialization pattern can be used to implement exception safe resource management.

## Class diagram
![alt text](./etc/resource-acquisition-is-initialization.png "Resource Acquisition Is Initialization")

## Applicability
Use the Resource Acquisition Is Initialization pattern when

* You have resources that must be closed in every condition
