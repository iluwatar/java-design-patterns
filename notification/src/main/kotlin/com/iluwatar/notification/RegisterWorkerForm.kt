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

// ABOUTME: Presentation layer form for worker registration, linked to the domain layer via DTO.
// ABOUTME: Handles form submission, validation delegation, and error display to the user.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

/**
 * The form submitted by the user, part of the presentation layer, linked to the domain layer
 * through a data transfer object and linked to the service layer directly.
 */
class RegisterWorkerForm(
    internal val name: String?,
    internal val occupation: String?,
    internal val dateOfBirth: LocalDate?
) {
    internal var worker: RegisterWorkerDto? = null
    internal var service: RegisterWorkerService = RegisterWorkerService()

    /** Attempts to submit the form for registering a worker. */
    fun submit() {
        // Transmit information to our transfer object to communicate between layers
        saveToWorker()
        // call the service layer to register our worker
        service.registerWorker(worker!!)

        // check for any errors
        if (worker!!.notification.hasErrors()) {
            indicateErrors()
            logger.info { "Not registered, see errors" }
        } else {
            logger.info { "Registration Succeeded" }
        }
    }

    /** Saves worker information to the data transfer object. */
    private fun saveToWorker() {
        worker = RegisterWorkerDto().apply {
            name = this@RegisterWorkerForm.name
            occupation = this@RegisterWorkerForm.occupation
            dateOfBirth = this@RegisterWorkerForm.dateOfBirth
        }
    }

    /** Check for any errors with form submission and show them to the user. */
    fun indicateErrors() {
        worker!!.notification.errors.forEach { error -> logger.error { error.toString() } }
    }
}
