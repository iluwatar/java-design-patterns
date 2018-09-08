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
 * Foundation of the application. Uses a JPanel for display. Requires a loop, updateState and updateView.
 * The JPanel can be either the {{@link BasicGamePanel}} or the {{@link InterpolatedGamePanel}}.
 * The gameLoop used can be any implementation in the following classes:
 * - {{@link BasicAnimation}}
 * - {{@link LockedAnimation}}
 * - {{@link CappedAnimation}}
 * - {{@link IndependentAnimation}}
 * <p>
 * updateState and updateView are used throughout the examples except for {{@link IndependentAnimation}}
 * which requires additional tuning for the view/render functionality.
 */
public class BallAnimation extends JFrame {

  BasicGamePanel gPanel;

  public BallAnimation() {
    this(new BasicGamePanel());
  }

  /**
   * Ball Animation constructor in which a game panel is passed.
   */
  public BallAnimation(BasicGamePanel gamePanel) {

    super("Ball Animation");

    this.gPanel = gamePanel;

    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    cp.add(gPanel, BorderLayout.CENTER);
    setSize(500,500);
  }

  /**
   * Run the gameLoop method in a new Thread.
   */
  public void run() {

    Thread loopThread = new Thread(this::gameLoop);
    loopThread.start();
  }

  /**
   * Game Loop placeholder.
   */
  public void gameLoop() {}

  /**
   * Basic update state (used in all examples).
   */
  public void updateState() {
    gPanel.update();
  }

  /**
   * Basic render (used in all except {{@link IndependentAnimation}}).
   */
  public void updateView() {
    gPanel.repaint();
  }
}
