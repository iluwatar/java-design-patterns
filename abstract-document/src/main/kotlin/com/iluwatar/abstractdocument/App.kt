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

// ABOUTME: Main application demonstrating the Abstract Document pattern with Car and Part entities.
// ABOUTME: Shows how traits enable type-safe access to dynamic document properties.
package com.iluwatar.abstractdocument

import com.iluwatar.abstractdocument.domain.Car
import com.iluwatar.abstractdocument.domain.enums.Property
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Abstract Document pattern enables handling additional, non-static properties. This pattern
 * uses concept of traits to enable type safety and separate properties of different classes into
 * set of interfaces.
 *
 * In Abstract Document pattern, [AbstractDocument] fully implements [Document] interface.
 * Traits are then defined to enable access to properties in usual, static way.
 */
fun main() {
    logger.info { "Constructing parts and car" }

    val wheelProperties =
        mapOf(
            Property.TYPE.toString() to "wheel",
            Property.MODEL.toString() to "15C",
            Property.PRICE.toString() to 100L,
        )

    val doorProperties =
        mapOf(
            Property.TYPE.toString() to "door",
            Property.MODEL.toString() to "Lambo",
            Property.PRICE.toString() to 300L,
        )

    val carProperties =
        mapOf(
            Property.MODEL.toString() to "300SL",
            Property.PRICE.toString() to 10000L,
            Property.PARTS.toString() to listOf(wheelProperties, doorProperties),
        )

    val car = Car(carProperties)

    logger.info { "Here is our car:" }
    logger.info { "-> model: ${car.getModel().orElseThrow()}" }
    logger.info { "-> price: ${car.getPrice().orElseThrow()}" }
    logger.info { "-> parts: " }
    car.getParts().forEach { p ->
        logger.info { "\t${p.getType().orElse(null)}/${p.getModel().orElse(null)}/${p.getPrice().orElse(null)}" }
    }
}