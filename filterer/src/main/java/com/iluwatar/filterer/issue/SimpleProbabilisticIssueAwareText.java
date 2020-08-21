/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.filterer.issue;

import com.google.common.collect.ImmutableList;
import com.iluwatar.filterer.domain.Filterer;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * {@inheritDoc}
 */
public class SimpleProbabilisticIssueAwareText implements ProbabilisticIssueAwareText {

  private final String text;
  private final ImmutableList<ProbableIssue> issues;

  SimpleProbabilisticIssueAwareText(final String text, final List<ProbableIssue> issues) {
    this.text = text;
    this.issues = ImmutableList.copyOf(issues);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String text() {
    return text;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends ProbableIssue> issues() {
    return issues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Filterer<? extends ProbabilisticIssueAwareText, ? extends ProbableIssue> filtered() {
    return this::filteredGroup;
  }

  private ProbabilisticIssueAwareText filteredGroup(
          final Predicate<? super ProbableIssue> predicate
  ) {
    return new SimpleProbabilisticIssueAwareText(this.text, filteredItems(predicate));
  }

  private ImmutableList<ProbableIssue> filteredItems(
          final Predicate<? super ProbableIssue> predicate
  ) {
    return this.issues.stream()
            .filter(predicate)
            .collect(ImmutableList.toImmutableList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SimpleProbabilisticIssueAwareText that = (SimpleProbabilisticIssueAwareText) o;
    return text.equals(that.text)
            && issues.equals(that.issues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, issues);
  }
}
