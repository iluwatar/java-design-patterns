---
layout: pattern
title: EIP Message Channel
folder: eip-message-channel
permalink: /patterns/eip-message-channel/
categories: Integration
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
