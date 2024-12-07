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
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**

    
        
          
    

        
        Expand All
    
    @@ -54,8 +57,9 @@
  
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
  private static final Map<String, Integer> sessions = new ConcurrentHashMap<>();
  private static final Map<String, Instant> sessionCreationTimes = new ConcurrentHashMap<>();
  private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  private static final long SESSION_EXPIRATION_TIME = 10000;

  /**

    
        
          
    

        
        Expand All
    
    @@ -81,31 +85,25 @@ public static void main(String[] args) throws IOException {
  
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
    scheduler.scheduleWithFixedDelay(() -> {
      try {
        LOGGER.info("Session expiration checker started...");

        Instant currentTime = Instant.now();
        Iterator<Map.Entry<String, Instant>> iterator = sessionCreationTimes.entrySet().iterator();

        while (iterator.hasNext()) {
          Map.Entry<String, Instant> entry = iterator.next();
          if (entry.getValue().plusMillis(SESSION_EXPIRATION_TIME).isBefore(currentTime)) {
            sessions.remove(entry.getKey());
            iterator.remove();
          }
        }
        LOGGER.info("Session expiration checker finished!");

      } catch (Exception e) {
        LOGGER.error("An error occured: ", e);
      }
    }, SESSION_EXPIRATION_TIME, SESSION_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
  }
}