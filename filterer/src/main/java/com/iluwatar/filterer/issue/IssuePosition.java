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

/**
 * Represents position of an issue. Takes starting and ending offset of issue in given text.
 */
public final class IssuePosition {

  private final int startOffset;
  private final int endOffset;

  /**
   * Factory method for constructing `IssuePosition` instances.
   * @param startOffset starting offset of where the issue begins.
   * @param endOffset ending offset of where the issue ends.
   * @return new IssuePosition instance.
   */
  public static IssuePosition of(final int startOffset, final int endOffset) {
    return new IssuePosition(startOffset, endOffset);
  }

  private IssuePosition(int startOffset, int endOffset) {
    this.startOffset = startOffset;
    this.endOffset = endOffset;
  }

  int startOffset() {
    return startOffset;
  }

  int endOffset() {
    return endOffset;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IssuePosition that = (IssuePosition) o;
    return startOffset == that.startOffset
            && endOffset == that.endOffset;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startOffset, endOffset);
  }
}
