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

import java.util.Arrays;
import java.util.Stack;
import lombok.extern.slf4j.Slf4j;

/**
 * The Interpreter pattern is a design pattern that specifies how to evaluate sentences in a
 * language. The basic idea is to have a class for each symbol (terminal or nonterminal) in a
 * specialized computer language. The syntax tree of a sentence in the language is an instance of
 * the composite pattern and is used to evaluate (interpret) the sentence for a client.
 * 解释器模式是一种设计模式，它指定如何计算语言中的句子。其基本思想是用专门的计算机语言为每个符号(终端或非终端)建立一个类。
 * 该语言中句子的语法树是复合模式的一个实例，用于为客户评估(解释)该句子。
 *
 * <p>In this example we use the Interpreter pattern to break sentences into expressions ({@link
 * Expression}) that can be evaluated and as a whole form the result.
 * 在这个例子中，我们使用解释器模式将句子分解为表达式({@link Expression})，这些表达式可以被计算并作为一个整体形成结果。
 *
 * <p>Expressions can be evaluated using prefix, infix or postfix notations This sample uses
 * postfix, where operator comes after the operands.
 * 表达式可以使用前缀、中缀或后缀符号求值。此示例使用后缀，其中运算符位于操作数之后。
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
    // 半身人的孩子们在学校里学习一些基本的数学
    // 定义要解析的数学字符串
    final var tokenString = "4 3 2 - 1 + *";

    // the stack holds the parsed expressions
    // 栈保存解析后的表达式
    var stack = new Stack<Expression>();

    // tokenize the string and go through them one by one
    // 对字符串进行标记，并逐个遍历它们
    var tokenList = tokenString.split(" ");
    for (var s : tokenList) {
      if (isOperator(s)) {
        // when an operator is encountered we expect that the numbers can be popped from the top of
        // the stack
        // 当遇到运算符时，我们期望数字可以从堆栈顶部弹出
        var rightExpression = stack.pop();
        var leftExpression = stack.pop();
        LOGGER.info("popped from stack left: {} right: {}",
            leftExpression.interpret(), rightExpression.interpret());
        var operator = getOperatorInstance(s, leftExpression, rightExpression);
        LOGGER.info("operator: {}", operator);
        var result = operator.interpret();
        // the operation result is pushed on top of the stack
        // 操作结果被压入栈顶
        var resultExpression = new NumberExpression(result);
        stack.push(resultExpression);
        LOGGER.info("push result to stack: {}", resultExpression.interpret());
      } else {
        // numbers are pushed on top of the stack
        // 数字被推到堆栈的顶部
        var i = new NumberExpression(s);
        stack.push(i);
        LOGGER.info("push to stack: {}", i.interpret());
      }
    }
    // in the end, the final result lies on top of the stack
    // 最后，最终结果位于堆栈的顶部
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
