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

package com.iluwatar.twin;

/**
 * Twin pattern is a design pattern which provides a standard solution to simulate multiple
 * inheritance in Java.
 *
 * <p>In this example, the essence of the Twin pattern is the {@link BallItem} class and {@link
 * BallThread} class represent the twin objects to coordinate with each other (via the twin
 * reference) like a single class inheriting from {@link GameItem} and {@link Thread}.
 */

public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) throws Exception {

    var ballItem = new BallItem();
    var ballThread = new BallThread();

    ballItem.setTwin(ballThread);
    ballThread.setTwin(ballItem);

    ballThread.start();

    waiting();

    ballItem.click();

    waiting();

    ballItem.click();

    waiting();

    // exit
    ballThread.stopMe();
  }

  private static void waiting() throws Exception {
    Thread.sleep(750);
  }
}
