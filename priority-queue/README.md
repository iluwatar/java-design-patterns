---
layout: pattern
title: Priority Queue Pattern
folder: priority-queue
permalink: /patterns/priority-queue/
categories: Behavioral
tags:
 - Decoupling
---

## Intent
Prioritize requests sent to services so that requests with a higher priority are received and processed more quickly than those of a lower priority. This pattern is useful in applications that offer different service level guarantees to individual clients.

## Explanation
Applications may delegate specific tasks to other services; for example, to perform background processing or to integrate with other applications or services. In the cloud, a message queue is typically used to delegate tasks to background processing. In many cases the order in which requests are received by a service is not important. However, in some cases it may be necessary to prioritize specific requests. These requests should be processed earlier than others of a lower priority that may have been sent previously by the application.

## Class diagram
![alt text](./etc/priority-queue.urm.png "Priority Queue pattern class diagram")

## Applicability
Use the Property pattern when

* The system must handle multiple tasks that might have different priorities.
* Different users or tenants should be served with different priority..

## Real world examples

* [ Priority Queue Pattern](https://docs.microsoft.com/en-us/previous-versions/msp-n-p/dn589794(v=pandp.10))
Microsoft Azure does not provide a queuing mechanism that natively support automatic prioritization of messages through sorting. However, it does provide Azure Service Bus topics and subscriptions, which support a queuing mechanism that provides message filtering, together with a wide range of flexible capabilities that make it ideal for use in almost all priority queue implementations.
