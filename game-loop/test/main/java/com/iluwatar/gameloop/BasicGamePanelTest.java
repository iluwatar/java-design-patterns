/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.gameloop;


import org.junit.jupiter.api.Test;

import javax.swing.JPanel;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test for BasicGamePanel
 */
public class BasicGamePanelTest {

  @Test
  void isPanel() {
    BasicGamePanel panel = new BasicGamePanel();
    assertThat(panel, instanceOf(JPanel.class));
  }

  @Test
  void testValuesUpdated() {
    BasicGamePanel panel = new BasicGamePanel();
    int ballX = panel.ballX;
    int ballY = panel.ballY;
    float ballXVel = panel.ballXVel;
    float ballYVel = panel.ballYVel;

    panel.update();

    assertThat("Check ball X was updated", panel.ballX == ballX + ballXVel);
    assertThat("Check ball Y was updated", panel.ballY == ballY + ballYVel);
  }

  @Test
  void testDraw() {

    BasicGamePanel panel = new BasicGamePanel();
    int ballX = panel.ballX;
    int ballY = panel.ballY;

    panel.update();

    int lastDrawX = panel.lastDrawX;
    int lastDrawY = panel.lastDrawY;

    assertThat("Check X was drawn", lastDrawX == ballX);
    assertThat("Check Y was drawn", lastDrawY == ballY);
  }
}
