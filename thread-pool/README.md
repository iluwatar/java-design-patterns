---
layout: pattern
title: Thread Pool
folder: thread-pool
permalink: /patterns/thread-pool/
pumlid: JSV14SCW30J0Lk82GFzq8uF6a1624IUx_UIPt-xHhMXK2TTN0zP-4pa_-UfeSSOMBzCWXbpceAxnCDZfmpUdAhjVbXO3uhPfyFw1q5oufZMdag3yFuUFl6Be5m00
categories: Concurrency
tags:
 - Java
 - Difficulty-Intermediate
 - Performance
---

## Intent
It is often the case that tasks to be executed are short-lived and
the number of tasks is large. Creating a new thread for each task would make
the system spend more time creating and destroying the threads than executing
the actual tasks. Thread Pool solves this problem by reusing existing threads
and eliminating the latency of creating new threads.

![alt text](./etc/thread-pool.png "Thread Pool")

## Applicability
Use the Thread Pool pattern when

* you have a large number of short-lived tasks to be executed in parallel
