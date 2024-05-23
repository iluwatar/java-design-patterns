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
package com.iluwatar.templatemethod;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;

/**
 * HalflingThiefTest
 *
 */
class HalflingThiefTest {

  /**
   * Verify if the thief uses the provided stealing method
   */
  @Test
  void testSteal() {
    final var method = spy(StealingMethod.class);
    final var thief = new HalflingThief(method);

    thief.steal();
    verify(method).steal();
    String target = verify(method).pickTarget();
    verify(method).confuseTarget(target);
    verify(method).stealTheItem(target);

    verifyNoMoreInteractions(method);
  }

  /**
   * Verify if the thief uses the provided stealing method, and the new method after changing it
   */
  @Test
  void testChangeMethod() {
    final var initialMethod = spy(StealingMethod.class);
    final var thief = new HalflingThief(initialMethod);

    thief.steal();
    verify(initialMethod).steal();
    String target = verify(initialMethod).pickTarget();
    verify(initialMethod).confuseTarget(target);
    verify(initialMethod).stealTheItem(target);

    final var newMethod = spy(StealingMethod.class);
    thief.changeMethod(newMethod);

    thief.steal();
    verify(newMethod).steal();
    String newTarget = verify(newMethod).pickTarget();
    verify(newMethod).confuseTarget(newTarget);
    verify(newMethod).stealTheItem(newTarget);
    verifyNoMoreInteractions(initialMethod, newMethod);
  }
}