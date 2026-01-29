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
package com.iluwatar.servicetoworker

// ABOUTME: Dispatcher that encapsulates worker and view selection based on request information.
// ABOUTME: Manages a list of actions and routes commands to the appropriate action handler.

/**
 * The type Dispatcher, which encapsulates worker and view selection based on request information
 * and/or an internal navigation model.
 *
 * @param giantView the giant view
 */
class Dispatcher(val giantView: GiantView) {

    private val actions = mutableListOf<Action>()

    /**
     * Add an action.
     *
     * @param action the action
     */
    internal fun addAction(action: Action) {
        actions.add(action)
    }

    /**
     * Perform an action.
     *
     * @param s the command
     * @param actionIndex the action index
     */
    fun performAction(s: Command, actionIndex: Int) {
        actions[actionIndex].updateModel(s)
    }

    /**
     * Update view.
     *
     * @param giantModel the giant model
     */
    fun updateView(giantModel: GiantModel) {
        giantView.displayGiant(giantModel)
    }
}
