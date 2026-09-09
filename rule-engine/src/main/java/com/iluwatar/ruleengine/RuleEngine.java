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
import java.util.Objects;

/**
 * Orchestrates a fixed collection of {@link Rule rules} against a context.
 *
 * <p>The engine owns the coordination logic that would otherwise live in a large nested {@code
 * if}/{@code else} block: it evaluates every rule in insertion order, runs the action of each rule
 * whose condition is satisfied, and reports the combined outcome as a {@link RuleEngineResult}.
 * Evaluation never stops early, so a single run reports <em>all</em> failing rules rather than only
 * the first, which is what makes rejection reasons useful.
 *
 * <p>The rule collection is copied defensively on construction, making the engine immutable and
 * safe to reuse across many contexts. New behaviour is added by supplying additional {@link Rule}
 * implementations; the engine itself never changes.
 *
 * @param <T> type of the context the rules are evaluated against
 */
public class RuleEngine<T> {

  private final List<Rule<T>> rules;

  /**
   * Creates an engine for the given rules.
   *
   * @param rules the rules to evaluate, applied in iteration order; must not be {@code null} or
   *     contain {@code null} elements
   * @throws NullPointerException if {@code rules} is {@code null} or contains a {@code null} rule
   */
  public RuleEngine(List<Rule<T>> rules) {
    this.rules = List.copyOf(rules);
  }

  /**
   * Evaluates every rule against the context and executes the rules that pass.
   *
   * @param context the context inspected by the rules; must not be {@code null}
   * @return the combined outcome of the run
   * @throws NullPointerException if {@code context} is {@code null}
   */
  public RuleEngineResult run(T context) {
    Objects.requireNonNull(context, "context must not be null");
    List<String> passed = new ArrayList<>();
    List<String> failed = new ArrayList<>();
    for (Rule<T> rule : rules) {
      if (rule.evaluate(context)) {
        rule.execute(context);
        passed.add(rule.name());
      } else {
        failed.add(rule.name());
      }
    }
    return new RuleEngineResult(failed.isEmpty(), passed, failed);
  }

  /**
   * Returns the rules held by this engine.
   *
   * @return an immutable view of the configured rules
   */
  public List<Rule<T>> rules() {
    return rules;
  }
}
