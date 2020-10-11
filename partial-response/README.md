---
layout: pattern
title: Partial Response
folder: partial-response
permalink: /patterns/partial-response/
categories: Behavioral
tags:
 - Decoupling
---

## Intent
Send partial response from the server to the client on a need basis. The client will specify the fields
that it needs to the server, instead of serving all details for the resource. 

## Class diagram
![alt text](./etc/partial-response.urm.png "partial-response")

## Applicability
Use the Partial Response pattern when

* Client needs the only subset of data from a resource.
* To avoid too much data transfer over the wire

## Credits

* [Common Design Patterns](https://cloud.google.com/apis/design/design_patterns)
