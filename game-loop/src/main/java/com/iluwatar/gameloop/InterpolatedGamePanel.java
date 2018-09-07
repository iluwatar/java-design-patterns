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

import java.awt.*;

/**
 * A more complex game panel used for most of the {{@link IndependentAnimation}} game loop.
 * This game panel takes into account interpolation when rendering.
 */
public class InterpolatedGamePanel extends BasicGamePanel {

  private float interpolation;

  @Override
  public void setInterpolation(float interpolation) {
    this.interpolation = interpolation;
  }

  float getInterpolation() {
    return interpolation;
  }

  @Override
  protected void paintComponent(Graphics g) {

    // Clearing previous draw by overwriting it
    g.setColor(getBackground());
    g.fillRect(lastDrawX - 1, lastDrawY - 1, ballWidth + 2, ballHeight + 2);

    g.setColor(Color.RED);
    int drawX = (int) ((ballX - lastBallX) * interpolation + lastBallX - ballWidth / 2);
    int drawY = (int) ((ballY - lastBallY) * interpolation + lastBallY - ballWidth / 2);
    g.fillOval(drawX, drawY, ballWidth, ballHeight);

    lastDrawX = drawX;
    lastDrawY = drawY;
  }
}
