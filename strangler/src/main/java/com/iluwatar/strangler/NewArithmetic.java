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
package com.iluwatar.strangler;

import lombok.extern.slf4j.Slf4j;

/**
 * System after whole migration. Only depends on new version source ({@link NewSource}).
 */
@Slf4j
public class NewArithmetic {
  private static final String VERSION = "2.0";

  private final NewSource source;

  public NewArithmetic(NewSource source) {
    this.source = source;
  }

  /**
   * Accumulate sum.
   * @param nums numbers need to add together
   * @return accumulate sum
   */
  public int sum(int... nums) {
    LOGGER.info("Arithmetic sum {}", VERSION);
    return source.accumulateSum(nums);
  }

  /**
   * Accumulate multiplication.
   * @param nums numbers need to multiply together
   * @return accumulate multiplication
   */
  public int mul(int... nums) {
    LOGGER.info("Arithmetic mul {}", VERSION);
    return source.accumulateMul(nums);
  }

  /**
   * Check if it has any zero.
   * @param nums numbers need to check
   * @return if it has any zero, return true, else, return false
   */
  public boolean ifHasZero(int... nums) {
    LOGGER.info("Arithmetic check zero {}", VERSION);
    return !source.ifNonZero(nums);
  }
}
