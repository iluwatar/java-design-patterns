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
// ABOUTME: Collects and buffers logs from services, flushing to central store at threshold or intervals.
// ABOUTME: Provides asynchronous log processing with configurable minimum log level filtering.
package com.iluwatar.logaggregation

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

private val logger = KotlinLogging.logger {}

/**
 * Responsible for collecting and buffering logs from different services. Once the logs reach a
 * certain threshold or after a certain time interval, they are flushed to the central log store.
 * This class ensures logs are collected and processed asynchronously and efficiently, providing
 * both an immediate collection and periodic flushing.
 *
 * @param centralLogStore central log store implementation
 * @param minLogLevel minimum log level to store log
 */
class LogAggregator(
    private val centralLogStore: CentralLogStore,
    private val minLogLevel: LogLevel?,
) {
    private val buffer = ConcurrentLinkedQueue<LogEntry>()
    private val executorService = Executors.newSingleThreadExecutor()
    private val logCount = AtomicInteger(0)

    init {
        startBufferFlusher()
    }

    /**
     * Collects a given log entry, and filters it by the defined log level.
     *
     * @param logEntry The log entry to collect.
     */
    fun collectLog(logEntry: LogEntry) {
        if (logEntry.level == null || minLogLevel == null) {
            logger.warn { "Log level or threshold level is null. Skipping." }
            return
        }

        if (logEntry.level < minLogLevel) {
            logger.debug { "Log level below threshold. Skipping." }
            return
        }

        buffer.offer(logEntry)

        if (logCount.incrementAndGet() >= BUFFER_THRESHOLD) {
            flushBuffer()
        }
    }

    /**
     * Stops the log aggregator service and flushes any remaining logs to the central log store.
     *
     * @throws InterruptedException If any thread has interrupted the current thread.
     */
    @Throws(InterruptedException::class)
    fun stop() {
        executorService.shutdownNow()
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            logger.error { "Log aggregator did not terminate." }
        }
        flushBuffer()
    }

    private fun flushBuffer() {
        var logEntry: LogEntry? = buffer.poll()
        while (logEntry != null) {
            centralLogStore.storeLog(logEntry)
            logCount.decrementAndGet()
            logEntry = buffer.poll()
        }
    }

    private fun startBufferFlusher() {
        executorService.execute {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    Thread.sleep(5000) // Flush every 5 seconds.
                    flushBuffer()
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }
    }

    companion object {
        private const val BUFFER_THRESHOLD = 3
    }
}
