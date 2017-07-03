/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.balking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Washing machine class
 */
public class WashingMachine {

  private static final Logger LOGGER = LoggerFactory.getLogger(WashingMachine.class);

  private WashingMachineState washingMachineState;

  public WashingMachine() {
    washingMachineState = WashingMachineState.ENABLED;
  }

  public WashingMachineState getWashingMachineState() {
    return washingMachineState;
  }

  /**
   * Method responsible for washing
   * if the object is in appropriate state
   */
  public void wash() {
    synchronized (this) {
      LOGGER.info("{}: Actual machine state: {}", Thread.currentThread().getName(), getWashingMachineState());
      if (washingMachineState == WashingMachineState.WASHING) {
        LOGGER.error("ERROR: Cannot wash if the machine has been already washing!");
        return;
      }
      washingMachineState = WashingMachineState.WASHING;
    }
    LOGGER.info("{}: Doing the washing", Thread.currentThread().getName());
    try {
      Thread.sleep(50);
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }
    endOfWashing();
  }

  /**
   * Method responsible of ending the washing
   * by changing machine state
   */
  public synchronized void endOfWashing() {
    washingMachineState = WashingMachineState.ENABLED;
    LOGGER.info("{}: Washing completed.", Thread.currentThread().getId());
  }

}
