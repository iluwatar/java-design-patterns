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
 * Old source with techniques out of date.
 */
@Slf4j
public class OldSource {
  private static final String VERSION = "1.0";

  /**
   * Implement accumulate sum with old technique.
   */
  public int accumulateSum(int... nums) {
    LOGGER.info("Source module {}", VERSION);
    var sum = 0;
    for (final var num : nums) {
      sum += num;
    }
    return sum;
  }

  /**
   * Implement accumulate multiply with old technique.
   */
  public int accumulateMul(int... nums) {
    LOGGER.info("Source module {}", VERSION);
    var sum = 1;
    for (final var num : nums) {
      sum *= num;
    }
    return sum;
  }
}
