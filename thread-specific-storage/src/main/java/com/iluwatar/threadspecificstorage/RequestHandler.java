package com.iluwatar.threadspecificstorage;

import java.security.SecureRandom;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Application Thread
 *
 * <p>Each instance simulates a request-processing thread that uses the Thread-Specific Object Proxy
 * to access Thread-Specific Object.
 */
@AllArgsConstructor
@Slf4j
public class RequestHandler {
  private final UserContextProxy contextProxy;
  private final String token;

  /**
   * Simulated business process: 1. Parse userId from token ("Token::userId"). 2. Store userId in
   * thread-local storage. 3. Later, retrieve userId and use it for business logic. 4. Finally,
   * clear thread-local to prevent memory leak.
   */
  public void process() {
    LOGGER.info("Start handling request with token: {}", token);

    try {
      // Step 1: Parse token to get userId
      Long userId = parseToken(token);

      // Step 2: Save userId in ThreadLocal storage
      contextProxy.set(new UserContext(userId));

      // Simulate delay between stages of request handling
      Thread.sleep(200);

      // Step 3: Retrieve userId later in the request flow
      Long retrievedId = contextProxy.get().getUserId();
      SecureRandom random = new SecureRandom();
      String accountInfo = retrievedId + "'s account: " + random.nextInt(400);
      LOGGER.info(accountInfo);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      // Step 4: Clear ThreadLocal to avoid potential memory leaks
      contextProxy.clear();
    }
  }

  private Long parseToken(String token) {
    // token format: "Token::1234"
    String[] parts = token.split("::");
    return (parts.length == 2) ? Long.parseLong(parts[1]) : -1L;
  }
}
