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
package com.iluwatar.adapter

// ABOUTME: Tests for the Adapter pattern verifying delegation through the adapter.
// ABOUTME: Uses MockK spyk to verify that row() on the adapter is properly invoked.

import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Tests for the adapter pattern. */
class AdapterPatternTest {
    private lateinit var beans: MutableMap<String, Any>

    companion object {
        private const val FISHING_BEAN = "fisher"
        private const val ROWING_BEAN = "captain"
    }

    /** This method runs before the test execution and sets the bean objects in the beans Map. */
    @BeforeEach
    fun setup() {
        beans = mutableMapOf()

        val fishingBoatAdapter = spyk(FishingBoatAdapter())
        beans[FISHING_BEAN] = fishingBoatAdapter

        val captain = Captain()
        captain.rowingBoat = beans[FISHING_BEAN] as FishingBoatAdapter
        beans[ROWING_BEAN] = captain
    }

    /**
     * This test asserts that when we use the row() method on a captain bean(client), it is internally
     * calling sail method on the fishing boat object. The Adapter ([FishingBoatAdapter])
     * converts the interface of the target class ([FishingBoat]) into a suitable one expected
     * by the client ([Captain]).
     */
    @Test
    fun testAdapter() {
        val captain = beans[ROWING_BEAN] as Captain

        // when captain moves
        captain.row()

        // the captain internally calls the battleship object to move
        val adapter = beans[FISHING_BEAN] as RowingBoat
        verify { adapter.row() }
    }
}