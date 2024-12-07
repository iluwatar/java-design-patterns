package com.iluwatar.sessionserver;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * Demonstrates the Server Session pattern.
 * Handles session creation and expiration for HTTP requests.
 */
@Slf4j
public class App {

  // Session storage
  private static final Map<String, Integer> sessions = new HashMap<>();
  private static final Map<String, Instant> sessionCreationTimes = new HashMap<>();
  private static final long SESSION_EXPIRATION_TIME = 10000; // Expiration in milliseconds

  /**
   * Main entry point.
   *
   * @param args arguments
   * @throws IOException if server fails to start
   */
  public static void main(String[] args) throws IOException {
    // Create an HTTP server
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

    // Register endpoints
    server.createContext("/login", new LoginHandler(sessions, sessionCreationTimes));
    server.createContext("/logout", new LogoutHandler(sessions, sessionCreationTimes));

    // Start the server
    server.start();
    LOGGER.info("Server started. Listening on port 8080...");

    // Start the session expiration task
    startSessionExpirationTask();
  }

  /**
   * Periodically removes expired sessions.
   */
  private static void startSessionExpirationTask() {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    scheduler.scheduleAtFixedRate(() -> {
      LOGGER.info("Running session expiration checker...");
      Instant now = Instant.now();

      synchronized (sessions) {
        Iterator<Map.Entry<String, Instant>> iterator = sessionCreationTimes.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry<String, Instant> entry = iterator.next();
          if (entry.getValue().plusMillis(SESSION_EXPIRATION_TIME).isBefore(now)) {
            // Remove expired session
            sessions.remove(entry.getKey());
            iterator.remove();
            LOGGER.info("Expired session removed: {}", entry.getKey());
          }
        }
      }
      LOGGER.info("Session expiration check completed.");
    }, 0, SESSION_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
  }
}
