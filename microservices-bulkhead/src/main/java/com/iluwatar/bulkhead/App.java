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
package com.iluwatar.bulkhead;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;

/**
 * The Bulkhead pattern partitions the resources of a service so that a failure or an overload in
 * one dependency cannot sink the whole service, just like the watertight compartments in a ship's
 * hull keep a single leak from flooding the entire vessel.
 *
 * <p>In this example an order service calls two downstream dependencies: a payment provider that
 * has become very slow and an inventory system that is perfectly healthy. The demo first sends both
 * kinds of calls through one shared thread pool. The slow payment calls fill every thread and the
 * queue, so a request for the healthy inventory system is rejected although nothing is wrong with
 * it. The demo then gives each dependency its own {@link Bulkhead}. The payment compartment still
 * saturates and rejects the excess calls fast, but the inventory compartment keeps answering
 * immediately because the payment calls can no longer consume its threads.
 */
@Slf4j
public class App {

  private static final Duration PAYMENT_LATENCY = Duration.ofMillis(300);
  private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

  /**
   * Program entry point.
   *
   * @param args command line arguments, not used
   */
  public static void main(String[] args) {
    var payment = new PaymentService(PAYMENT_LATENCY);
    var inventory = new InventoryService();

    LOGGER.info("--- Scenario 1: one shared thread pool for every downstream call ---");
    try (var sharedPool = new Bulkhead("shared-pool", 2, 2)) {
      var paymentFutures = flood(sharedPool, payment, "order", 4);
      callInventory(sharedPool, inventory, "order-5");
      awaitAll(paymentFutures);
    }

    LOGGER.info("--- Scenario 2: a dedicated bulkhead for each downstream dependency ---");
    try (var paymentBulkhead = new Bulkhead("payment", 2, 2);
        var inventoryBulkhead = new Bulkhead("inventory", 2, 2)) {
      var paymentFutures = flood(paymentBulkhead, payment, "order", 10);
      for (var i = 1; i <= 3; i++) {
        callInventory(inventoryBulkhead, inventory, "order-" + i);
      }
      awaitAll(paymentFutures);
      LOGGER.info(
          "Bulkhead '{}' rejected {} of 10 calls, bulkhead '{}' rejected {} of 3 calls",
          paymentBulkhead.getName(),
          paymentBulkhead.getRejectedCalls(),
          inventoryBulkhead.getName(),
          inventoryBulkhead.getRejectedCalls());
    }
  }

  static List<Future<String>> flood(
      Bulkhead bulkhead, RemoteService service, String requestPrefix, int calls) {
    var accepted = new ArrayList<Future<String>>();
    for (var i = 1; i <= calls; i++) {
      var request = requestPrefix + "-" + i;
      try {
        accepted.add(bulkhead.submit(() -> service.call(request)));
      } catch (BulkheadFullException e) {
        LOGGER.info("Request '{}' rejected immediately: {}", request, e.getMessage());
      }
    }
    return accepted;
  }

  static void callInventory(Bulkhead bulkhead, RemoteService inventory, String request) {
    try {
      var response = bulkhead.submit(() -> inventory.call(request));
      LOGGER.info(
          "Inventory response: {}", response.get(WAIT_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS));
    } catch (BulkheadFullException e) {
      LOGGER.error(
          "Inventory check for '{}' rejected although the inventory system is healthy: {}",
          request,
          e.getMessage());
    } catch (ExecutionException | TimeoutException e) {
      LOGGER.error("Inventory check for '{}' failed", request, e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.warn(
          "Inventory check for '{}' was interrupted while waiting for the response", request);
    }
  }

  static void awaitAll(List<Future<String>> futures) {
    for (var future : futures) {
      try {
        LOGGER.info(
            "Payment response: {}", future.get(WAIT_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS));
      } catch (ExecutionException | TimeoutException e) {
        LOGGER.error("Payment call failed", e);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
    }
  }
}
