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

import java.util.Objects;

public class SimpleIssue implements Issue {

  private final IssuePosition issuePosition;
  private final IssueType issueType;

  SimpleIssue(final IssuePosition issuePosition, IssueType issueType) {
    this.issuePosition = issuePosition;
    this.issueType = issueType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int startOffset() {
    return issuePosition.startOffset();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int endOffset() {
    return issuePosition.endOffset();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IssueType type() {
    return issueType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SimpleIssue that = (SimpleIssue) o;
    return issuePosition.equals(that.issuePosition)
            && issueType == that.issueType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(issuePosition, issueType);
  }
}
