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
package com.iluwatar.`object`.pool

// ABOUTME: Tests for the OliphauntPool verifying checkout, checkin, and object reuse behavior.
// ABOUTME: Validates both sequential and concurrent pool usage with timeout constraints.

import java.time.Duration.ofMillis
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTimeout
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** OliphauntPoolTest. */
class OliphauntPoolTest {

    /**
     * Use the same object 100 times subsequently. This should not take much time since the heavy
     * object instantiation is done only once. Verify if we get the same object each time.
     */
    @Test
    fun testSubsequentCheckinCheckout() {
        assertTimeout(
            ofMillis(5000)
        ) {
            val pool = OliphauntPool()
            assertEquals("Pool available=0 inUse=0", pool.toString())

            val expectedOliphaunt = pool.checkOut()
            assertEquals("Pool available=0 inUse=1", pool.toString())

            pool.checkIn(expectedOliphaunt)
            assertEquals("Pool available=1 inUse=0", pool.toString())

            for (i in 0 until 100) {
                val oliphaunt = pool.checkOut()
                assertEquals("Pool available=0 inUse=1", pool.toString())
                assertSame(expectedOliphaunt, oliphaunt)
                assertEquals(expectedOliphaunt.id, oliphaunt.id)
                assertEquals(expectedOliphaunt.toString(), oliphaunt.toString())

                pool.checkIn(oliphaunt)
                assertEquals("Pool available=1 inUse=0", pool.toString())
            }
        }
    }

    /**
     * Use the same object 100 times subsequently. This should not take much time since the heavy
     * object instantiation is done only once. Verify if we get the same object each time.
     */
    @Test
    fun testConcurrentCheckinCheckout() {
        assertTimeout(
            ofMillis(5000)
        ) {
            val pool = OliphauntPool()
            assertEquals(pool.toString(), "Pool available=0 inUse=0")

            val firstOliphaunt = pool.checkOut()
            assertEquals(pool.toString(), "Pool available=0 inUse=1")

            val secondOliphaunt = pool.checkOut()
            assertEquals(pool.toString(), "Pool available=0 inUse=2")

            assertNotSame(firstOliphaunt, secondOliphaunt)
            assertEquals(firstOliphaunt.id + 1, secondOliphaunt.id)

            // After checking in the second, we should get the same when checking out a new oliphaunt
            // ...
            pool.checkIn(secondOliphaunt)
            assertEquals(pool.toString(), "Pool available=1 inUse=1")

            val oliphaunt3 = pool.checkOut()
            assertEquals(pool.toString(), "Pool available=0 inUse=2")
            assertSame(secondOliphaunt, oliphaunt3)

            // ... and the same applies for the first one
            pool.checkIn(firstOliphaunt)
            assertEquals(pool.toString(), "Pool available=1 inUse=1")

            val oliphaunt4 = pool.checkOut()
            assertEquals(pool.toString(), "Pool available=0 inUse=2")
            assertSame(firstOliphaunt, oliphaunt4)

            // When both oliphaunt return to the pool, we should still get the same instances
            pool.checkIn(firstOliphaunt)
            assertEquals(pool.toString(), "Pool available=1 inUse=1")

            pool.checkIn(secondOliphaunt)
            assertEquals(pool.toString(), "Pool available=2 inUse=0")

            // The order of the returned instances is not determined, so just put them in a list
            // and verify if both expected instances are in there.
            val oliphaunts = listOf(pool.checkOut(), pool.checkOut())
            assertEquals(pool.toString(), "Pool available=0 inUse=2")
            assertTrue(oliphaunts.contains(firstOliphaunt))
            assertTrue(oliphaunts.contains(secondOliphaunt))
        }
    }
}
