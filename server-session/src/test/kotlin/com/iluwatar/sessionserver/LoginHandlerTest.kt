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

// ABOUTME: Unit tests for LoginHandler verifying session creation and response handling.
// ABOUTME: Uses a test HttpExchange implementation to validate session ID generation.
package com.iluwatar.sessionserver

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpContext
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpPrincipal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.URI
import java.time.Instant

/** LoginHandlerTest. */
class LoginHandlerTest {

    private lateinit var loginHandler: LoginHandler
    private lateinit var sessions: MutableMap<String, Int>
    private lateinit var sessionCreationTimes: MutableMap<String, Instant>

    /** Setup tests. */
    @BeforeEach
    fun setUp() {
        sessions = mutableMapOf()
        sessionCreationTimes = mutableMapOf()
        loginHandler = LoginHandler(sessions, sessionCreationTimes)
    }

    @Test
    fun testHandle() {
        // assemble
        val outputStream = ByteArrayOutputStream()
        val responseHeaders = Headers()
        val exchange = TestHttpExchange(
            responseBodyStream = outputStream,
            responseHeaders = responseHeaders
        )

        // act
        loginHandler.handle(exchange)

        // assert
        val response = outputStream.toString().split("Session ID: ")
        assertEquals(sessions.entries.first().key, response[1])
    }

    /**
     * Test implementation of HttpExchange for unit testing purposes.
     */
    private class TestHttpExchange(
        private val requestHeaders: Headers = Headers(),
        private val responseHeaders: Headers = Headers(),
        private val requestBodyStream: InputStream = ByteArrayInputStream(ByteArray(0)),
        private val responseBodyStream: OutputStream = ByteArrayOutputStream()
    ) : HttpExchange() {
        private var responseCode: Int = 0
        private var responseLength: Long = 0

        override fun getRequestHeaders(): Headers = requestHeaders
        override fun getResponseHeaders(): Headers = responseHeaders
        override fun getRequestURI(): URI = URI.create("/login")
        override fun getRequestMethod(): String = "POST"
        override fun getHttpContext(): HttpContext? = null
        override fun close() {}
        override fun getRequestBody(): InputStream = requestBodyStream
        override fun getResponseBody(): OutputStream = responseBodyStream
        override fun sendResponseHeaders(rCode: Int, responseLength: Long) {
            this.responseCode = rCode
            this.responseLength = responseLength
        }
        override fun getRemoteAddress(): InetSocketAddress = InetSocketAddress(8080)
        override fun getResponseCode(): Int = responseCode
        override fun getLocalAddress(): InetSocketAddress = InetSocketAddress(8080)
        override fun getProtocol(): String = "HTTP/1.1"
        override fun getAttribute(name: String?): Any? = null
        override fun setAttribute(name: String?, value: Any?) {}
        override fun setStreams(i: InputStream?, o: OutputStream?) {}
        override fun getPrincipal(): HttpPrincipal? = null
    }
}
