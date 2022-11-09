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

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 * Source under development. Replace part of old source and has added some new features.
 */
@Slf4j
public class HalfSource {
  private static final String VERSION = "1.5";

  /**
   * Implement accumulate sum with new technique.
   * Replace old one in {@link OldSource}
   */
  public int accumulateSum(int... nums) {
    LOGGER.info("Source module {}", VERSION);
    return Arrays.stream(nums).reduce(0, Integer::sum);
  }

  /**
   * Check if all number is not zero.
   * New feature.
   */
  public boolean ifNonZero(int... nums) {
    LOGGER.info("Source module {}", VERSION);
    return Arrays.stream(nums).allMatch(num -> num != 0);
  }
}
