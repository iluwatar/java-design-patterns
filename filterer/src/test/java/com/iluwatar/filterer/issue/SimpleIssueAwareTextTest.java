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

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleIssueAwareTextTest {

  @Test
  void shouldFilterByStartOffset() {
    //given
    SimpleIssue spellingIssue = new SimpleIssue(IssuePosition.of(4, 5), IssueType.SPELLING);
    SimpleIssue grammarIssue = new SimpleIssue(IssuePosition.of(8, 12), IssueType.GRAMMAR);
    List<Issue> issues = List.of(spellingIssue, grammarIssue);

    SimpleIssueAwareText simpleIssueWiseText = new SimpleIssueAwareText("I mihgt gone there", issues);

    //when
    IssueAwareText filtered = simpleIssueWiseText.filtered()
            .by(issue1 -> issue1.startOffset() == 4);

    //then
    assertThat(filtered.issues()).hasSize(1);
    assertThat(filtered.issues()).element(0).isEqualTo(spellingIssue);
  }

}
