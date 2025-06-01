package com.iluwatar.rate.limiting.pattern;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <em>Rate Limiter</em> pattern is a key defensive strategy used to prevent system overload and
 * ensure fair usage of shared services. This demo showcases how different rate limiting techniques
 * can regulate traffic in distributed systems.
 *
 * <p>Specifically, this simulation implements three rate limiter strategies:
 *
 * <ul>
 *   <li><b>Token Bucket</b> – Allows short bursts followed by steady request rates.
 *   <li><b>Fixed Window</b> – Enforces a strict limit per discrete time window (e.g., 3
 *       requests/sec).
 *   <li><b>Adaptive</b> – Dynamically scales limits based on system health, simulating elastic
 *       backoff.
 * </ul>
 *
 * <p>Each simulated service (e.g., S3, DynamoDB, Lambda) is governed by one of these limiters.
 * Multiple concurrent client threads issue randomized requests to these services over a fixed
 * duration. Each request is either:
 *
 * <ul>
 *   <li><b>ALLOWED</b> – Permitted under the current rate limit
 *   <li><b>THROTTLED</b> – Rejected due to quota exhaustion
 *   <li><b>FAILED</b> – Dropped due to transient service failure
 * </ul>
 *
 * <p>Statistics are printed every few seconds, and the simulation exits gracefully after a fixed
 * runtime, offering a clear view into how each limiter behaves under pressure.
 *
 * <p><b>Relation to AWS API Gateway:</b><br>
 * This implementation mirrors the throttling behavior described in the <a
 * href="https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-request-throttling.html">
 * AWS API Gateway Request Throttling documentation</a>, where limits are applied per second and
 * over longer durations (burst and rate limits). The <code>TokenBucketRateLimiter</code> mimics
 * burst capacity, the <code>FixedWindowRateLimiter</code> models steady rate enforcement, and the
 * <code>AdaptiveRateLimiter</code> reflects elasticity in real-world systems like AWS Lambda under
 * variable load.
 */
public final class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  private static final int RUN_DURATION_SECONDS = 10;
  private static final int SHUTDOWN_TIMEOUT_SECONDS = 5;

  static final AtomicInteger successfulRequests = new AtomicInteger();
  static final AtomicInteger throttledRequests = new AtomicInteger();
  static final AtomicInteger failedRequests = new AtomicInteger();
  static final AtomicBoolean running = new AtomicBoolean(true);
  private static final String DIVIDER_LINE = "====================================";

  public static void main(String[] args) {
    LOGGER.info("Starting Rate Limiter Demo");
    LOGGER.info(DIVIDER_LINE);

    ExecutorService executor = Executors.newFixedThreadPool(3);
    ScheduledExecutorService statsPrinter = Executors.newSingleThreadScheduledExecutor();

    try {
      TokenBucketRateLimiter tb = new TokenBucketRateLimiter(2, 1);
      FixedWindowRateLimiter fw = new FixedWindowRateLimiter(3, 1);
      AdaptiveRateLimiter ar = new AdaptiveRateLimiter(2, 6);

      statsPrinter.scheduleAtFixedRate(App::printStats, 2, 2, TimeUnit.SECONDS);

      for (int i = 1; i <= 3; i++) {
        executor.submit(createClientTask(i, tb, fw, ar));
      }

      Thread.sleep(RUN_DURATION_SECONDS * 1000L);
      LOGGER.info("Shutting down the demo...");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      running.set(false);
      shutdownExecutor(executor, "mainExecutor");
      shutdownExecutor(statsPrinter, "statsPrinter");
      printFinalStats();
      LOGGER.info("Demo completed.");
    }
  }

  private static void shutdownExecutor(ExecutorService service, String name) {
    service.shutdown();
    try {
      if (!service.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
        service.shutdownNow();
        LOGGER.warn("Forced shutdown of {}", name);
      }
    } catch (InterruptedException e) {
      service.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

  static Runnable createClientTask(
      int clientId, RateLimiter s3Limiter, RateLimiter dynamoDbLimiter, RateLimiter lambdaLimiter) {
    return () -> {
      String[] services = {"s3", "dynamodb", "lambda"};
      String[] operations = {
        "GetObject", "PutObject", "Query", "Scan", "PutItem", "Invoke", "ListFunctions"
      };
      ThreadLocalRandom random = ThreadLocalRandom.current();

      while (running.get() && !Thread.currentThread().isInterrupted()) {
        try {
          String service = services[random.nextInt(services.length)];
          String operation = operations[random.nextInt(operations.length)];

          switch (service) {
            case "s3" -> makeRequest(clientId, s3Limiter, service, operation);
            case "dynamodb" -> makeRequest(clientId, dynamoDbLimiter, service, operation);
            case "lambda" -> makeRequest(clientId, lambdaLimiter, service, operation);
            default -> LOGGER.warn("Unknown service: {}", service);
          }

          Thread.sleep(30L + random.nextInt(50));
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    };
  }

  static void makeRequest(int clientId, RateLimiter limiter, String service, String operation) {
    try {
      limiter.check(service, operation);
      successfulRequests.incrementAndGet();
      LOGGER.info("Client {}: {}.{} - ALLOWED", clientId, service, operation);
    } catch (ThrottlingException e) {
      throttledRequests.incrementAndGet();
      LOGGER.warn(
          "Client {}: {}.{} - THROTTLED (Retry in {}ms)",
          clientId,
          service,
          operation,
          e.getRetryAfterMillis());
    } catch (ServiceUnavailableException e) {
      failedRequests.incrementAndGet();
      LOGGER.warn("Client {}: {}.{} - SERVICE UNAVAILABLE", clientId, service, operation);
    } catch (Exception e) {
      failedRequests.incrementAndGet();
      LOGGER.error("Client {}: {}.{} - ERROR: {}", clientId, service, operation, e.getMessage());
    }
  }

  static void printStats() {
    if (!running.get()) return;
    LOGGER.info("=== Current Statistics ===");
    LOGGER.info("Successful Requests: {}", successfulRequests.get());
    LOGGER.info("Throttled Requests : {}", throttledRequests.get());
    LOGGER.info("Failed Requests    : {}", failedRequests.get());
    LOGGER.info(DIVIDER_LINE);
  }

  static void printFinalStats() {
    LOGGER.info("Final Statistics");
    LOGGER.info(DIVIDER_LINE);
    LOGGER.info("Successful Requests: {}", successfulRequests.get());
    LOGGER.info("Throttled Requests : {}", throttledRequests.get());
    LOGGER.info("Failed Requests    : {}", failedRequests.get());
    LOGGER.info(DIVIDER_LINE);
  }

  static void resetCountersForTesting() {
    successfulRequests.set(0);
    throttledRequests.set(0);
    failedRequests.set(0);
  }
}
