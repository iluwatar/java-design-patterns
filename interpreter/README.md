---
title: "Interpreter Pattern in Java: Building Custom Parsers for Java Applications"
shortTitle: Interpreter
description: "Explore the Interpreter Design Pattern in Java with real-world examples, class diagrams, and step-by-step implementation. Learn when and how to use this powerful behavioral pattern."
category: Behavioral
language: en
tag:
  - Abstraction
  - Data transformation
  - Decoupling
  - Domain
  - Gang of Four
  - Polymorphism
  - Runtime
---

## Intent of Interpreter Design Pattern

The Interpreter design pattern is used to define a grammatical representation for a language and provides an interpreter to handle this grammar. This pattern is useful in scenarios where a specific set of rules or grammar needs to be interpreted and acted upon, such as in arithmetic expressions or scripting languages.

## Detailed Explanation of Interpreter Pattern with Real-World Examples

Real-world example

> Consider a calculator application designed to interpret and calculate expressions entered by users. The application uses the Interpreter pattern in Java to parse and evaluate arithmetic expressions such as "5 + 3 * 2". Here, the Interpreter translates each part of the expression into objects that represent numbers and operations. These objects follow a defined grammar that allows the application to understand and compute the result correctly based on the rules of arithmetic. Each element of the expression corresponds to a class in the program's structure, simplifying the parsing and evaluation process for any inputted arithmetic formula.

In plain words

> The Interpreter design pattern defines a representation for a language's grammar along with an interpreter that uses the representation to interpret sentences in the language.

Wikipedia says

> In computer programming, the interpreter pattern is a design pattern that specifies how to evaluate sentences in a language. The basic idea is to have a class for each symbol (terminal or nonterminal) in a specialized computer language. The syntax tree of a sentence in the language is an instance of the composite pattern and is used to evaluate (interpret) the sentence for a client.

## Programmatic Example of Interpreter Pattern in Java

To be able to interpret basic math in Java, we need a hierarchy of expressions. The `Expression` class is the base, and concrete implementations like `NumberExpression` handle specific parts of the grammar. The Interpreter pattern in Java simplifies parsing and evaluating arithmetic expressions by translating them into a structured format that the application can process.

```java
public abstract class Expression {

    public abstract int interpret();

    @Override
    public abstract String toString();
}
```

The simplest of the expressions is the `NumberExpression` that contains only a single integer number.

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

The more complex expressions are operations such as `PlusExpression`, `MinusExpression`, and `MultiplyExpression`. Here's the first of them, the others are similar.

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

Now, we are able to show the interpreter pattern in action parsing some simple math.

```java
@Slf4j
public class App {

    public static void main(String[] args) {

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
    }

    public static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*");
    }

    public static Expression getOperatorInstance(String s, Expression left, Expression right) {
        return switch (s) {
            case "+" -> new PlusExpression(left, right);
            case "-" -> new MinusExpression(left, right);
            default -> new MultiplyExpression(left, right);
        };
    }
}
 ```

Executing the program produces the following console output.

```
13:33:15.437 [main] INFO com.iluwatar.interpreter.App -- push to stack: 4
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- push to stack: 3
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- push to stack: 2
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- popped from stack left: 3 right: 2
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- operator: -
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- push result to stack: 1
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- push to stack: 1
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- popped from stack left: 1 right: 1
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- operator: +
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- push result to stack: 2
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- popped from stack left: 4 right: 2
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- operator: *
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- push result to stack: 8
13:33:15.440 [main] INFO com.iluwatar.interpreter.App -- result: 8
```

## Detailed Explanation of Interpreter Pattern with Real-World Examples

![Interpreter](./etc/interpreter_1.png "Interpreter")

## When to Use the Interpreter Pattern in Java

Use the Interpreter design pattern when there is a language to interpret, and you can represent statements in the language as abstract syntax trees. The Interpreter pattern works best when

* The grammar is simple. For complex grammars, the class hierarchy for the grammar becomes large and unmanageable. Tools such as parser generators are a better alternative in such cases. They can interpret expressions without building abstract syntax trees, which can save space and possibly time.
* Efficiency is not a critical concern. The most efficient interpreters are usually not implemented by interpreting parse trees directly but by first translating them into another form. For example, regular expressions are often transformed into state machines. But even then, the translator can be implemented by the Interpreter pattern, so the pattern is still applicable.

## Real-World Applications of Interpreter Pattern in Java

* [java.util.Pattern](http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)
* [java.text.Normalizer](http://docs.oracle.com/javase/8/docs/api/java/text/Normalizer.html)
* All subclasses of [java.text.Format](http://docs.oracle.com/javase/8/docs/api/java/text/Format.html)
* [javax.el.ELResolver](http://docs.oracle.com/javaee/7/api/javax/el/ELResolver.html)
* SQL parsers in various database management systems.

## Benefits and Trade-offs of Interpreter Pattern

Benefits:

* Adds new operations to interpret expressions easily without changing the grammar or classes of data.
* Implements grammar directly in the language, making it easy to modify or extend.

Trade-offs:

* Can become complex and inefficient for large grammars.
* Each rule in the grammar results in a class, leading to a large number of classes for complex grammars.

## Related Java Design Patterns

* [Composite](https://java-design-patterns.com/patterns/composite/): Often used together, where the Interpreter pattern leverages the Composite pattern to represent the grammar as a tree structure.
* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): Useful for sharing state to reduce memory usage in the Interpreter pattern, particularly for interpreters that deal with repetitive elements in a language.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
