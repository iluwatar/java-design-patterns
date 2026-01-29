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
// ABOUTME: Integration tests for CakeBakingServiceImpl using Spring Boot test context.
// ABOUTME: Tests layer, topping, and cake baking operations with real database.
package com.iluwatar.layers.service

import com.iluwatar.layers.app.LayersApp
import dto.CakeInfo
import dto.CakeLayerInfo
import dto.CakeToppingInfo
import exception.CakeBakingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import service.CakeBakingServiceImpl

/** Constructs a new instance of CakeBakingServiceImplTest. */
@SpringBootTest(classes = [LayersApp::class])
class CakeBakingServiceImplTest
    @Autowired
    constructor(
        private val cakeBakingService: CakeBakingServiceImpl,
    ) {
        @BeforeEach
        fun setUp() {
            cakeBakingService.deleteAllCakes()
            cakeBakingService.deleteAllLayers()
            cakeBakingService.deleteAllToppings()
        }

        @Test
        fun testLayers() {
            val initialLayers = cakeBakingService.getAvailableLayers()
            assertNotNull(initialLayers)
            assertTrue(initialLayers.isEmpty())

            cakeBakingService.saveNewLayer(CakeLayerInfo("Layer1", 1000))
            cakeBakingService.saveNewLayer(CakeLayerInfo("Layer2", 2000))

            val availableLayers = cakeBakingService.getAvailableLayers()
            assertNotNull(availableLayers)
            assertEquals(2, availableLayers.size)
            for (layer in availableLayers) {
                assertNotNull(layer.id)
                assertNotNull(layer.name)
                assertNotNull(layer.toString())
                assertTrue(layer.calories > 0)
            }
        }

        @Test
        fun testToppings() {
            val initialToppings = cakeBakingService.getAvailableToppings()
            assertNotNull(initialToppings)
            assertTrue(initialToppings.isEmpty())

            cakeBakingService.saveNewTopping(CakeToppingInfo("Topping1", 1000))
            cakeBakingService.saveNewTopping(CakeToppingInfo("Topping2", 2000))

            val availableToppings = cakeBakingService.getAvailableToppings()
            assertNotNull(availableToppings)
            assertEquals(2, availableToppings.size)
            for (topping in availableToppings) {
                assertNotNull(topping.id)
                assertNotNull(topping.name)
                assertNotNull(topping.toString())
                assertTrue(topping.calories > 0)
            }
        }

        @Test
        @Throws(CakeBakingException::class)
        fun testBakeCakes() {
            val initialCakes = cakeBakingService.getAllCakes()
            assertNotNull(initialCakes)
            assertTrue(initialCakes.isEmpty())

            val topping1 = CakeToppingInfo("Topping1", 1000)
            val topping2 = CakeToppingInfo("Topping2", 2000)
            cakeBakingService.saveNewTopping(topping1)
            cakeBakingService.saveNewTopping(topping2)

            val layer1 = CakeLayerInfo("Layer1", 1000)
            val layer2 = CakeLayerInfo("Layer2", 2000)
            val layer3 = CakeLayerInfo("Layer3", 2000)
            cakeBakingService.saveNewLayer(layer1)
            cakeBakingService.saveNewLayer(layer2)
            cakeBakingService.saveNewLayer(layer3)

            cakeBakingService.bakeNewCake(CakeInfo(topping1, listOf(layer1, layer2)))
            cakeBakingService.bakeNewCake(CakeInfo(topping2, listOf(layer3)))

            val allCakes = cakeBakingService.getAllCakes()
            assertNotNull(allCakes)
            assertEquals(2, allCakes.size)
            for (cakeInfo in allCakes) {
                assertNotNull(cakeInfo.id)
                assertNotNull(cakeInfo.cakeToppingInfo)
                assertNotNull(cakeInfo.cakeLayerInfos)
                assertNotNull(cakeInfo.toString())
                assertFalse(cakeInfo.cakeLayerInfos.isEmpty())
                assertTrue(cakeInfo.calculateTotalCalories() > 0)
            }
        }

        @Test
        fun testBakeCakeMissingTopping() {
            val layer1 = CakeLayerInfo("Layer1", 1000)
            val layer2 = CakeLayerInfo("Layer2", 2000)
            cakeBakingService.saveNewLayer(layer1)
            cakeBakingService.saveNewLayer(layer2)

            val missingTopping = CakeToppingInfo("Topping1", 1000)
            assertThrows(CakeBakingException::class.java) {
                cakeBakingService.bakeNewCake(CakeInfo(missingTopping, listOf(layer1, layer2)))
            }
        }

        @Test
        fun testBakeCakeMissingLayer() {
            val initialCakes = cakeBakingService.getAllCakes()
            assertNotNull(initialCakes)
            assertTrue(initialCakes.isEmpty())

            val topping1 = CakeToppingInfo("Topping1", 1000)
            cakeBakingService.saveNewTopping(topping1)

            val layer1 = CakeLayerInfo("Layer1", 1000)
            cakeBakingService.saveNewLayer(layer1)

            val missingLayer = CakeLayerInfo("Layer2", 2000)
            assertThrows(CakeBakingException::class.java) {
                cakeBakingService.bakeNewCake(CakeInfo(topping1, listOf(layer1, missingLayer)))
            }
        }

        @Test
        @Throws(CakeBakingException::class)
        fun testBakeCakesUsedLayer() {
            val initialCakes = cakeBakingService.getAllCakes()
            assertNotNull(initialCakes)
            assertTrue(initialCakes.isEmpty())

            val topping1 = CakeToppingInfo("Topping1", 1000)
            val topping2 = CakeToppingInfo("Topping2", 2000)
            cakeBakingService.saveNewTopping(topping1)
            cakeBakingService.saveNewTopping(topping2)

            val layer1 = CakeLayerInfo("Layer1", 1000)
            val layer2 = CakeLayerInfo("Layer2", 2000)
            cakeBakingService.saveNewLayer(layer1)
            cakeBakingService.saveNewLayer(layer2)

            cakeBakingService.bakeNewCake(CakeInfo(topping1, listOf(layer1, layer2)))
            assertThrows(CakeBakingException::class.java) {
                cakeBakingService.bakeNewCake(CakeInfo(topping2, listOf(layer2)))
            }
        }
    }
