package com.iluwatar.ruleengine;

import java.awt.*;

public interface IRule {
    public boolean shouldRun(Candidate candidate);
    public int runRule(Candidate candidate);
}
