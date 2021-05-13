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

package com.iluwatar.updatemethod;

import lombok.extern.slf4j.Slf4j;

/**
 * This pattern simulate a collection of independent objects by telling each to
 * process one frame of behavior at a time. The game world maintains a collection
 * of objects. Each object implements an update method that simulates one frame of
 * the object’s behavior. Each frame, the game updates every object in the collection.
 */
@Slf4j
public class App {

  private static final int GAME_RUNNING_TIME = 2000;

  /**
   * Program entry point.
   * @param args runtime arguments
   */
  public static void main(String[] args) {
    try {
      var world = new World();
      var skeleton1 = new Skeleton(1, 10);
      var skeleton2 = new Skeleton(2, 70);
      var statue = new Statue(3, 20);
      world.addEntity(skeleton1);
      world.addEntity(skeleton2);
      world.addEntity(statue);
      world.run();
      Thread.sleep(GAME_RUNNING_TIME);
      world.stop();
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
