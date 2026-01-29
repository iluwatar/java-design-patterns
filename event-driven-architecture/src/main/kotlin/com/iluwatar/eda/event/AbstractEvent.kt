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

// ABOUTME: Abstract base class for custom events in the event-driven architecture.
// ABOUTME: Provides default implementation of getType() that returns the concrete event class.
package com.iluwatar.eda.event

import com.iluwatar.eda.framework.Event
import kotlin.reflect.KClass

/**
 * The [AbstractEvent] class serves as a base class for defining custom events happening with
 * your system. In this example we have two types of events defined.
 *
 * - [UserCreatedEvent] - used when a user is created
 * - [UserUpdatedEvent] - used when a user is updated
 *
 * Events can be distinguished using the [type] property.
 */
abstract class AbstractEvent : Event {

    /**
     * Returns the event type as a [KClass] object. In this example, this method is used by the
     * EventDispatcher to dispatch events depending on their type.
     *
     * @return the AbstractEvent type as a [KClass].
     */
    override val type: KClass<out Event>
        get() = this::class
}
