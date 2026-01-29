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

// ABOUTME: Main application entry point demonstrating the Model-View-Controller pattern.
// ABOUTME: Creates a giant with health, fatigue, and nourishment statuses and displays it via the MVC components.
package com.iluwatar.model.view.controller

/**
 * Model-View-Controller is a pattern for implementing user interfaces. It divides the application
 * into three interconnected parts namely the model, the view and the controller.
 *
 * The central component of MVC, the model, captures the behavior of the application in terms of
 * its problem domain, independent of the user interface. The model directly manages the data, logic
 * and rules of the application. A view can be any output representation of information, such as a
 * chart or a diagram. The third part, the controller, accepts input and converts it to commands for
 * the model or view.
 *
 * In this example we have a giant ([GiantModel]) with statuses for health, fatigue and
 * nourishment. [GiantView] can display the giant with its current status. [GiantController]
 * receives input affecting the model and delegates redrawing the giant to the view.
 */
fun main() {
    // create model, view and controller
    val giant = GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED)
    val view = GiantView()
    val controller = GiantController(giant, view)
    // initial display
    controller.updateView()
    // controller receives some interactions that affect the giant
    controller.health = Health.WOUNDED
    controller.nourishment = Nourishment.HUNGRY
    controller.fatigue = Fatigue.TIRED
    // redisplay
    controller.updateView()
}