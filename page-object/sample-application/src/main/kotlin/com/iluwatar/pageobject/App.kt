/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppala
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

package com.iluwatar.pageobject

// ABOUTME: Entry point for the Page Object pattern sample application.
// ABOUTME: Opens the login HTML page in the default desktop browser.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.awt.Desktop
import java.io.File
import java.io.IOException

private val logger = KotlinLogging.logger {}

/**
 * Page Object pattern wraps a UI component with an application-specific API allowing you to
 * manipulate the UI elements without having to dig around with the underlying UI technology used.
 * This is especially useful for testing as it means your tests will be less brittle. Your tests can
 * concentrate on the actual test cases whereas the manipulation of the UI can be left to the
 * internals of the page object itself.
 *
 * Due to this reason, it has become very popular within the test automation community. In
 * particular, it is very common in that the page object is used to represent the html pages of a
 * web application that is under test. This web application is referred to as AUT (Application Under
 * Test). A web browser automation tool/framework like Selenium for instance, is then used to drive
 * the automating of the browser navigation and user actions journeys through this web application.
 * Your test class would therefore only be responsible for particular test cases and page object
 * would be used by the test class for UI manipulation required for the tests.
 *
 * In this implementation rather than using Selenium, the HtmlUnit library is used as a
 * replacement to represent the specific html elements and to drive the browser. The purpose of this
 * example is just to provide a simple version that showcase the intentions of this pattern and how
 * this pattern is used in order to understand it.
 */
fun main() {
    try {
        val classLoader = object {}.javaClass.classLoader
        val applicationFile = File(classLoader.getResource("sample-ui/login.html")!!.path)

        // should work for unix like OS (mac, unix etc...)
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(applicationFile)
        } else {
            // java Desktop not supported - above unlikely to work for Windows so try instead...
            @Suppress("DEPRECATION")
            Runtime.getRuntime().exec("cmd.exe start $applicationFile")
        }
    } catch (ex: IOException) {
        logger.error(ex) { "An error occurred." }
    }
}
