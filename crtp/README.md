---
title: Curiously Recurring Template Pattern
language: en
---

## Name / classification

Curiously Recurring Template Pattern

## Also known as

Recursive Type Bound, Recursive Generic

## Intent

Allow derived components to inherit certain functionalities from a base component that are compatible with the derived type.

## Explanation

Real-world example

TODO: add real-world example

In plain words

Modify certain methods within a type to accept arguments specific to its subtypes.

Wikipedia says

> The curiously recurring template pattern (CRTP) is an idiom, originally in C++, in which a class X
> derives from a class template instantiation using X itself as a template argument.

**Programmatic example**

TODO: add programmatic example

## Class diagram

TODO: add class diagram

## Applicability

Use the Curiously Recurring Template Pattern when

* TODO: add use cases

## Tutorials

* [The NuaH Blog](https://nuah.livejournal.com/328187.html)
* Yogesh Umesh Vaity answer to [What does "Recursive type bound" in Generics mean?](https://stackoverflow.com/questions/7385949/what-does-recursive-type-bound-in-generics-mean)

## Known uses

* [java.lang.Enum](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html)

## Credits

* [How do I decrypt "Enum<E extends Enum<E>>"?](http://www.angelikalanger.com/GenericsFAQ/FAQSections/TypeParameters.html#FAQ106)
* Chapter 5 Generics, Item 30 in [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
