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

// ABOUTME: Unit tests for the LotteryNumbers value object.
// ABOUTME: Verifies number creation, immutability, and equality.
package com.iluwatar.hexagonal.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Unit tests for [LotteryNumbers]
 */
class LotteryNumbersTest {

    @Test
    fun testGivenNumbers() {
        val numbers = LotteryNumbers.create(setOf(1, 2, 3, 4))
        assertEquals(4, numbers.getNumbers().size)
        assertTrue(numbers.getNumbers().contains(1))
        assertTrue(numbers.getNumbers().contains(2))
        assertTrue(numbers.getNumbers().contains(3))
        assertTrue(numbers.getNumbers().contains(4))
    }

    @Test
    fun testNumbersCantBeModified() {
        val numbers = LotteryNumbers.create(setOf(1, 2, 3, 4))
        assertThrows(UnsupportedOperationException::class.java) {
            (numbers.getNumbers() as MutableSet).add(5)
        }
    }

    @Test
    fun testRandomNumbers() {
        val numbers = LotteryNumbers.createRandom()
        assertEquals(LotteryNumbers.NUM_NUMBERS, numbers.getNumbers().size)
    }

    @Test
    fun testEquals() {
        val numbers1 = LotteryNumbers.create(setOf(1, 2, 3, 4))
        val numbers2 = LotteryNumbers.create(setOf(1, 2, 3, 4))
        assertEquals(numbers1, numbers2)
        val numbers3 = LotteryNumbers.create(setOf(11, 12, 13, 14))
        assertNotEquals(numbers1, numbers3)
    }
}
