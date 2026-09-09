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

/**
 * Immutable outcome produced by {@link RuleEngine#run(Object)}.
 *
 * <p>The result reports whether every rule passed and, for transparency, the names of the rules
 * that passed and failed in the order they were evaluated. The lists are copied on construction so
 * the result cannot be mutated by callers or by later changes to the source collections.
 *
 * @param approved {@code true} when no rule failed
 * @param passedRules names of the rules whose condition was satisfied, in evaluation order
 * @param failedRules names of the rules whose condition was not satisfied, in evaluation order
 */
public record RuleEngineResult(
    boolean approved, List<String> passedRules, List<String> failedRules) {

  /** Defensive copy keeps the value object immutable. */
  public RuleEngineResult {
    passedRules = List.copyOf(passedRules);
    failedRules = List.copyOf(failedRules);
  }
}
