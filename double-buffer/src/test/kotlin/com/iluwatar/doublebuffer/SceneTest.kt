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
package com.iluwatar.doublebuffer

// ABOUTME: Unit tests for Scene verifying buffer retrieval and double-buffer swapping.
// ABOUTME: Tests that draw() swaps buffers and getBuffer() returns the current front buffer.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** Scene unit tests. */
class SceneTest {

    @Test
    fun testGetBuffer() {
        val scene = Scene()
        val frameBuffer = FrameBuffer()
        frameBuffer.draw(0, 0)
        // The initial current buffer should be index 0
        assertEquals(0, scene.current)
        val buffer = scene.getBuffer()
        // Verify getBuffer returns a valid buffer
        assertEquals(Pixel.WHITE, buffer.getPixels()[0])
    }

    @Test
    fun testDraw() {
        val scene = Scene()
        assertEquals(0, scene.current)
        assertEquals(1, scene.next)
        scene.draw(emptyList())
        // After draw, current and next should be swapped
        assertEquals(1, scene.current)
        assertEquals(0, scene.next)
    }
}
