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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RuleEngineTest {

  private static RuleEngine<LoanApplication> loanEngine() {
    return new RuleEngine<>(
        List.of(new MinimumAgeRule(18), new MinimumIncomeRule(2000.0), new CreditScoreRule(650)));
  }

  @Test
  void approvesWhenEveryRulePasses() {
    var result = loanEngine().run(new LoanApplication(30, 3000.0, 10000.0, 700));
    assertTrue(result.approved());
    assertEquals(
        List.of("MinimumAgeRule", "MinimumIncomeRule", "CreditScoreRule"), result.passedRules());
    assertTrue(result.failedRules().isEmpty());
  }

  @Test
  void rejectsWhenASingleRuleFails() {
    var result = loanEngine().run(new LoanApplication(30, 3000.0, 10000.0, 600));
    assertFalse(result.approved());
    assertEquals(List.of("CreditScoreRule"), result.failedRules());
    assertEquals(List.of("MinimumAgeRule", "MinimumIncomeRule"), result.passedRules());
  }

  @Test
  void reportsEveryFailingRuleWithoutStoppingEarly() {
    var result = loanEngine().run(new LoanApplication(16, 500.0, 10000.0, 400));
    assertFalse(result.approved());
    assertEquals(
        List.of("MinimumAgeRule", "MinimumIncomeRule", "CreditScoreRule"), result.failedRules());
    assertTrue(result.passedRules().isEmpty());
  }

  @Test
  void evaluatesRulesInInsertionOrder() {
    var engine =
        new RuleEngine<>(
            List.of(
                new CreditScoreRule(650), new MinimumAgeRule(18), new MinimumIncomeRule(2000.0)));
    var result = engine.run(new LoanApplication(16, 500.0, 10000.0, 400));
    assertEquals(
        List.of("CreditScoreRule", "MinimumAgeRule", "MinimumIncomeRule"), result.failedRules());
  }

  @Test
  void emptyEngineApprovesVacuously() {
    var result =
        new RuleEngine<LoanApplication>(List.of()).run(new LoanApplication(1, 1.0, 1.0, 1));
    assertTrue(result.approved());
    assertTrue(result.passedRules().isEmpty());
    assertTrue(result.failedRules().isEmpty());
  }

  @Test
  void rejectsNullContext() {
    assertThrows(NullPointerException.class, () -> loanEngine().run(null));
  }

  @Test
  void rejectsNullRuleCollection() {
    assertThrows(NullPointerException.class, () -> new RuleEngine<LoanApplication>(null));
  }

  @Test
  void rejectsNullRuleElement() {
    var rules = new ArrayList<Rule<LoanApplication>>();
    rules.add(null);
    assertThrows(NullPointerException.class, () -> new RuleEngine<>(rules));
  }

  @Test
  void copiesRulesDefensivelyOnConstruction() {
    var rules = new ArrayList<Rule<LoanApplication>>();
    rules.add(new MinimumAgeRule(18));
    var engine = new RuleEngine<>(rules);

    rules.add(new CreditScoreRule(650)); // mutate the source after construction
    assertEquals(1, engine.rules().size());
  }

  @Test
  void exposedRulesAreUnmodifiable() {
    var engine = loanEngine();
    assertThrows(
        UnsupportedOperationException.class, () -> engine.rules().add(new MinimumAgeRule(21)));
  }
}
