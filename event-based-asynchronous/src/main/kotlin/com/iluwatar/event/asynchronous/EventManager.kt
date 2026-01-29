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

import java.security.SecureRandom
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

// ABOUTME: Manages a pool of event threads with support for sync and async events.
// ABOUTME: Handles event lifecycle including creation, start, cancel, and status tracking.

/**
 * EventManager handles and maintains a pool of event threads. [AsyncEvent] threads are
 * created upon user request. There are two types of events; Asynchronous and Synchronous.
 * There can be multiple Asynchronous events running at once but only one Synchronous event
 * running at a time. Currently supported event operations are: start, stop, and getStatus.
 * Once an event is complete, it then notifies EventManager through a listener. The EventManager
 * then takes the event out of the pool.
 */
class EventManager : ThreadCompleteListener {

    private var currentlyRunningSyncEvent = -1
    private val rand = SecureRandom()
    val eventPool: MutableMap<Int, AsyncEvent> = ConcurrentHashMap(MAX_RUNNING_EVENTS)

    /**
     * Create a Synchronous event.
     *
     * @param eventTime Time an event should run for.
     * @return eventId
     * @throws MaxNumOfEventsAllowedException When too many events are running at a time.
     * @throws InvalidOperationException No new synchronous events can be created when one is already running.
     * @throws LongRunningEventException Long-running events are not allowed in the app.
     */
    @Throws(MaxNumOfEventsAllowedException::class, InvalidOperationException::class, LongRunningEventException::class)
    fun create(eventTime: Duration): Int {
        if (currentlyRunningSyncEvent != -1) {
            throw InvalidOperationException(
                "Event [$currentlyRunningSyncEvent] is still running. Please wait until it finishes and try again."
            )
        }

        val eventId = createEvent(eventTime, isSynchronous = true)
        currentlyRunningSyncEvent = eventId

        return eventId
    }

    /**
     * Create an Asynchronous event.
     *
     * @param eventTime Time an event should run for.
     * @return eventId
     * @throws MaxNumOfEventsAllowedException When too many events are running at a time.
     * @throws LongRunningEventException Long-running events are not allowed in the app.
     */
    @Throws(MaxNumOfEventsAllowedException::class, LongRunningEventException::class)
    fun createAsync(eventTime: Duration): Int {
        return createEvent(eventTime, isSynchronous = false)
    }

    @Throws(MaxNumOfEventsAllowedException::class, LongRunningEventException::class)
    private fun createEvent(eventTime: Duration, isSynchronous: Boolean): Int {
        require(!eventTime.isNegative) { "eventTime cannot be negative" }

        if (eventPool.size == MAX_RUNNING_EVENTS) {
            throw MaxNumOfEventsAllowedException(
                "Too many events are running at the moment. Please try again later."
            )
        }

        if (eventTime.seconds > MAX_EVENT_TIME.seconds) {
            throw LongRunningEventException(
                "Maximum event time allowed is $MAX_EVENT_TIME seconds. Please try again."
            )
        }

        val newEventId = generateId()

        val newEvent = AsyncEvent(newEventId, eventTime, isSynchronous)
        newEvent.addListener(this)
        eventPool[newEventId] = newEvent

        return newEventId
    }

    /**
     * Starts event.
     *
     * @param eventId The event that needs to be started.
     * @throws EventDoesNotExistException If event does not exist in our eventPool.
     */
    @Throws(EventDoesNotExistException::class)
    fun start(eventId: Int) {
        if (!eventPool.containsKey(eventId)) {
            throw EventDoesNotExistException("$eventId$DOES_NOT_EXIST")
        }

        eventPool[eventId]?.start()
    }

    /**
     * Stops event.
     *
     * @param eventId The event that needs to be stopped.
     * @throws EventDoesNotExistException If event does not exist in our eventPool.
     */
    @Throws(EventDoesNotExistException::class)
    fun cancel(eventId: Int) {
        if (!eventPool.containsKey(eventId)) {
            throw EventDoesNotExistException("$eventId$DOES_NOT_EXIST")
        }

        if (eventId == currentlyRunningSyncEvent) {
            currentlyRunningSyncEvent = -1
        }

        eventPool[eventId]?.stop()
        eventPool.remove(eventId)
    }

    /**
     * Get status of a running event.
     *
     * @param eventId The event to inquire status of.
     * @throws EventDoesNotExistException If event does not exist in our eventPool.
     */
    @Throws(EventDoesNotExistException::class)
    fun status(eventId: Int) {
        if (!eventPool.containsKey(eventId)) {
            throw EventDoesNotExistException("$eventId$DOES_NOT_EXIST")
        }

        eventPool[eventId]?.status()
    }

    /**
     * Gets status of all running events.
     */
    fun statusOfAllEvents() {
        eventPool.values.forEach { it.status() }
    }

    /**
     * Stop all running events.
     */
    fun shutdown() {
        eventPool.values.forEach { it.stop() }
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive. The difference between min and
     * max can be at most `Integer.MAX_VALUE - 1`.
     */
    private fun generateId(): Int {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        var randomNum = rand.nextInt((MAX_ID - MIN_ID) + 1) + MIN_ID
        while (eventPool.containsKey(randomNum)) {
            randomNum = rand.nextInt((MAX_ID - MIN_ID) + 1) + MIN_ID
        }

        return randomNum
    }

    /**
     * Callback from an [AsyncEvent] (once it is complete). The Event is then removed from the pool.
     */
    override fun completedEventHandler(eventId: Int) {
        eventPool[eventId]?.status()
        if (eventPool[eventId]?.isSynchronous == true) {
            currentlyRunningSyncEvent = -1
        }
        eventPool.remove(eventId)
    }

    /**
     * Get number of currently running Synchronous events.
     */
    fun numOfCurrentlyRunningSyncEvent(): Int {
        return currentlyRunningSyncEvent
    }

    companion object {
        const val MAX_RUNNING_EVENTS = 1000
        // Just don't want to have too many running events. :)
        const val MIN_ID = 1
        const val MAX_ID = MAX_RUNNING_EVENTS
        val MAX_EVENT_TIME: Duration = Duration.ofSeconds(1800) // 30 minutes.
        private const val DOES_NOT_EXIST = " does not exist."
    }
}
