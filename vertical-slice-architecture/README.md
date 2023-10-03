---
title: Vertical-Slice-Architecture
aka: Layer-By-Feature
category: Architectural
language: en
tag:
- Decoupling
---

## Intent

package the application based on features. Each feature will have its own set of layers (
Models, Services, Repository and Controllers ).

## Explanation

> With vertical slice architecture we can have high cohesion within package and low coupling
> among the packages. In Conceptual term

> Consider that you are going to make a backend service for a online e-commerce application.
> initially you make it with usual grouping of controllers, models etc. but as the application
> grows more it's requires implementation of new features. Let's say that you thought of having
> orders, customers and products associated layers. But now you need to include another set of
> features with Cart system and wishlists. Now it's really hard to integrate those features it
> requires lot's of dependency modifications and mocking. So if you make the package by feature
> it will be really feasible for future additions. General example.

## Class diagram

![Vertical Slice Architecture](./etc/vertical-slice-architecture.urm.png)

## Applicability

Use Vertical Slice Architecture when

* You want future modification ( new addition of features ).
* You want to reduce the amount of mocking.
* You want to make it more modular by feature.

## Resources

* [How to Implement Vertical Slice Architecture by Gary Woodfine](https://garywoodfine.com/implementing-vertical-slice-architecture/)
* [youtube](https://www.youtube.com/watch?v=B1d95I7-zsw)
* [medium](https://medium.com/sahibinden-technology/package-by-layer-vs-package-by-feature-7e89cde2ae3a)
* [A reference application](https://github.com/sugan0tech/Event-Manager)
