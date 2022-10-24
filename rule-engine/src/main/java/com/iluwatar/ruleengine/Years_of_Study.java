package com.iluwatar.ruleengine;

public class Years_of_Study implements IRule{
    @Override
    public boolean shouldRun(Candidate candidate) {
        return candidate.getYears_of_Study() != 0;
    }

    @Override
    public int runRule(Candidate candidate) {

        if(candidate.getYears_of_Study() >= 4){
            return 20;
        }
        if(candidate.getYears_of_Study() == 3){
            return 15;
        }
        if(candidate.getYears_of_Study() == 2){
            return 10;
        }
        if(candidate.getYears_of_Study() == 1){
            return 5;
        }

        return 0;
    }
}
