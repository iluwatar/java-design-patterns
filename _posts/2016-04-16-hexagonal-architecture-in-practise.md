---
layout: post
title: Hexagonal Architecture in Practise
author: ilu
---

![Nut]({{ site.url }}{{ site.baseurl }}/assets/nut-small.jpg)

layered architecture
- 1-dimensional picture
- separation of concerns
- maintainability
- changes are not propagated throughout the application

hexagonal architecture
- 2-dimensional picture
- domain in the middle, see domain driven design
- primary ports drive the application
- secondary ports are used by the domain
- adapters are implementations of the ports
- clean architecture, onion architecture
- fully testable systems that can be driven by users, programs, batch scripts equally in isolation of database
- naked objects

lottery system implementation
- description what the system does
- drawing with domain, ports and adapters
- implement each part
