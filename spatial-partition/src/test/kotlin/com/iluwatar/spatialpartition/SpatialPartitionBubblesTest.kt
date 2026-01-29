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

// ABOUTME: Unit tests for SpatialPartitionBubbles class verifying quadtree-based collision handling.
// ABOUTME: Tests that colliding bubbles are correctly popped while non-colliding bubbles remain.
package com.iluwatar.spatialpartition

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/** Testing SpatialPartition_Bubbles class. */
class SpatialPartitionBubblesTest {

    @Test
    fun handleCollisionsUsingQtTest() {
        val b1 = Bubble(10, 10, 1, 3)
        val b2 = Bubble(5, 5, 2, 1)
        val b3 = Bubble(9, 9, 3, 1)
        val b4 = Bubble(8, 8, 4, 2)
        val bubbles = mutableMapOf<Int, Bubble>()
        bubbles[1] = b1
        bubbles[2] = b2
        bubbles[3] = b3
        bubbles[4] = b4
        val r = Rect(10.0, 10.0, 20.0, 20.0)
        val qt = QuadTree(r, 4)
        qt.insert(b1)
        qt.insert(b2)
        qt.insert(b3)
        qt.insert(b4)
        val sp = SpatialPartitionBubbles(bubbles, qt)
        sp.handleCollisionsUsingQt(b1)
        // b1 touches b3 and b4 but not b2 - so b1,b3,b4 get popped
        assertNull(bubbles[1])
        assertNotNull(bubbles[2])
        assertNull(bubbles[3])
        assertNull(bubbles[4])
    }
}
