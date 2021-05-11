---
layout: pattern
title: Leader/Followers
folder: leader-followers
permalink: /patterns/leader-followers/
categories: Concurrency
language: en
tags:
 - Performance
---

## Intent
The Leader/Followers pattern provides a concurrency model where multiple 
threads can efficiently de-multiplex events and dispatch event handlers 
that process I/O handles shared by the threads.

## Class diagram
![Leader/Followers class diagram](./etc/leader-followers.png)

## Applicability
Use Leader-Followers pattern when

* multiple threads take turns sharing a set of event sources in order to detect, de-multiplex, dispatch and process service requests that occur on the event sources.

## Real world examples

* [ACE Thread Pool Reactor framework](https://www.dre.vanderbilt.edu/~schmidt/PDF/HPL.pdf)
* [JAWS](http://www.dre.vanderbilt.edu/~schmidt/PDF/PDCP.pdf)
* [Real-time CORBA](http://www.dre.vanderbilt.edu/~schmidt/PDF/RTS.pdf)

## Credits

* [Douglas C. Schmidt and Carlos O’Ryan - Leader/Followers](http://www.kircher-schwanninger.de/michael/publications/lf.pdf)
