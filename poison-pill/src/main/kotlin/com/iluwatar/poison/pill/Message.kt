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
package com.iluwatar.poison.pill

// ABOUTME: Defines the Message interface for producer-consumer communication.
// ABOUTME: Includes a POISON_PILL singleton to signal termination of message processing.

/**
 * Interface that implements the Message pattern and represents an inbound or outbound message as
 * part of a [Producer]-[Consumer] exchange.
 */
interface Message {

    /** Enumeration of Type of Headers. */
    enum class Headers {
        DATE,
        SENDER
    }

    fun addHeader(header: Headers, value: String)

    fun getHeader(header: Headers): String?

    fun getHeaders(): Map<Headers, String>

    fun setBody(body: String)

    fun getBody(): String?

    companion object {
        val POISON_PILL: Message = object : Message {

            override fun addHeader(header: Headers, value: String) {
                throw poison()
            }

            override fun getHeader(header: Headers): String? {
                throw poison()
            }

            override fun getHeaders(): Map<Headers, String> {
                throw poison()
            }

            override fun setBody(body: String) {
                throw poison()
            }

            override fun getBody(): String? {
                throw poison()
            }

            private fun poison(): RuntimeException {
                return UnsupportedOperationException("Poison")
            }
        }
    }
}
