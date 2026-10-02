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
package com.iluwatar.soa;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Enterprise service that owns stock levels.
 *
 * <p>Operations:
 *
 * <ul>
 *   <li>{@code checkStock} with {@code sku} and {@code quantity} returns whether the quantity is
 *       available
 *   <li>{@code reserve} with {@code sku} and {@code quantity} takes the quantity out of stock and
 *       returns the remaining amount
 *   <li>{@code release} with {@code sku} and {@code quantity} puts a reserved quantity back into
 *       stock and returns the remaining amount, compensating a reservation that cannot be completed
 * </ul>
 */
public class InventoryService implements Service {

  public static final String NAME = "inventory";

  private final Map<String, Integer> stock = new ConcurrentHashMap<>();

  /** Creates the service with a small in-memory stock. */
  public InventoryService() {
    this(Map.of("LAPTOP", 5, "PHONE", 10));
  }

  /** Creates the service with the given initial stock levels. */
  public InventoryService(Map<String, Integer> initialStock) {
    stock.putAll(initialStock);
  }

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public ServiceResponse handle(ServiceRequest request) {
    return switch (request.operation()) {
      case "checkStock" -> checkStock(
          request.param("sku", String.class), request.param("quantity", Integer.class));
      case "reserve" -> reserve(
          request.param("sku", String.class), request.param("quantity", Integer.class));
      case "release" -> release(
          request.param("sku", String.class), request.param("quantity", Integer.class));
      default -> ServiceResponse.error("Unknown operation: " + request.operation());
    };
  }

  private ServiceResponse checkStock(String sku, int quantity) {
    if (quantity <= 0) {
      return ServiceResponse.error("Quantity must be positive: " + quantity);
    }
    return ServiceResponse.ok(stock.getOrDefault(sku, 0) >= quantity);
  }

  private ServiceResponse reserve(String sku, int quantity) {
    if (quantity <= 0) {
      return ServiceResponse.error("Quantity must be positive: " + quantity);
    }
    var reserved = new AtomicBoolean();
    var remaining =
        stock.computeIfPresent(
            sku,
            (key, available) -> {
              if (available >= quantity) {
                reserved.set(true);
                return available - quantity;
              }
              return available;
            });
    if (!reserved.get()) {
      return ServiceResponse.error(
          "Insufficient stock for "
              + sku
              + ": requested "
              + quantity
              + ", available "
              + (remaining == null ? 0 : remaining));
    }
    return ServiceResponse.ok(remaining);
  }

  private ServiceResponse release(String sku, int quantity) {
    if (quantity <= 0) {
      return ServiceResponse.error("Quantity must be positive: " + quantity);
    }
    var remaining = stock.computeIfPresent(sku, (key, available) -> available + quantity);
    if (remaining == null) {
      return ServiceResponse.error("Unknown sku: " + sku);
    }
    return ServiceResponse.ok(remaining);
  }
}
