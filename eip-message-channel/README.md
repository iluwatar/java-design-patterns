---
layout: pattern
title: EIP Message Channel
folder: eip-message-channel
permalink: /patterns/eip-message-channel/
categories: Integration
language: en
tags:
 - Enterprise Integration Pattern
---

## Intent
When two applications communicate using a messaging system they do it by using logical addresses
of the system, so called Message Channels.

## Class diagram
![alt text](./etc/message-channel.png "Message Channel")

## Applicability
Use the Message Channel pattern when

* two or more applications need to communicate using a messaging system

## Real world examples

* [akka-camel](http://doc.akka.io/docs/akka/snapshot/scala/camel.html)
* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://www.amazon.com/gp/product/0321200683/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321200683&linkCode=as2&tag=javadesignpat-20&linkId=122e0cff74eedd004cc81a3ecfa623cf)
