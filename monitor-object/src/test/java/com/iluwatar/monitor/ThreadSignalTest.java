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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.monitor.examples.VoteInterface;
import com.iluwatar.monitor.examples.VoteMonitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
  @BeforeEach
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