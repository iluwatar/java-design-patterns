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

// ABOUTME: Manager for render components that stores them in contiguous memory for cache efficiency.
// ABOUTME: Demonstrates data locality by keeping all render components together in an array.
package com.iluwatar.data.locality.game.component.manager

import com.iluwatar.data.locality.game.component.Component
import com.iluwatar.data.locality.game.component.RenderComponent
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/** Render component manager for Game. */
class RenderComponentManager(private val numEntities: Int) {

    private val renderComponents: Array<Component?> = arrayOfNulls(MAX_ENTITIES)

    /** Start render component. */
    fun start() {
        logger.info { "Start Render Game Component " }
        for (i in 0 until numEntities) {
            renderComponents[i] = RenderComponent()
        }
    }

    /** Render component. */
    fun render() {
        logger.info { "Update Render Game Component " }
        // Process Render.
        for (i in 0 until numEntities) {
            if (renderComponents.size > i && renderComponents[i] != null) {
                renderComponents[i]?.render()
            }
        }
    }

    companion object {
        private const val MAX_ENTITIES = 10000
    }
}
