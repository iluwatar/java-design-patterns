---
layout: pattern
title: Execute Around
folder: execute-around
permalink: /patterns/execute-around/
categories: Other
tags: Java
---

**Intent:** Execute Around idiom frees the user from certain actions that
should always be executed before and after the business method. A good example
of this is resource allocation and deallocation leaving the user to specify
only what to do with the resource.

![alt text](./etc/execute-around.png "Execute Around")

**Applicability:** Use the Execute Around idiom when

* you use an API that requires methods to be called in pairs such as open/close or allocate/deallocate.
