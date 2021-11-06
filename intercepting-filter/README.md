---
layout: pattern
title: Intercepting Filter
folder: intercepting-filter
permalink: /patterns/intercepting-filter/
categories: Behavioral
language: en
tags:
 - Decoupling
---

## Intent
Provide pluggable filters to conduct necessary pre-processing and
post-processing to requests from a client to a target

## Class diagram 
![alt text](./etc/intercepting-filter.png "Intercepting Filter")

## Applicability
Use the Intercepting Filter pattern when

* a system uses pre-processing or post-processing requests
* a system should do the authentication/ authorization/ logging or tracking of request and then pass the requests to corresponding handlers 
* you want a modular approach to configuring pre-processing and post-processing schemes

## Tutorials

* [Introduction to Intercepting Filter Pattern in Java](https://www.baeldung.com/intercepting-filter-pattern-in-java)

## Real world examples

* [javax.servlet.FilterChain](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/FilterChain.html) and [javax.servlet.Filter](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/Filter.html)
* [Struts 2 - Interceptors](https://struts.apache.org/core-developers/interceptors.html)

## Credits

* [TutorialsPoint - Intercepting Filter](http://www.tutorialspoint.com/design_pattern/intercepting_filter_pattern.htm)
