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
package com.iluwatar.flyweight

// ABOUTME: Tests for AlchemistShop verifying shelf sizes and flyweight instance sharing.
// ABOUTME: Asserts that 13 total potions on both shelves share only 5 unique object instances.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/** AlchemistShopTest */
class AlchemistShopTest {
    @Test
    fun testShop() {
        val shop = AlchemistShop()

        val bottomShelf = shop.bottomShelf
        assertNotNull(bottomShelf)
        assertEquals(5, bottomShelf.size)

        val topShelf = shop.topShelf
        assertNotNull(topShelf)
        assertEquals(8, topShelf.size)

        val allPotions = topShelf + bottomShelf

        // There are 13 potion instances, but only 5 unique instance types
        assertEquals(13, allPotions.size)
        assertEquals(5, allPotions.toSet().size)
    }
}
