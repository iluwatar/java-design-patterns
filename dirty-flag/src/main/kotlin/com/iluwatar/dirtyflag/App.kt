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
package com.iluwatar.dirtyflag

// ABOUTME: Entry point demonstrating the Dirty Flag pattern with periodic data fetching.
// ABOUTME: Schedules a task that fetches country data from a file every 15 seconds.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * This application demonstrates the **Dirty Flag** pattern. The dirty flag behavioral pattern
 * allows you to avoid expensive operations that would just need to be done again anyway. This is a
 * simple pattern that really just explains how to add a bool value to your class that you can set
 * anytime a property changes. This will let your class know that any results it may have previously
 * calculated will need to be calculated again when they're requested. Once the results are
 * re-calculated, then the bool value can be cleared.
 *
 * There are some points that need to be considered before diving into using this pattern:-
 * (1) Do you need it? This design pattern works well when the results to be calculated are
 * difficult or resource intensive to compute. You want to save them. You also don't want to be
 * calculating them several times in a row when only the last one counts.
 * (2) When do you set the dirty flag? Make sure that you set the dirty flag within the class itself
 * whenever an important property changes. This property should affect the result of the calculated
 * result and by changing the property, that makes the last result invalid.
 * (3) When do you clear the dirty flag? It might seem obvious that the dirty flag should be cleared
 * whenever the result is calculated with up-to-date information but there are other times when you
 * might want to clear the flag.
 *
 * In this example, the [DataFetcher] holds the *dirty flag*. It fetches and re-fetches from
 * *world.txt* when needed. [World] mainly serves the data to the front-end.
 */
class App {

    /** Program execution point. */
    fun run() {
        val executorService = Executors.newSingleThreadScheduledExecutor()
        try {
            val world = World()
            executorService.scheduleAtFixedRate(
                {
                    val countries = world.fetch()
                    logger.info { "Our world currently has the following countries:-" }
                    countries.forEach { country -> logger.info { "\t$country" } }
                },
                0,
                15,
                TimeUnit.SECONDS
            )

            // Keep running for 45 seconds before shutdown (for demo purpose)
            TimeUnit.SECONDS.sleep(45)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            logger.error(e) { "Thread was interrupted" }
        } finally {
            executorService.shutdown()
        }
    }
}

/**
 * Program entry point.
 *
 * @param args command line args
 */
fun main(args: Array<String> = emptyArray()) {
    val app = App()
    app.run()
}
