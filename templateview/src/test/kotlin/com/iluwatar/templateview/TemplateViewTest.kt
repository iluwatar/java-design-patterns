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
// ABOUTME: Tests for the TemplateView abstract class.
// ABOUTME: Verifies that render() calls methods in correct order using spies.
package com.iluwatar.templateview

import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test

class TemplateViewTest {

    @Test
    fun testRenderHomePage() {
        // Create a spy for HomePageView
        val homePage = spyk(HomePageView())

        // Call the render method
        homePage.render()

        // Verify that the steps of rendering are executed in the correct order
        verifyOrder {
            homePage.printHeader() // Header is printed
            homePage.renderDynamicContent() // Dynamic content specific to home page
            homePage.printFooter() // Footer is printed
        }
    }

    @Test
    fun testRenderContactPage() {
        // Create a spy for ContactPageView
        val contactPage = spyk(ContactPageView())

        // Call the render method
        contactPage.render()

        // Verify that the steps of rendering are executed in the correct order
        verifyOrder {
            contactPage.printHeader() // Header is printed
            contactPage.renderDynamicContent() // Dynamic content specific to contact page
            contactPage.printFooter() // Footer is printed
        }
    }
}
