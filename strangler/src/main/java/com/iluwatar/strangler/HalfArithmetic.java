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

package com.iluwatar.strangler;

import lombok.extern.slf4j.Slf4j;

/**
 * System under migration. Depends on old version source ({@link OldSource}) and
 * developing one ({@link HalfSource}).
 */
@Slf4j
public class HalfArithmetic {
  private static final String VERSION = "1.5";

  private final HalfSource newSource;
  private final OldSource oldSource;

  public HalfArithmetic(HalfSource newSource, OldSource oldSource) {
    this.newSource = newSource;
    this.oldSource = oldSource;
  }

  /**
   * Accumulate sum.
   * @param nums numbers need to add together
   * @return accumulate sum
   */
  public int sum(int... nums) {
    LOGGER.info("Arithmetic sum {}", VERSION);
    return newSource.accumulateSum(nums);
  }

  /**
   * Accumulate multiplication.
   * @param nums numbers need to multiply together
   * @return accumulate multiplication
   */
  public int mul(int... nums) {
    LOGGER.info("Arithmetic mul {}", VERSION);
    return oldSource.accumulateMul(nums);
  }

  /**
   * Chech if has any zero.
   * @param nums numbers need to check
   * @return if has any zero, return true, else, return false
   */
  public boolean ifHasZero(int... nums) {
    LOGGER.info("Arithmetic check zero {}", VERSION);
    return !newSource.ifNonZero(nums);
  }
}
