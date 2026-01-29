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
package com.iluwatar.servant

// ABOUTME: Tests for the Queen class verifying mood changes under various conditions.
// ABOUTME: Covers combinations of flirty/not-flirty and complimented/not states.

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** QueenTest */
class QueenTest {

    @Test
    fun testNotFlirtyUncomplemented() {
        val queen = Queen()
        queen.setFlirtiness(false)
        queen.changeMood()
        assertFalse(queen.getMood())
    }

    @Test
    fun testNotFlirtyComplemented() {
        val queen = Queen()
        queen.setFlirtiness(false)
        queen.receiveCompliments()
        queen.changeMood()
        assertFalse(queen.getMood())
    }

    @Test
    fun testFlirtyUncomplemented() {
        val queen = Queen()
        queen.changeMood()
        assertFalse(queen.getMood())
    }

    @Test
    fun testFlirtyComplemented() {
        val queen = Queen()
        queen.receiveCompliments()
        queen.changeMood()
        assertTrue(queen.getMood())
    }
}
