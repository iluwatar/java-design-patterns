/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.strategy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;

/**
 * Date: 12/29/15 - 10:50 PM.
 *
 * @author Jeroen Meulemeester
 */
public class DragonSlayerTest {

  /**
   * Verify if the dragon slayer uses the strategy during battle.
   */
  @Test
  void testGoToBattle() {
    final var strategy = mock(DragonSlayingStrategy.class);
    final var dragonSlayer = new DragonSlayer(strategy);

    dragonSlayer.goToBattle();
    verify(strategy).execute();
    verifyNoMoreInteractions(strategy);
  }

  /**
   * Verify if the dragon slayer uses the new strategy during battle after a change of strategy.
   */
  @Test
  void testChangeStrategy() {
    final var initialStrategy = mock(DragonSlayingStrategy.class);
    final var dragonSlayer = new DragonSlayer(initialStrategy);

    dragonSlayer.goToBattle();
    verify(initialStrategy).execute();

    final var newStrategy = mock(DragonSlayingStrategy.class);
    dragonSlayer.changeStrategy(newStrategy);

    dragonSlayer.goToBattle();
    verify(newStrategy).execute();

    verifyNoMoreInteractions(initialStrategy, newStrategy);
  }
}