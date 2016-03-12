---
layout: pattern
title: Plugin
folder: plugin
permalink: /patterns/plugin/
categories: Enterprise
tags:
 - Java
 - Difficulty-Intermediate
 - EIP
---

## Intent
Ensure the use of correct implementation of a particular behavior depending on different configurations for different environments

![alt text](./etc/plugin.png "Plugin Pattern")

## Applicability

* Configuration shouldn't be scattered throughout your application, nor should it require a rebuild or redeployment. Plugin solves both problems by providing centralized, runtime configuration.
