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

// ABOUTME: Demonstrates collection pipeline operations using Kotlin's functional collection API.
// ABOUTME: Provides filtering, sorting, mapping, grouping, and flatMapping using idiomatic Kotlin.

/**
 * Iterating and sorting with a collection pipeline.
 *
 * In functional programming, it's common to sequence complex operations through a series of
 * smaller modular functions or operations. The series is called a composition of functions, or a
 * function composition. When a collection of data flows through a function composition, it becomes
 * a collection pipeline. Function Composition and Collection Pipeline are two design patterns
 * frequently used in functional-style programming.
 */
object FunctionalProgramming {
    /**
     * Returns models of cars built after year 2000, sorted by year, using collection pipeline.
     *
     * @param cars [List] of [Car] to be used for filtering
     * @return [List] of [String] representing models built after year 2000
     */
    fun getModelsAfter2000(cars: List<Car>): List<String> =
        cars
            .filter { it.year > 2000 }
            .sortedBy { it.year }
            .map { it.model }

    /**
     * Groups cars by category using [groupBy].
     *
     * @param cars [List] of [Car] to be used for grouping
     * @return [Map] with category as key and cars belonging to that category as value
     */
    fun getGroupingOfCarsByCategory(cars: List<Car>): Map<Category, List<Car>> = cars.groupBy { it.category }

    /**
     * Returns all Sedan cars belonging to a group of persons, sorted by year of manufacture.
     *
     * @param persons [List] of [Person] to be used
     * @return [List] of [Car] belonging to the group
     */
    fun getSedanCarsOwnedSortedByDate(persons: List<Person>): List<Car> =
        persons
            .flatMap { it.cars }
            .filter { it.category == Category.SEDAN }
            .sortedBy { it.year }
}