---
layout: pattern
title: Data transfer object
folder: data-transfer-object
permalink: /patterns/data-transfer-object/
categories: Creational
tags: 
 - Java
 - Difficulty-Beginner
 - Gang Of Four
---

## Also known as
Virtual Constructor

## Intent
Remote calls are expensive. Hence instead of giving multiple calls,
you can get all the information you want as a data transfer object.

![alt text](./etc/dto.png "data transfer object")

## Applicability
Use this pattern when

* you need to make multiple calls to remote.


## Credits

* https://msdn.microsoft.com/en-us/library/ff649585.aspx
* http://www.tutorialspoint.com/design_pattern/transfer_object_pattern.htm
