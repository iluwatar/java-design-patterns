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
package com.iluwatar.collectionpipeline

// ABOUTME: Tests that Collection Pipeline methods work as expected for both imperative and functional styles.
// ABOUTME: Verifies filtering, grouping, and sorting operations produce identical results across approaches.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** Tests that Collection Pipeline methods work as expected. */
class AppTest {
    private val cars = CarFactory.createCars()

    @Test
    fun testGetModelsAfter2000UsingFor() {
        val models = ImperativeProgramming.getModelsAfter2000(cars)
        assertEquals(listOf("Avenger", "Wrangler", "Focus", "Cascada"), models)
    }

    @Test
    fun testGetModelsAfter2000UsingPipeline() {
        val models = FunctionalProgramming.getModelsAfter2000(cars)
        assertEquals(listOf("Avenger", "Wrangler", "Focus", "Cascada"), models)
    }

    @Test
    fun testGetGroupingOfCarsByCategory() {
        val modelsExpected =
            mapOf(
                Category.CONVERTIBLE to
                    listOf(
                        Car("Buick", "Cascada", 2016, Category.CONVERTIBLE),
                        Car("Chevrolet", "Geo Metro", 1992, Category.CONVERTIBLE),
                    ),
                Category.SEDAN to
                    listOf(
                        Car("Dodge", "Avenger", 2010, Category.SEDAN),
                        Car("Ford", "Focus", 2012, Category.SEDAN),
                    ),
                Category.JEEP to
                    listOf(
                        Car("Jeep", "Wrangler", 2011, Category.JEEP),
                        Car("Jeep", "Comanche", 1990, Category.JEEP),
                    ),
            )
        val modelsFunctional = FunctionalProgramming.getGroupingOfCarsByCategory(cars)
        val modelsImperative = ImperativeProgramming.getGroupingOfCarsByCategory(cars)
        assertEquals(modelsExpected, modelsFunctional)
        assertEquals(modelsExpected, modelsImperative)
    }

    @Test
    fun testGetSedanCarsOwnedSortedByDate() {
        val john = Person(cars)
        val modelsExpected =
            listOf(
                Car("Dodge", "Avenger", 2010, Category.SEDAN),
                Car("Ford", "Focus", 2012, Category.SEDAN),
            )
        val modelsFunctional = FunctionalProgramming.getSedanCarsOwnedSortedByDate(listOf(john))
        val modelsImperative = ImperativeProgramming.getSedanCarsOwnedSortedByDate(listOf(john))
        assertEquals(modelsExpected, modelsFunctional)
        assertEquals(modelsExpected, modelsImperative)
    }
}