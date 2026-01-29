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
// ABOUTME: Unit tests for the Cake entity class.
// ABOUTME: Tests setters, getters, addLayer functionality, and toString output.
package com.iluwatar.layers.entity

import entity.Cake
import entity.CakeLayer
import entity.CakeTopping
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * This class contains unit tests for the Cake class. It tests the functionality of setting and
 * getting the id, topping, and layers of a Cake object. It also tests the functionality of adding a
 * layer to a Cake object and converting a Cake object to a string.
 */
class CakeTest {
    @Test
    fun testSetId() {
        val cake = Cake()
        assertNull(cake.id)

        val expectedId = 1234L
        cake.id = expectedId
        assertEquals(expectedId, cake.id)
    }

    @Test
    fun testSetTopping() {
        val cake = Cake()
        assertNull(cake.topping)

        val expectedTopping = CakeTopping("DummyTopping", 1000)
        cake.topping = expectedTopping
        assertEquals(expectedTopping, cake.topping)
    }

    @Test
    fun testSetLayers() {
        val cake = Cake()
        assertNotNull(cake.layers)
        assertTrue(cake.layers.isEmpty())

        val expectedLayers =
            mutableSetOf(
                CakeLayer("layer1", 1000),
                CakeLayer("layer2", 2000),
                CakeLayer("layer3", 3000),
            )
        cake.layers = expectedLayers
        assertEquals(expectedLayers, cake.layers)
    }

    @Test
    fun testAddLayer() {
        val cake = Cake()
        assertNotNull(cake.layers)
        assertTrue(cake.layers.isEmpty())

        val initialLayers = mutableSetOf<CakeLayer>()
        initialLayers.add(CakeLayer("layer1", 1000))
        initialLayers.add(CakeLayer("layer2", 2000))

        cake.layers = initialLayers
        assertEquals(initialLayers, cake.layers)

        val newLayer = CakeLayer("layer3", 3000)
        cake.addLayer(newLayer)

        val expectedLayers = mutableSetOf<CakeLayer>()
        expectedLayers.addAll(initialLayers)
        expectedLayers.addAll(initialLayers)
        expectedLayers.add(newLayer)
        assertEquals(expectedLayers, cake.layers)
    }

    @Test
    fun testToString() {
        val topping = CakeTopping("topping", 20)
        topping.id = 2345L

        val layer = CakeLayer("layer", 100)
        layer.id = 3456L

        val cake = Cake()
        cake.id = 1234L
        cake.topping = topping
        cake.addLayer(layer)

        val expected =
            "id=1234 topping=id=2345 name=topping calories=20 " +
                "layers=[id=3456 name=layer calories=100]"
        assertEquals(expected, cake.toString())
    }
}
