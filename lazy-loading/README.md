---
layout: pattern
title: Lazy Loading
folder: lazy-loading
permalink: /patterns/lazy-loading/
pumlid: LSXB3W8X303Gg-W1e7jlqu66gIc5zED4JwzRTo_lpjeaEwN9xOpO_W0mlEhWEFD89sjBWpHgMnDOyi90WoU-i7Ho7besHf2fmqJ_0GG_xo8BE-i0YlONDMtMdLE-
categories: Other
tags:
 - Java
 - Difficulty-Beginner
 - Idiom
 - Performance
---

## Intent
Lazy loading is a design pattern commonly used to defer
initialization of an object until the point at which it is needed. It can
contribute to efficiency in the program's operation if properly and
appropriately used.

![alt text](./etc/lazy-loading.png "Lazy Loading")

## Applicability
Use the Lazy Loading idiom when

* eager loading is expensive or the object to be loaded might not be needed at all

## Real world examples

* JPA annotations @OneToOne, @OneToMany, @ManyToOne, @ManyToMany and fetch = FetchType.LAZY

## Credits

* [J2EE Design Patterns](http://www.amazon.com/J2EE-Design-Patterns-William-Crawford/dp/0596004273/ref=sr_1_2)
