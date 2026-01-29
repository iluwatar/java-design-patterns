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

// ABOUTME: Data transfer object storing worker registration information (name, occupation, DOB).
// ABOUTME: Carried between layers to reduce the number of method calls and defines validation error constants.

import java.time.LocalDate

/**
 * Data transfer object which stores information about the worker. This is carried between objects
 * and layers to reduce the number of method calls made.
 */
class RegisterWorkerDto : DataTransferObject() {

    var name: String? = null
    var occupation: String? = null
    var dateOfBirth: LocalDate? = null

    /**
     * Simple set up function for capturing our worker information.
     *
     * @param name Name of the worker
     * @param occupation occupation of the worker
     * @param dateOfBirth Date of Birth of the worker
     */
    fun setupWorkerDto(name: String?, occupation: String?, dateOfBirth: LocalDate?) {
        this.name = name
        this.occupation = occupation
        this.dateOfBirth = dateOfBirth
    }

    companion object {
        /** Error for when name field is blank or missing. */
        val MISSING_NAME = NotificationError(1, "Name is missing")

        /** Error for when occupation field is blank or missing. */
        val MISSING_OCCUPATION = NotificationError(2, "Occupation is missing")

        /** Error for when date of birth field is blank or missing. */
        val MISSING_DOB = NotificationError(3, "Date of birth is missing")

        /** Error for when date of birth is less than 18 years ago. */
        val DOB_TOO_SOON = NotificationError(4, "Worker registered must be over 18")
    }
}
