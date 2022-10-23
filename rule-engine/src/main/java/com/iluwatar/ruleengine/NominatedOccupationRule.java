package com.iluwatar.ruleengine;

public class NominatedOccupationRule implements IRule{
    @Override
    public boolean shouldRun(Candidate candidate) {
        return candidate.isNominatedOccupationOnTheList();
    }

    @Override
    public int runRule(Candidate candidate) {
        return 20;
    }
}
