---
layout: pattern
title: Update Method
folder: update-method
permalink: /patterns/update-method/
categories: Creational
tags:
 - Java
 - Gang Of Four
 - Difficulty-...
---

## Intent
Simulate a collection of independent objects by telling each to process one frame of behavior at a time.

![alt text](./etc/update-method.png "Update method")

## Applicability
Update methods work well when:

* Your game has a number of objects or systems that need to run simultaneously.

* Each object’s behavior is mostly independent of the others.

* The objects need to be simulated over time.

## Credits

* [Anita Ungor - Update method]
