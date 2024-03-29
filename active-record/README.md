---
title: Active Record
category: Architectural
language: en
tag:
  - Data access
---

## Intent

The Active Record pattern is a design pattern that integrates data access logic directly into the
domain model, typically within the model classes themselves. This means that each domain object is
responsible for its own persistence, including tasks such as database querying, saving, updating,
and deleting records.

This pattern is particularly useful in scenarios where simplicity and rapid development are
prioritized, as it allows developers to quickly implement data access functionality with minimal
effort. By encapsulating persistence logic within the domain objects, the Active Record pattern
promotes a straightforward and intuitive approach to working with data, making it easier to
understand and maintain codebases.

## Explanation

## Class diagram

## Tutorials

* [Active Record](https://www.martinfowler.com/eaaCatalog/activeRecord.html/)
* [Overview of the Active Record Pattern](https://blog.savetchuk.com/overview-of-the-active-record-pattern)

## Consequences

## Related patterns

## Credits