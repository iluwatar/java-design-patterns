---
layout: pattern
title: Interpreter
folder: interpreter
permalink: /patterns/interpreter/
categories: Behavioral
tags:
 - Gang of Four
---

## Intent
Given a language, define a representation for its grammar along
with an interpreter that uses the representation to interpret sentences in the
language.

## Class diagram
![alt text](./etc/interpreter_1.png "Interpreter")

## Applicability
Use the Interpreter pattern when there is a language to
interpret, and you can represent statements in the language as abstract syntax
trees. The Interpreter pattern works best when

* the grammar is simple. For complex grammars, the class hierarchy for the grammar becomes large and unmanageable. Tools such as parser generators are a better alternative in such cases. They can interpret expressions without building abstract syntax trees, which can save space and possibly time
* efficiency is not a critical concern. The most efficient interpreters are usually not implemented by interpreting parse trees directly but by first translating them into another form. For example, regular expressions are often transformed into state machines. But even then, the translator can be implemented by the Interpreter pattern, so the pattern is still applicable

## Real world examples

* [java.util.Pattern](http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)
* [java.text.Normalizer](http://docs.oracle.com/javase/8/docs/api/java/text/Normalizer.html)
* All subclasses of [java.text.Format](http://docs.oracle.com/javase/8/docs/api/java/text/Format.html)
* [javax.el.ELResolver](http://docs.oracle.com/javaee/7/api/javax/el/ELResolver.html)


## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
