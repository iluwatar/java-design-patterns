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

// ABOUTME: Worker class that processes user input and updates the giant model.
// ABOUTME: Translates commands into model state changes for health, fatigue, and nourishment.

import com.iluwatar.model.view.controller.Fatigue
import com.iluwatar.model.view.controller.Health
import com.iluwatar.model.view.controller.Nourishment

/**
 * The type Action (Worker), which can process user input and perform a specific update on the
 * model.
 *
 * @param giant the giant
 */
class Action(var giant: GiantModel) {

    /**
     * Update model based on command.
     *
     * @param command the command
     */
    fun updateModel(command: Command) {
        fatigue = command.fatigue
        health = command.health
        nourishment = command.nourishment
    }

    /**
     * Sets health.
     */
    var health: Health
        get() = giant.health
        set(value) {
            giant.health = value
        }

    /**
     * Sets fatigue.
     */
    var fatigue: Fatigue
        get() = giant.fatigue
        set(value) {
            giant.fatigue = value
        }

    /**
     * Sets nourishment.
     */
    var nourishment: Nourishment
        get() = giant.nourishment
        set(value) {
            giant.nourishment = value
        }
}
