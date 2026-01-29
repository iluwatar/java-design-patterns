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

// ABOUTME: Manages the current state and notifies registered listeners of state changes.
// ABOUTME: Implements the ListenerManager interface for the BLoC pattern.
package com.iluwatar.bloc

/**
 * The Bloc class is responsible for managing the current state and notifying registered listeners
 * whenever the state changes. It implements the ListenerManager interface, allowing listeners to be
 * added, removed, and notified of state changes.
 */
class Bloc : ListenerManager<State> {
    private var currentState: State = State(0)
    private val listeners: MutableList<StateListener<State>> = mutableListOf()

    /**
     * Adds a listener to receive state change notifications.
     *
     * @param listener the listener to add
     */
    override fun addListener(listener: StateListener<State>) {
        listeners.add(listener)
        listener.onStateChange(currentState)
    }

    /**
     * Removes a listener from receiving state change notifications.
     *
     * @param listener the listener to remove
     */
    override fun removeListener(listener: StateListener<State>) {
        listeners.remove(listener)
    }

    /**
     * Returns an unmodifiable list of all registered listeners.
     *
     * @return an unmodifiable list of listeners
     */
    override fun getListeners(): List<StateListener<State>> = listeners.toList()

    /**
     * Emits a new state and notifies all registered listeners of the change.
     *
     * @param newState the new state to emit
     */
    private fun emitState(newState: State) {
        currentState = newState
        listeners.forEach { it.onStateChange(currentState) }
    }

    /**
     * Increments the current state value by 1 and notifies listeners of the change.
     */
    fun increment() {
        emitState(State(currentState.value + 1))
    }

    /**
     * Decrements the current state value by 1 and notifies listeners of the change.
     */
    fun decrement() {
        emitState(State(currentState.value - 1))
    }
}