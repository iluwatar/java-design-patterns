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

// ABOUTME: Tests for the WashingMachine class demonstrating balking behavior.
// ABOUTME: Verifies state transitions and that concurrent wash attempts are correctly rejected.
package com.iluwatar.balking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

/** Tests for [WashingMachine] */
class WashingMachineTest {
    private val fakeDelayProvider = FakeDelayProvider()

    @Test
    fun wash() {
        val washingMachine = WashingMachine(fakeDelayProvider)

        washingMachine.wash()
        washingMachine.wash()

        val machineStateGlobal = washingMachine.washingMachineState

        fakeDelayProvider.task!!.run()

        // washing machine remains in washing state
        assertEquals(WashingMachineState.WASHING, machineStateGlobal)

        // washing machine goes back to enabled state
        assertEquals(WashingMachineState.ENABLED, washingMachine.washingMachineState)
    }

    @Test
    fun endOfWashing() {
        val washingMachine = WashingMachine()
        washingMachine.wash()
        assertEquals(WashingMachineState.ENABLED, washingMachine.washingMachineState)
    }

    private class FakeDelayProvider : DelayProvider {
        var task: Runnable? = null

        override fun executeAfterDelay(
            interval: Long,
            timeUnit: TimeUnit,
            task: Runnable,
        ) {
            this.task = task
        }
    }
}