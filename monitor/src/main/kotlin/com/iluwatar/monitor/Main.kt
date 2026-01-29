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
package com.iluwatar.monitor

// ABOUTME: Entry point demonstrating the Monitor pattern for thread-safe bank operations.
// ABOUTME: Uses a thread pool to run concurrent transfers on bank accounts.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

private val logger = KotlinLogging.logger {}

private const val NUMBER_OF_THREADS = 5
private const val BASE_AMOUNT = 1000
private const val ACCOUNT_NUM = 4

/**
 * The Monitor pattern is used in concurrent algorithms to achieve mutual exclusion.
 *
 * Bank is a simple class that transfers money from an account to another account using [Bank.transfer].
 * It can also return the balance of the bank account stored in the bank.
 *
 * Main function uses ThreadPool to run threads that do transactions on the bank accounts.
 */
fun main() {
    val bank = Bank(ACCOUNT_NUM, BASE_AMOUNT)
    val latch = CountDownLatch(NUMBER_OF_THREADS)
    val executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

    repeat(NUMBER_OF_THREADS) {
        executorService.execute { runner(bank, latch) }
    }

    latch.await()
}

/**
 * Runner to perform a bunch of transfers and handle exception.
 *
 * @param bank bank object
 * @param latch signal finished execution
 */
fun runner(bank: Bank, latch: CountDownLatch) {
    try {
        val random = SecureRandom()
        Thread.sleep(random.nextInt(1000).toLong())
        logger.info { "Start transferring..." }
        repeat(1000000) {
            bank.transfer(random.nextInt(4), random.nextInt(4), random.nextInt(BASE_AMOUNT))
        }
        logger.info { "Finished transferring." }
        latch.countDown()
    } catch (e: InterruptedException) {
        logger.error { e.message }
        Thread.currentThread().interrupt()
    }
}
