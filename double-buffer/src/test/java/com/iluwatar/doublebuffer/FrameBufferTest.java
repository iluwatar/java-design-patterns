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

package com.iluwatar.doublebuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * FrameBuffer unit test.
 */
class FrameBufferTest {

  @Test
  void testClearAll() {
    try {
      var field = FrameBuffer.class.getDeclaredField("pixels");
      var pixels = new Pixel[FrameBuffer.HEIGHT * FrameBuffer.WIDTH];
      Arrays.fill(pixels, Pixel.WHITE);
      pixels[0] = Pixel.BLACK;
      var frameBuffer = new FrameBuffer();
      field.setAccessible(true);
      field.set(frameBuffer, pixels);
      frameBuffer.clearAll();
      assertEquals(Pixel.WHITE, frameBuffer.getPixels()[0]);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Fail to modify field access.");
    }
  }

  @Test
  void testClear() {
    try {
      var field = FrameBuffer.class.getDeclaredField("pixels");
      var pixels = new Pixel[FrameBuffer.HEIGHT * FrameBuffer.WIDTH];
      Arrays.fill(pixels, Pixel.WHITE);
      pixels[0] = Pixel.BLACK;
      var frameBuffer = new FrameBuffer();
      field.setAccessible(true);
      field.set(frameBuffer, pixels);
      frameBuffer.clear(0, 0);
      assertEquals(Pixel.WHITE, frameBuffer.getPixels()[0]);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Fail to modify field access.");
    }
  }

  @Test
  void testDraw() {
    var frameBuffer = new FrameBuffer();
    frameBuffer.draw(0, 0);
    assertEquals(Pixel.BLACK, frameBuffer.getPixels()[0]);
  }

  @Test
  void testGetPixels() {
    try {
      var field = FrameBuffer.class.getDeclaredField("pixels");
      var pixels = new Pixel[FrameBuffer.HEIGHT * FrameBuffer.WIDTH];
      Arrays.fill(pixels, Pixel.WHITE);
      pixels[0] = Pixel.BLACK;
      var frameBuffer = new FrameBuffer();
      field.setAccessible(true);
      field.set(frameBuffer, pixels);
      assertEquals(pixels, frameBuffer.getPixels());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Fail to modify field access.");
    }
  }

}
