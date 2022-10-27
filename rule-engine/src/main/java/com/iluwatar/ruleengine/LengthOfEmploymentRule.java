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
package com.iluwatar.ruleengine;

/**
 * This rule states:
 * The candidate has worked locally for enough amount of time.
 * 12+ months: 10 points
 * 6+ months: 5 points
 * None: 0 point
 */
public class LengthOfEmploymentRule implements ImmigrationRule {
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  @Override
  public boolean shouldRun(Candidate candidate) {
    return candidate.getLengthOfEmployment() != 0;
  }
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  @Override
  public int runRule(Candidate candidate) {
    if (candidate.getLengthOfEmployment() >= 12) {
      return 10;
    }
    if (candidate.getLengthOfEmployment() >= 6) {
      return 5;
    }
    return 0;
  }

}
