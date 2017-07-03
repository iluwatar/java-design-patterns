---
layout: pattern
title: Object Mother
folder: object-mother
permalink: /patterns/object-mother/
pumlid: LOr13iCW30JlVKNx0E3UKxxYW9KGWK7sklb-wR6dtLbfj9k15DxRurKbDo_isfudCEsTaj8TZuhJTpVMF0GiY7dqL9lVjDHqqOT2OQk7X4a0grZgPAkaiL-S4Vh0kOYH_vVeskFyVMyiPUKN
categories: Creational
tags:
 - Java
 - Difficulty-Beginner
---

## Object Mother
Define a factory of immutable content with separated builder and factory interfaces.

![alt text](./etc/object-mother.png "Object Mother")

## Applicability
Use the Object Mother pattern when

* You want consistent objects over several tests
* you want to reduce code for creation of objects in tests
* every test should run with fresh data

## Credits

* [Answer by David Brown](http://stackoverflow.com/questions/923319/what-is-an-objectmother) to the stackoverflow question: [What is an ObjectMother?](http://stackoverflow.com/questions/923319/what-is-an-objectmother)

* [c2wiki - Object Mother](http://c2.com/cgi/wiki?ObjectMother)

* [Nat Pryce - Test Data Builders: an alternative to the Object Mother pattern](http://www.natpryce.com/articles/000714.html)
