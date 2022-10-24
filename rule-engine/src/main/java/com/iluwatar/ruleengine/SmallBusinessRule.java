package com.iluwatar.ruleengine;

public class SmallBusinessRule implements IRule {
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  @Override
  public boolean shouldRun(Candidate candidate) {
    return candidate.isSmallBusinessOwner();
  }
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  @Override
  public int runRule(Candidate candidate) {
    int turnover = candidate.getSmallBusinessTurnover();

    if (turnover >= 100000 && turnover < 200000) {
      return 10;
    }
    if (turnover >= 200000) {
      return 20;
    }

    return 0;
  }
}
