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

// ABOUTME: Central orchestrator that manages saga transactions and coordinates services.
// ABOUTME: Directs participant services to execute local transactions based on events.
package com.iluwatar.saga.orchestration

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The orchestrator that manages all the transactions and directs the participant services to
 * execute local transactions based on events.
 */
class SagaOrchestrator(
    private val saga: Saga,
    private val sd: ServiceDiscoveryService
) {
    private val state = CurrentState()

    /**
     * Pipeline to execute saga process/story.
     *
     * @param value incoming value
     * @param K type for incoming value
     * @return result [Saga.Result]
     */
    @Suppress("UNCHECKED_CAST")
    fun <K> execute(value: K): Saga.Result {
        state.cleanUp()
        logger.info { " The new saga is about to start" }
        var result = Saga.Result.FINISHED
        var tempVal = value
        var next: Int

        while (true) {
            next = state.current()
            val ch = saga[next]
            val srv = sd.find(ch.name) as? OrchestrationChapter<K>

            if (srv == null) {
                state.directionToBack()
                state.back()
                continue
            }

            if (state.isForward()) {
                val processRes = srv.process(tempVal)
                if (processRes.isSuccess) {
                    next = state.forward()
                    tempVal = processRes.value
                } else {
                    state.directionToBack()
                }
            } else {
                val rlRes = srv.rollback(tempVal)
                if (rlRes.isSuccess) {
                    next = state.back()
                    tempVal = rlRes.value
                } else {
                    result = Saga.Result.CRASHED
                    next = state.back()
                }
            }

            if (!saga.isPresent(next)) {
                return when {
                    state.isForward() -> Saga.Result.FINISHED
                    result == Saga.Result.CRASHED -> Saga.Result.CRASHED
                    else -> Saga.Result.ROLLBACK
                }
            }
        }
    }

    private class CurrentState {
        private var currentNumber: Int = 0
        private var isForward: Boolean = true

        fun cleanUp() {
            currentNumber = 0
            isForward = true
        }

        fun isForward(): Boolean = isForward

        fun directionToBack() {
            isForward = false
        }

        fun forward(): Int = ++currentNumber

        fun back(): Int = --currentNumber

        fun current(): Int = currentNumber
    }
}
