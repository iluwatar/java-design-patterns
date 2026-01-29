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

// ABOUTME: Entry point demonstrating the Promise design pattern with async file download examples.
// ABOUTME: Shows line counting and character frequency analysis using chained promises.
package com.iluwatar.promise

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private val logger = KotlinLogging.logger {}

private const val DEFAULT_URL =
    "https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/promise/README.md"

/**
 * The Promise object is used for asynchronous computations. A Promise represents an operation that
 * hasn't completed yet, but is expected in the future.
 *
 * A Promise represents a proxy for a value not necessarily known when the promise is created. It
 * allows you to associate dependent promises to an asynchronous action's eventual success value or
 * failure reason. This lets asynchronous methods return values like synchronous methods: instead of
 * the final value, the asynchronous method returns a promise of having a value at some point in the
 * future.
 *
 * Promises provide a few advantages over callback objects:
 * - Functional composition and error handling
 * - Prevents callback hell and provides callback aggregation
 *
 * In this application the usage of promise is demonstrated with two examples:
 * - Count Lines: a file is downloaded and its line count is calculated and printed on console.
 * - Lowest Character Frequency: a file is downloaded and its lowest frequency character is found
 *   and printed on console via a chain of promises.
 */
class App {

    private val executor: ExecutorService = Executors.newFixedThreadPool(2)
    private val stopLatch: CountDownLatch = CountDownLatch(2)

    private fun promiseUsage() {
        calculateLineCount()
        calculateLowestFrequencyChar()
    }

    /*
     * Calculate the lowest frequency character and when that promise is fulfilled,
     * consume the result in a consumer lambda.
     */
    private fun calculateLowestFrequencyChar() {
        lowestFrequencyChar()
            .thenAccept { charFrequency ->
                logger.info { "Char with lowest frequency is: $charFrequency" }
                taskCompleted()
            }
    }

    /*
     * Calculate the line count and when that promise is fulfilled, consume the result
     * in a consumer lambda.
     */
    private fun calculateLineCount() {
        countLines()
            .thenAccept { count ->
                logger.info { "Line count is: $count" }
                taskCompleted()
            }
    }

    /*
     * Calculate the character frequency of a file and when that promise is fulfilled,
     * then promise to apply function to calculate the lowest character frequency.
     */
    private fun lowestFrequencyChar(): Promise<Char> =
        characterFrequency().thenApply(Utility::lowestFrequencyChar)

    /*
     * Download the file at DEFAULT_URL and when that promise is fulfilled,
     * then promise to apply function to calculate character frequency.
     */
    private fun characterFrequency(): Promise<Map<Char, Long>> =
        download(DEFAULT_URL).thenApply(Utility::characterFrequency)

    /*
     * Download the file at DEFAULT_URL and when that promise is fulfilled,
     * then promise to apply function to count lines in that file.
     */
    private fun countLines(): Promise<Int> =
        download(DEFAULT_URL).thenApply(Utility::countLines)

    /*
     * Return a promise to provide the local absolute path of the file downloaded in background.
     * This is an async method and does not wait until the file is downloaded.
     */
    private fun download(urlString: String): Promise<String> =
        Promise<String>()
            .fulfillInAsync({ Utility.downloadFile(urlString) }, executor)
            .onError { throwable ->
                logger.error(throwable as? Exception) { "An error occurred" }
                taskCompleted()
            }

    @Throws(InterruptedException::class)
    private fun stop() {
        stopLatch.await()
        executor.shutdownNow()
    }

    private fun taskCompleted() {
        stopLatch.countDown()
    }

    companion object {
        /**
         * Program entry point.
         *
         * @param args arguments
         * @throws InterruptedException if main thread is interrupted.
         */
        @JvmStatic
        @Throws(InterruptedException::class)
        fun main(args: Array<String>?) {
            val app = App()
            try {
                app.promiseUsage()
            } finally {
                app.stop()
            }
        }
    }
}

/**
 * Program entry point (top-level Kotlin function).
 */
@Throws(InterruptedException::class)
fun main(args: Array<String> = emptyArray()) {
    App.main(args)
}
