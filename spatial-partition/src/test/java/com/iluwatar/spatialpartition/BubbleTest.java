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
package com.iluwatar.spatialpartition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Testing methods in Bubble class.
 */

class BubbleTest {

  @Test
  void moveTest() {
    var b = new Bubble(10, 10, 1, 2);
    var initialX = b.coordinateX;
    var initialY = b.coordinateY;
    b.move();
    //change in x and y < |2|
    assertTrue(b.coordinateX - initialX < 2 && b.coordinateX - initialX > -2);
    assertTrue(b.coordinateY - initialY < 2 && b.coordinateY - initialY > -2);
  }

  @Test
  void touchesTest() {
    var b1 = new Bubble(0, 0, 1, 2);
    var b2 = new Bubble(1, 1, 2, 1);
    var b3 = new Bubble(10, 10, 3, 1);
    //b1 touches b2 but not b3
    assertTrue(b1.touches(b2));
    assertFalse(b1.touches(b3));
  }

  @Test
  void popTest() {
    var b1 = new Bubble(10, 10, 1, 2);
    var b2 = new Bubble(0, 0, 2, 2);
    var bubbles = new HashMap<Integer, Bubble>();
    bubbles.put(1, b1);
    bubbles.put(2, b2);
    b1.pop(bubbles);
    //after popping, bubble no longer in hashMap containing all bubbles
    assertNull(bubbles.get(1));
    assertNotNull(bubbles.get(2));
  }

  @Test
  void handleCollisionTest() {
    var b1 = new Bubble(0, 0, 1, 2);
    var b2 = new Bubble(1, 1, 2, 1);
    var b3 = new Bubble(10, 10, 3, 1);
    var bubbles = new HashMap<Integer, Bubble>();
    bubbles.put(1, b1);
    bubbles.put(2, b2);
    bubbles.put(3, b3);
    var bubblesToCheck = new ArrayList<Point>();
    bubblesToCheck.add(b2);
    bubblesToCheck.add(b3);
    b1.handleCollision(bubblesToCheck, bubbles);
    //b1 touches b2 and not b3, so b1, b2 will be popped
    assertNull(bubbles.get(1));
    assertNull(bubbles.get(2));
    assertNotNull(bubbles.get(3));
  }
}
