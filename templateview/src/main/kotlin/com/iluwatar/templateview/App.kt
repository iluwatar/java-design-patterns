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
// ABOUTME: Entry point demonstrating the Template View pattern.
// ABOUTME: Renders HomePageView and ContactPageView to show consistent layout with dynamic content.
package com.iluwatar.templateview

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Template View defines a consistent layout for rendering views, delegating dynamic content
 * rendering to subclasses.
 *
 * In this example, the [TemplateView] class provides the skeleton for rendering views with
 * a header, dynamic content, and a footer. Subclasses [HomePageView] and [ContactPageView]
 * define the specific dynamic content for their respective views.
 *
 * The [main] function demonstrates the usage of the Template View Pattern by rendering
 * instances of [HomePageView] and [ContactPageView].
 */
fun main() {
    // Create and render the HomePageView
    val homePage: TemplateView = HomePageView()
    logger.info { "Rendering HomePage:" }
    homePage.render()

    // Create and render the ContactPageView
    val contactPage: TemplateView = ContactPageView()
    logger.info { "\nRendering ContactPage:" }
    contactPage.render()
}
