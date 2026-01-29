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

// ABOUTME: Washing machine class demonstrating the Balking design pattern.
// ABOUTME: Uses synchronized blocks to ensure thread-safe state transitions between ENABLED and WASHING.
package com.iluwatar.balking

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/** Washing machine class. */
class WashingMachine(
    private val delayProvider: DelayProvider,
) {
    var washingMachineState: WashingMachineState = WashingMachineState.ENABLED
        private set

    /** Creates a new instance of WashingMachine with a default delay provider. */
    constructor() : this(
        DelayProvider { interval, timeUnit, task ->
            try {
                Thread.sleep(timeUnit.toMillis(interval))
            } catch (ie: InterruptedException) {
                logger.error(ie) { "" }
                Thread.currentThread().interrupt()
            }
            task.run()
        },
    )

    /** Method responsible for washing if the object is in appropriate state. */
    fun wash() {
        synchronized(this) {
            val machineState = washingMachineState
            logger.info { "${Thread.currentThread().name}: Actual machine state: $machineState" }
            if (washingMachineState == WashingMachineState.WASHING) {
                logger.error { "Cannot wash if the machine has been already washing!" }
                return
            }
            washingMachineState = WashingMachineState.WASHING
        }
        logger.info { "${Thread.currentThread().name}: Doing the washing" }

        delayProvider.executeAfterDelay(50, TimeUnit.MILLISECONDS, Runnable { endOfWashing() })
    }

    /** Method is responsible for ending the washing by changing machine state. */
    @Synchronized
    fun endOfWashing() {
        washingMachineState = WashingMachineState.ENABLED
        logger.info { "${Thread.currentThread().id}: Washing completed." }
    }
}