---
layout: pattern
title: Plugin
folder: plugin
permalink: /patterns/plugin/
categories: Creational
tags:
 - Java
 - Difficulty-Beginner
 - Extension Points
 - Extensibility
---

## Intent
Ensure configuration update in a dynamic way and provide different configurations for different environments

![alt text](./etc/plugin.png "Plugin Pattern")

## Applicability

* Configuration shouldn't be scattered throughout your application, nor should it require a rebuild or redeployment. Plugin solves both problems by providing centralized, runtime configuration.
