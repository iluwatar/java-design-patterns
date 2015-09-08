---
layout: pattern
title: Service Locator
folder: service-locator
permalink: /patterns/service-locator/
categories: Structural
tags: Java
---

**Intent:** Encapsulate the processes involved in obtaining a service with a
strong abstraction layer.

![alt text](./etc/service-locator.png "Proxy")

**Applicability:** The service locator pattern is applicable whenever we want
to locate/fetch various services using JNDI which, typically, is a redundant
and expensive lookup. The service Locator pattern addresses this expensive
lookup by making use of caching techniques ie. for the very first time a
particular service is requested, the service Locator looks up in JNDI, fetched
the relevant service and then finally caches this service object. Now, further
lookups of the same service via Service Locator is done in its cache which
improves the performance of application to great extent.

**Typical Use Case:**

* when network hits are expensive and time consuming
* lookups of services are done quite frequently
* large number of services are being used
