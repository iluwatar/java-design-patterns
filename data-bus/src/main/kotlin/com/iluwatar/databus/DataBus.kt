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

// ABOUTME: Singleton implementation of the Data-Bus pattern for event distribution.
// ABOUTME: Allows members to subscribe/unsubscribe and publishes events to all subscribers.

package com.iluwatar.databus

/**
 * The Data-Bus implementation.
 *
 * This implementation uses a Singleton via Kotlin object.
 */
class DataBus private constructor() {

    private val listeners: MutableSet<Member> = HashSet()

    /**
     * Register a member with the data-bus to start receiving events.
     *
     * @param member The member to register
     */
    fun subscribe(member: Member) {
        listeners.add(member)
    }

    /**
     * Deregister a member to stop receiving events.
     *
     * @param member The member to deregister
     */
    fun unsubscribe(member: Member) {
        listeners.remove(member)
    }

    /**
     * Publish an event to all members.
     *
     * @param event The event
     */
    fun publish(event: DataType) {
        event.dataBus = this
        listeners.forEach { listener -> listener.accept(event) }
    }

    companion object {
        @JvmStatic
        val instance: DataBus = DataBus()
    }
}
