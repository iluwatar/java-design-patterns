package com.iluwatar.ruleengine;

public class Length_of_employmentRule implements IRule {

    @Override
    public boolean shouldRun(Candidate candidate) {
        return candidate.getLength_of_ACT_employment()!= 0;
    }

    @Override
    public int runRule(Candidate candidate) {


        if(candidate.getLength_of_ACT_employment() >=12){
            return 10;
        }
        if(candidate.getLength_of_ACT_employment() >= 6){
            return 5;
        }
        return 0;
    }

}
