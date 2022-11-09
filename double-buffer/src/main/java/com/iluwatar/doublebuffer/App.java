/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.MutablePair;

/**
 * Double buffering is a term used to describe a device that has two buffers. The usage of multiple
 * buffers increases the overall throughput of a device and helps prevents bottlenecks. This example
 * shows using double buffer pattern on graphics. It is used to show one image or frame while a
 * separate frame is being buffered to be shown next. This method makes animations and games look
 * more realistic than the same done in a single buffer mode.
 */
@Slf4j
public class App {

  /**
   * Program main entry point.
   *
   * @param args runtime arguments
   */
  public static void main(String[] args) {
    final var scene = new Scene();
    var drawPixels1 = List.of(
        new MutablePair<>(1, 1),
        new MutablePair<>(5, 6),
        new MutablePair<>(3, 2)
    );
    scene.draw(drawPixels1);
    var buffer1 = scene.getBuffer();
    printBlackPixelCoordinate(buffer1);

    var drawPixels2 = List.of(
        new MutablePair<>(3, 7),
        new MutablePair<>(6, 1)
    );
    scene.draw(drawPixels2);
    var buffer2 = scene.getBuffer();
    printBlackPixelCoordinate(buffer2);
  }

  private static void printBlackPixelCoordinate(Buffer buffer) {
    StringBuilder log = new StringBuilder("Black Pixels: ");
    var pixels = buffer.getPixels();
    for (var i = 0; i < pixels.length; ++i) {
      if (pixels[i] == Pixel.BLACK) {
        var y = i / FrameBuffer.WIDTH;
        var x = i % FrameBuffer.WIDTH;
        log.append(" (").append(x).append(", ").append(y).append(")");
      }
    }
    LOGGER.info(log.toString());
  }
}
