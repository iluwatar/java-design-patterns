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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class InventoryServiceTest {

  private final InventoryService service = new InventoryService(Map.of("LAPTOP", 3));

  @Test
  void shouldReportAvailability() {
    assertEquals(true, check("LAPTOP", 3).body());
    assertEquals(false, check("LAPTOP", 4).body());
    assertEquals(false, check("TABLET", 1).body());
  }

  @Test
  void shouldReserveAndReduceStock() {
    var response = reserve("LAPTOP", 2);

    assertTrue(response.success());
    assertEquals(1, response.body());
    assertEquals(false, check("LAPTOP", 2).body());
  }

  @Test
  void shouldRejectReservationBeyondStock() {
    var response = reserve("LAPTOP", 5);

    assertFalse(response.success());
    assertEquals("Insufficient stock for LAPTOP: requested 5, available 3", response.message());
    assertEquals(true, check("LAPTOP", 3).body());
  }

  @Test
  void shouldRejectReservationOfUnknownSku() {
    var response = reserve("TABLET", 1);

    assertFalse(response.success());
    assertEquals("Insufficient stock for TABLET: requested 1, available 0", response.message());
  }

  @Test
  void shouldReleaseReservedStock() {
    reserve("LAPTOP", 2);

    var response = release("LAPTOP", 2);

    assertTrue(response.success());
    assertEquals(3, response.body());
    assertEquals(true, check("LAPTOP", 3).body());
  }

  @Test
  void shouldRejectReleaseOfUnknownSku() {
    var response = release("TABLET", 1);

    assertFalse(response.success());
    assertEquals("Unknown sku: TABLET", response.message());
  }

  @Test
  void shouldRejectNonPositiveQuantity() {
    assertRejected(check("LAPTOP", 0), 0);
    assertRejected(check("LAPTOP", -5), -5);
    assertRejected(reserve("LAPTOP", 0), 0);
    assertRejected(reserve("LAPTOP", -5), -5);
    assertRejected(release("LAPTOP", 0), 0);
    assertRejected(release("LAPTOP", -5), -5);
    assertEquals(true, check("LAPTOP", 3).body());
    assertEquals(false, check("LAPTOP", 4).body());
  }

  @Test
  void shouldFailForUnknownOperation() {
    var response = service.handle(new ServiceRequest(InventoryService.NAME, "audit", Map.of()));

    assertFalse(response.success());
    assertEquals("Unknown operation: audit", response.message());
  }

  private ServiceResponse check(String sku, int quantity) {
    return service.handle(
        new ServiceRequest(
            InventoryService.NAME, "checkStock", Map.of("sku", sku, "quantity", quantity)));
  }

  private ServiceResponse reserve(String sku, int quantity) {
    return service.handle(
        new ServiceRequest(
            InventoryService.NAME, "reserve", Map.of("sku", sku, "quantity", quantity)));
  }

  private ServiceResponse release(String sku, int quantity) {
    return service.handle(
        new ServiceRequest(
            InventoryService.NAME, "release", Map.of("sku", sku, "quantity", quantity)));
  }

  private static void assertRejected(ServiceResponse response, int quantity) {
    assertFalse(response.success());
    assertEquals("Quantity must be positive: " + quantity, response.message());
  }
}
