/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.interpreter;

import java.util.Stack;
import lombok.extern.slf4j.Slf4j;

/**
 * The Interpreter pattern is a design pattern that specifies how to evaluate sentences in a
 * language. The basic idea is to have a class for each symbol (terminal or nonterminal) in a
 * specialized computer language. The syntax tree of a sentence in the language is an instance of
 * the composite pattern and is used to evaluate (interpret) the sentence for a client.
 *
 * <p>In this example we use the Interpreter pattern to break sentences into expressions ({@link
 * Expression}) that can be evaluated and as a whole form the result.
 *
 * <p>Expressions can be evaluated using prefix, infix or postfix notations This sample uses
 * postfix, where operator comes after the operands.
 *
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   * @param args program arguments
   */
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

  /**
   * Checks whether the input parameter is an operator.
   * @param s input string
   * @return true if the input parameter is an operator
   */
  public static boolean isOperator(String s) {
    return s.equals("+") || s.equals("-") || s.equals("*");
  }

  /**
   * Returns correct expression based on the parameters.
   * @param s input string
   * @param left expression
   * @param right expression
   * @return expression
   */
  public static Expression getOperatorInstance(String s, Expression left, Expression right) {
    switch (s) {
      case "+":
        return new PlusExpression(left, right);
      case "-":
        return new MinusExpression(left, right);
      default:
        return new MultiplyExpression(left, right);
    }
  }
}
