---
layout: pattern
title: Page Controller
folder: parameter-controller
permalink: /patterns/parameter-object/
categories: Structural
language: en
tags:
- Decoupling
---
## Intent
It is an approach of one page leading to one logical file that handles actions or requests on a website.

## Class diagram
![alt text](./etc/page-controller.urm.png)

## Applicability
Use the Page Controller pattern when
- you implement a site where most controller logic is simple
- you implement a site where particular actions are handled with a particular server page

## Credits
- [Page Controller](https://www.martinfowler.com/eaaCatalog/pageController.html)
- [Pattern of Enterprise Application Architecture](https://www.martinfowler.com/books/eaa.html)