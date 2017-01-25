---
layout: pattern 
title: Leader/Followers
folder: leader-followers
permalink: /patterns/leader-followers/ 
tags:
 - Java
 - Difficulty-Intermediate
 - Performance
---

## Intent
The Leader/Follower pattern simplifies the programming
of concurrency models where multiple threads can receive requests, process
responses, and demultiplex connections using a shared handle set.

![alt text](./etc/leader-follower.png "Leader/Followers")

## Applicability
Use the Leader/Followers pattern when:

* you have a large number of short-lived tasks s that arrive simultaneously.

