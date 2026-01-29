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

package com.iluwatar.lazy.loading

// ABOUTME: Abstract base test class for testing lazy holder implementations.
// ABOUTME: Verifies that the heavy field is not instantiated until getHeavy is called.

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTimeout
import org.junit.jupiter.api.Test
import java.time.Duration.ofMillis

/**
 * AbstractHolderTest
 */
abstract class AbstractHolderTest {

    /**
     * Get the internal state of the holder value
     *
     * @return The internal value
     */
    abstract fun getInternalHeavyValue(): Heavy?

    /**
     * Request a lazy loaded [Heavy] object from the holder.
     *
     * @return The lazy loaded [Heavy] object
     */
    abstract fun getHeavy(): Heavy

    /**
     * This test shows that the heavy field is not instantiated until the method getHeavy is called
     */
    @Test
    fun testGetHeavy() {
        assertTimeout(ofMillis(3000)) {
            assertNull(getInternalHeavyValue())
            assertNotNull(getHeavy())
            assertNotNull(getInternalHeavyValue())
            assertSame(getHeavy(), getInternalHeavyValue())
        }
    }
}
