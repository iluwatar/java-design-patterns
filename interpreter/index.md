---
layout: pattern
title: Interpreter
folder: interpreter
permalink: /patterns/interpreter/
categories: Behavioral
tags: Java
---

**Intent:** Given a language, define a representation for its grammar along
with an interpreter that uses the representation to interpret sentences in the
language.

![alt text](./etc/interpreter_1.png "Interpreter")

**Applicability:** Use the Interpreter pattern when there is a language to
interpret, and you can represent statements in the language as abstract syntax
trees. The Interpreter pattern works best when
* the grammar is simple. For complex grammars, the class hierarchy for the grammar becomes large and unmanageable. Tools such as parser generators are a better alternative in such cases. They can interpret expressions without building abstract syntax trees, which can save space and possibly time
* efficiency is not a critical concern. The most efficient interpreters are usually not implemented by interpreting parse trees directly but by first translating them into another form. For example, regular expressions are often transformed into state machines. But even then, the translator can be implemented by the Interpreter pattern, so the pattern is still applicable
