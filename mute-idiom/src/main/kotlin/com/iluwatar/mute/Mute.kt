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
package com.iluwatar.mute

// ABOUTME: Provides utility functions implementing the mute idiom for exception suppression.
// ABOUTME: Offers mute() for unexpected exceptions (throws AssertionError) and loggedMute() for logging and swallowing.

import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * A utility object that allows you to utilize the mute idiom.
 *
 * The [mute] function executes a block and wraps any exception in an [AssertionError].
 * It should be used when an API declares a checked exception that cannot actually occur,
 * for instance [ByteArrayOutputStream.write] declares [IOException] but never throws it.
 *
 * The [loggedMute] function executes a block and prints the stack trace of any exception.
 * It should be used when the only reasonable handling is to log the error, such as
 * when closing a resource.
 */
object Mute {

    /**
     * Executes the [runnable] and throws any exception wrapped in an [AssertionError].
     * This should be used to mute operations that are guaranteed not to throw an exception.
     *
     * @param runnable a block that should never throw an exception on execution.
     */
    @JvmStatic
    fun mute(runnable: () -> Unit) {
        try {
            runnable()
        } catch (e: Exception) {
            throw AssertionError(e)
        }
    }

    /**
     * Executes the [runnable] and logs the exception trace before swallowing it.
     * This should be used to mute operations where the best you can do is log the error,
     * such as closing a database connection or cleaning up a resource.
     *
     * @param runnable a block that may throw an exception on execution.
     */
    @JvmStatic
    fun loggedMute(runnable: () -> Unit) {
        try {
            runnable()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
