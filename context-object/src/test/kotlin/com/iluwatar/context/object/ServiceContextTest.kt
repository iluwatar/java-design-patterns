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
package com.iluwatar.contect.`object`

// ABOUTME: Tests for ServiceContext verifying context sharing across layers.
// ABOUTME: Validates that the same context instance is passed and enriched through LayerA, LayerB, and LayerC.

import com.iluwatar.context.`object`.LayerA
import com.iluwatar.context.`object`.LayerB
import com.iluwatar.context.`object`.LayerC
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** ServiceContextTest */
class ServiceContextTest {

    companion object {
        private const val SERVICE = "SERVICE"
    }

    private lateinit var layerA: LayerA

    @BeforeEach
    fun initiateLayerA() {
        layerA = LayerA()
    }

    @Test
    fun testSameContextPassedBetweenLayers() {
        val context1 = layerA.context
        val layerB = LayerB(layerA)
        val context2 = layerB.context
        val layerC = LayerC(layerB)
        val context3 = layerC.context

        assertSame(context1, context2)
        assertSame(context2, context3)
        assertSame(context3, context1)
    }

    @Test
    fun testScopedDataPassedBetweenLayers() {
        layerA.addAccountInfo(SERVICE)
        val layerB = LayerB(layerA)
        val layerC = LayerC(layerB)
        layerC.addSearchInfo(SERVICE)
        val context = layerC.context

        assertEquals(SERVICE, context.accountService)
        assertNull(context.sessionService)
        assertEquals(SERVICE, context.searchService)
    }

    @Test
    fun testLayerContexts() {
        assertAll(
            { assertNull(layerA.context.accountService) },
            { assertNull(layerA.context.searchService) },
            { assertNull(layerA.context.sessionService) }
        )
        layerA.addAccountInfo(SERVICE)
        assertAll(
            { assertEquals(SERVICE, layerA.context.accountService) },
            { assertNull(layerA.context.searchService) },
            { assertNull(layerA.context.sessionService) }
        )
        val layerB = LayerB(layerA)
        layerB.addSessionInfo(SERVICE)
        assertAll(
            { assertEquals(SERVICE, layerB.context.accountService) },
            { assertEquals(SERVICE, layerB.context.sessionService) },
            { assertNull(layerB.context.searchService) }
        )
        val layerC = LayerC(layerB)
        layerC.addSearchInfo(SERVICE)
        assertAll(
            { assertEquals(SERVICE, layerC.context.accountService) },
            { assertEquals(SERVICE, layerC.context.searchService) },
            { assertEquals(SERVICE, layerC.context.sessionService) }
        )
    }
}
