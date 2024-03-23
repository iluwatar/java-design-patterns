---
title: MonoState
category: Creational
language: es
tag:
 - Instantiation
---

## También conocido como
Borg

## Propósito
Impone un comportamiento como compartir el mismo estado entre todas las instancias.

## Diagrama de Clases
![alt text](./etc/monostate.png "MonoState")

## Applicability
Utilice el patrón Monostate cuando

* El mismo estado debe ser compartido por todas las instancias de una clase.
* Típicamente, este patrón puede ser usado en cualquier lugar donde un Singleton pueda ser usado. Sin embargo, el uso de Singleton no es transparente, el uso de Monostate sí lo es.
* Monostate tiene una gran ventaja sobre Singleton. Las subclases pueden decorar el estado compartido como deseen y, por lo tanto, pueden proporcionar dinámicamente un comportamiento diferente de la clase base.

## Casos de uso típicos

* La clase de registro
* Gestión de una conexión a una base de datos
* Gestor de archivos

## Ejemplos del mundo real

Aún no lo he visto.
