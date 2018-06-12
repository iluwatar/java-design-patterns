package com.iluwatar.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.iluwatar.monitor.examples.VoteInterface;
import com.iluwatar.monitor.examples.VoteMonitor;

/**
 * Test Case for Thread Signaling.
 */
public class ThreadSignalTest {

  private Voter v0;
  private Voter v1;
  private Voter v2;

  /**
   * Setup method for Test Case.
   */
  @BeforeAll
  public void setUp() {
    VoteInterface vm = new VoteMonitor(3);
    v0 = new Voter0(vm);
    v1 = new Voter1(vm);
    v2 = new Voter2(vm);
  }

  @Test
  public void testVotingResult() throws InterruptedException {
    v0.start();
    v1.start();
    v2.start();
    v0.join();
    v1.join();
    v2.join();
    assertEquals("Passed", v0.getTestResult());
    assertEquals("Passed", v1.getTestResult());
    assertEquals("Passed", v2.getTestResult());
  }
}