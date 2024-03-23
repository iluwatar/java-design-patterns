---
title: Partial Response
category: Behavioral
language: en
tag:
 - Decoupling
---

## Intent
Send partial response from server to client on need basis. Client will specify the fields
that it needs to server, instead of serving all details for resource. 

## Class diagram
![alt text](./etc/partial-response.urm.png "partial-response")

## Applicability
Use the Partial Response pattern when

* Client needs only subset of data from resource.
* To avoid too much data transfer over wire

## Credits

* [Common Design Patterns](https://cloud.google.com/apis/design/design_patterns)
