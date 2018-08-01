/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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