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

// ABOUTME: Entry point demonstrating the mute idiom pattern for exception suppression.
// ABOUTME: Shows mute() for impossible exceptions and loggedMute() for logging-only exception handling.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.ByteArrayOutputStream
import java.io.IOException

private val logger = KotlinLogging.logger {}

/**
 * Mute pattern is utilized when we need to suppress an exception due to an API flaw or in a
 * situation when all we can do to handle the exception is to log it. This pattern should not be
 * used everywhere. It is very important to logically handle the exceptions in a system, but some
 * situations like the ones described above require this pattern, so that we don't need to repeat
 *
 * ```
 * try {
 *     // code that may throw an exception we need to ignore or may never be thrown
 * } catch (ex: Exception) {
 *     // ignore by logging or throw error if unexpected exception occurs
 * }
 * ```
 *
 * every time we need to ignore an exception.
 */
fun main() {
    useOfLoggedMute()
    useOfMute()
}

/*
 * Typically used when the API declares some exception but cannot do so. Usually a
 * signature mistake. In this example out is not supposed to throw exception as it is a
 * ByteArrayOutputStream. So we utilize mute, which will throw AssertionError if unexpected
 * exception occurs.
 */
private fun useOfMute() {
    val out = ByteArrayOutputStream()
    Mute.mute { out.write("Hello".toByteArray()) }
}

private fun useOfLoggedMute() {
    var resource: Resource? = null
    try {
        resource = acquireResource()
        utilizeResource(resource)
    } finally {
        resource?.let { closeResource(it) }
    }
}

/*
 * All we can do while failed close of a resource is to log it.
 */
private fun closeResource(resource: Resource) {
    Mute.loggedMute { resource.close() }
}

private fun utilizeResource(resource: Resource) {
    logger.info { "Utilizing acquired resource: $resource" }
}

private fun acquireResource(): Resource {
    return object : Resource {
        override fun close() {
            throw IOException("Error in closing resource: $this")
        }
    }
}
