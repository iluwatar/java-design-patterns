---
layout: pattern
title: Partial Response
folder: partial-response
permalink: /patterns/partial-response/
categories: Architectural
tags:
 - Java
 - KISS
 - YAGNI
 - Difficulty-Beginner
---

## Intent
Send partial response from server to client on need basis. Client will specify the the fields
that it need to server, instead of serving all details for resource. 

![alt text](./etc/partial-response.urm.png "partial-response")

## Applicability
Use the Partial Response pattern when

* Client need only subset of data from resource.
* To avoid too much data transfer over wire

## Credits

* [Common Design Patterns](https://cloud.google.com/apis/design/design_patterns)
* [Partial Response in RESTful API Design](http://yaoganglian.com/2013/07/01/partial-response/)
