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

// ABOUTME: Test for SignupModel data class functionality.
// ABOUTME: Verifies that name, email, and password properties work correctly.
package com.iluwatar.page.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** Test for Signup Model */
class SignupModelTest {
    /** Verify if a user can set a name properly */
    @Test
    fun testSetName() {
        val model = SignupModel()
        model.name = "Lily"
        assertEquals("Lily", model.name)
    }

    /** Verify if a user can set an email properly */
    @Test
    fun testSetEmail() {
        val model = SignupModel()
        model.email = "Lily@email"
        assertEquals("Lily@email", model.email)
    }

    /** Verify if a user can set a password properly */
    @Test
    fun testSetPassword() {
        val model = SignupModel()
        model.password = "password1234"
        assertEquals("password1234", model.password)
    }
}
