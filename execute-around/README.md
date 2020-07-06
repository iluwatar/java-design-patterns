---
layout: pattern
title: Execute Around
folder: execute-around
permalink: /patterns/execute-around/
categories: Idiom
tags:
 - Extensibility
---

## Intent
Execute Around idiom frees the user from certain actions that
should always be executed before and after the business method. A good example
of this is resource allocation and deallocation leaving the user to specify
only what to do with the resource.

## Class diagram
![alt text](./etc/execute-around.png "Execute Around")

## Applicability
Use the Execute Around idiom when

* you use an API that requires methods to be called in pairs such as open/close or allocate/deallocate.

## Credits

* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
