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

// ABOUTME: Unit tests for the Bubble class verifying movement, collision detection, and popping.
// ABOUTME: Tests cover move(), touches(), pop(), and handleCollision() methods.
package com.iluwatar.spatialpartition

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** Testing methods in Bubble class. */
class BubbleTest {

    @Test
    fun moveTest() {
        val b = Bubble(10, 10, 1, 2)
        val initialX = b.coordinateX
        val initialY = b.coordinateY
        b.move()
        // change in x and y < |2|
        assertTrue(b.coordinateX - initialX < 2 && b.coordinateX - initialX > -2)
        assertTrue(b.coordinateY - initialY < 2 && b.coordinateY - initialY > -2)
    }

    @Test
    fun touchesTest() {
        val b1 = Bubble(0, 0, 1, 2)
        val b2 = Bubble(1, 1, 2, 1)
        val b3 = Bubble(10, 10, 3, 1)
        // b1 touches b2 but not b3
        assertTrue(b1.touches(b2))
        assertFalse(b1.touches(b3))
    }

    @Test
    fun popTest() {
        val b1 = Bubble(10, 10, 1, 2)
        val b2 = Bubble(0, 0, 2, 2)
        val bubbles = mutableMapOf<Int, Bubble>()
        bubbles[1] = b1
        bubbles[2] = b2
        b1.pop(bubbles)
        // after popping, bubble no longer in hashMap containing all bubbles
        assertNull(bubbles[1])
        assertNotNull(bubbles[2])
    }

    @Test
    fun handleCollisionTest() {
        val b1 = Bubble(0, 0, 1, 2)
        val b2 = Bubble(1, 1, 2, 1)
        val b3 = Bubble(10, 10, 3, 1)
        val bubbles = mutableMapOf<Int, Bubble>()
        bubbles[1] = b1
        bubbles[2] = b2
        bubbles[3] = b3
        val bubblesToCheck = mutableListOf<Point<*>>()
        bubblesToCheck.add(b2)
        bubblesToCheck.add(b3)
        b1.handleCollision(bubblesToCheck, bubbles)
        // b1 touches b2 and not b3, so b1, b2 will be popped
        assertNull(bubbles[1])
        assertNull(bubbles[2])
        assertNotNull(bubbles[3])
    }
}
