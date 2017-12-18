---
layout: pattern
title: Producer Consumer
folder: producer-consumer
permalink: /patterns/producer-consumer/
categories: Concurrency
tags:
 - Java
 - Difficulty-Intermediate
 - I/O
 - Reactive
---

## Intent
Producer Consumer Design pattern is a classic concurrency pattern which reduces
 coupling between Producer and Consumer by separating Identification of work with Execution of
 Work.

![alt text](./etc/producer-consumer.png "Producer Consumer")

## Applicability
Use the Producer Consumer idiom when

* decouple system by separate work in two process produce and consume.
* addresses the issue of different timing require to produce work or consuming work
