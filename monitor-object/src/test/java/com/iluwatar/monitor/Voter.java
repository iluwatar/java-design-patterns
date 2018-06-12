package com.iluwatar.monitor;

import com.iluwatar.monitor.examples.VoteInterface;

abstract class Voter extends Thread {

  private VoteInterface vm;

  private String testResult;

  public String getTestResult() {
    return testResult;
  }

  public void setTestResult(String testResult) {
    this.testResult = testResult;
  }

  Voter(VoteInterface vm) {
    this.vm = vm;
  }

  public void run() {
    for (int i = 0; i < 100; ++i) {
      boolean vote = makeVote(i);
      boolean consensus = vm.castVoteAndWaitForResult(vote);
      boolean expected = i % 6 == 1 || i % 6 == 2 || i % 6 == 5;
      if (expected != consensus) {
        System.out.println("Failed");
        setTestResult("Failed");
        System.exit(1);
      }
      setTestResult("Passed");
      System.out.println(i + ": " + consensus);
    }
    System.out.println("Done");
  }

  abstract boolean makeVote(int i);
}