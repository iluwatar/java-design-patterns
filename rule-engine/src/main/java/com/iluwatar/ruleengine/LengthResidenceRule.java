package com.iluwatar.ruleengine;

import java.awt.*;

public class LengthResidenceRule implements IRule {
    @Override
    public boolean shouldRun(Candidate candidate) {
        return candidate.getLengthOfCurrentResidence() != 0;
    }

    @Override
    public int runRule(Candidate candidate) {
        int length = candidate.getLengthOfCurrentResidence();
        if(length == 1){
            return 5;
        }
        if(length == 2){
            return 10;
        }
        if(length == 3){
            return 15;
        }
        if(length == 4){
            return 20;
        }
        if(length >= 5){
            return 25;
        }
        return 0;
    }
}
