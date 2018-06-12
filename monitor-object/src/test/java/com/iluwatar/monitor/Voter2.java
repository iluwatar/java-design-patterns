package com.iluwatar.monitor;

import com.iluwatar.monitor.examples.VoteInterface;

class Voter2 extends Voter {
  Voter2(VoteInterface vm) {
    super(vm);
  }

  boolean makeVote(int i) {
    return i % 3 != 0;
  }
}