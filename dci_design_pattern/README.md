---
layout: pattern
title: dci_design_pattern
folder: builder
permalink: /patterns/dci_design_pattern/
categories: Structural
language: en
tags:
 - Decoupling
---

## Intent

DCI can be seen as an architectural pattern, which is considered to be a complementary pattern to MVC.Although object-oriented programs can capture structure well, they cannot capture system actions. DCI is a vision that captures the end user role cognitive model and interactions between roles

## Applicability
Use the DCI pattern in order to achieve the following

* To improve the readability of object-oriented code by giving system behavior first-class status;
* To cleanly separate code for rapidly changing system behavior (what a system does) versus slowly changing domain knowledge (what a system is), instead of combining both in one class interface;
* To help software developers reason about system-level state and behavior instead of only object state and behavior;
* To support an object style of thinking that is close to programmers' mental models, rather than the class style of thinking that overshadowed object thinking early in the history of object-oriented programming languages.


## Credits

* [DCI Pattern: Wikipedia ](https://en.wikipedia.org/wiki/Data,_context_and_interaction)