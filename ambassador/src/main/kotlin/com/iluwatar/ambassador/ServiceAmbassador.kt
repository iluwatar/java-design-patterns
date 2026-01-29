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

// ABOUTME: Ambassador service that provides an interface for clients to access RemoteService.
// ABOUTME: Adds logging, latency testing and retry logic for safe remote service access.
package com.iluwatar.ambassador

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * ServiceAmbassador provides an interface for a [Client] to access [RemoteService].
 * The interface adds logging, latency testing and usage of the service in a safe way that will not
 * add stress to the remote service when connectivity issues occur.
 */
class ServiceAmbassador internal constructor() : RemoteServiceInterface {
    override fun doRemoteFunction(value: Int): Long = safeCall(value)

    private fun checkLatency(value: Int): Long {
        val startTime = System.currentTimeMillis()
        val result = RemoteService.getRemoteService().doRemoteFunction(value)
        val timeTaken = System.currentTimeMillis() - startTime

        logger.info { "Time taken (ms): $timeTaken" }
        return result
    }

    private fun safeCall(value: Int): Long {
        var retries = 0
        var result = RemoteServiceStatus.FAILURE.remoteServiceStatusValue

        for (i in 0 until RETRIES) {
            if (retries >= RETRIES) {
                return RemoteServiceStatus.FAILURE.remoteServiceStatusValue
            }

            result = checkLatency(value)
            if (result == RemoteServiceStatus.FAILURE.remoteServiceStatusValue) {
                logger.info { "Failed to reach remote: (${i + 1})" }
                retries++
                try {
                    Thread.sleep(DELAY_MS)
                } catch (e: InterruptedException) {
                    logger.error(e) { "Thread sleep state interrupted" }
                    Thread.currentThread().interrupt()
                }
            } else {
                break
            }
        }
        return result
    }

    companion object {
        private const val RETRIES = 3
        private const val DELAY_MS = 3000L
    }
}