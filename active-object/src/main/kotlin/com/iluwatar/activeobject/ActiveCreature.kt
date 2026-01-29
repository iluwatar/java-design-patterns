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

// ABOUTME: Base class for the Active Object pattern implementation.
// ABOUTME: Uses a BlockingQueue to synchronize method calls via Runnable invocators.
package com.iluwatar.activeobject

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

private val logger = KotlinLogging.logger {}

/**
 * ActiveCreature class is the base of the active object example.
 */
abstract class ActiveCreature(
    private val name: String,
) {
    private val requests: BlockingQueue<Runnable> = LinkedBlockingQueue()

    private val thread: Thread

    @Volatile
    internal var status: Int = 0
        private set

    init {
        thread =
            Thread {
                var infinite = true
                while (infinite) {
                    try {
                        requests.take().run()
                    } catch (e: InterruptedException) {
                        if (status != 0) {
                            logger.error { "Thread was interrupted. --> ${e.message}" }
                        }
                        infinite = false
                        Thread.currentThread().interrupt()
                    }
                }
            }
        thread.start()
    }

    /**
     * Eats the porridge.
     *
     * @throws InterruptedException due to firing a new Runnable.
     */
    @Throws(InterruptedException::class)
    fun eat() {
        requests.put(
            Runnable {
                logger.info { "${name()} is eating!" }
                logger.info { "${name()} has finished eating!" }
            },
        )
    }

    /**
     * Roam the wastelands.
     *
     * @throws InterruptedException due to firing a new Runnable.
     */
    @Throws(InterruptedException::class)
    fun roam() {
        requests.put(
            Runnable {
                logger.info { "${name()} has started to roam in the wastelands." }
            },
        )
    }

    /**
     * Returns the name of the creature.
     *
     * @return the name of the creature.
     */
    fun name(): String = name

    /**
     * Kills the thread of execution.
     *
     * @param status of the thread of execution. 0 == OK, the rest is logging an error.
     */
    fun kill(status: Int) {
        this.status = status
        thread.interrupt()
    }
}