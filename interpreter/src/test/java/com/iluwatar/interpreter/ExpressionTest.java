/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

import org.junit.Test;

/**
 * Date: 12/14/15 - 11:48 AM
 *
 * Test Case for Expressions
 * @param <E> Type of Expression
 * @author Jeroen Meulemeester
 */
public abstract class ExpressionTest<E extends Expression> {

  /**
   * Generate inputs ranging from -10 to 10 for both input params and calculate the expected result
   *
   * @param resultCalc The function used to calculate the expected result
   * @return A data set with test entries
   */
  static List<Object[]> prepareParameters(final IntBinaryOperator resultCalc) {
    final List<Object[]> testData = new ArrayList<>();
    for (int i = -10; i < 10; i++) {
      for (int j = -10; j < 10; j++) {
        testData.add(new Object[]{
            new NumberExpression(i),
            new NumberExpression(j),
            resultCalc.applyAsInt(i, j)
        });
      }
    }
    return testData;
  }

  /**
   * The input used as first parameter during the test
   */
  private final NumberExpression first;

  /**
   * The input used as second parameter during the test
   */
  private final NumberExpression second;

  /**
   * The expected result of the calculation, taking the first and second parameter in account
   */
  private final int result;

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
   * @param first            The input used as first parameter during the test
   * @param second           The input used as second parameter during the test
   * @param result           The expected result of the tested expression
   * @param expectedToString The expected {@link E#toString()} response
   * @param factory          Factory, used to create a new test object instance
   */
  ExpressionTest(final NumberExpression first, final NumberExpression second, final int result,
                 final String expectedToString, final BiFunction<NumberExpression, NumberExpression, E> factory) {

    this.first = first;
    this.second = second;
    this.result = result;
    this.expectedToString = expectedToString;
    this.factory = factory;
  }

  /**
   * Get the first parameter
   *
   * @return The first parameter
   */
  final NumberExpression getFirst() {
    return this.first;
  }

  /**
   * Verify if the expression calculates the correct result when calling {@link E#interpret()}
   */
  @Test
  public void testInterpret() {
    final E expression = this.factory.apply(this.first, this.second);
    assertNotNull(expression);
    assertEquals(this.result, expression.interpret());
  }

  /**
   * Verify if the expression has the expected {@link E#toString()} value
   */
  @Test
  public void testToString() {
    final E expression = this.factory.apply(this.first, this.second);
    assertNotNull(expression);
    assertEquals(expectedToString, expression.toString());
  }
}
