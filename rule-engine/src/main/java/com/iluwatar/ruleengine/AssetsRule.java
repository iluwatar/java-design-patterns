package com.iluwatar.ruleengine;

public class AssetsRule implements IRule{
    @Override
    public boolean shouldRun(Candidate candidate) {
        return candidate.getAssets()!=0;
    }

    @Override
    public int runRule(Candidate candidate) {


        if(candidate.getAssets() >= 250000 ){
            return 5;
        }

        return 0;
    }
}
