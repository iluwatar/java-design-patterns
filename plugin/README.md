---
layout: pattern
title: Plugin Pattern
folder: plugin-pattern
permalink: /patterns/plugin-pattern/
categories: Separation
tags: 
 - Extensibility
---

## Also known as
plug-in pattern

## Intent
Use Plugin whenever you have a good reason to reverse a package, or layer, dependency rule.

## Explanation
Plugin pattern provides centralized, runtime configuration.

## Class diagram
[]()

## Applicability
Using when:

+ Establishing a new deployment configuration - say "execute unit tests against in-memory database without transaction control" or "execute in production mode against DB2 database with full transaction control" - requires editing conditional statements in a number of factories, rebuilding, and redeploying. 

+ Configuration shouldn't be scattered throughout your application, nor should it require a rebuild or redeployment. 


