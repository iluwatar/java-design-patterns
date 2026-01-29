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

// ABOUTME: Unit tests for the Action class verifying model updates.
// ABOUTME: Tests health, fatigue, nourishment setters and the updateModel method.

import com.iluwatar.model.view.controller.Fatigue
import com.iluwatar.model.view.controller.Health
import com.iluwatar.model.view.controller.Nourishment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** The type Action test. */
class ActionTest {

    /** Verify if the health value is set properly though the constructor and setter */
    @Test
    fun testSetHealth() {
        val model = GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED)
        val action = Action(model)
        assertEquals(Health.HEALTHY, model.health)
        val messageFormat = "Giant giant1, The giant looks %s, alert and saturated."
        for (health in Health.entries) {
            action.health = health
            assertEquals(health, model.health)
            assertEquals(String.format(messageFormat, health), model.toString())
        }
    }

    /** Verify if the fatigue level is set properly though the constructor and setter */
    @Test
    fun testSetFatigue() {
        val model = GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED)
        val action = Action(model)
        assertEquals(Fatigue.ALERT, model.fatigue)
        val messageFormat = "Giant giant1, The giant looks healthy, %s and saturated."
        for (fatigue in Fatigue.entries) {
            action.fatigue = fatigue
            assertEquals(fatigue, model.fatigue)
            assertEquals(String.format(messageFormat, fatigue), model.toString())
        }
    }

    /** Verify if the nourishment level is set properly though the constructor and setter */
    @Test
    fun testSetNourishment() {
        val model = GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED)
        val action = Action(model)
        assertEquals(Nourishment.SATURATED, model.nourishment)
        val messageFormat = "Giant giant1, The giant looks healthy, alert and %s."
        for (nourishment in Nourishment.entries) {
            action.nourishment = nourishment
            assertEquals(nourishment, model.nourishment)
            assertEquals(String.format(messageFormat, nourishment), model.toString())
        }
    }

    /** Test update model. */
    @Test
    fun testUpdateModel() {
        val model = GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED)
        val action = Action(model)
        assertEquals(Nourishment.SATURATED, model.nourishment)
        for (nourishment in Nourishment.entries) {
            for (fatigue in Fatigue.entries) {
                for (health in Health.entries) {
                    val cmd = Command(fatigue, health, nourishment)
                    action.updateModel(cmd)
                    assertEquals(nourishment, model.nourishment)
                    assertEquals(fatigue, model.fatigue)
                    assertEquals(health, model.health)
                }
            }
        }
    }
}
