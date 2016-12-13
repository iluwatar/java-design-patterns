---
layout: post
title: Controlling Access With Proxy Pattern
author: ilu
---

![Hexagonal Architecture]({{ site.baseurl }}/assets/hexagonal-architecture.png)

## Introduction

One of the most useful and versatile design patterns is the classic Proxy.

## Proxy concept

Basically it wraps an object and acts as a proxy between calls to the object. At first thought it might not sound so useful but this simple concept is a great solution to many problems.

## Proxy example

To elaborate let's think of a simple example. In a fantasy land far away inhabited by creatures from hobbits to dragons the mystic profession of wizard also exists. Wizards are known from their great magic but this power comes with a price. To upkeep and gain spell power the wizards must spend their time studying spellbooks and practising their spells. The best place in the neighborhood to study is the famous Ivory Tower built by the archmage Ivorious and it has become very popular among the wizards. However, to keep his tower tidy and not too crowded Ivorious decided to limit the amount of wizards that are allowed to enter simultaneously. He cast a protection spell on the tower that allows only the three first wizards to enter.

The class diagram shows the structure used to implement the Proxy pattern. WizardTower is the interface for all the towers and IvoryTower implements it. The plain IvoryTower allows everyone to enter but Ivorius has specifically cast a protection spell on it to limit the number of simultaneous visitors. The protection spell WizardTowerProxy also implements WizardTower but then it wraps IvoryTower. Now everyone wanting to access IvoryTower needs to go through WizardTowerProxy.

## Types of proxies

What we have seen in the previous tutorial is an example of protection proxy that limits access to the object. Protection proxies can also be used to implement more fine grained access control.

Another type is virtual proxy. Virtual proxies are used when an object is expensive to instantiate. We let the proxy manage the lifetime of the object, namely when the instance creation is needed and when it can be reused. 

Cache proxy can be used to cache expensive calls to the object improving performance.

Remote proxies are used in distributed object communication. Invoking a local object method causes execution on the remote object.

Smart proxies are used to implement reference counting and log calls to the object.

