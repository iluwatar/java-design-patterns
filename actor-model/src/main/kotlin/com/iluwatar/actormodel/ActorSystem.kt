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

// ABOUTME: Manages the lifecycle of actors in the actor model pattern.
// ABOUTME: Responsible for creating, starting, registering, and shutting down actors.
package com.iluwatar.actormodel

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * Manages the lifecycle of actors.
 *
 * The ActorSystem is responsible for creating, starting, and managing actors.
 * It provides a thread pool for running actors and maintains a registry for actor lookup.
 */
class ActorSystem {

    private val executor = Executors.newCachedThreadPool()
    private val actorRegister = ConcurrentHashMap<String, Actor>()
    private val idCounter = AtomicInteger(0)

    /**
     * Starts an actor by assigning it a unique ID and running it in a thread.
     *
     * @param actor The actor to start
     */
    fun startActor(actor: Actor) {
        val actorId = "actor-${idCounter.incrementAndGet()}" // Generate a new and unique ID
        actor.actorId = actorId // assign the actor it's ID
        actorRegister[actorId] = actor // Register and save the actor with it's ID
        executor.submit(actor) // Run the actor in a thread
    }

    /**
     * Finds an actor by its ID.
     *
     * @param actorId The ID of the actor to find
     * @return The actor, or null if not found
     */
    fun getActorById(actorId: String): Actor? {
        return actorRegister[actorId]
    }

    /**
     * Shuts down the actor system and stops all threads.
     */
    fun shutdown() {
        executor.shutdownNow()
    }
}
