/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

/**
 * Date: 12/14/15 - 12:08 PM
 *
 * @author Jeroen Meulemeester
 */
public class PlusExpressionTest extends ExpressionTest<PlusExpression> {

  /**
   * Create a new set of test entries with the expected result
   *
   * @return The list of parameters used during this test
   */
  @Override
  public Stream<Arguments> expressionProvider() {
    return prepareParameters(Integer::sum);
  }

  /**
   * Create a new test instance using the given test parameters and expected result
   */
  public PlusExpressionTest() {
    super("+", PlusExpression::new);
  }

}