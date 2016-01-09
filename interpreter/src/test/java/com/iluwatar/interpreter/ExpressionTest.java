package com.iluwatar.interpreter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 12/14/15 - 11:48 AM
 *
 * @author Jeroen Meulemeester
 */
public abstract class ExpressionTest<E extends Expression> {

  /**
   * Generate inputs ranging from -10 to 10 for both input params and calculate the expected result
   *
   * @param resultCalc The function used to calculate the expected result
   * @return A data set with test entries
   */
  static List<Object[]> prepareParameters(final BiFunction<Integer, Integer, Integer> resultCalc) {
    final List<Object[]> testData = new ArrayList<>();
    for (int i = -10; i < 10; i++) {
      for (int j = -10; j < 10; j++) {
        testData.add(new Object[]{
            new NumberExpression(i),
            new NumberExpression(j),
            resultCalc.apply(i, j)
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
