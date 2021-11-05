---
layout: pattern
title: Feature Toggle
folder: feature-toggle
permalink: /patterns/feature-toggle/
categories: Behavioral
language: en
tags:
 - Extensibility
---

## Also known as
Feature Flag

## Intent
Used to switch code execution paths based on properties or groupings. Allowing new features to be released, tested
and rolled out. Allowing switching back to the older feature quickly if needed. It should be noted that this pattern,
can easily introduce code complexity. There is also cause for concern that the old feature that the toggle is eventually
going to phase out is never removed, causing redundant code smells and increased maintainability.

## Class diagram
![alt text](./etc/feature-toggle.png "Feature Toggle")

## Applicability
Use the Feature Toggle pattern when

* Giving different features to different users.
* Rolling out a new feature incrementally.
* Switching between development and production environments.

## Credits

* [Martin Fowler 29 October 2010 (2010-10-29).](http://martinfowler.com/bliki/FeatureToggle.html)
