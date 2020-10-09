---
layout: pattern
title: Page Factory
folder: page-factory
permalink: /patterns/page-factory/
categories: Structural
tags:
- Decoupling
---

## About Page Factory Design Pattern

Page-Factory allows you to write autotests in a human-readable language, thereby lowering the entry threshold for test developers and increasing their readability by untrained users. Page-factory uses the Cucumber-JVM framework, but unlike pure use, in which a fairly large part of the architecture is occupied by steps (stepdefs) , here the emphasis is on getting rid of the need to write them yourself, or reducing the number of self-written steps (stepdefs) focusing on describing the code of pages using the PageObject pattern .
Page-Factory has already implemented many standard steps , which are enough to start developing automated tests.
Page-Factory is a cross-platform framework that allows you to run tests on all popular browsers, because Selenium WebDriver is used to run them. Page-Factory can also work with an Android application using Appium.

## Requirements

To work Page-Factory you need:
1. Java 8 or higher


## Applicability

Use the Page Factory pattern when

* You are writing automated tests for your web application and you want to separate the UI manipulation required for the tests from the actual test logic.
* Make your tests less brittle, and more readable and robust

## Credits

* [Martin Fowler - PageObject](http://martinfowler.com/bliki/PageObject.html)
* [Selenium - Page Objects](https://github.com/SeleniumHQ/selenium/wiki/PageObjects)
