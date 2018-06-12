package com.iluwatar.monitor;

import com.iluwatar.monitor.examples.VoteInterface;

class Voter1 extends Voter {
  Voter1(VoteInterface vm) {
    super(vm);
  }

  boolean makeVote(int i) {
    return i % 3 == 2;
  }
}