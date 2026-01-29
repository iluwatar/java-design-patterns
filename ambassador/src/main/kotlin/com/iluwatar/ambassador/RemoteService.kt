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

// ABOUTME: A remote legacy application represented by a Singleton implementation.
// ABOUTME: Simulates network latency and occasional failures for demonstration.
package com.iluwatar.ambassador

import com.iluwatar.ambassador.util.RandomProvider
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.math.floor

private val logger = KotlinLogging.logger {}

/** A remote legacy application represented by a Singleton implementation. */
class RemoteService internal constructor(
    private val randomProvider: RandomProvider = RandomProvider { Math.random() },
) : RemoteServiceInterface {
    /**
     * Remote function takes a value and multiplies it by 10 taking a random amount of time. Will
     * sometimes return -1. This imitates connectivity issues a client might have to account for.
     *
     * @param value integer value to be multiplied.
     * @return if waitTime is less than [THRESHOLD], it returns value * 10,
     *     otherwise [RemoteServiceStatus.FAILURE].
     */
    override fun doRemoteFunction(value: Int): Long {
        val waitTime = floor(randomProvider.random() * 1000).toLong()

        try {
            Thread.sleep(waitTime)
        } catch (e: InterruptedException) {
            logger.error(e) { "Thread sleep state interrupted" }
            Thread.currentThread().interrupt()
        }
        return if (waitTime <= THRESHOLD) {
            (value * 10).toLong()
        } else {
            RemoteServiceStatus.FAILURE.remoteServiceStatusValue
        }
    }

    companion object {
        private const val THRESHOLD = 200L

        @Volatile
        private var service: RemoteService? = null

        @Synchronized
        fun getRemoteService(): RemoteService {
            if (service == null) {
                service = RemoteService()
            }
            return service!!
        }
    }
}