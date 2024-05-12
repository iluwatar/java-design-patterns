---
title: Dirty Flag
category: Behavioral
language: es
tag:
 - Game programming
 - Performance
---

## También conocido como
* IsDirty pattern

## Propósito
Evitar la costosa readquisición de recursos. Los recursos conservan su identidad, se guardan en algún almacenamiento de acceso rápido y se reutilizan para evitar tener que adquirirlos de nuevo.

## Diagrama de clases
![alt text](./etc/dirty-flag.png "Dirty Flag")

## Aplicabilidad
Utilice el patrón Dirty Flag cuando

* La adquisición, inicialización y liberación repetitiva del mismo recurso causa una sobrecarga de rendimiento innecesaria.

## Créditos

* [Design Patterns: Dirty Flag](https://www.takeupcode.com/podcast/89-design-patterns-dirty-flag/)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
