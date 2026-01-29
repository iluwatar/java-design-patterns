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
package com.iluwatar.context.`object`

// ABOUTME: Entry point demonstrating the Context Object design pattern.
// ABOUTME: Shows how a ServiceContext is passed and enriched across LayerA, LayerB, and LayerC.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

private const val SERVICE = "SERVICE"

/**
 * In the context object pattern, information and data from underlying protocol-specific
 * classes/systems is decoupled and stored into a protocol-independent object in an organised
 * format. The pattern ensures the data contained within the context object can be shared and
 * further structured between different layers of a software system.
 *
 * In this example we show how a context object [ServiceContext] can be initiated, edited
 * and passed/retrieved in different layers of the program ([LayerA], [LayerB], [LayerC])
 * through use of static methods.
 */
fun main() {
    // Initiate first layer and add service information into context
    val layerA = LayerA()
    layerA.addAccountInfo(SERVICE)

    logContext(layerA.context)

    // Initiate second layer and preserving information retrieved in first layer through passing
    // context object
    val layerB = LayerB(layerA)
    layerB.addSessionInfo(SERVICE)

    logContext(layerB.context)

    // Initiate third layer and preserving information retrieved in first and second layer through
    // passing context object
    val layerC = LayerC(layerB)
    layerC.addSearchInfo(SERVICE)

    logContext(layerC.context)
}

private fun logContext(context: ServiceContext) {
    logger.info { "Context = $context" }
}
