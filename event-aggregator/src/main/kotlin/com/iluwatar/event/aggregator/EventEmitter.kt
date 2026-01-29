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

// ABOUTME: Abstract base class for event producers that can be observed.
// ABOUTME: Manages observer registration and notification for specific event types.
package com.iluwatar.event.aggregator

/**
 * EventEmitter is the base class for event producers that can be observed.
 */
abstract class EventEmitter {

    private val observerLists: MutableMap<Event, MutableList<EventObserver>> = mutableMapOf()

    constructor()

    constructor(obs: EventObserver, e: Event) : this() {
        registerObserver(obs, e)
    }

    /**
     * Registers observer for specific event in the related list.
     *
     * @param obs the observer that observers this emitter
     * @param e the specific event for that observation occurs
     */
    fun registerObserver(obs: EventObserver, e: Event) {
        observerLists.getOrPut(e) { mutableListOf() }.let { observers ->
            if (obs !in observers) {
                observers.add(obs)
            }
        }
    }

    internal fun notifyObservers(e: Event) {
        observerLists[e]?.forEach { observer -> observer.onEvent(e) }
    }

    abstract fun timePasses(day: Weekday)
}
