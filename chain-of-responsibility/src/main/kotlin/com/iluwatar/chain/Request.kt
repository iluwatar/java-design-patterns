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
package com.iluwatar.chain

// ABOUTME: Represents a request with a type, description, and handled state.
// ABOUTME: A request can only transition from unhandled to handled via markHandled().

/**
 * Request.
 *
 * @param requestType The type of this request, used by each item in the chain to see if they
 *   should or can handle this particular request.
 * @param requestDescription A description of the request.
 */
class Request(
    val requestType: RequestType,
    val requestDescription: String,
) {
    /**
     * Indicates if the request is handled or not. A request can only switch state from unhandled to
     * handled, there's no way to 'unhandle' a request.
     */
    var handled: Boolean = false
        private set

    /** Mark the request as handled. */
    fun markHandled() {
        handled = true
    }

    override fun toString(): String = requestDescription
}