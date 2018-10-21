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

/**
 * This loop allows the state to be updated at a constant speed per second.
 * <p>
 * The view is updated separately meaning frames can be skipped if needed on slow hardware.
 * A limit of this is that the frames per second can never increase more than the
 * number of times the game state is set to update per second. This means performance
 * potential is wasted when running on faster hardware.
 */
public class CappedAnimation extends BallAnimation<BasicGamePanel> {

  public CappedAnimation() {
    super(new BasicGamePanel());
  }

  /**
   * Capped Game Loop
   */
  @Override
  public void gameLoop() {

    final long ticksPerSecond = 50;
    final long skipTicks = 1000 / ticksPerSecond;
    final int maxFrameSkip = 10;

    long nextUpdateTick = System.currentTimeMillis();
    int loops;

    while (true) {
      loops = 0;
      while (System.currentTimeMillis() > nextUpdateTick && loops < maxFrameSkip) {
        updateState();
        nextUpdateTick += skipTicks;
        loops++;
      }
      updateView();
    }
  }

  /**
   * Entry point
   */
  public static void main(String[] args) {
    BallAnimation ballAnimation = new CappedAnimation();
    ballAnimation.setVisible(true);
    ballAnimation.run();
  }

}
