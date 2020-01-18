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

package com.iluwatar.spatialpartition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Testing Rect class.
 */

class RectTest {

  @Test
  void containsTest() {
    var r = new Rect(10, 10, 20, 20);
    var b1 = new Bubble(2, 2, 1, 1);
    var b2 = new Bubble(30, 30, 2, 1);
    //r contains b1 and not b2
    assertTrue(r.contains(b1));
    assertFalse(r.contains(b2));
  }

  @Test
  void intersectsTest() {
    var r1 = new Rect(10, 10, 20, 20);
    var r2 = new Rect(15, 15, 20, 20);
    var r3 = new Rect(50, 50, 20, 20);
    //r1 intersects r2 and not r3
    assertTrue(r1.intersects(r2));
    assertFalse(r1.intersects(r3));
  }
}
