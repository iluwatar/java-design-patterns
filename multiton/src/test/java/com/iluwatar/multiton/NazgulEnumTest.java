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

package com.iluwatar.multiton;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author anthony
 *
 */
class NazgulEnumTest {

  /**
   * Check that multiple calls to any one of the instances in the multiton returns 
   * only that one particular instance, and do that for all instances in multiton
   */
  @Test
  public void testTheSameObjectIsReturnedWithMultipleCalls() {
    for (int i = 0; i < NazgulEnum.values().length; i++) {
      NazgulEnum instance1 = NazgulEnum.values()[i];
      NazgulEnum instance2 = NazgulEnum.values()[i];
      NazgulEnum instance3 = NazgulEnum.values()[i];
      assertSame(instance1, instance2);
      assertSame(instance1, instance3);
      assertSame(instance2, instance3);
    }
  }
}
