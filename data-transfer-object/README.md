---
layout: pattern
title: Data Transfer Object
folder: data-transfer-object
permalink: /patterns/data-transfer-object/
categories: Architectural
tags:
 - Performance
---

## Intent
Pass data with multiple attributes in one shot from client to server,
to avoid multiple calls to remote server. 

## Class diagram
![alt text](./etc/data-transfer-object.urm.png "data-transfer-object")

## Applicability
Use the Data Transfer Object pattern when

* The client is asking for multiple information. And the information is related.
* When you want to boost the performance to get resources.
* You want reduced number of remote calls.

## Credits

* [Design Pattern - Transfer Object Pattern](https://www.tutorialspoint.com/design_pattern/transfer_object_pattern.htm)
* [Data Transfer Object](https://msdn.microsoft.com/en-us/library/ff649585.aspx)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
