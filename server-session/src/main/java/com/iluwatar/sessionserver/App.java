package com.iluwatar.sessionserver;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

/**
 * The server session pattern is a behavioral design pattern concerned with storing session data
 * on the server side. This implementation demonstrates how to manage sessions with expiration
 * using server-side storage.
 */
@Slf4j
public class App {

  // Concurrent maps to store session data and creation times
  private static final ConcurrentHashMap<String, Integer> sessions = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<String, Instant> sessionCreationTimes = new ConcurrentHashMap<>();
  private static final long SESSION_EXPIRATION_TIME_MS = 10_000; // 10 seconds

  /**
   * Main entry point.
   *
   * @param args arguments
   * @throws IOException if the server cannot start
   */
  public static void main(String[] args) throws IOException {
    // Create HTTP server listening on port 8080
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

    // Set up session management endpoints
    server.createContext("/login", new LoginHandler(sessions, sessionCreationTimes));
    server.createContext("/logout", new LogoutHandler(sessions, sessionCreationTimes));

    // Start the server
    server.setExecutor(Executors.newCachedThreadPool()); // Improve thread management
    server.start();

    // Start the background session expiration task
    startSessionExpirationTask();

    LOGGER.info("Server started. Listening on port 8080...");
  }

  /**
   * Starts a background task to remove expired sessions periodically.
   */
  private static void startSessionExpirationTask() {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
      try {
        LOGGER.info("Session expiration checker started...");
        Instant currentTime = Instant.now();

        // Remove expired sessions
        sessionCreationTimes.forEach((sessionId, creationTime) -> {
          if (creationTime.plusMillis(SESSION_EXPIRATION_TIME_MS).isBefore(currentTime)) {
            sessions.remove(sessionId);
            sessionCreationTimes.remove(sessionId);
            LOGGER.info("Session expired: {}", sessionId);
          }
        });

        LOGGER.info("Session expiration checker completed!");
      } catch (Exception e) {
        LOGGER.error("Error during session expiration task: ", e);
      }
    }, 0, SESSION_EXPIRATION_TIME_MS, java.util.concurrent.TimeUnit.MILLISECONDS);
  }
}