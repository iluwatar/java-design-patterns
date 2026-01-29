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

package com.iluwatar.doublechecked.locking

// ABOUTME: Entry point demonstrating the double-checked locking pattern with concurrent inventory access.
// ABOUTME: Shows how multiple threads safely add items to a size-limited inventory.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * Double Checked Locking is a concurrency design pattern used to reduce the overhead of acquiring a
 * lock by first testing the locking criterion (the "lock hint") without actually acquiring the
 * lock. Only if the locking criterion check indicates that locking is required does the actual
 * locking logic proceed.
 *
 * In [Inventory] we store the items with a given size. However, we do not store more items
 * than the inventory size. To address concurrent access problems we use double checked locking to
 * add item to inventory. In this method, the thread which gets the lock first adds the item.
 */
fun main() {
    val inventory = Inventory(1000)
    val executorService = Executors.newFixedThreadPool(3)
    repeat(3) {
        executorService.execute {
            while (inventory.addItem(Item())) {
                logger.info { "Adding another item" }
            }
        }
    }

    executorService.shutdown()
    try {
        executorService.awaitTermination(5, TimeUnit.SECONDS)
    } catch (e: InterruptedException) {
        logger.error { "Error waiting for ExecutorService shutdown" }
        Thread.currentThread().interrupt()
    }
}
