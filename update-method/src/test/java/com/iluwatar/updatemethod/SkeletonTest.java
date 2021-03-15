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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkeletonTest {

  private static Skeleton skeleton;

  @BeforeAll
  public static void setup() {
    skeleton = new Skeleton(1);
  }

  @AfterAll
  public static void tearDown() {
    skeleton = null;
  }

  @Test
  void testUpdateForPatrollingLeft() {
    skeleton.patrollingLeft = true;
    skeleton.setPosition(50);
    skeleton.update();
    assertEquals(49, skeleton.getPosition());
  }

  @Test
  void testUpdateForPatrollingRight() {
    skeleton.patrollingLeft = false;
    skeleton.setPosition(50);
    skeleton.update();
    assertEquals(51, skeleton.getPosition());
  }

  @Test
  void testUpdateForReverseDirectionFromLeftToRight() {
    skeleton.patrollingLeft = true;
    skeleton.setPosition(1);
    skeleton.update();
    assertEquals(0, skeleton.getPosition());
    assertFalse(skeleton.patrollingLeft);
  }

  @Test
  void testUpdateForReverseDirectionFromRightToLeft() {
    skeleton.patrollingLeft = false;
    skeleton.setPosition(99);
    skeleton.update();
    assertEquals(100, skeleton.getPosition());
    assertTrue(skeleton.patrollingLeft);
  }
}
