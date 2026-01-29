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

// ABOUTME: Tests for the Inventory class demonstrating double-checked locking correctness.
// ABOUTME: Verifies thread-safe item addition and inventory size constraints under concurrent access.

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTimeout
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.time.Duration.ofMillis
import java.util.LinkedList
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/** InventoryTest */
class InventoryTest {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender(Inventory::class.java)
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    companion object {
        /**
         * The number of threads used to stress test the locking of the [Inventory.addItem]
         * method
         */
        private const val THREAD_COUNT = 8

        /** The maximum number of [Item]s allowed in the [Inventory] */
        private const val INVENTORY_SIZE = 1000
    }

    /**
     * Concurrently add multiple items to the inventory, and check if the items were added in order by
     * checking the stdOut for continuous growth of the inventory. When 'items.size()=xx' shows up out
     * of order, it means that the locking is not ok, increasing the risk of going over the inventory
     * item limit.
     */
    @Test
    fun testAddItem() {
        assertTimeout(ofMillis(10000)) {
            // Create a new inventory with a limit of 1000 items and put some load on the add method
            val inventory = Inventory(INVENTORY_SIZE)
            val executorService = Executors.newFixedThreadPool(THREAD_COUNT)
            repeat(THREAD_COUNT) {
                executorService.execute {
                    while (inventory.addItem(Item())) {
                        // Keep adding items
                    }
                }
            }

            // Wait until all threads have finished
            executorService.shutdown()
            executorService.awaitTermination(5, TimeUnit.SECONDS)

            // Check the number of items in the inventory. It should not have exceeded the allowed
            // maximum
            val items = inventory.getItems()
            assertNotNull(items)
            assertEquals(INVENTORY_SIZE, items.size)

            assertEquals(INVENTORY_SIZE, appender.logSize)

            // ... and check if the inventory size is increasing continuously
            for (i in items.indices) {
                assertTrue(appender.log[i].formattedMessage.contains("items.size()=${i + 1}"))
            }
        }
    }

    private class InMemoryAppender(clazz: Class<*>) : AppenderBase<ILoggingEvent>() {
        val log: MutableList<ILoggingEvent> = LinkedList()

        init {
            (LoggerFactory.getLogger(clazz) as Logger).addAppender(this)
            start()
        }

        override fun append(eventObject: ILoggingEvent) {
            log.add(eventObject)
        }

        val logSize: Int
            get() = log.size
    }
}
