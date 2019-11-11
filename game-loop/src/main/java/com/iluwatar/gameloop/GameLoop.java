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

package com.iluwatar.gameloop;

/**
 * Abstract class for GameLoop implemention class.
 */
public abstract class GameLoop {

  /**
   * {@code true} if the game is running. {@code false} if not.
   */
  protected GameStatus status;

  /**
   * Initialize game status to be stopped.
   */
  public GameLoop() {
    status = GameStatus.STOPPED;
  }

  /**
   * Run game loop.
   */
  public void run() {
    status = GameStatus.RUNNING;
    processGameLoop();
  }

  /**
   * Stop game loop.
   */
  public void stop() {
    status = GameStatus.STOPPED;
  }

  /**
   * Check if game is running or not.
   *
   * @return {@code true} if the game is running.
   */
  public boolean isRunning() {
    return status == GameStatus.RUNNING ? true : false;
  }

  /**
   * execute game loop logic.
   */
  protected abstract void processGameLoop();

}
