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

// ABOUTME: Unit tests for the Rect class verifying containment and intersection logic.
// ABOUTME: Tests cover contains() for points and intersects() for rectangle overlaps.
package com.iluwatar.spatialpartition

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** Testing Rect class. */
class RectTest {

    @Test
    fun containsTest() {
        val r = Rect(10.0, 10.0, 20.0, 20.0)
        val b1 = Bubble(2, 2, 1, 1)
        val b2 = Bubble(30, 30, 2, 1)
        // r contains b1 and not b2
        assertTrue(r.contains(b1))
        assertFalse(r.contains(b2))
    }

    @Test
    fun intersectsTest() {
        val r1 = Rect(10.0, 10.0, 20.0, 20.0)
        val r2 = Rect(15.0, 15.0, 20.0, 20.0)
        val r3 = Rect(50.0, 50.0, 20.0, 20.0)
        // r1 intersects r2 and not r3
        assertTrue(r1.intersects(r2))
        assertFalse(r1.intersects(r3))
    }
}
