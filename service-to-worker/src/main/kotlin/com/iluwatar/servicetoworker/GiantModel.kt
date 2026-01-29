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

// ABOUTME: Model class representing a giant with name and delegating state to the MVC GiantModel.
// ABOUTME: Provides accessors for health, fatigue, and nourishment properties.

import com.iluwatar.model.view.controller.Fatigue
import com.iluwatar.model.view.controller.Health
import com.iluwatar.model.view.controller.Nourishment
import com.iluwatar.model.view.controller.GiantModel as MvcGiantModel

/**
 * GiantModel contains the giant data.
 *
 * @param name the name
 * @param health the health
 * @param fatigue the fatigue
 * @param nourishment the nourishment
 */
class GiantModel internal constructor(
    val name: String,
    health: Health,
    fatigue: Fatigue,
    nourishment: Nourishment
) {
    private val model = MvcGiantModel(health, fatigue, nourishment)

    /** Gets health. */
    internal var health: Health
        get() = model.health!!
        set(value) {
            model.health = value
        }

    /** Gets fatigue. */
    internal var fatigue: Fatigue
        get() = model.fatigue!!
        set(value) {
            model.fatigue = value
        }

    /** Gets nourishment. */
    internal var nourishment: Nourishment
        get() = model.nourishment!!
        set(value) {
            model.nourishment = value
        }

    override fun toString(): String =
        "Giant $name, The giant looks ${model.health}, ${model.fatigue} and ${model.nourishment}."
}
