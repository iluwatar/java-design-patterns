---
layout: pattern
title: Throttling
folder: throttling
permalink: /patterns/throttling/
categories: Behavioral
tags:
 - Java
 - Difficulty-Beginner
---

## Intent
Ensure that a given client is not able to access service resources more than the assigned limit.

## Applicability
The Throttling pattern should be used:

* when a service access needs to be restricted to not have high impacts on the performance of the service.
* when multiple clients are consuming the same service resources and restriction has to be made according to the usage per client.
