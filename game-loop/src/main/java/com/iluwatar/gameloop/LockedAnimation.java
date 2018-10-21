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
package com.iluwatar.gameloop;

import static java.lang.Thread.sleep;

/**
 * This game loop allows state and frames to be locked to a single value.
 * This means that both update in the same loop and the loop will wait if there is
 * time until the next update.
 * <p>
 * On slower hardware, the desired frames per second may not be reachable and
 * cause both the state and render to slow together. As in {{@link BasicAnimation}},
 * the game state will update at different speeds depending on the speed of the hardware
 * and cause inconsistency.
 */
public class LockedAnimation extends BallAnimation<BasicGamePanel> {

  public LockedAnimation() {
    super(new BasicGamePanel());
  }

  /**
   * Locked game loop.
   */
  @Override
  public void gameLoop() {

    final int framesPerSecond = 25;
    final long skipTicks = 1000 / framesPerSecond;

    long sleepPeriod;
    long nextUpdateTick = System.currentTimeMillis();

    while (true) {

      updateState();
      updateView();

      nextUpdateTick += skipTicks;
      sleepPeriod = nextUpdateTick - System.currentTimeMillis();

      if (sleepPeriod >= 0) {
        try {
          sleep(sleepPeriod);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Entry point
   */
  public static void main(String[] args) {
    BallAnimation ballAnimation = new LockedAnimation();
    ballAnimation.setVisible(true);
    ballAnimation.run();
  }
}
