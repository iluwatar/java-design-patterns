---
title: Interpreter
category: Behavioral
language: en
tag:
 - Gang of Four
---

## Intent

Given a language, define a representation for its grammar along with an interpreter that uses the 
representation to interpret sentences in the language.

## Explanation

Real-world example

> The halfling kids are learning basic math at school. They start from the very basics "1 + 1",
> "4 - 2", "5 + 5", and so forth.

In plain words

> Interpreter pattern interprets sentences in the desired language.

Wikipedia says

> In computer programming, the interpreter pattern is a design pattern that specifies how to 
> evaluate sentences in a language. The basic idea is to have a class for each symbol (terminal or 
> nonterminal) in a specialized computer language. The syntax tree of a sentence in the language 
> is an instance of the composite pattern and is used to evaluate (interpret) the sentence for 
> a client.

**Programmatic example**

To be able to interpret basic math, we need a hierarchy of expressions. The basic abstraction for
it is the `Expression` class.

```java
public abstract class Expression {

  public abstract int interpret();

  @Override
  public abstract String toString();
}
```

The simplest of the expressions is the `NumberExpression` that contains only a single integer
number.

```java
public class NumberExpression extends Expression {

  private final int number;

  public NumberExpression(int number) {
    this.number = number;
  }

  public NumberExpression(String s) {
    this.number = Integer.parseInt(s);
  }

  @Override
  public int interpret() {
    return number;
  }

  @Override
  public String toString() {
    return "number";
  }
}
```

The more complex expressions are operations such as `PlusExpression`, `MinusExpression`, and
`MultiplyExpression`. Here's the first of them, the others are similar.

```java
public class PlusExpression extends Expression {

  private final Expression leftExpression;
  private final Expression rightExpression;

  public PlusExpression(Expression leftExpression, Expression rightExpression) {
    this.leftExpression = leftExpression;
    this.rightExpression = rightExpression;
  }

  @Override
  public int interpret() {
    return leftExpression.interpret() + rightExpression.interpret();
  }

  @Override
  public String toString() {
    return "+";
  }
}
```

Now we are able to show the interpreter pattern in action parsing some simple math.

```java
    // the halfling kids are learning some basic math at school
    // define the math string we want to parse
    final var tokenString = "4 3 2 - 1 + *";

    // the stack holds the parsed expressions
    var stack = new Stack<Expression>();

    // tokenize the string and go through them one by one
    var tokenList = tokenString.split(" ");
    for (var s : tokenList) {
        if (isOperator(s)) {
            // when an operator is encountered we expect that the numbers can be popped from the top of
            // the stack
            var rightExpression = stack.pop();
            var leftExpression = stack.pop();
            LOGGER.info("popped from stack left: {} right: {}",
            leftExpression.interpret(), rightExpression.interpret());
            var operator = getOperatorInstance(s, leftExpression, rightExpression);
            LOGGER.info("operator: {}", operator);
            var result = operator.interpret();
            // the operation result is pushed on top of the stack
            var resultExpression = new NumberExpression(result);
            stack.push(resultExpression);
            LOGGER.info("push result to stack: {}", resultExpression.interpret());
        } else {
            // numbers are pushed on top of the stack
            var i = new NumberExpression(s);
            stack.push(i);
            LOGGER.info("push to stack: {}", i.interpret());
        }
    }
    // in the end, the final result lies on top of the stack
    LOGGER.info("result: {}", stack.pop().interpret());
```

Executing the program produces the following console output.

```
popped from stack left: 1 right: 1
operator: +
push result to stack: 2
popped from stack left: 4 right: 2
operator: *
push result to stack: 8
result: 8
```

## Class diagram

![alt text](./etc/interpreter_1.png "Interpreter")

## Applicability

Use the Interpreter pattern when there is a language to interpret, and you can represent statements 
in the language as abstract syntax trees. The Interpreter pattern works best when

* The grammar is simple. For complex grammars, the class hierarchy for the grammar becomes large and unmanageable. Tools such as parser generators are a better alternative in such cases. They can interpret expressions without building abstract syntax trees, which can save space and possibly time
* Efficiency is not a critical concern. The most efficient interpreters are usually not implemented by interpreting parse trees directly but by first translating them into another form. For example, regular expressions are often transformed into state machines. But even then, the translator can be implemented by the Interpreter pattern, so the pattern is still applicable

## Known uses

* [java.util.Pattern](http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)
* [java.text.Normalizer](http://docs.oracle.com/javase/8/docs/api/java/text/Normalizer.html)
* All subclasses of [java.text.Format](http://docs.oracle.com/javase/8/docs/api/java/text/Format.html)
* [javax.el.ELResolver](http://docs.oracle.com/javaee/7/api/javax/el/ELResolver.html)


## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
