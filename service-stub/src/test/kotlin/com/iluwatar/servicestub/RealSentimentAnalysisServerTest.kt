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

package com.iluwatar.servicestub

// ABOUTME: Tests for RealSentimentAnalysisServer using deterministic supplier functions.
// ABOUTME: Verifies that each sentiment value (0, 1, 2) maps to the correct classification.

import java.util.function.Supplier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RealSentimentAnalysisServerTest {

    @Test
    fun testPositiveSentiment() {
        val server = RealSentimentAnalysisServer(Supplier { 0 })
        assertEquals("Positive", server.analyzeSentiment("Test"))
    }

    @Test
    fun testNegativeSentiment() {
        val server = RealSentimentAnalysisServer(Supplier { 1 })
        assertEquals("Negative", server.analyzeSentiment("Test"))
    }

    @Test
    fun testNeutralSentiment() {
        val server = RealSentimentAnalysisServer(Supplier { 2 })
        assertEquals("Neutral", server.analyzeSentiment("Test"))
    }
}
