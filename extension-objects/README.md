---
layout: pattern
title: Extension objects
folder: extension-objects
permalink: /patterns/extension-objects/
categories: Behavioral
language: en
tags:
 - Extensibility
---

## Intent
Anticipate that an object’s interface needs to be extended in the future. Additional
interfaces are defined by extension objects.

## Class diagram
![Extension_objects](./etc/extension_obj.png "Extension objects")

## Applicability
Use the Extension Objects pattern when:

* you need to support the addition of new or unforeseen interfaces to existing classes and you don't want to impact clients that don't need this new interface. Extension Objects lets you keep related operations together by defining them in a separate class
* a class representing a key abstraction plays different roles for different clients. The number of roles the class can play should be open-ended. There is a need to preserve the key abstraction itself. For example, a customer object is still a customer object even if different subsystems view it differently.
* a class should be extensible with new behavior without subclassing from it.

## Real world examples

* [OpenDoc](https://en.wikipedia.org/wiki/OpenDoc)
* [Object Linking and Embedding](https://en.wikipedia.org/wiki/Object_Linking_and_Embedding)
