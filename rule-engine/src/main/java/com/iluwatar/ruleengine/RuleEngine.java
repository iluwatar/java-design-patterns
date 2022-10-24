package com.iluwatar.ruleengine;

import java.util.ArrayList;

public class RuleEngine {
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  public static void main(String[] args) {
    ArrayList<IRule> rules = new ArrayList<IRule>();
    rules.add(new EnglishProficiencyRule());
    rules.add(new LengthResidenceRule());
    rules.add(new NominatedOccupationRule());
    rules.add(new SmallBusinessRule());

    Candidate candidate = new Candidate();
    candidate.setEnglishProficiency("superior");
    candidate.setSmallBusinessOwner(true);
    candidate.setNominatedOccupationOnTheList(true);
    candidate.setLengthOfCurrentResidence(5);

    int score = 0;
    for (IRule rule : rules) {
      if (rule.shouldRun(candidate)) {
        score += rule.runRule(candidate);
      }
    }

    System.out.println(score);

  }
}