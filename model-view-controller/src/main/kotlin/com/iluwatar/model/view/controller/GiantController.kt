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

// ABOUTME: GiantController represents the Controller in the MVC pattern.
// ABOUTME: Handles user input and updates both the model and view accordingly.
package com.iluwatar.model.view.controller

/**
 * GiantController can update the giant data and redraw it using the view.
 */
class GiantController(
    private val giant: GiantModel,
    private val view: GiantView,
) {
    var health: Health?
        get() = giant.health
        set(value) {
            giant.health = value
        }

    var fatigue: Fatigue?
        get() = giant.fatigue
        set(value) {
            giant.fatigue = value
        }

    var nourishment: Nourishment?
        get() = giant.nourishment
        set(value) {
            giant.nourishment = value
        }

    fun updateView() {
        view.displayGiant(giant)
    }
}