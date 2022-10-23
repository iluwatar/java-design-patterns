package com.iluwatar.ruleengine;

public class EnglishProficiencyRule implements IRule{

    @Override
    public boolean shouldRun(Candidate candidate) {
        return candidate.getEnglishProficiency() != null;
    }

    @Override
    public int runRule(Candidate candidate) {
        String proficiency = candidate.getEnglishProficiency();

       if(proficiency == "superior"){
           return 15;
       }
       if(proficiency == "proficient"){
           return 10;
       }
       return 0;
    }
}
