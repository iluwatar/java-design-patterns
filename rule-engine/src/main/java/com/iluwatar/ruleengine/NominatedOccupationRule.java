package com.iluwatar.ruleengine;

public class NominatedOccupationRule implements IRule {
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  @Override
  public boolean shouldRun(Candidate candidate) {
    return candidate.isNominatedOccupationOnTheList();
  }
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  @Override
  public int runRule(Candidate candidate) {
    return 20;
  }
}
