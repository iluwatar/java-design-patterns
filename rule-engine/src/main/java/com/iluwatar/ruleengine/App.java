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

import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * The Rule Engine pattern encapsulates each business rule as an independent object and lets a
 * {@link RuleEngine} evaluate a collection of them against a shared context. This replaces large,
 * tangled conditional blocks with small, testable rules that can be added or removed without
 * touching the orchestration logic.
 *
 * <p>This demo builds a loan approval engine from three rules and runs two applications through it:
 * one that satisfies every rule and one that fails two of them. Because the engine evaluates every
 * rule rather than stopping at the first failure, the result reports all the reasons an application
 * was rejected.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    var engine =
        new RuleEngine<>(
            List.of(
                new MinimumAgeRule(18), new MinimumIncomeRule(2000.0), new CreditScoreRule(650)));

    var approved = new LoanApplication(30, 3500.0, 15000.0, 720);
    var rejected = new LoanApplication(17, 1500.0, 15000.0, 720);

    report(engine.run(approved));
    report(engine.run(rejected));
  }

  private static void report(RuleEngineResult result) {
    if (result.approved()) {
      LOGGER.info("Loan approved. Passed rules: {}", result.passedRules());
    } else {
      LOGGER.info("Loan rejected. Failed rules: {}", result.failedRules());
    }
  }
}
