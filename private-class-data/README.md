---
layout: pattern
title: Private Class Data
folder: private-class-data
permalink: /patterns/private-class-data/
pumlid: RShR3SCm243HLTe1RFwx3S4eeSB4uf6itmpGlwkZ-nOZhS7b-ZeoLtm07E--InwrLR3JQScMdSu9edLZeiCNBso3GtPh2pFPBM1YF07BvSBaHeeHRJm_SD8VxkMphvhw0m00
categories: Other
tags:
 - Java
 - Difficulty-Beginner
 - Idiom
---

## Intent
Private Class Data design pattern seeks to reduce exposure of
attributes by limiting their visibility. It reduces the number of class
attributes by encapsulating them in single Data object.

![alt text](./etc/private-class-data.png "Private Class Data")

## Applicability
Use the Private Class Data pattern when

* you want to prevent write access to class data members
