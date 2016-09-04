---
layout: pattern
title: Message Channel
folder: message-channel
permalink: /patterns/message-channel/
pumlid: NSZB3SCm203GLTe1RExTXX1akm9YyMdMRy-zFRtdCf8wkLmUCtF72y3nxcFbhAE2dIvBjknqAIof6nCTtlZ1TdAiOMrZ9hi5ACOFe1o1WnjDD6C1Jlg_NgvzbyeN
categories: Integration
tags: 
 - Java
 - EIP
 - Apache Camel™
---

## Intent
When two applications communicate using a messaging system they do it by using logical addresses
of the system, so called Message Channels.

![alt text](./etc/message-channel.png "Message Channel")

## Applicability
Use the Message Channel pattern when

* two or more applications need to communicate using a messaging system

## Real world examples

* [akka-camel](http://doc.akka.io/docs/akka/snapshot/scala/camel.html)