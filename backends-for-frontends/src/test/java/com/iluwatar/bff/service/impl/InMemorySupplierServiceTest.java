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
 * copies of the Software is furnished to do so, subject to the following conditions:
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
package com.iluwatar.bff.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.bff.model.SupplierRecord;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** Tests for {@link InMemorySupplierService}. */
class InMemorySupplierServiceTest {

  private static final String PRODUCT_NAME = "Headphones";

  @Test
  void shouldReturnSupplierRecordsForKnownProductName() {
    var record = new SupplierRecord("p-1", "Acme Audio", 30);
    var service = new InMemorySupplierService(Map.of(PRODUCT_NAME, List.of(record)));

    var records = service.getSupplierRecords(PRODUCT_NAME);

    assertEquals(1, records.size());
    assertEquals(record, records.get(0));
  }

  @Test
  void shouldReturnEmptyListForUnknownProductName() {
    var service = new InMemorySupplierService(Map.of());

    var records = service.getSupplierRecords("unknown-product");

    assertTrue(records.isEmpty());
  }
}
