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
package com.iluwatar.notification

// ABOUTME: Tests for the RegisterWorkerForm presentation layer class.
// ABOUTME: Verifies successful submission and error collection for invalid form data.

import java.time.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class RegisterWorkerFormTest {

    @Test
    fun submitSuccessfully() {
        // Ensure the worker is null initially
        val registerWorkerForm = RegisterWorkerForm("John Doe", "Engineer", LocalDate.of(1990, 1, 1))

        assertNull(registerWorkerForm.worker)

        // Submit the form
        registerWorkerForm.submit()

        // Verify that the worker is not null after submission
        assertNotNull(registerWorkerForm.worker)

        // Verify that the worker's properties are set correctly
        assertEquals("John Doe", registerWorkerForm.worker!!.name)
        assertEquals("Engineer", registerWorkerForm.worker!!.occupation)
        assertEquals(LocalDate.of(1990, 1, 1), registerWorkerForm.worker!!.dateOfBirth)
    }

    @Test
    fun submitWithErrors() {
        // Set up the worker with a notification containing errors
        val registerWorkerForm = RegisterWorkerForm(null, null, null)

        // Submit the form
        registerWorkerForm.submit()

        // Verify that the worker's properties remain unchanged
        assertNull(registerWorkerForm.worker!!.name)
        assertNull(registerWorkerForm.worker!!.occupation)
        assertNull(registerWorkerForm.worker!!.dateOfBirth)

        // Verify the presence of errors
        assertEquals(4, registerWorkerForm.worker!!.notification.errors.size)
    }
}
