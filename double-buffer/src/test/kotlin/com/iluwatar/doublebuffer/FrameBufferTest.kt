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

// ABOUTME: Unit tests for FrameBuffer verifying pixel drawing, clearing, and retrieval.
// ABOUTME: Covers clearAll, clear, draw, and getPixels operations on the frame buffer.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** FrameBuffer unit test. */
class FrameBufferTest {

    @Test
    fun testClearAll() {
        val frameBuffer = FrameBuffer()
        frameBuffer.draw(0, 0)
        assertEquals(Pixel.BLACK, frameBuffer.getPixels()[0])
        frameBuffer.clearAll()
        assertEquals(Pixel.WHITE, frameBuffer.getPixels()[0])
    }

    @Test
    fun testClear() {
        val frameBuffer = FrameBuffer()
        frameBuffer.draw(0, 0)
        assertEquals(Pixel.BLACK, frameBuffer.getPixels()[0])
        frameBuffer.clear(0, 0)
        assertEquals(Pixel.WHITE, frameBuffer.getPixels()[0])
    }

    @Test
    fun testDraw() {
        val frameBuffer = FrameBuffer()
        frameBuffer.draw(0, 0)
        assertEquals(Pixel.BLACK, frameBuffer.getPixels()[0])
    }

    @Test
    fun testGetPixels() {
        val frameBuffer = FrameBuffer()
        val pixels = frameBuffer.getPixels()
        assertEquals(FrameBuffer.WIDTH * FrameBuffer.HEIGHT, pixels.size)
        // All pixels should be white initially
        pixels.forEach { assertEquals(Pixel.WHITE, it) }
        // Draw a pixel and verify it is reflected in getPixels
        frameBuffer.draw(0, 0)
        assertEquals(Pixel.BLACK, frameBuffer.getPixels()[0])
    }
}
