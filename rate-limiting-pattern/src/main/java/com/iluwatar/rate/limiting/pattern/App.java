package com.iluwatar.rate.limiting.pattern;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The <em>Rate Limiter</em> pattern is a key defensive strategy used to prevent system overload
 * and ensure fair usage of shared services. This demo showcases how different rate limiting techniques
 * can regulate traffic in distributed systems.
 *
 * <p>Specifically, this simulation implements three rate limiter strategies:
 *
 * <ul>
 *   <li><b>Token Bucket</b> – Allows short bursts followed by steady request rates.</li>
 *   <li><b>Fixed Window</b> – Enforces a strict limit per discrete time window (e.g., 3 requests/sec).</li>
 *   <li><b>Adaptive</b> – Dynamically scales limits based on system health, simulating elastic backoff.</li>
 * </ul>
 *
 * <p>Each simulated service (e.g., S3, DynamoDB, Lambda) is governed by one of these limiters. Multiple
 * concurrent client threads issue randomized requests to these services over a fixed duration. Each
 * request is either:
 *
 * <ul>
 *   <li><b>ALLOWED</b> – Permitted under the current rate limit</li>
 *   <li><b>THROTTLED</b> – Rejected due to quota exhaustion</li>
 *   <li><b>FAILED</b> – Dropped due to transient service failure</li>
 * </ul>
 *
 * <p>Statistics are printed every few seconds, and the simulation exits gracefully after a fixed runtime,
 * offering a clear view into how each limiter behaves under pressure.
 *
 * <p><b>Relation to AWS API Gateway:</b><br>
 * This implementation mirrors the throttling behavior described in the
 * <a href="https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-request-throttling.html">
 * AWS API Gateway Request Throttling documentation</a>, where limits are applied per second and over
 * longer durations (burst and rate limits). The <code>TokenBucketRateLimiter</code> mimics burst capacity,
 * the <code>FixedWindowRateLimiter</code> models steady rate enforcement, and the <code>AdaptiveRateLimiter</code>
 * reflects elasticity in real-world systems like AWS Lambda under variable load.
 *
 * */
public final class App {
  private static final int RUN_DURATION_SECONDS = 10;
  private static final int SHUTDOWN_TIMEOUT_SECONDS = 5;

  private static final AtomicInteger successfulRequests = new AtomicInteger();
  private static final AtomicInteger throttledRequests = new AtomicInteger();
  private static final AtomicInteger failedRequests = new AtomicInteger();
  private static final AtomicBoolean running = new AtomicBoolean(true);

  public static void main(String[] args) {
    System.out.println("\nStarting Rate Limiter Demo");
    System.out.println("====================================");

    ExecutorService executor = Executors.newFixedThreadPool(3);
    ScheduledExecutorService statsPrinter = Executors.newSingleThreadScheduledExecutor();

    try {
      // Explicit limiter setup for demonstration clarity
      TokenBucketRateLimiter tb = new TokenBucketRateLimiter(2, 1); // capacity 2, refill 1/sec
      FixedWindowRateLimiter fw = new FixedWindowRateLimiter(3, 1); // max 3 req/sec
      AdaptiveRateLimiter ar = new AdaptiveRateLimiter(2, 6);       // adaptive from 2 to 6 req/sec

      // Print statistics every 2 seconds
      statsPrinter.scheduleAtFixedRate(App::printStats, 2, 2, TimeUnit.SECONDS);

      // Launch 3 simulated clients
      for (int i = 1; i <= 3; i++) {
        executor.submit(createClientTask(i, tb, fw, ar));
      }

      // Run simulation for N seconds
      Thread.sleep(RUN_DURATION_SECONDS * 1000L);
      System.out.println("\nShutting down the demo...");

      running.set(false);
      executor.shutdown();
      statsPrinter.shutdown();

      if (!executor.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
        executor.shutdownNow();
      }
      if (!statsPrinter.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
        statsPrinter.shutdownNow();
      }

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      printFinalStats();
      System.out.println("Demo completed.");
    }
  }

  private static Runnable createClientTask(int clientId,
                                           RateLimiter s3Limiter,
                                           RateLimiter dynamoDbLimiter,
                                           RateLimiter lambdaLimiter) {
    return () -> {
      String[] services = {"s3", "dynamodb", "lambda"};
      String[] operations = {
          "GetObject", "PutObject", "Query", "Scan", "PutItem", "Invoke", "ListFunctions"
      };
      Random random = new Random();

      while (running.get() && !Thread.currentThread().isInterrupted()) {
        try {
          String service = services[random.nextInt(services.length)];
          String operation = operations[random.nextInt(operations.length)];

          switch (service) {
            case "s3" -> makeRequest(clientId, s3Limiter, service, operation);
            case "dynamodb" -> makeRequest(clientId, dynamoDbLimiter, service, operation);
            case "lambda" -> makeRequest(clientId, lambdaLimiter, service, operation);
          }

          Thread.sleep(30 + random.nextInt(50));
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    };
  }

  private static void makeRequest(int clientId, RateLimiter limiter,
                                  String service, String operation) {
    try {
      limiter.check(service, operation);
      successfulRequests.incrementAndGet();
      System.out.printf("Client %d: %s.%s - ALLOWED%n", clientId, service, operation);
    } catch (ThrottlingException e) {
      throttledRequests.incrementAndGet();
      System.out.printf("Client %d: %s.%s - THROTTLED (Retry in %dms)%n",
          clientId, service, operation, e.getRetryAfterMillis());
    } catch (ServiceUnavailableException e) {
      failedRequests.incrementAndGet();
      System.out.printf("Client %d: %s.%s - SERVICE UNAVAILABLE%n",
          clientId, service, operation);
    } catch (Exception e) {
      failedRequests.incrementAndGet();
      System.out.printf("Client %d: %s.%s - ERROR: %s%n",
          clientId, service, operation, e.getMessage());
    }
  }

  private static void printStats() {
    if (!running.get()) return;
    System.out.println("\n=== Current Statistics ===");
    System.out.printf("Successful Requests: %d%n", successfulRequests.get());
    System.out.printf("Throttled Requests : %d%n", throttledRequests.get());
    System.out.printf("Failed Requests    : %d%n", failedRequests.get());
    System.out.println("==========================\n");
  }

  private static void printFinalStats() {
    System.out.println("\nFinal Statistics");
    System.out.println("==========================");
    System.out.printf("Successful Requests: %d%n", successfulRequests.get());
    System.out.printf("Throttled Requests : %d%n", throttledRequests.get());
    System.out.printf("Failed Requests    : %d%n", failedRequests.get());
    System.out.println("==========================");
  }
}