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
 * The most complex game loop in this project.
 * <p>
 * This loop allows state and rendering to update independently but allows
 * the frames per second to rise to a higher rate than the game state updates per second.
 * The loop achieves this by calculating an interpolation value which is used to
 * estimate positioning in between game state updates.
 */
public class IndependentAnimation extends BallAnimation<InterpolatedGamePanel> {

  public IndependentAnimation() {
    super(new InterpolatedGamePanel());
  }

  /**
   * Independent Game Loop
   */
  @Override
  public void gameLoop() {

    final long ticksPerSecond = 25;
    final long skipTicks = 1000 / ticksPerSecond;
    final int maxFrameSkip = 5;

    long nextUpdateTick = System.currentTimeMillis();
    int loops;
    float interpolation;

    while (true) {
      loops = 0;
      while (System.currentTimeMillis() > nextUpdateTick && loops < maxFrameSkip) {
        updateState();
        nextUpdateTick += skipTicks;
        loops++;
      }
      interpolation =
          (System.currentTimeMillis() + skipTicks - nextUpdateTick)
              / (float) skipTicks;
      updateView(interpolation);
    }

  }

  /**
   * Set interpolation and render display.
   * @param interpolation Value used for estimating render between states.
   */
  public void updateView(float interpolation) {
    gPanel.setInterpolation(interpolation);
    gPanel.repaint();
  }

  /**
   * Entry point
   */
  public static void main(String[] args) {
    IndependentAnimation ballAnimation = new IndependentAnimation();
    ballAnimation.setVisible(true);
    ballAnimation.run();
  }
}
