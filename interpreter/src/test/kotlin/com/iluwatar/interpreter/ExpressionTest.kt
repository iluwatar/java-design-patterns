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

// ABOUTME: Abstract base test class for testing Expression implementations with parameterized tests.
// ABOUTME: Provides common test infrastructure for verifying interpret() and toString() behavior.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Test Case for Expressions
 *
 * @param E Type of Expression
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ExpressionTest<E : Expression>(
    /** The expected [E.toString] response */
    private val expectedToString: String,
    /** Factory, used to create a new test object instance with the correct first and second parameter */
    private val factory: (NumberExpression, NumberExpression) -> E
) {

    companion object {
        /**
         * Generate inputs ranging from -10 to 10 for both input params and calculate the expected result
         *
         * @param resultCalc The function used to calculate the expected result
         * @return A stream with test entries
         */
        @JvmStatic
        fun prepareParameters(resultCalc: (Int, Int) -> Int): Stream<Arguments> {
            val testData = mutableListOf<Arguments>()
            for (i in -10 until 10) {
                for (j in -10 until 10) {
                    testData.add(
                        Arguments.of(
                            NumberExpression(i),
                            NumberExpression(j),
                            resultCalc(i, j)
                        )
                    )
                }
            }
            return testData.stream()
        }
    }

    /**
     * Create a new set of test entries with the expected result
     *
     * @return The list of parameters used during this test
     */
    abstract fun expressionProvider(): Stream<Arguments>

    /** Verify if the expression calculates the correct result when calling [E.interpret] */
    @ParameterizedTest
    @MethodSource("expressionProvider")
    fun testInterpret(first: NumberExpression, second: NumberExpression, result: Int) {
        val expression = factory(first, second)
        assertNotNull(expression)
        assertEquals(result, expression.interpret())
    }

    /** Verify if the expression has the expected [E.toString] value */
    @ParameterizedTest
    @MethodSource("expressionProvider")
    fun testToString(first: NumberExpression, second: NumberExpression) {
        val expression = factory(first, second)
        assertNotNull(expression)
        assertEquals(expectedToString, expression.toString())
    }
}
