/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.scheduler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

class TriggerTest {

  @Test
  void timeTriggerFiresOnlyAtItsTime() {
    var trigger = new TimeTrigger(3);
    assertFalse(trigger.fires(2));
    assertTrue(trigger.fires(3));
    assertFalse(trigger.fires(4));
  }

  @Test
  void intervalTriggerFiresEveryPeriodFromStart() {
    var trigger = new IntervalTrigger(2, 5);
    assertFalse(trigger.fires(0));
    assertTrue(trigger.fires(2));
    assertFalse(trigger.fires(5));
    assertTrue(trigger.fires(7));
    assertTrue(trigger.fires(12));
  }

  @Test
  void intervalTriggerRejectsNonPositivePeriod() {
    assertThrows(IllegalArgumentException.class, () -> new IntervalTrigger(0, 0));
  }

  @Test
  void conditionTriggerFiresOnceWhenConditionHolds() {
    var condition = new AtomicBoolean(false);
    var trigger = new ConditionTrigger(condition::get);
    assertFalse(trigger.fires(0));
    condition.set(true);
    assertTrue(trigger.fires(1));
    assertFalse(trigger.fires(2));
  }
}
