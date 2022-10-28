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

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * The Rules Pattern works by separating out the rules from the rules processing logic
 * (applying the Single Responsibility Principle).
 * This makes it easy to add new rules without changing the rest of the system
 * (applying the Open/Closed Principle).
 * *
 * In this example we have an imaginary country that has an immigration policy with multiple criteria
 * We create separate class for each criterion to implement the pattern to calculate the candidate's
 * immigration score.
 */

@Slf4j
public class App {
  /**
   * The main method of rule engine pattern.
   */
  public static void main(String[] args) {
    List<ImmigrationRule> rules = new ArrayList<>();

    rules.add(new LengthOfResidenceRule());
    rules.add(new EnglishProficiencyRule());
    rules.add(new NominatedOccupationRule());
    rules.add(new SmallBusinessRule());
    rules.add(new LengthOfEmploymentRule());
    rules.add(new TertiaryQualificationRule());
    rules.add(new YearsOfStudyRule());
    rules.add(new LocalAssetsRule());
    rules.add(new CloseFamilyRule());

    Candidate candidate1 = new Candidate(5, "superior", true, true, 100000, 7, 6, "Master Degree", 300000, "Spouse");
    Candidate candidate2 = new Candidate(3, "proficient", false, false, 0, 12, 3, "Bachelor Degree", 20000, "Parent");
    Candidate candidate3 = new Candidate(1, "proficient", false, false, 0, 12, 7, "Doctoral Degree", 200000, "Parent");

    int score1 = 0;
    int score2 = 0;
    int score3 = 0;
    for (ImmigrationRule rule : rules) {
      if (rule.shouldRun(candidate1)) {
        score1 += rule.runRule(candidate1);
        score2 += rule.runRule(candidate2);
        score3 += rule.runRule(candidate3);
      }
    }

    LOGGER.info("score for candidate 1: " + score1);
    LOGGER.info("score for candidate 2: " + score2);
    LOGGER.info("score for candidate 3: " + score3);

  }
}