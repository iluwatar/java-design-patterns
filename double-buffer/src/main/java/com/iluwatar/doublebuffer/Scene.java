/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.doublebuffer;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scene class. Render the output frame.
 */
public class Scene {

  private static final Logger LOGGER = LoggerFactory.getLogger(Scene.class);

  private Buffer[] frameBuffers;

  private int current;

  private int next;

  /**
   * Constructor of Scene.
   */
  public Scene() {
    frameBuffers = new FrameBuffer[2];
    frameBuffers[0] = new FrameBuffer();
    frameBuffers[1] = new FrameBuffer();
    current = 0;
    next = 1;
  }

  /**
   * Draw the next frame.
   *
   * @param coordinateList list of pixels of which the color should be black
   */
  public void draw(List<? extends Pair<Integer, Integer>> coordinateList) {
    LOGGER.info("Start drawing next frame");
    LOGGER.info("Current buffer: " + current + " Next buffer: " + next);
    frameBuffers[next].clearAll();
    coordinateList.forEach(coordinate -> {
      var x = coordinate.getKey();
      var y = coordinate.getValue();
      frameBuffers[next].draw(x, y);
    });
    LOGGER.info("Swap current and next buffer");
    swap();
    LOGGER.info("Finish swapping");
    LOGGER.info("Current buffer: " + current + " Next buffer: " + next);
  }

  public Buffer getBuffer() {
    LOGGER.info("Get current buffer: " + current);
    return frameBuffers[current];
  }

  private void swap() {
    current = current ^ next;
    next = current ^ next;
    current = current ^ next;
  }

}
