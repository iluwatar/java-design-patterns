---
layout: pattern
title: Monitor
folder: monitor
permalink: /patterns/monitor/
categories: Concurrency
tags:
 - Performance
---

## Intent
Monitor pattern is used to create thread-safe objects and prevent conflicts between threads in concurrent applications.

## Explanation

In plain words

> Monitor pattern is used to enforce single-threaded access to data. Only one thread at a time is allowed to execute code within the monitor object.

Wikipedia says

> In concurrent programming (also known as parallel programming), a monitor is a synchronization construct that allows threads to have both mutual exclusion and the ability to wait (block) for a certain condition to become false. Monitors also have a mechanism for signaling other threads that their condition has been met. 

## Class diagram
![alt text](./etc/monitor.urm.png "Monitor class diagram")

## Applicability
Use the Monitor pattern when

* we have a shared resource and there is critical section .
* you want to create thread-safe objects .
* you want to achieve mutual exclusion in high level programming language .
