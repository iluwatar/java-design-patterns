---
layout: pattern
title: EIP Splitter
folder: eip-splitter
permalink: /patterns/eip-splitter/
categories: Integration
language: en
tags:
 - Enterprise Integration Pattern
---

## Intent
It is very common in integration systems that incoming messages consists of many items bundled together. For example
an invoice document contains multiple invoice lines describing transaction (quantity, name of provided
service/sold goods, price etc.). Such bundled messages may not be accepted by other systems. This is where splitter
pattern comes in handy. It will take the whole document, split it based on given criteria and send individual
items to the endpoint.

## Diagram
![alt text](./etc/sequencer.gif "Splitter")

## Applicability
Use the Splitter pattern when

* You need to split received data into smaller pieces to process them individually
* You need to control the size of data batches you are able to process

## Credits

* [Gregor Hohpe, Bobby Woolf - Enterprise Integration Patterns](http://www.enterpriseintegrationpatterns.com/patterns/messaging/Sequencer.html)
* [Apache Camel - Documentation](http://camel.apache.org/splitter.html)
* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://www.amazon.com/gp/product/0321200683/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321200683&linkCode=as2&tag=javadesignpat-20&linkId=122e0cff74eedd004cc81a3ecfa623cf)
