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

// ABOUTME: Main application entry point demonstrating the Front Controller pattern.
// ABOUTME: Routes requests through FrontController to appropriate views (Archer, Catapult, Error).
package com.iluwatar.front.controller

/**
 * The Front Controller is a presentation tier pattern. Essentially, it defines a controller that
 * handles all requests for a website.
 *
 * The Front Controller pattern consolidates request handling through a single handler object
 * ([FrontController]). This object can carry out common behavior such as authorization,
 * request logging and routing requests to corresponding views.
 *
 * Typically, the requests are mapped to command objects ([Command]) which then display the
 * correct view ([View]).
 *
 * In this example we have implemented two views: [ArcherView] and [CatapultView].
 * These are displayed by sending correct request to the [FrontController] object. For
 * example, the [ArcherView] gets displayed when [FrontController] receives request
 * "Archer". When the request is unknown, we display the error view ([ErrorView]).
 */
fun main() {
    val controller = FrontController()
    controller.handleRequest("Archer")
    controller.handleRequest("Catapult")
    controller.handleRequest("foobar")
}
