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
package com.iluwatar.servicetoworker

// ABOUTME: Unit tests for the Dispatcher class verifying action routing and view updates.
// ABOUTME: Tests performAction and updateView methods with various command combinations.

import com.iluwatar.model.view.controller.Fatigue
import com.iluwatar.model.view.controller.Health
import com.iluwatar.model.view.controller.Nourishment
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** The type Dispatcher test. */
class DispatcherTest {

    /** Test perform action. */
    @Test
    fun testPerformAction() {
        val model = GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED)
        val action = Action(model)
        val giantView = GiantView()
        val dispatcher = Dispatcher(giantView)
        assertEquals(Nourishment.SATURATED, model.nourishment)
        dispatcher.addAction(action)
        for (nourishment in Nourishment.entries) {
            for (fatigue in Fatigue.entries) {
                for (health in Health.entries) {
                    val cmd = Command(fatigue, health, nourishment)
                    dispatcher.performAction(cmd, 0)
                    assertEquals(nourishment, model.nourishment)
                    assertEquals(fatigue, model.fatigue)
                    assertEquals(health, model.health)
                }
            }
        }
    }

    @Test
    fun testUpdateView() {
        val model = GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED)
        val giantView = GiantView()
        val dispatcher = Dispatcher(giantView)
        assertDoesNotThrow { dispatcher.updateView(model) }
    }
}
