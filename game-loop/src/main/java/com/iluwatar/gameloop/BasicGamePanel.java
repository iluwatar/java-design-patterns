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

import javax.swing.*;
import java.awt.*;

/**
 * The basic game panel used for most of the game loops shown in this package.
 * This class includes the game logic as well as the paint instructions
 * used to render the animation.
 */
public class BasicGamePanel extends JPanel {

  int ballX;
  int ballY;
  int lastBallX;
  int lastBallY;
  int ballWidth;
  int ballHeight;
  float ballXVel;
  float ballYVel;
  private double bounceDepreciation = 1;

  int lastDrawX;
  int lastDrawY;

  BasicGamePanel() {

    ballX = lastBallX = 100;
    ballY = lastBallY = 100;
    ballWidth = 25;
    ballHeight = 25;
    float ballSpeed = 100;
    double randomX = Math.random();
    randomX = randomX > 0.5 ? randomX - 0.25 : randomX + 0.25;
    double randomY = Math.random();
    randomY = randomY > 0.5 ? randomY - 0.25 : randomY + 0.25;
    ballXVel = (float) randomX * ballSpeed * 2 - ballSpeed;
    ballYVel = (float) randomY * ballSpeed * 2 - ballSpeed;
  }

  void update() {

    lastBallX = ballX;
    lastBallY = ballY;

    ballX += ballXVel;
    ballY += ballYVel;

    if (ballX + ballWidth / 2 >= getWidth()) {
      bounceDepreciation *= 0.999;
      ballXVel *= -bounceDepreciation;
      ballX = getWidth() - ballWidth / 2;

    } else if (ballX - ballWidth / 2 <= 0) {
      bounceDepreciation *= 0.999;
      ballXVel *= -bounceDepreciation;
      ballX = ballWidth / 2;
    }

    if (ballY + ballHeight / 2 >= getHeight()) {
      bounceDepreciation *= 0.999;
      ballYVel *= -bounceDepreciation;
      ballY = getHeight() - ballHeight / 2;

    } else if (ballY - ballHeight / 2 <= 0) {
      bounceDepreciation *= 0.999;
      ballYVel *= -bounceDepreciation;
      ballY = ballHeight / 2;
    }
  }

  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    g.setColor(Color.RED);
    g.fillOval(ballX, ballY, ballWidth, ballHeight);

    lastDrawX = ballX;
    lastDrawY = ballY;
  }
}