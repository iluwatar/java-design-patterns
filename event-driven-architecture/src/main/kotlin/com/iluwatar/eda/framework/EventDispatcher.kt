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

// ABOUTME: Routes Event messages to their associated handlers using a type-based mapping.
// ABOUTME: Uses a HashMap to store the association between event types and their handlers.
package com.iluwatar.eda.framework

import kotlin.reflect.KClass

/**
 * Handles the routing of [Event] messages to associated handlers. A HashMap is used
 * to store the association between events and their respective handlers.
 */
open class EventDispatcher {

    private val handlers: MutableMap<KClass<out Event>, Handler<out Event>> = HashMap()

    /**
     * Links an [Event] to a specific [Handler].
     *
     * @param eventType The [Event] to be registered
     * @param handler The [Handler] that will be handling the [Event]
     */
    fun <E : Event> registerHandler(eventType: KClass<E>, handler: Handler<E>) {
        handlers[eventType] = handler
    }

    /**
     * Dispatches an [Event] depending on its type.
     *
     * @param event The [Event] to be dispatched
     */
    @Suppress("UNCHECKED_CAST")
    open fun <E : Event> dispatch(event: E) {
        val handler = handlers[event::class] as? Handler<E>
        handler?.onEvent(event)
    }
}
