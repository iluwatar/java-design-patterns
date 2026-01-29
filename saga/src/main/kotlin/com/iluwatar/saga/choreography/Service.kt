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

// ABOUTME: Abstract base class for choreography services implementing ChoreographyChapter.
// ABOUTME: Handles saga execution flow including forward processing and rollback.
package com.iluwatar.saga.choreography

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Common abstraction class representing services. implementing a general contract
 * [ChoreographyChapter]
 */
abstract class Service(private val sd: ServiceDiscoveryService) : ChoreographyChapter {

    override fun execute(saga: Saga): Saga {
        var nextSaga = saga
        val nextVal: Any?
        val chapterName = saga.current.name
        if (chapterName == name) {
            if (saga.isForward) {
                nextSaga = process(saga)
                nextVal = nextSaga.currentValue
                if (nextSaga.isCurrentSuccess) {
                    nextSaga.forward()
                } else {
                    nextSaga.back()
                }
            } else {
                nextSaga = rollback(saga)
                nextVal = nextSaga.currentValue
                nextSaga.back()
            }

            if (isSagaFinished(nextSaga)) {
                return nextSaga
            }

            nextSaga.setCurrentValue(nextVal)
        }

        return sd.find(chapterName)
            ?.execute(nextSaga)
            ?: throw RuntimeException("the service $chapterName has not been found")
    }

    override fun process(saga: Saga): Saga {
        val inValue = saga.currentValue
        logger.info {
            "The chapter '$name' has been started. " +
                "The data $inValue has been stored or calculated successfully"
        }
        saga.setCurrentStatus(Saga.ChapterResult.SUCCESS)
        saga.setCurrentValue(inValue)
        return saga
    }

    override fun rollback(saga: Saga): Saga {
        val inValue = saga.currentValue
        logger.info {
            "The Rollback for a chapter '$name' has been started. " +
                "The data $inValue has been rollbacked successfully"
        }
        saga.setCurrentStatus(Saga.ChapterResult.ROLLBACK)
        saga.setCurrentValue(inValue)
        return saga
    }

    private fun isSagaFinished(saga: Saga): Boolean {
        if (!saga.isPresent) {
            saga.setFinished(true)
            logger.info { " the saga has been finished with ${saga.result} status" }
            return true
        }
        return false
    }
}
