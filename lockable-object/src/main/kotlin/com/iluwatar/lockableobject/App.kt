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

// ABOUTME: Main application demonstrating the Lockable Object concurrency pattern.
// ABOUTME: Creates creatures that compete to acquire a shared lockable sword resource.
package com.iluwatar.lockableobject

import com.iluwatar.lockableobject.domain.Creature
import com.iluwatar.lockableobject.domain.Elf
import com.iluwatar.lockableobject.domain.Feind
import com.iluwatar.lockableobject.domain.Human
import com.iluwatar.lockableobject.domain.Orc
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * The Lockable Object pattern is a concurrency pattern. Instead of using the "synchronized" word
 * upon the methods to be synchronized, the object which implements the Lockable interface handles
 * the request.
 *
 * In this example, we create a new Lockable object with the SwordOfAragorn implementation of it.
 * Afterward we create 6 Creatures with the Elf, Orc and Human implementations and assign them each
 * to a Fiend object and the Sword is the target object. Because there is only one Sword, and it
 * uses the Lockable Object pattern, only one creature can hold the sword at a given time. When the
 * sword is locked, any other alive Fiends will try to lock, which will result in a race to lock the
 * sword.
 */
class App : Runnable {

    companion object {
        private const val WAIT_TIME = 3L
        private const val WORKERS = 2
        private const val MULTIPLICATION_FACTOR = 3
    }

    override fun run() {
        // The target object for this example.
        val sword = SwordOfAragorn()
        // Creation of creatures.
        val creatures = mutableListOf<Creature>()
        for (i in 0 until WORKERS) {
            creatures.add(Elf("Elf $i"))
            creatures.add(Orc("Orc $i"))
            creatures.add(Human("Human $i"))
        }
        val totalFiends = WORKERS * MULTIPLICATION_FACTOR
        val service = Executors.newFixedThreadPool(totalFiends)
        // Attach every creature and the sword is a Fiend to fight for the sword.
        var i = 0
        while (i < totalFiends) {
            service.submit(Feind(creatures[i], sword))
            service.submit(Feind(creatures[i + 1], sword))
            service.submit(Feind(creatures[i + 2], sword))
            i += MULTIPLICATION_FACTOR
        }
        // Wait for program to terminate.
        try {
            if (!service.awaitTermination(WAIT_TIME, TimeUnit.SECONDS)) {
                logger.info { "The master of the sword is now ${sword.getLocker()?.name}." }
            }
        } catch (e: InterruptedException) {
            logger.error { e.message }
            Thread.currentThread().interrupt()
        } finally {
            service.shutdown()
        }
    }
}

/**
 * Main method.
 *
 * @param args as arguments for the main method.
 */
fun main(args: Array<String>) {
    val app = App()
    app.run()
}
