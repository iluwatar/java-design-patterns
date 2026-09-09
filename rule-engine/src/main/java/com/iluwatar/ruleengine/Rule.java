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
 * Abstraction for a single, self-contained business rule.
 *
 * <p>A rule separates the decision ({@link #evaluate(Object)}) from the action ({@link
 * #execute(Object)}). {@code evaluate} inspects the context and reports whether the rule's
 * condition is satisfied, while {@code execute} performs the side effect associated with a
 * satisfied rule. Keeping the two responsibilities apart lets a {@link RuleEngine} decide which
 * rules apply before running any action, and lets new rules be added without touching the engine.
 *
 * @param <T> type of the context the rule is evaluated against
 */
public interface Rule<T> {

  /**
   * Returns a human readable identifier used when reporting rule outcomes.
   *
   * @return the rule name
   */
  String name();

  /**
   * Evaluates the rule against the given context.
   *
   * @param context the immutable input the rule inspects
   * @return {@code true} when the rule's condition is satisfied, {@code false} otherwise
   */
  boolean evaluate(T context);

  /**
   * Performs the action associated with a satisfied rule.
   *
   * @param context the immutable input the action operates on
   */
  void execute(T context);
}
