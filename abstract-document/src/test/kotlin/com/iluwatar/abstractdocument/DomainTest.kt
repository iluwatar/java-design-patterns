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

// ABOUTME: Tests for Part and Car domain entities verifying property access via traits.
// ABOUTME: Validates that HasType, HasModel, HasPrice, and HasParts interfaces work correctly.
package com.iluwatar.abstractdocument

import com.iluwatar.abstractdocument.domain.Car
import com.iluwatar.abstractdocument.domain.Part
import com.iluwatar.abstractdocument.domain.enums.Property
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Test for Part and Car
 */
class DomainTest {
    @Test
    fun shouldConstructPart() {
        val partProperties =
            mapOf(
                Property.TYPE.toString() to TEST_PART_TYPE,
                Property.MODEL.toString() to TEST_PART_MODEL,
                Property.PRICE.toString() to TEST_PART_PRICE as Any,
            )
        val part = Part(partProperties)
        assertEquals(TEST_PART_TYPE, part.getType().orElseThrow())
        assertEquals(TEST_PART_MODEL, part.getModel().orElseThrow())
        assertEquals(TEST_PART_PRICE, part.getPrice().orElseThrow())
    }

    @Test
    fun shouldConstructCar() {
        val carProperties =
            mapOf(
                Property.MODEL.toString() to TEST_CAR_MODEL,
                Property.PRICE.toString() to TEST_CAR_PRICE,
                Property.PARTS.toString() to listOf(emptyMap<String, Any>(), emptyMap()),
            )
        val car = Car(carProperties)
        assertEquals(TEST_CAR_MODEL, car.getModel().orElseThrow())
        assertEquals(TEST_CAR_PRICE, car.getPrice().orElseThrow())
        assertEquals(2, car.getParts().count())
    }

    companion object {
        private const val TEST_PART_TYPE = "test-part-type"
        private const val TEST_PART_MODEL = "test-part-model"
        private const val TEST_PART_PRICE = 0L

        private const val TEST_CAR_MODEL = "test-car-model"
        private const val TEST_CAR_PRICE = 1L
    }
}