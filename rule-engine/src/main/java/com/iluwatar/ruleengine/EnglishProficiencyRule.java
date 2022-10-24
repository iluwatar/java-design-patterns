package com.iluwatar.ruleengine;

public class EnglishProficiencyRule implements IRule {
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  @Override
  public boolean shouldRun(Candidate candidate) {
    return candidate.getEnglishProficiency() != null;
  }
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  @Override
  public int runRule(Candidate candidate) {
    String proficiency = candidate.getEnglishProficiency();

    if (proficiency == "superior") {
      return 15;
    }
    if (proficiency == "proficient") {
      return 10;
    }
    return 0;
  }
}
