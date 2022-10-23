package com.iluwatar.ruleengine;

public class SmallBusinessRule implements IRule{

    @Override
    public boolean shouldRun(Candidate candidate) {
        return candidate.isSmallBusinessOwner();
    }

    @Override
    public int runRule(Candidate candidate) {
        int turnover = candidate.getSmallBusinessTurnover();

        if(turnover >= 100000 && turnover < 200000){
            return 10;
        }
        if(turnover >= 200000){
            return 20;
        }
        return 0;
    }
}
