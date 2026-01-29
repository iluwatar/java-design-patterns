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

// ABOUTME: Entry point demonstrating the Collection Pipeline design pattern.
// ABOUTME: Compares imperative vs functional approaches to collection processing.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * In imperative-style programming, it is common to use for and while loops for most kinds of data
 * processing. Function composition is a simple technique that lets you sequence modular functions
 * to create more complex operations. When you run data through the sequence, you have a collection
 * pipeline. Together, the Function Composition and Collection Pipeline patterns enable you to
 * create sophisticated programs where data flow from upstream to downstream and is passed through a
 * series of transformations.
 */
fun main() {
    val cars = CarFactory.createCars()

    val modelsImperative = ImperativeProgramming.getModelsAfter2000(cars)
    logger.info { modelsImperative.toString() }

    val modelsFunctional = FunctionalProgramming.getModelsAfter2000(cars)
    logger.info { modelsFunctional.toString() }

    val groupingByCategoryImperative = ImperativeProgramming.getGroupingOfCarsByCategory(cars)
    logger.info { groupingByCategoryImperative.toString() }

    val groupingByCategoryFunctional = FunctionalProgramming.getGroupingOfCarsByCategory(cars)
    logger.info { groupingByCategoryFunctional.toString() }

    val john = Person(cars)

    val sedansOwnedImperative = ImperativeProgramming.getSedanCarsOwnedSortedByDate(listOf(john))
    logger.info { sedansOwnedImperative.toString() }

    val sedansOwnedFunctional = FunctionalProgramming.getSedanCarsOwnedSortedByDate(listOf(john))
    logger.info { sedansOwnedFunctional.toString() }
}