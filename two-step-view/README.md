---
layout: pattern
title: Two-step view 
folder: two-step-view
permalink: /patterns/two-step-view/
categories: Structural
language: en
tags:
- Creational
---

## Intent
Two-step view pattern is a design pattern that enable to turn the domain data into HTML in two steps:
1) Produces logical representation of the page which acts as a key map to value of the different elements in the page
2) The logical representation is rendered to compose the final HTML 

## Explanation

Real-world example
> I need to add new elements to the websites which requires me to make global changes to the appearance of website. However, 
> I need to modify the duplicates across multiple which make this difficult. Two-step view pattern resolves this issue as 
> it would make the global change by simply altering the second stage. Therefore, I do not have to spend more time to modify
> more and more pages.

In plain words
> It turns domain data into HTML which build an intermediate representation first, then run it through a formatting step.

Wikipedia says
>Turn domain data into HTML in two steps: first by forming some kind of logical page, then rendering the logical page into HTML. 
> This makes it possible to make a global change to all output by altering the second stage, or to support multiple
> looks-and-feels by providing multiple second stages.


## Class diagram

## Applicability
Use the two-step view when

* When you want a consistent look and organization to the site for web application that maintains many pages
* When you want to make global of changes to the appearance of the websites 


## Credits
* [Patterns of Enterprise Application Architecture: Pattern Enterprise Application Architecture] (https://books.google.fi/books?id=vqTfNFDzzdIC&pg=PA365#v=onepage&q&f=false)
* [P of EAA:Two step view] (https://www.martinfowler.com/eaaCatalog/twoStepView.html)
