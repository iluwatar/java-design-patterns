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
