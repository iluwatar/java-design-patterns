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

// ABOUTME: Unit tests for the GiantController class verifying command and view operations.
// ABOUTME: Tests setCommand and updateView methods for proper dispatcher delegation.

import com.iluwatar.model.view.controller.Fatigue
import com.iluwatar.model.view.controller.Health
import com.iluwatar.model.view.controller.Nourishment
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** The type Giant controller test. */
class GiantControllerTest {

    /** Test set command. */
    @Test
    fun testSetCommand() {
        val model = GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED)
        val action = Action(model)
        val giantView = GiantView()
        val dispatcher = Dispatcher(giantView)
        assertEquals(Nourishment.SATURATED, model.nourishment)
        dispatcher.addAction(action)
        val controller = GiantController(dispatcher)
        controller.setCommand(Command(Fatigue.ALERT, Health.HEALTHY, Nourishment.HUNGRY), 0)
        assertEquals(Fatigue.ALERT, model.fatigue)
        assertEquals(Health.HEALTHY, model.health)
        assertEquals(Nourishment.HUNGRY, model.nourishment)
    }

    /** Test update view. */
    @Test
    fun testUpdateView() {
        val model = GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED)
        val giantView = GiantView()
        val dispatcher = Dispatcher(giantView)
        val giantController = GiantController(dispatcher)
        assertDoesNotThrow { giantController.updateView(model) }
    }
}
