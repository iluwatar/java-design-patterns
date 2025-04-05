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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/** Scene unit tests. */
class SceneTest {

  @Test
  void testGetBuffer() {
    try {
      var scene = new Scene();
      var field1 = Scene.class.getDeclaredField("current");
      field1.setAccessible(true);
      field1.set(scene, 0);
      var frameBuffers = new FrameBuffer[2];
      var frameBuffer = new FrameBuffer();
      frameBuffer.draw(0, 0);
      frameBuffers[0] = frameBuffer;
      var field2 = Scene.class.getDeclaredField("frameBuffers");
      field2.setAccessible(true);
      field2.set(scene, frameBuffers);
      assertEquals(frameBuffer, scene.getBuffer());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Fail to access private field.");
    }
  }

  @Test
  void testDraw() {
    try {
      var scene = new Scene();
      var field1 = Scene.class.getDeclaredField("current");
      var field2 = Scene.class.getDeclaredField("next");
      field1.setAccessible(true);
      field1.set(scene, 0);
      field2.setAccessible(true);
      field2.set(scene, 1);
      scene.draw(new ArrayList<>());
      assertEquals(1, field1.get(scene));
      assertEquals(0, field2.get(scene));
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Fail to access private field");
    }
  }
}
