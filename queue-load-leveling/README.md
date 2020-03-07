---
layout: pattern
title: Queue based load leveling
folder: queue-load-leveling
permalink: /patterns/queue-load-leveling/
categories: Concurrency
tags:
 - Decoupling
 - Performance
---

## Intent
Use a queue that acts as a buffer between a task and a service that it invokes in order to smooth 
intermittent heavy loads that may otherwise cause the service to fail or the task to time out. 
This pattern can help to minimize the impact of peaks in demand on availability and responsiveness 
for both the task and the service.

## Class diagram
![alt text](./etc/queue-load-leveling.gif "queue-load-leveling")

## Applicability

* This pattern is ideally suited to any type of application that uses services that may be subject to overloading.
* This pattern might not be suitable if the application expects a response from the service with minimal latency.

## Tutorials
* [Queue-Based Load Leveling Pattern](http://java-design-patterns.com/blog/queue-load-leveling/)

## Real world example

* A Microsoft Azure web role stores data by using a separate storage service. If a large number of instances of the web role run concurrently, it is possible that the storage service could be overwhelmed and be unable to respond to requests quickly enough to prevent these requests from timing out or failing. 

## Credits

* [Microsoft Cloud Design Patterns: Queue-Based Load Leveling Pattern](https://msdn.microsoft.com/en-us/library/dn589783.aspx)
