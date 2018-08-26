/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.monitor.examples;

import com.iluwatar.monitor.AbstractMonitor;
import com.iluwatar.monitor.Assertion;
import com.iluwatar.monitor.Condition;
import com.iluwatar.monitor.RunnableWithResult;

/**
 * Monitor Example.
 */

public class VoteMonitor extends AbstractMonitor implements VoteInterface {

  private final int totalVotes;
  private int votesFor = 0;
  private int votesAgainst = 0;

  private Condition electionDone = makeCondition(new Assertion() {
    @Override
    public boolean isTrue() {
      return votesFor + votesAgainst == totalVotes;
    }
  });

  public VoteMonitor(int n) {
    assert n > 0;
    this.totalVotes = n;
  }

  @Override
  protected boolean invariant() {
    return 0 <= votesFor && 0 <= votesAgainst && votesFor + votesAgainst < totalVotes;
  }

  /**
   * Method to cast a vote and wait for the result.
   */
  public boolean castVoteAndWaitForResult(final boolean vote) {
    return doWithin(new RunnableWithResult<Boolean>() {
      public Boolean run() {
        if (vote) {
          votesFor++;
        } else {
          votesAgainst++;
        }

        electionDone.conditionalAwait();

        // Assert: votesFor+votesAgainst == N
        boolean result = votesFor > votesAgainst;

        if (!electionDone.isEmpty()) {
          electionDone.signalAndLeave();
        } else {
          votesFor = votesAgainst = 0;
        }
        // At this point the thread could be occupying
        // the monitor, or not!
        return result;
      }
    });
  }
}