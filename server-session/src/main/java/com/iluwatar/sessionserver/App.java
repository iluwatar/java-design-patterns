package com.iluwatar.sessionserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

public class App {

  // Map to store session data (simulated using a HashMap)
  private static Map<String, Integer> sessions = new HashMap<>();
  private static Map<String, Instant> sessionCreationTimes = new HashMap<>();
  private static final long SESSION_EXPIRATION_TIME = 10000;
  private static Logger logger = Logger.getLogger(App.class.getName());

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

      logger.info("Server started. Listening on port 8080...");
    }

    private static void sessionExpirationTask() {
        new Thread(() -> {
            while (true) {
                try {
                    logger.info("Session expiration checker started...");
                    Thread.sleep(SESSION_EXPIRATION_TIME); // Sleep for expiration time
                    Instant currentTime = Instant.now();
                    synchronized (sessions) {
                        synchronized (sessionCreationTimes) {
                            Iterator<Map.Entry<String, Instant>> iterator = sessionCreationTimes.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Map.Entry<String, Instant> entry = iterator.next();
                                if (entry.getValue().plusMillis(SESSION_EXPIRATION_TIME).isBefore(currentTime)) {
                                    sessions.remove(entry.getKey());
                                    iterator.remove();
                                }
                            }
                        }
                    }
                    logger.info("Session expiration checker finished!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}