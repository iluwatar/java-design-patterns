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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Verifies each concrete rule independently at, above and below its threshold. */
class LoanRulesTest {

  private static LoanApplication applicant(int age, double income, int score) {
    return new LoanApplication(age, income, 10000.0, score);
  }

  @Test
  void minimumAgeRulePassesAtOrAboveThreshold() {
    var rule = new MinimumAgeRule(18);
    assertTrue(rule.evaluate(applicant(18, 3000.0, 700)));
    assertTrue(rule.evaluate(applicant(40, 3000.0, 700)));
    assertDoesNotThrow(() -> rule.execute(applicant(18, 3000.0, 700)));
  }

  @Test
  void minimumAgeRuleFailsBelowThreshold() {
    var rule = new MinimumAgeRule(18);
    assertFalse(rule.evaluate(applicant(17, 3000.0, 700)));
  }

  @Test
  void minimumIncomeRulePassesAtOrAboveThreshold() {
    var rule = new MinimumIncomeRule(2000.0);
    assertTrue(rule.evaluate(applicant(30, 2000.0, 700)));
    assertTrue(rule.evaluate(applicant(30, 5000.0, 700)));
    assertDoesNotThrow(() -> rule.execute(applicant(30, 2000.0, 700)));
  }

  @Test
  void minimumIncomeRuleFailsBelowThreshold() {
    var rule = new MinimumIncomeRule(2000.0);
    assertFalse(rule.evaluate(applicant(30, 1999.99, 700)));
  }

  @Test
  void creditScoreRulePassesAtOrAboveThreshold() {
    var rule = new CreditScoreRule(650);
    assertTrue(rule.evaluate(applicant(30, 3000.0, 650)));
    assertTrue(rule.evaluate(applicant(30, 3000.0, 800)));
    assertDoesNotThrow(() -> rule.execute(applicant(30, 3000.0, 650)));
  }

  @Test
  void creditScoreRuleFailsBelowThreshold() {
    var rule = new CreditScoreRule(650);
    assertFalse(rule.evaluate(applicant(30, 3000.0, 649)));
  }
}
