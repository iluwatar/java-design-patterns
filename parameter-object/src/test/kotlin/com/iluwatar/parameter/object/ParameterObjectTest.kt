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
package com.iluwatar.parameter.`object`

// ABOUTME: Tests that ParameterObject correctly applies default values for sortBy and sortOrder.
// ABOUTME: Verifies defaults are used when constructor arguments are omitted via named parameters.

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

class ParameterObjectTest {

    @Test
    fun testForDefaultSortBy() {
        // Creating parameter object with default value for SortBy set
        val params = ParameterObject(type = "sneakers", sortOrder = SortOrder.DESC)

        assertEquals(ParameterObject.DEFAULT_SORT_BY, params.sortBy, "Default SortBy is not set.")
        logger.info {
            "SortBy Default parameter value is set during object creation as no value is passed."
        }
    }

    @Test
    fun testForDefaultSortOrder() {
        // Creating parameter object with default value for SortOrder set
        val params = ParameterObject(type = "sneakers", sortBy = "brand")

        assertEquals(ParameterObject.DEFAULT_SORT_ORDER, params.sortOrder, "Default SortOrder is not set.")
        logger.info {
            "SortOrder Default parameter value is set during object creation as no value is passed."
        }
    }
}
