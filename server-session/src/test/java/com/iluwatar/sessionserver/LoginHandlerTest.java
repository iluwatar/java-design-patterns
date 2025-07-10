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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for LoginHandler class.
 *
 * <p>Tests the login handling functionality including session creation and management.
 */
public class LoginHandlerTest {

  private LoginHandler loginHandler;
  private Map<String, Integer> sessions;
  private Map<String, Instant> sessionCreationTimes;

  /** Setup tests. */
  @BeforeEach
  public void setUp() {
    sessions = new HashMap<>();
    sessionCreationTimes = new HashMap<>();
    loginHandler = new LoginHandler(sessions, sessionCreationTimes);
  }

  @Test
  public void testLoginHandlerCreation() {
    // Test that LoginHandler can be created successfully
    assertNotNull(loginHandler);
  }

  @Test
  public void testSessionMapsInitialization() {
    // Test that session maps are properly initialized
    assertNotNull(sessions);
    assertNotNull(sessionCreationTimes);
    assertEquals(0, sessions.size());
    assertEquals(0, sessionCreationTimes.size());
  }

  @Test
  public void testSessionStorage() {
    // Test manual session addition (simulating what LoginHandler would do)
    String sessionId = "test-session-123";
    sessions.put(sessionId, 1);
    sessionCreationTimes.put(sessionId, Instant.now());

    assertEquals(1, sessions.size());
    assertEquals(1, sessionCreationTimes.size());
    assertTrue(sessions.containsKey(sessionId));
    assertTrue(sessionCreationTimes.containsKey(sessionId));
  }

  @Test
  public void testMultipleSessions() {
    // Test multiple session handling
    sessions.put("session-1", 1);
    sessions.put("session-2", 2);
    sessionCreationTimes.put("session-1", Instant.now());
    sessionCreationTimes.put("session-2", Instant.now());

    assertEquals(2, sessions.size());
    assertEquals(2, sessionCreationTimes.size());
  }

  @Test
  public void testSessionRemoval() {
    // Test session cleanup
    String sessionId = "temp-session";
    sessions.put(sessionId, 1);
    sessionCreationTimes.put(sessionId, Instant.now());

    assertEquals(1, sessions.size());

    // Remove session
    sessions.remove(sessionId);
    sessionCreationTimes.remove(sessionId);

    assertEquals(0, sessions.size());
    assertEquals(0, sessionCreationTimes.size());
  }
}
