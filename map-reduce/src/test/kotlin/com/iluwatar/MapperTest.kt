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

// ABOUTME: Unit tests for the Mapper component of the MapReduce pattern.
// ABOUTME: Tests word counting and case-insensitive processing behavior.
package com.iluwatar

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MapperTest {

    @Test
    fun testMapSingleSentence() {
        val input = "Hello world hello"
        val result = Mapper.map(input)

        assertEquals(2, result["hello"])
        assertEquals(1, result["world"])
    }

    @Test
    fun testMapCaseInsensitivity() {
        val input = "HeLLo WoRLd hello WORLD"
        val result = Mapper.map(input)

        assertEquals(2, result["hello"])
        assertEquals(2, result["world"])
    }
}
