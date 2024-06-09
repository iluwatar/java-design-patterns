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
package com.iluwatar.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test Case for Expressions
 *
 * @param <E> Type of Expression
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ExpressionTest<E extends Expression> {

  /**
   * Generate inputs ranging from -10 to 10 for both input params and calculate the expected result
   *
   * @param resultCalc The function used to calculate the expected result
   * @return A stream with test entries
   */
  static Stream<Arguments> prepareParameters(final IntBinaryOperator resultCalc) {
    final var testData = new ArrayList<Arguments>();
    for (var i = -10; i < 10; i++) {
      for (var j = -10; j < 10; j++) {
        testData.add(Arguments.of(
            new NumberExpression(i),
            new NumberExpression(j),
            resultCalc.applyAsInt(i, j)
        ));
      }
    }
    return testData.stream();
  }

  /**
   * The expected {@link E#toString()} response
   */
  private final String expectedToString;

  /**
   * Factory, used to create a new test object instance with the correct first and second parameter
   */
  private final BiFunction<NumberExpression, NumberExpression, E> factory;

  /**
   * Create a new test instance with the given parameters and expected results
   *
   * @param expectedToString The expected {@link E#toString()} response
   * @param factory          Factory, used to create a new test object instance
   */
  ExpressionTest(final String expectedToString,
                 final BiFunction<NumberExpression, NumberExpression, E> factory
  ) {
    this.expectedToString = expectedToString;
    this.factory = factory;
  }

  /**
   * Create a new set of test entries with the expected result
   *
   * @return The list of parameters used during this test
   */
  public abstract Stream<Arguments> expressionProvider();

  /**
   * Verify if the expression calculates the correct result when calling {@link E#interpret()}
   */
  @ParameterizedTest
  @MethodSource("expressionProvider")
  void testInterpret(NumberExpression first, NumberExpression second, int result) {
    final var expression = factory.apply(first, second);
    assertNotNull(expression);
    assertEquals(result, expression.interpret());
  }

  /**
   * Verify if the expression has the expected {@link E#toString()} value
   */
  @ParameterizedTest
  @MethodSource("expressionProvider")
  void testToString(NumberExpression first, NumberExpression second) {
    final var expression = factory.apply(first, second);
    assertNotNull(expression);
    assertEquals(expectedToString, expression.toString());
  }
}
