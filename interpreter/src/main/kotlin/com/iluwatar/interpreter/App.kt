/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.interpreter

// ABOUTME: Demonstrates the Interpreter pattern by evaluating postfix mathematical expressions.
// ABOUTME: Parses tokens into Expression objects and uses a stack to evaluate the result.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.Stack

private val logger = KotlinLogging.logger {}

/**
 * The Interpreter pattern is a design pattern that specifies how to evaluate sentences in a
 * language. The basic idea is to have a class for each symbol (terminal or nonterminal) in a
 * specialized computer language. The syntax tree of a sentence in the language is an instance of
 * the composite pattern and is used to evaluate (interpret) the sentence for a client.
 *
 * In this example we use the Interpreter pattern to break sentences into expressions ([Expression])
 * that can be evaluated and as a whole form the result.
 *
 * Expressions can be evaluated using prefix, infix or postfix notations. This sample uses
 * postfix, where operator comes after the operands.
 */

/**
 * Program entry point.
 *
 * @param args program arguments
 */
fun main(args: Array<String>) {
    // the halfling kids are learning some basic math at school
    // define the math string we want to parse
    val tokenString = "4 3 2 - 1 + *"

    // the stack holds the parsed expressions
    val stack = Stack<Expression>()

    // tokenize the string and go through them one by one
    val tokenList = tokenString.split(" ")
    for (s in tokenList) {
        if (isOperator(s)) {
            // when an operator is encountered we expect that the numbers can be popped from the top of
            // the stack
            val rightExpression = stack.pop()
            val leftExpression = stack.pop()
            logger.info {
                "popped from stack left: ${leftExpression.interpret()} right: ${rightExpression.interpret()}"
            }
            val operator = getOperatorInstance(s, leftExpression, rightExpression)
            logger.info { "operator: $operator" }
            val result = operator.interpret()
            // the operation result is pushed on top of the stack
            val resultExpression = NumberExpression(result)
            stack.push(resultExpression)
            logger.info { "push result to stack: ${resultExpression.interpret()}" }
        } else {
            // numbers are pushed on top of the stack
            val i = NumberExpression(s)
            stack.push(i)
            logger.info { "push to stack: ${i.interpret()}" }
        }
    }
    // in the end, the final result lies on top of the stack
    logger.info { "result: ${stack.pop().interpret()}" }
}

/**
 * Checks whether the input parameter is an operator.
 *
 * @param s input string
 * @return true if the input parameter is an operator
 */
fun isOperator(s: String): Boolean = s == "+" || s == "-" || s == "*"

/**
 * Returns correct expression based on the parameters.
 *
 * @param s input string
 * @param left expression
 * @param right expression
 * @return expression
 */
fun getOperatorInstance(s: String, left: Expression, right: Expression): Expression = when (s) {
    "+" -> PlusExpression(left, right)
    "-" -> MinusExpression(left, right)
    else -> MultiplyExpression(left, right)
}
