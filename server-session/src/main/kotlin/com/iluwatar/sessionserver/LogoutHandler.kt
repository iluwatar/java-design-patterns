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

// ABOUTME: HTTP handler for user logout that removes session data from the server.
// ABOUTME: Extracts session ID from cookie, validates it, and clears the session.
package com.iluwatar.sessionserver

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.IOException
import java.time.Instant

private val logger = KotlinLogging.logger {}

/** LogoutHandler. */
class LogoutHandler(
    private val sessions: MutableMap<String, Int>,
    private val sessionCreationTimes: MutableMap<String, Instant>
) : HttpHandler {

    override fun handle(exchange: HttpExchange) {
        // Get session ID from cookie
        val sessionId = exchange.requestHeaders.getFirst("Cookie").replace("sessionID=", "")
        val currentSessionId = if (sessions[sessionId] == null) null else sessionId

        // Send response
        val response = if (currentSessionId == null) {
            "Session has already expired!"
        } else {
            "Logout successful!\nSession ID: $currentSessionId"
        }

        // Remove session
        if (currentSessionId != null) {
            logger.info { "User ${sessions[currentSessionId]} deleted!" }
        } else {
            logger.info { "User already deleted!" }
        }
        sessions.remove(sessionId)
        sessionCreationTimes.remove(sessionId)

        try {
            exchange.sendResponseHeaders(200, response.length.toLong())
        } catch (e: IOException) {
            logger.error(e) { "An error has occurred" }
        }

        try {
            exchange.responseBody.use { os ->
                os.write(response.toByteArray())
            }
        } catch (e: IOException) {
            logger.error(e) { "An error has occurred" }
        }
    }
}
