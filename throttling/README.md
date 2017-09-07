---
layout: pattern
title: Throttling pattern
folder: throttling
permalink: /patterns/throttling/
tags:
 - Java
 - Difficulty-Beginner
 - Throttling
---

## Intent
Ensure that a given tenant is not able to access resources more than the assigned limit.
![alt text](./etc/throttling-patern.png "Throttling pattern")

## Applicability
The Throttling pattern should be used:

* when a service access needs to be restricted to not have high impacts on the performance of the service.
* when multiple tenants are consuming the same resources and restriction has to be made according to the usage per tenant.
