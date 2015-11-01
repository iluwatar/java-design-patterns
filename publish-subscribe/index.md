---
layout: pattern
title: Publish Subscribe
folder: publish-subscribe
permalink: /patterns/publish-subscribe/
categories: Integration
tags: Java
---

**Intent:** When applications communicate using a messaging system they do it by using logical addresses
of the system, so called Publish Subscribe Channel. The publisher broadcasts a message to all registered Subscriber.

![alt text](./etc/publish-subscribe.png "Publish Subscribe Channel")

**Applicability:** Use the Publish Subscribe Channel pattern when

* two or more applications need to communicate using a messaging system for broadcasts.
