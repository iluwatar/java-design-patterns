package com.iluwatar.interpreter;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;

/**
 * Date: 12/14/15 - 12:08 PM
 *
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class MinusExpressionTest extends ExpressionTest<MinusExpression> {

  /**
   * Create a new set of test entries with the expected result
   *
   * @return The list of parameters used during this test
   */
  @Parameters
  public static List<Object[]> data() {
    return prepareParameters((f, s) -> f - s);
  }

  /**
   * Create a new test instance using the given test parameters and expected result
   *
   * @param first  The first expression parameter
   * @param second The second expression parameter
   * @param result The expected result
   */
  public MinusExpressionTest(final NumberExpression first, final NumberExpression second, final int result) {
    super(first, second, result, "-", MinusExpression::new);
  }

}