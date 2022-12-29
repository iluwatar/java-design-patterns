---
title: Interpreter
category: Behavioral
language: zh
tag:
 - Gang of Four
---

## 目的
给定一种语言，请定义其语法的表示形式，以及使用该表示形式来解释该语言中的句子的解释器。

## 类图
![alt text](./etc/interpreter_1.png "Interpreter")

## 适用性
有一种要解释的语言时，请使用解释器模式，并且可以将语言中的语句表示为抽象语法树。解释器模式在以下情况下效果最佳

* 语法很简单。 对于复杂的语法，语法的类层次结构变得庞大且难以管理。 在这种情况下，解析器生成器之类的工具是更好的选择。 他们可以在不构建抽象语法树的情况下解释表达式，这可以节省空间并可能节省时间
* 效率不是关键问题。 通常，最有效的解释器不是通过直接解释解析树来实现的，而是先将其转换为另一种形式。 例如，正则表达式通常会转换为状态机。 但是即使这样，翻译器也可以通过解释器模式实现，因此该模式仍然适用。

## 真实世界例子

* [java.util.Pattern](http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)
* [java.text.Normalizer](http://docs.oracle.com/javase/8/docs/api/java/text/Normalizer.html)
* All subclasses of [java.text.Format](http://docs.oracle.com/javase/8/docs/api/java/text/Format.html)
* [javax.el.ELResolver](http://docs.oracle.com/javaee/7/api/javax/el/ELResolver.html)


## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
