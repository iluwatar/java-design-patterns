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

// ABOUTME: Entry point demonstrating the Double Buffer pattern for frame rendering.
// ABOUTME: Draws pixels to a scene using double buffering and logs black pixel coordinates.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Double buffering is a term used to describe a device that has two buffers. The usage of multiple
 * buffers increases the overall throughput of a device and helps prevents bottlenecks. This example
 * shows using double buffer pattern on graphics. It is used to show one image or frame while a
 * separate frame is being buffered to be shown next. This method makes animations and games look
 * more realistic than the same done in a single buffer mode.
 */
fun main() {
    val scene = Scene()
    val drawPixels1 = listOf(1 to 1, 5 to 6, 3 to 2)
    scene.draw(drawPixels1)
    val buffer1 = scene.getBuffer()
    printBlackPixelCoordinate(buffer1)

    val drawPixels2 = listOf(3 to 7, 6 to 1)
    scene.draw(drawPixels2)
    val buffer2 = scene.getBuffer()
    printBlackPixelCoordinate(buffer2)
}

private fun printBlackPixelCoordinate(buffer: Buffer) {
    val log = StringBuilder("Black Pixels: ")
    val pixels = buffer.getPixels()
    for (i in pixels.indices) {
        if (pixels[i] == Pixel.BLACK) {
            val y = i / FrameBuffer.WIDTH
            val x = i % FrameBuffer.WIDTH
            log.append(" ($x, $y)")
        }
    }
    logger.info { log.toString() }
}
