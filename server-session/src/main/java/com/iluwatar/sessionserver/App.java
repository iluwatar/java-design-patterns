package com.iluwatar.sessionserver;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * The server session pattern is a behavioral design pattern concerned with assigning the responsibility
 * of storing session data on the server side. Within the context of stateless protocols like HTTP all
 * requests are isolated events independent of previous requests. In order to create sessions during
 * user-access for a particular web application various methods can be used, such as cookies. Cookies
 * are a small piece of data that can be sent between client and server on every request and response
 * so that the server can "remember" the previous requests. In general cookies can either store the session
 * data or the cookie can store a session identifier and be used to access appropriate data from a persistent
 * storage. In the latter case the session data is stored on the server-side and appropriate data is
 * identified by the cookie sent from a client's request.
 * This project demonstrates the latter case.
 * In the following example the ({@link App}) class starts a server and assigns ({@link LoginHandler})
 * class to handle login request. When a user logs in a session identifier is created and stored for future
 * requests in a list. When a user logs out the session identifier is deleted from the list along with
 * the appropriate user session data, which is handle by the ({@link LogoutHandler}) class.
 */

@Slf4j
public class App {

  // Map to store session data (simulated using a HashMap)
  private static Map<String, Integer> sessions = new HashMap<>();
  private static Map<String, Instant> sessionCreationTimes = new HashMap<>();
  private static final long SESSION_EXPIRATION_TIME = 10000;

  /**
   * Main entry point.
   * @param args arguments
   * @throws IOException ex
   */
  public static void main(String[] args) throws IOException {
    // Create HTTP server listening on port 8000
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

    // Set up session management endpoints
    server.createContext("/login", new LoginHandler(sessions, sessionCreationTimes));
    server.createContext("/logout", new LogoutHandler(sessions, sessionCreationTimes));

    // Start the server
    server.start();

    // Start background task to check for expired sessions
    sessionExpirationTask();

    LOGGER.info("Server started. Listening on port 8080...");
  }

  private static void sessionExpirationTask() {
    new Thread(() -> {
      while (true) {
        try {
          LOGGER.info("Session expiration checker started...");
          Thread.sleep(SESSION_EXPIRATION_TIME); // Sleep for expiration time
          Instant currentTime = Instant.now();
          synchronized (sessions) {
            synchronized (sessionCreationTimes) {
              Iterator<Map.Entry<String, Instant>> iterator =
                  sessionCreationTimes.entrySet().iterator();
              while (iterator.hasNext()) {
                Map.Entry<String, Instant> entry = iterator.next();
                if (entry.getValue().plusMillis(SESSION_EXPIRATION_TIME).isBefore(currentTime)) {
                  sessions.remove(entry.getKey());
                  iterator.remove();
                }
              }
            }
          }
          LOGGER.info("Session expiration checker finished!");
        } catch (InterruptedException e) {
          LOGGER.error("An error occurred: ", e);
          Thread.currentThread().interrupt();
        }
      }
    }).start();
  }
}