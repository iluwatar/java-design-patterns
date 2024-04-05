---
title: Strangler
category: Structural
language: es
tag:
 - Extensibility
 - Cloud distributed
---

## Propósito
Migrar de forma incremental un sistema heredado sustituyendo gradualmente piezas específicas de funcionalidad
con nuevas aplicaciones y servicios. A medida que se sustituyen las funciones del sistema heredado, el nuevo
sistema acaba cubriendo todas las funciones del sistema antiguo y puede tener sus propias funciones nuevas, con lo que
estrangulando el sistema antiguo y permitiéndole retirarlo del servicio.

## Diagrama de clases
![alt text](./etc/strangler.png "Strangler")

## Aplicabilidad
Este patrón estrangulador es una manera segura de eliminar gradualmente una cosa por algo mejor, más barato, o
más expandible. Especialmente cuando se quiere actualizar el sistema heredado con nuevas técnicas y se necesita
desarrollar continuamente nuevas características al mismo tiempo. Tenga en cuenta que este patrón requiere un esfuerzo adicional,
por lo que normalmente se utiliza cuando el sistema no es tan simple.

## Créditos

* [Strangler pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/strangler)
* [Legacy Application Strangulation : Case Studies](https://paulhammant.com/2013/07/14/legacy-application-strangulation-case-studies/)
