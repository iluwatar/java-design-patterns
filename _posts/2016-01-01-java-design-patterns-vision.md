---
layout: post
title: Java Design Patterns Vision
author: ilu
---

![Mosque]({{ site.baseurl }}/assets/mosque-small.jpg)

The software industry is still relatively young. However most of the design problems occurring today have already been solved hundreds of times before in various software systems. Some of the solutions may have been great and worked well in production while some of them may have been substandard and produced various kinds of bugs pestering the end users.

What if we could take the best solution out of the several hundred and reuse it everywhere? This standard solution would be of proven quality in several areas: correctness, efficiency, reliability, security, maintainability, testability, flexibility etc. Without doubt the overall benefits would be enormous. The wheel would no longer be reinvented in every code shop which would save time and resources throughout the lifecycle of the produced software.

These industry's best solutions already exist and they have a common name: design patterns. The importance of patterns in crafting complex systems has been long recognized in other disciplines. In particular, [Christopher Alexander](https://en.wikipedia.org/wiki/Christopher_Alexander) and his colleagues were perhaps the first to propose the idea of using a pattern language to architect buildings and cities. In short, the concept of the design pattern in software provides a key to helping developers leverage the expertise of other skilled architects.

The legendary Gang of Four started the software design pattern catalog twenty years ago with their ground breaking book [Design Patterns: Elements of Reusable Object-Oriented Software](https://en.wikipedia.org/wiki/Design_Patterns). Over the years, many more books have been published on the same topic. Some of the most influencial are Martin Fowler's [Patterns of Enterprise Application Architecture](http://martinfowler.com/books/eaa.html) and [Pattern-Oriented Software Architecture](http://eu.wiley.com/WileyCDA/WileyTitle/productCd-0471958697.html) series by Buschmann et al. Some of the patterns have never made it to the pages of a book but live only in the Internet as a white paper. The book and white paper publishing formats are not ideal for the practising coder. The design patterns are first and foremost code and code lives in repositories where it can be checked out, reviewed, executed, enhanced and tested using the industry standard tools.

With the advent of Java 8 functional programming most of the classic design patterns as written by the original authors have become obsolete. With modern programming style we can mostly avoid deep class hierarchies and abstract base classes. The style has changed from passing values to passing behavior in shape of functions. The verbosity of the classic Java vanishes as anonymous classes turn into nimble one-liner lambdas.

Enter the Java Design Patterns open source community project. We strive to offer the most comprehensive catalog of top quality design patterns on Internet. All the patterns have been documented, categorized and tagged by their properties so they are easy to find and study. Our focus is to provide sample code of highest standard. Every commit runs automatic build tests, code style tests and static analysis. Unlike the patterns published in books and white papers our code is not static but lives and evolves with every new Java version.

In addition to high quality sample code we strive to provide you a pleasant open source experience. Should you want to contribute, we will answer your questions and be nice to you. No matter if you are just starting your career as a programmer or already are a seasoned developer we have suitable tasks for your skill level. Java Design Patterns is one of the best projects in Github to learn modern Java, software design and architecture.
