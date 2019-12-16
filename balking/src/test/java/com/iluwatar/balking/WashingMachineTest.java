/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.balking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WashingMachine}
 */
class WashingMachineTest {

  private final FakeDelayProvider fakeDelayProvider = new FakeDelayProvider();

  @Test
  void wash() {
    var washingMachine = new WashingMachine(fakeDelayProvider);

    washingMachine.wash();
    washingMachine.wash();

    var machineStateGlobal = washingMachine.getWashingMachineState();

    fakeDelayProvider.task.run();

    // washing machine remains in washing state
    assertEquals(WashingMachineState.WASHING, machineStateGlobal);

    // washing machine goes back to enabled state
    assertEquals(WashingMachineState.ENABLED, washingMachine.getWashingMachineState());
  }

  @Test
  void endOfWashing() {
    var washingMachine = new WashingMachine();
    washingMachine.wash();
    assertEquals(WashingMachineState.ENABLED, washingMachine.getWashingMachineState());
  }

  private static class FakeDelayProvider implements DelayProvider {
    private Runnable task;

    @Override
    public void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task) {
      this.task = task;
    }
  }
}