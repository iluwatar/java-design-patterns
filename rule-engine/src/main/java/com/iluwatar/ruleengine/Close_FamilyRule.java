package com.iluwatar.ruleengine;

public class Close_FamilyRule implements IRule{

        @Override
        public boolean shouldRun(Candidate candidate) {
            return candidate.getClose_Family_Ties() != null;
        }

        @Override
        public int runRule(Candidate candidate) {


            if (candidate.getClose_Family_Ties() == "Spouse") {
                return 20;
            }   if (candidate.getClose_Family_Ties() == "Parent") {
                return 10;
            }

            return 0;
        }
    }
