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

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 * New source. Completely covers functionalities of old source with new techniques
 * and also has some new features.
 */
@Slf4j
public class NewSource {
  private static final String VERSION = "2.0";
  public static final String SOURCE_MODULE = "Source module {}";

  public int accumulateSum(int... nums) {
    LOGGER.info(SOURCE_MODULE, VERSION);
    return Arrays.stream(nums).reduce(0, Integer::sum);
  }

  /**
   * Implement accumulate multiply with new technique.
   * Replace old one in {@link OldSource}
   */
  public int accumulateMul(int... nums) {
    LOGGER.info(SOURCE_MODULE, VERSION);
    return Arrays.stream(nums).reduce(1, (a, b) -> a * b);
  }

  public boolean ifNonZero(int... nums) {
    LOGGER.info(SOURCE_MODULE, VERSION);
    return Arrays.stream(nums).allMatch(num -> num != 0);
  }
}
