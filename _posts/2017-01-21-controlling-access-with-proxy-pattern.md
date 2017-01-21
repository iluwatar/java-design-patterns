---
layout: post
title: Controlling Access With Proxy Pattern
author: ilu
---

![Proxy]({{ site.baseurl }}/assets/castle.jpg)

## Introduction

One of the most useful and versatile design patterns is the classic Proxy.

## Proxy concept

Basically it wraps an object and acts as a proxy between the calls to the object. At first thought it might not sound so useful but this simple concept is a great solution to many problems.

![Proxy class diagram]({{ site.baseurl }}/assets/proxy-concept.png)

In the class diagram we see that the client depends only on the interface so it can as well use the proxy instead of the real subject. When the proxy object is called it does its thing and eventually forwards the call to the real subject.

## Types of proxies

There are multiple use cases where the proxy pattern is useful.

- Protection proxy limits access to the real subject. Based on some condition the proxy filters the calls and only some of them are let through to the real subject. The code example below is an example of protection proxy.

- Another type is the virtual proxy. Virtual proxies are used when an object is expensive to instantiate.  In the implementation the proxy manages the lifetime of the real subject. It decides when the instance creation is needed and when it can be reused. Virtual proxies are used to optimize performance.

- Caching proxies are used to cache expensive calls to the real subject. There are multiple caching strategies that the proxy can use. Some examples are read-through, write-through, cache-aside and time-based. The caching proxies are used for enhancing performance.

- Remote proxies are used in distributed object communication. Invoking a local object method on the remote proxy causes execution on the remote object.

- Smart proxies are used to implement reference counting and log calls to the object.

## Proxy example

To elaborate how Proxy design pattern works in practise let's think of a simple example. In a fantasy land far away inhabited by creatures from hobbits to dragons the mystic profession of wizard also exists. Wizards are known from their great magic but this power comes with a price. To upkeep and gain spell power the wizards must spend their time studying spellbooks and practising their spells. The best place in the neighborhood to study is the famous Ivory Tower built by the local archwizard and it has become very popular among the wizards. However, to keep his tower tidy and not too crowded the archwizard decided to limit the amount of wizards that are allowed to enter the tower simultaneously. He cast a protection spell on the tower that allows only the three first wizards to enter.

In the example `WizardTower` is the interface for all the towers and `IvoryTower` implements it. The plain `IvoryTower` allows everyone to enter but the archwizard has specifically cast a protection spell on it to limit the number of simultaneous visitors. The protection spell enhanced `IvoryTower` is called `WizardTowerProxy`. It implements `WizardTower` and wraps `IvoryTower`. Now everyone wanting to access `IvoryTower` needs to go through the `WizardTowerProxy`.

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/proxy/src/main/java/com/iluwatar/proxy/WizardTower.java?slice=27:"></script>

`WizardTower` is the interface for all towers. It contains one simple method for entering the tower.<br/><br/>

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/proxy/src/main/java/com/iluwatar/proxy/IvoryTower.java?slice=32:"></script>

`IvoryTower` is a simple implementation of `WizardTower`. It prints the entering wizard's name.<br/><br/>

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/proxy/src/main/java/com/iluwatar/proxy/Wizard.java?slice=29:"></script>

`Wizard` is a simple immutable object with name.<br/><br/>

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/proxy/src/main/java/com/iluwatar/proxy/WizardTowerProxy.java?slice=32:"></script>

`WizardTowerProxy` is the proxy class that wraps any kind of `WizardTower` while implementing its interface. It keeps count how many wizards have entered the wrapped `WizardTower` and when the number reaches three it refuses to let any more wizards in.<br/><br/>

<script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/proxy/src/main/java/com/iluwatar/proxy/App.java?slice=40:"></script>

Running the application produces the following output.<br/><br/>

```
22:01:56.571 [main] INFO com.iluwatar.proxy.IvoryTower - Red wizard enters the tower.
22:01:56.576 [main] INFO com.iluwatar.proxy.IvoryTower - White wizard enters the tower.
22:01:56.576 [main] INFO com.iluwatar.proxy.IvoryTower - Black wizard enters the tower.
22:01:56.576 [main] INFO com.iluwatar.proxy.WizardTowerProxy - Green wizard is not allowed to enter!
22:01:56.576 [main] INFO com.iluwatar.proxy.WizardTowerProxy - Brown wizard is not allowed to enter!
```
