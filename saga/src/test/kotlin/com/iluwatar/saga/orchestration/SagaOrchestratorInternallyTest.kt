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

// ABOUTME: Internal test for orchestration saga logic with custom services.
// ABOUTME: Verifies the correct sequence of process and rollback operations.
package com.iluwatar.saga.orchestration

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Test to test orchestration logic
 */
class SagaOrchestratorInternallyTest {

    private val records: MutableList<String> = mutableListOf()

    @Test
    fun executeTest() {
        val sagaOrchestrator = SagaOrchestrator(newSaga(), serviceDiscovery())
        val result = sagaOrchestrator.execute(1)
        assertEquals(Saga.Result.ROLLBACK, result)
        assertArrayEquals(
            arrayOf("+1", "+2", "+3", "+4", "-4", "-3", "-2", "-1"),
            records.toTypedArray()
        )
    }

    private fun newSaga(): Saga {
        return Saga.create().chapter("1").chapter("2").chapter("3").chapter("4")
    }

    private fun serviceDiscovery(): ServiceDiscoveryService {
        return ServiceDiscoveryService()
            .discover(Service1())
            .discover(Service2())
            .discover(Service3())
            .discover(Service4())
    }

    inner class Service1 : Service<Int>() {
        override val name: String = "1"

        override fun process(value: Int): ChapterResult<Int> {
            records.add("+1")
            return ChapterResult.success(value)
        }

        override fun rollback(value: Int): ChapterResult<Int> {
            records.add("-1")
            return ChapterResult.success(value)
        }
    }

    inner class Service2 : Service<Int>() {
        override val name: String = "2"

        override fun process(value: Int): ChapterResult<Int> {
            records.add("+2")
            return ChapterResult.success(value)
        }

        override fun rollback(value: Int): ChapterResult<Int> {
            records.add("-2")
            return ChapterResult.success(value)
        }
    }

    inner class Service3 : Service<Int>() {
        override val name: String = "3"

        override fun process(value: Int): ChapterResult<Int> {
            records.add("+3")
            return ChapterResult.success(value)
        }

        override fun rollback(value: Int): ChapterResult<Int> {
            records.add("-3")
            return ChapterResult.success(value)
        }
    }

    inner class Service4 : Service<Int>() {
        override val name: String = "4"

        override fun process(value: Int): ChapterResult<Int> {
            records.add("+4")
            return ChapterResult.failure(value)
        }

        override fun rollback(value: Int): ChapterResult<Int> {
            records.add("-4")
            return ChapterResult.success(value)
        }
    }
}
