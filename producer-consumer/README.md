---
layout: pattern
title: Producer Consumer
folder: producer-consumer
permalink: /patterns/producer-consumer/
categories: Concurrency
tags:
 - Reactive
---

## Intent
Producer Consumer Design pattern is a classic concurrency pattern which reduces
 coupling between Producer and Consumer by separating Identification of work with Execution of
 Work.

## Class diagram
![alt text](./etc/producer-consumer.png "Producer Consumer")

## Applicability
Use the Producer Consumer idiom when

* Decouple system by separate work in two process produce and consume.
* Addresses the issue of different timing require to produce work or consuming work
