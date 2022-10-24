package com.iluwatar.ruleengine;

public interface IRule {
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  public boolean shouldRun(Candidate candidate);
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  public int runRule(Candidate candidate);
}
