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

// ABOUTME: Test class for NumberExpression that verifies numeric interpretation and string parsing.
// ABOUTME: Tests both Int and String constructor behavior for terminal numeric expressions.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * NumberExpressionTest
 */
class NumberExpressionTest : ExpressionTest<NumberExpression>(
    "number",
    { f, _ -> f }
) {
    /**
     * Create a new set of test entries with the expected result
     *
     * @return The list of parameters used during this test
     */
    override fun expressionProvider(): Stream<Arguments> = prepareParameters { f, _ -> f }

    /**
     * Verify if the [NumberExpression] constructor from String works as expected
     */
    @ParameterizedTest
    @MethodSource("expressionProvider")
    fun testFromString(first: NumberExpression) {
        val expectedValue = first.interpret()
        val testStringValue = expectedValue.toString()
        val numberExpression = NumberExpression(testStringValue)
        assertEquals(expectedValue, numberExpression.interpret())
    }
}
