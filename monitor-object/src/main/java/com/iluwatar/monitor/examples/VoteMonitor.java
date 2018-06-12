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