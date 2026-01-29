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
package com.iluwatar.event.asynchronous

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration
import java.util.Properties
import java.util.Scanner

// ABOUTME: Demonstrates the Event-based Asynchronous pattern with sync and async event modes.
// ABOUTME: Provides interactive and non-interactive modes for event management operations.

private val logger = KotlinLogging.logger {}

const val PROP_FILE_NAME = "config.properties"

/**
 * This application demonstrates the **Event-based Asynchronous** pattern. Essentially, users (of
 * the pattern) may choose to run events in an Asynchronous or Synchronous mode. There can be
 * multiple Asynchronous events running at once but only one Synchronous event can run at a time.
 * Asynchronous events are synonymous to multi-threads. The key point here is that the threads run
 * in the background and the user is free to carry on with other processes. Once an event is
 * complete, the appropriate listener/callback method will be called. The listener then proceeds to
 * carry out further processing depending on the needs of the user.
 *
 * The [EventManager] manages the events/threads that the user creates. Currently, the
 * supported event operations are: `start`, `stop`, `getStatus`.
 * For Synchronous events, the user is unable to start another (Synchronous) event if one is already
 * running at the time. The running event would have to either be stopped or completed before a new
 * event can be started.
 *
 * The Event-based Asynchronous Pattern makes available the advantages of multithreaded
 * applications while hiding many of the complex issues inherent in multithreaded design. Using a
 * class that supports this pattern can allow you to:- (1) Perform time-consuming tasks, such as
 * downloads and database operations, "in the background," without interrupting your application.
 * (2) Execute multiple operations simultaneously, receiving notifications when each completes. (3)
 * Wait for resources to become available without stopping ("hanging") your application. (4)
 * Communicate with pending asynchronous operations using the familiar events-and-delegates model.
 *
 * @see EventManager
 * @see AsyncEvent
 */
class App {

    var interactiveMode = false
        internal set

    /**
     * App can run in interactive mode or not. Interactive mode == Allow user interaction with command
     * line. Non-interactive is a quick sequential run through the available [EventManager]
     * operations.
     */
    fun setUp() {
        val prop = Properties()

        val inputStream = App::class.java.classLoader.getResourceAsStream(PROP_FILE_NAME)

        if (inputStream != null) {
            try {
                prop.load(inputStream)
            } catch (e: Exception) {
                logger.error(e) { "$PROP_FILE_NAME was not found. Defaulting to non-interactive mode." }
            }
            val property = prop.getProperty("INTERACTIVE_MODE")
            if (property.equals("YES", ignoreCase = true)) {
                interactiveMode = true
            }
        }
    }

    /**
     * Run program in either interactive mode or not.
     */
    fun run() {
        if (interactiveMode) {
            runInteractiveMode()
        } else {
            quickRun()
        }
    }

    /**
     * Run program in non-interactive mode.
     */
    fun quickRun() {
        val eventManager = EventManager()

        try {
            // Create an Asynchronous event.
            val asyncEventId = eventManager.createAsync(Duration.ofSeconds(60))
            logger.info { "Async Event [$asyncEventId] has been created." }
            eventManager.start(asyncEventId)
            logger.info { "Async Event [$asyncEventId] has been started." }

            // Create a Synchronous event.
            val syncEventId = eventManager.create(Duration.ofSeconds(60))
            logger.info { "Sync Event [$syncEventId] has been created." }
            eventManager.start(syncEventId)
            logger.info { "Sync Event [$syncEventId] has been started." }

            eventManager.status(asyncEventId)
            eventManager.status(syncEventId)

            eventManager.cancel(asyncEventId)
            logger.info { "Async Event [$asyncEventId] has been stopped." }
            eventManager.cancel(syncEventId)
            logger.info { "Sync Event [$syncEventId] has been stopped." }
        } catch (e: MaxNumOfEventsAllowedException) {
            logger.error { e.message }
        } catch (e: LongRunningEventException) {
            logger.error { e.message }
        } catch (e: EventDoesNotExistException) {
            logger.error { e.message }
        } catch (e: InvalidOperationException) {
            logger.error { e.message }
        }
    }

    /**
     * Run program in interactive mode.
     */
    fun runInteractiveMode() {
        val eventManager = EventManager()

        val s = Scanner(System.`in`)
        var option = -1
        while (option != 4) {
            logger.info { "Hello. Would you like to boil some eggs?" }
            logger.info {
                """
                (1) BOIL AN EGG
                (2) STOP BOILING THIS EGG
                (3) HOW ARE MY EGGS?
                (4) EXIT
                """.trimIndent()
            }
            logger.info { "Choose [1,2,3,4]: " }
            option = s.nextInt()

            when (option) {
                1 -> processOption1(eventManager, s)
                2 -> processOption2(eventManager, s)
                3 -> processOption3(eventManager, s)
                4 -> eventManager.shutdown()
            }
        }

        s.close()
    }

    private fun processOption3(eventManager: EventManager, s: Scanner) {
        s.nextLine()
        logger.info { "Just one egg (O) OR all of them (A) ?: " }
        val eggChoice = s.nextLine()

        if (eggChoice.equals("O", ignoreCase = true)) {
            logger.info { "Which egg?: " }
            val eventId = s.nextInt()
            try {
                eventManager.status(eventId)
            } catch (e: EventDoesNotExistException) {
                logger.error { e.message }
            }
        } else if (eggChoice.equals("A", ignoreCase = true)) {
            eventManager.statusOfAllEvents()
        }
    }

    private fun processOption2(eventManager: EventManager, s: Scanner) {
        logger.info { "Which egg?: " }
        val eventId = s.nextInt()
        try {
            eventManager.cancel(eventId)
            logger.info { "Egg [$eventId] is removed from boiler." }
        } catch (e: EventDoesNotExistException) {
            logger.error { e.message }
        }
    }

    private fun processOption1(eventManager: EventManager, s: Scanner) {
        s.nextLine()
        logger.info { "Boil multiple eggs at once (A) or boil them one-by-one (S)?: " }
        val eventType = s.nextLine()
        logger.info { "How long should this egg be boiled for (in seconds)?: " }
        val eventTime = Duration.ofSeconds(s.nextInt().toLong())
        if (eventType.equals("A", ignoreCase = true)) {
            try {
                val eventId = eventManager.createAsync(eventTime)
                eventManager.start(eventId)
                logger.info { "Egg [$eventId] is being boiled." }
            } catch (e: MaxNumOfEventsAllowedException) {
                logger.error { e.message }
            } catch (e: LongRunningEventException) {
                logger.error { e.message }
            } catch (e: EventDoesNotExistException) {
                logger.error { e.message }
            }
        } else if (eventType.equals("S", ignoreCase = true)) {
            try {
                val eventId = eventManager.create(eventTime)
                eventManager.start(eventId)
                logger.info { "Egg [$eventId] is being boiled." }
            } catch (e: MaxNumOfEventsAllowedException) {
                logger.error { e.message }
            } catch (e: InvalidOperationException) {
                logger.error { e.message }
            } catch (e: LongRunningEventException) {
                logger.error { e.message }
            } catch (e: EventDoesNotExistException) {
                logger.error { e.message }
            }
        } else {
            logger.info { "Unknown event type." }
        }
    }
}

/**
 * Program entry point.
 *
 * @param args command line args
 */
fun main(args: Array<String>) {
    val app = App()
    app.setUp()
    app.run()
}
