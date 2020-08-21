--- # this is so called 'Yaml Front Matter', read up on it here: http://jekyllrb.com/docs/frontmatter/
layout: pattern
title: Filterer Pattern
folder: filterer
permalink: /patterns/filterer/
description: Design pattern that helps container-like objects to return filtered version of themselves.# short meta description that shows in Google search results
categories:
 - Functional
tags:
 - Extensibility
---

## Name / classification
Filterer Pattern

## Intent
The intent of this design pattern is to to introduce a functional interface that will add a functionality for container-like objects to easily return filtered versions of themselves.

## Explanation
The container-like object needs to have a method that returns an instance of `Filterer`. This helper interface gives 
ability to covariantly specify a lower bound of contravariant `Predicate` in the subinterfaces of interfaces representing the container-like objects.

## Class diagram
![Filterer](./etc/filterer.png "Filterer")

## Applicability
Pattern can be used when working with container-like objects that use subtyping, instead of parametrizing(generics) for extensible class structure.
It enables you to easily extend filtering ability of container-like objects as business requirements change.

## Tutorials
* [Article about Filterer pattern posted on it's author's blog](https://blog.tlinkowski.pl/2018/filterer-pattern/)
* [Application of Filterer pattern in domain of text analysis](https://www.javacodegeeks.com/2019/02/filterer-pattern-10-steps.html)

## Known uses
One of the uses is present on the blog presented in this link. It presents how to use `Filterer` pattern to create text issue anaylyzer with support for test cases used for unit testing.

## Consequences (the good and the bad, add criticism here)
Good :
 * you can easily introduce new subtypes for container-like objects and subtypes for objects that are contained within them and still be able to filter easily be new properties of those new subtypes.

Bad :
 * covariant return types mixed with generics can be sometimes tricky

## Credits
* Author of the pattern : [Tomasz Linkowski](https://tlinkowski.pl/)