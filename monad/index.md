---
layout: pattern
title: Monad
folder: monad
permalink: /patterns/monad/
categories: Presentation Tier
tags:
 - Java
 - Difficulty-Advanced
---

## Intent

Monad pattern based on monad from linear algebra represents the way of chaining operations
together step by step. Binding functions can be described as passing one's output to another's input
basing on the 'same type' contract.

![alt text](./etc/monad.png "Monad")

## Applicability

Use the Monad in any of the following situations

* when you want to chain operations easily
* when you want to apply each function regardless of the result of any of them

## Credits
* [Design Pattern Reloaded by Remi Forax](https://youtu.be/-k2X7guaArU)
* [Brian Beckman: Don't fear the Monad](https://channel9.msdn.com/Shows/Going+Deep/Brian-Beckman-Dont-fear-the-Monads)
