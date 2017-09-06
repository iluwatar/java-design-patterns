---
layout: pattern
title: Specification
folder: specification
permalink: /patterns/specification/
pumlid: LSX14i8m20NGg-W16lRU1YcsE0d9mCTUNxVkthoxkVJQjQBVJc3bWoZuQeVXh6UbXao7EfhCGTRhOd3Gcp-yxPfs-BOOqF2amVa3vLAnbmd3ffD2_gTLZBPgz2y0
categories: Behavioral
tags:
 - Java
 - Difficulty-Beginner
---

## Also known as
Filter, Criteria

## Intent
Specification pattern separates the statement of how to match a
candidate, from the candidate object that it is matched against. As well as its
usefulness in selection, it is also valuable for validation and for building to
order

![alt text](./etc/specification.png "Specification")

## Applicability
Use the Specification pattern when

* you need to select a subset of objects based on some criteria, and to refresh the selection at various times
* you need to check that only suitable objects are used for a certain role (validation)

## Related patterns

* Repository

## Credits

* [Martin Fowler - Specifications](http://martinfowler.com/apsupp/spec.pdf)
