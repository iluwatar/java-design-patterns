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
package com.iluwatar.throttling

import java.security.InvalidParameterException

// ABOUTME: Represents a bar customer (tenant) with a name and allowed calls per second.
// ABOUTME: Validates parameters and registers the tenant with the CallsCount tracker.

/**
 * BarCustomer is a tenant with a name and a number of allowed calls per second.
 *
 * @param name Name of the BarCustomer
 * @param allowedCallsPerSecond The number of calls allowed for this particular tenant.
 * @param callsCount The CallsCount instance to register this tenant with.
 * @throws InvalidParameterException If number of calls is less than 0, throws exception.
 */
class BarCustomer(
    val name: String,
    val allowedCallsPerSecond: Int,
    callsCount: CallsCount
) {
    init {
        if (allowedCallsPerSecond < 0) {
            throw InvalidParameterException("Number of calls less than 0 not allowed")
        }
        callsCount.addTenant(name)
    }
}
