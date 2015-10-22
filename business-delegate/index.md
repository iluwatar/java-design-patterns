---
layout: pattern
title: Business Delegate
folder: business-delegate
permalink: /patterns/business-delegate/
categories: Business Tier
tags: Java
---

**Intent:** The Business Delegate pattern adds an abstraction layer between
presentation and business tiers. By using the pattern we gain loose coupling
between the tiers and encapsulate knowledge about how to locate, connect to,
and interact with the business objects that make up the application.

![alt text](./etc/business-delegate.png "Business Delegate")

**Applicability:** Use the Business Delegate pattern when

* you want loose coupling between presentation and business tiers
* you want to orchestrate calls to multiple business services
* you want to encapsulate service lookups and service calls
