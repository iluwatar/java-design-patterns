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

package com.iluwatar.doubledispatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for Rectangle
 */
public class RectangleTest {

  /**
   * Test if the values passed through the constructor matches the values fetched from the getters
   */
  @Test
  public void testConstructor() {
    final var rectangle = new Rectangle(1, 2, 3, 4);
    assertEquals(1, rectangle.getLeft());
    assertEquals(2, rectangle.getTop());
    assertEquals(3, rectangle.getRight());
    assertEquals(4, rectangle.getBottom());
  }

  /**
   * Test if the values passed through the constructor matches the values in the {@link
   * #toString()}
   */
  @Test
  public void testToString() throws Exception {
    final var rectangle = new Rectangle(1, 2, 3, 4);
    assertEquals("[1,2,3,4]", rectangle.toString());
  }

  /**
   * Test if the {@link Rectangle} class can detect if it intersects with another rectangle.
   */
  @Test
  public void testIntersection() {
    assertTrue(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(0, 0, 1, 1)));
    assertTrue(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(-1, -5, 7, 8)));
    assertFalse(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(2, 2, 3, 3)));
    assertFalse(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(-2, -2, -1, -1)));
  }

}
