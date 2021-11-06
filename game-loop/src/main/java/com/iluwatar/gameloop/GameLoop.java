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

import java.security.SecureRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for GameLoop implementation class.
 */
public abstract class GameLoop {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected volatile GameStatus status;

  protected final GameController controller;

  private Thread gameThread;

  /**
   * Initialize game status to be stopped.
   */
  public GameLoop() {
    controller = new GameController();
    status = GameStatus.STOPPED;
  }

  /**
   * Run game loop.
   */
  public void run() {
    status = GameStatus.RUNNING;
    gameThread = new Thread(this::processGameLoop);
    gameThread.start();
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
  public boolean isGameRunning() {
    return status == GameStatus.RUNNING;
  }

  /**
   * Handle any user input that has happened since the last call. In order to
   * simulate the situation in real-life game, here we add a random time lag.
   * The time lag ranges from 50 ms to 250 ms.
   */
  protected void processInput() {
    try {
      var lag = new SecureRandom().nextInt(200) + 50;
      Thread.sleep(lag);
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * Render game frames to screen. Here we print bullet position to simulate
   * this process.
   */
  protected void render() {
    var position = controller.getBulletPosition();
    logger.info("Current bullet position: " + position);
  }

  /**
   * execute game loop logic.
   */
  protected abstract void processGameLoop();

}
