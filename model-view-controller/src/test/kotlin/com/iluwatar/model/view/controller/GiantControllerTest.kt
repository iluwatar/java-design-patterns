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

// ABOUTME: Tests for the GiantController class.
// ABOUTME: Verifies that the controller properly delegates to the model and view.
package com.iluwatar.model.view.controller

import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * GiantControllerTest
 */
class GiantControllerTest {
    /**
     * Verify if the controller passes the health level through to the model and vice versa
     */
    @Test
    fun testSetHealth() {
        val model = mockk<GiantModel>(relaxed = true)
        val view = mockk<GiantView>(relaxed = true)
        val controller = GiantController(model, view)

        confirmVerified(model, view)

        for (health in Health.entries) {
            controller.health = health
            verify { model.health = health }
            confirmVerified(view)
        }

        controller.health
        verify { model.health }

        confirmVerified(model, view)
    }

    /**
     * Verify if the controller passes the fatigue level through to the model and vice versa
     */
    @Test
    fun testSetFatigue() {
        val model = mockk<GiantModel>(relaxed = true)
        val view = mockk<GiantView>(relaxed = true)
        val controller = GiantController(model, view)

        confirmVerified(model, view)

        for (fatigue in Fatigue.entries) {
            controller.fatigue = fatigue
            verify { model.fatigue = fatigue }
            confirmVerified(view)
        }

        controller.fatigue
        verify { model.fatigue }

        confirmVerified(model, view)
    }

    /**
     * Verify if the controller passes the nourishment level through to the model and vice versa
     */
    @Test
    fun testSetNourishment() {
        val model = mockk<GiantModel>(relaxed = true)
        val view = mockk<GiantView>(relaxed = true)
        val controller = GiantController(model, view)

        confirmVerified(model, view)

        for (nourishment in Nourishment.entries) {
            controller.nourishment = nourishment
            verify { model.nourishment = nourishment }
            confirmVerified(view)
        }

        controller.nourishment
        verify { model.nourishment }

        confirmVerified(model, view)
    }

    @Test
    fun testUpdateView() {
        val model = mockk<GiantModel>(relaxed = true)
        val view = mockk<GiantView>(relaxed = true)
        val controller = GiantController(model, view)

        confirmVerified(model, view)

        controller.updateView()
        verify { view.displayGiant(model) }

        confirmVerified(model, view)
    }
}