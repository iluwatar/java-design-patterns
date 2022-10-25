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

public class SmallBusinessRule implements IRule {
  /**
   * Check if this Small Business Rule can be executed or not
   *
   * @param candidate: the current candidate
   * @return boolean: true if the candidate has met the Small Business Rule
   * @author Harry Li
   */
  @Override
  public boolean shouldRun(Candidate candidate) {
    return candidate.isSmallBusinessOwner();
  }

  /**
   * Execute this Small Business Rule
   *
   * @param candidate: the current candidate
   * @return int: the score that the candidate can get based on its small business turnover
   * @author Harry Li
   */
  @Override
  public int runRule(Candidate candidate) {
    int turnover = candidate.getSmallBusinessTurnover();

    if (turnover >= 100000 && turnover < 200000) {
      return 10;
    }
    if (turnover >= 200000) {
      return 20;
    }

    return 0;
  }
}
