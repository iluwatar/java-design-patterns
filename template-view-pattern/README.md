--- 
layout: pattern 
title: Template View Pattern 
folder: template-view-pattern
permalink: /java-design-patterns/template-view-pattern/ 
categories: 
 - Architectural 
language: en 
tags:
 - Creational
 - Concurrency
---

## Template View Pattern

## Intent
The intent of the template view pattern is to create an intermediary for handling the processing of logic, specifically to render information into an HTML page. 

## Explanation

Template view acts as the view in the model view controller design pattern.

![Model View Controller](https://github.com/alainalawson02/java-design-patterns/blob/master/template-view-pattern/etc/model-view-controller-diagram.png)

The intent of the template view pattern is to create an intermediary for handling the processing of logic, specifically to render information into an HTML page. 

The intent is achieved by embedding static markers in an HTML page, which at run time, gets replaced based on the value received by the java intermediary program. The idea behind this is to create an Infrastructure as Code (IaC) like implementation when working with HTML web pages. page information can be modified in a database, on a parameter file or equivalent and automatically populate on the page.

## Class diagram

## Applicability

Commonly, these parameters are written directly into the HTML, therefore separating these responsibilities creates 3 opportunities:
Code / Parameter organisation 
Code reusability 
Easy parameter modification - particularly easy for non-programmers to contribute to this.

## Tutorials

## Known uses

## Consequences

## Related patterns

[model view controller](https://github.com/iluwatar/java-design-patterns/tree/master/model-view-controller)

## Credits
