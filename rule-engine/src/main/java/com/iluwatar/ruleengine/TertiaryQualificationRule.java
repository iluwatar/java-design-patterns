package com.iluwatar.ruleengine;

public class TertiaryQualificationRule implements IRule {
    @Override
    public boolean shouldRun(Candidate candidate) {
        return candidate.getTertiary_Qualification() !=null;
    }

    @Override
    public int runRule(Candidate candidate) {


        if(candidate.getTertiary_Qualification() == "Doctoral degree" ){
            return 20;
        } if(candidate.getTertiary_Qualification() == "Master degree" ){
            return 15;
        } if(candidate.getTertiary_Qualification() == "Bachelor degree" ){
            return 10;
        } if(candidate.getTertiary_Qualification() == "Diploma degree" ){
            return 5;
        }  if(candidate.getTertiary_Qualification() == "Doctoral degree" ){
            return 20;
        }
        return 0;
    }
}
