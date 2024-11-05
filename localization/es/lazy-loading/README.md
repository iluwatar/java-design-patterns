---
title: Lazy Loading
shortTitle: Lazy Loading
category: Idiom
language: es
tag:
 - Performance
---

## Propósito
La carga diferida (*Lazy loading* en inglés) es un patrón de diseño comúnmente usado para diferir
la inicialización de un objeto hasta el punto en que se necesita. Puede
contribuir a la eficiencia en la operación del programa si se usa de manera adecuada.

## Diagrama de clases
![alt text](./etc/lazy-loading.png "Lazy Loading")

## Aplicabilidad
Utilice el modelo de Carga Diferida cuando:

* La carga anticipada es costosa o el objeto a cargar podría no ser necesario en absoluto

## Ejemplos del mundo real

* Anotaciones JPA `@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany` y `fetch = FetchType.LAZY`

## Créditos

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
