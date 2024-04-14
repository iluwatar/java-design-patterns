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
package com.iluwatar.sessionserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

/**
 * LoginHandler.
 */
@Slf4j
public class LoginHandler implements HttpHandler {

  private Map<String, Integer> sessions;
  private Map<String, Instant> sessionCreationTimes;

  public LoginHandler(Map<String, Integer> sessions, Map<String, Instant> sessionCreationTimes) {
    this.sessions = sessions;
    this.sessionCreationTimes = sessionCreationTimes;
  }

  @Override
  public void handle(HttpExchange exchange) {
    // Generate session ID
    String sessionId = UUID.randomUUID().toString();

    // Store session data (simulated)
    int newUser = sessions.size() + 1;
    sessions.put(sessionId, newUser);
    sessionCreationTimes.put(sessionId, Instant.now());
    LOGGER.info("User " + newUser + " created at time " + sessionCreationTimes.get(sessionId));

    // Set session ID as cookie
    exchange.getResponseHeaders().add("Set-Cookie", "sessionID=" + sessionId);

    // Send response
    String response = "Login successful!\n" + "Session ID: " + sessionId;
    try {
      exchange.sendResponseHeaders(200, response.length());
    } catch (IOException e) {
      LOGGER.error("An error occurred: ", e);
    }
    try (OutputStream os = exchange.getResponseBody()) {
      os.write(response.getBytes());
    } catch (IOException e) {
      LOGGER.error("An error occurred: ", e);
    }
  }
}
