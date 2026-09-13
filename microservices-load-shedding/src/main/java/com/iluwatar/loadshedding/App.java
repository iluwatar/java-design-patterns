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
package com.iluwatar.loadshedding;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;

/**
 * Load Shedding is a resilience pattern for services that receive more work than they can handle.
 * Instead of accepting every request and slowly drowning, the service measures its own load and
 * proactively rejects excess requests at the door, so that the requests it does accept are served
 * with normal latency and the process never runs out of threads, memory or connections.
 *
 * <p>The key ingredients demonstrated here are:
 *
 * <ul>
 *   <li>a capacity limit expressed as the number of requests in flight ({@link LoadShedder}),
 *   <li>fail-fast rejection: shed requests receive an immediate {@link Response.Status#REJECTED}
 *       answer instead of waiting in a queue ({@link ShedGuardedService}),
 *   <li>priority-aware shedding: low priority work is dropped first and a small reserve is kept for
 *       critical requests ({@link Priority}),
 *   <li>metrics that make the shedding decisions observable.
 * </ul>
 *
 * <p>The demo runs an order service with capacity for five concurrent requests. Phase one shows
 * normal operation. In phase two the payment provider becomes slow, four orders get stuck inside
 * the service and probes of every priority show which of them are shed. In phase three the provider
 * recovers, the stuck orders complete and new requests are admitted again.
 */
@Slf4j
public class App {

  private static final int CAPACITY = 5;
  private static final int LOW_PRIORITY_LIMIT = 3;
  private static final int CRITICAL_RESERVE = 1;
  private static final int STUCK_ORDERS = 4;
  private static final Duration WAIT = Duration.ofSeconds(5);

  /**
   * Program entry point.
   *
   * @param args command line arguments, not used
   * @throws InterruptedException if the demo is interrupted while waiting for the workers
   */
  public static void main(String[] args) throws InterruptedException {
    var shedder = new LoadShedder(CAPACITY, LOW_PRIORITY_LIMIT, CRITICAL_RESERVE);
    var paymentSlow = new AtomicBoolean(false);
    var paymentRecovered = new CountDownLatch(1);
    var entered = new Semaphore(0);
    var orderService =
        new ShedGuardedService(
            "order-service",
            shedder,
            simulatedPaymentProvider(paymentSlow, paymentRecovered, entered, WAIT));
    var executor = Executors.newCachedThreadPool();
    try {
      LOGGER.info(
          "Order service capacity: {} in flight, low priority shed at {}, {} slot reserved for"
              + " critical requests",
          CAPACITY,
          LOW_PRIORITY_LIMIT,
          CRITICAL_RESERVE);

      LOGGER.info("--- Phase 1: light load, every request is admitted ---");
      report(orderService.handle(new Request("r1", Priority.LOW, "prefetch recommendations")));
      report(orderService.handle(new Request("r2", Priority.NORMAL, "view cart")));

      LOGGER.info("--- Phase 2: payment provider slows down, orders pile up ---");
      paymentSlow.set(true);
      List<Future<Response>> stuckOrders = new ArrayList<>();
      for (var i = 1; i <= STUCK_ORDERS; i++) {
        var order = new Request("order-" + i, Priority.NORMAL, "place order");
        stuckOrders.add(executor.submit(() -> orderService.handle(order)));
      }
      awaitEntered(entered, STUCK_ORDERS, WAIT);
      LOGGER.info(
          "{} of {} slots busy, probing with every priority", shedder.getInFlight(), CAPACITY);
      report(orderService.handle(new Request("p1", Priority.LOW, "prefetch recommendations")));
      report(orderService.handle(new Request("p2", Priority.NORMAL, "view cart")));
      var checkout =
          executor.submit(
              () -> orderService.handle(new Request("p3", Priority.CRITICAL, "checkout payment")));
      awaitEntered(entered, 1, WAIT);

      LOGGER.info("--- Phase 3: payment provider recovers, load drops ---");
      paymentSlow.set(false);
      paymentRecovered.countDown();
      for (var order : stuckOrders) {
        report(result(order, WAIT));
      }
      report(result(checkout, WAIT));
      report(orderService.handle(new Request("r3", Priority.LOW, "prefetch recommendations")));

      LOGGER.info(
          "Summary: accepted={}, shed {} requests in total: low={}, normal={}, critical={}",
          shedder.getAccepted(),
          shedder.getTotalShed(),
          shedder.getShed(Priority.LOW),
          shedder.getShed(Priority.NORMAL),
          shedder.getShed(Priority.CRITICAL));
    } finally {
      shutdown(executor, WAIT);
    }
  }

  /**
   * Business logic of the order service. While the payment provider is slow every admitted request
   * blocks until the provider recovers, which is exactly the situation in which requests pile up
   * and load shedding becomes necessary. The semaphore tells the demo how many requests are stuck.
   */
  static RequestHandler simulatedPaymentProvider(
      AtomicBoolean paymentSlow,
      CountDownLatch paymentRecovered,
      Semaphore entered,
      Duration recoveryTimeout) {
    return request -> {
      if (paymentSlow.get()) {
        // Signal the demo that one more request is now stuck behind the slow provider.
        entered.release();
        try {
          if (!paymentRecovered.await(recoveryTimeout.toMillis(), TimeUnit.MILLISECONDS)) {
            throw new IllegalStateException("payment provider never recovered");
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new IllegalStateException("interrupted while processing " + request.id(), e);
        }
      }
      return "processed " + request.description();
    };
  }

  /** Waits until the given number of requests are stuck inside the service. */
  static void awaitEntered(Semaphore entered, int count, Duration timeout)
      throws InterruptedException {
    if (!entered.tryAcquire(count, timeout.toMillis(), TimeUnit.MILLISECONDS)) {
      throw new IllegalStateException("requests did not enter the service in time");
    }
  }

  /** Collects the response of a request that was handled on a worker thread. */
  static Response result(Future<Response> future, Duration timeout) throws InterruptedException {
    try {
      return future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
    } catch (ExecutionException | TimeoutException e) {
      throw new IllegalStateException("worker failed", e);
    }
  }

  /** Stops the worker pool, forcing the shutdown if workers do not finish within the timeout. */
  static void shutdown(ExecutorService executor, Duration timeout) throws InterruptedException {
    executor.shutdown();
    if (!executor.awaitTermination(timeout.toMillis(), TimeUnit.MILLISECONDS)) {
      executor.shutdownNow();
    }
  }

  static void report(Response response) {
    LOGGER.info("{} -> {}: {}", response.requestId(), response.status(), response.message());
  }
}
