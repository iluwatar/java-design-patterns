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

// ABOUTME: Main application demonstrating the Event Aggregator design pattern.
// ABOUTME: Shows how multiple event sources can be aggregated through a central handler.
package com.iluwatar.event.aggregator

/**
 * A system with lots of objects can lead to complexities when a client wants to subscribe to
 * events. The client has to find and register for each object individually, if each object has
 * multiple events then each event requires a separate subscription.
 *
 * An Event Aggregator acts as a single source of events for many objects. It registers for all
 * the events of the many objects allowing clients to register with just the aggregator.
 *
 * In the example [LordBaelish], [LordVarys] and [Scout] deliver events to
 * [KingsHand]. [KingsHand], the event aggregator, then delivers the events to
 * [KingJoffrey].
 */
fun main() {
    val kingJoffrey = KingJoffrey()

    val kingsHand = KingsHand()
    kingsHand.registerObserver(kingJoffrey, Event.TRAITOR_DETECTED)
    kingsHand.registerObserver(kingJoffrey, Event.STARK_SIGHTED)
    kingsHand.registerObserver(kingJoffrey, Event.WARSHIPS_APPROACHING)
    kingsHand.registerObserver(kingJoffrey, Event.WHITE_WALKERS_SIGHTED)

    val varys = LordVarys()
    varys.registerObserver(kingsHand, Event.TRAITOR_DETECTED)
    varys.registerObserver(kingsHand, Event.WHITE_WALKERS_SIGHTED)

    val scout = Scout()
    scout.registerObserver(kingsHand, Event.WARSHIPS_APPROACHING)
    scout.registerObserver(varys, Event.WHITE_WALKERS_SIGHTED)

    val baelish = LordBaelish(kingsHand, Event.STARK_SIGHTED)

    val emitters = listOf(kingsHand, baelish, varys, scout)

    Weekday.entries.forEach { day ->
        emitters.forEach { emitter -> emitter.timePasses(day) }
    }
}
