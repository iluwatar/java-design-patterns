package com.iluwatar.monitor;

import com.iluwatar.monitor.examples.VoteInterface;

class Voter0 extends Voter {
  Voter0(VoteInterface vm) {
    super(vm);
  }

  boolean makeVote(int i) {
    return i % 2 == 1;
  }
}