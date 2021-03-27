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

package com.iluwatar.gameloop;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * GameLoop unit test class.
 */
class GameLoopTest {

  private GameLoop gameLoop;

  /**
   * Create mock implementation of GameLoop.
   */
  @BeforeEach
  void setup() {
    gameLoop = new GameLoop() {
      @Override
      protected void processGameLoop() {
      }
    };
  }

  @AfterEach
  void tearDown() {
    gameLoop = null;
  }

  @Test
  void testRun() {
    gameLoop.run();
    Assertions.assertEquals(GameStatus.RUNNING, gameLoop.status);
  }

  @Test
  void testStop() {
    gameLoop.stop();
    Assertions.assertEquals(GameStatus.STOPPED, gameLoop.status);
  }

  @Test
  void testIsGameRunning() {
    assertFalse(gameLoop.isGameRunning());
  }
}
