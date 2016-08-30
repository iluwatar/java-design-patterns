---
layout: pattern
title: Multiton
folder: multiton
permalink: /patterns/multiton/
pumlid: FST14i8m20NGg-W16lRUXgPCYnD81Zxs-hfozzvJlOywf68yBc3bYoZuRgVYghrIea-7E5gVHZhgPd3Gcp-y7P9w-hOOaF0au_o1h0OKqqdG_saLrbRP-080
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
