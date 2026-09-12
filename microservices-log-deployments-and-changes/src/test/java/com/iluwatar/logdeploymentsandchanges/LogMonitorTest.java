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
package com.iluwatar.logdeploymentsandchanges;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class LogMonitorTest {
  @Test
  void emptyHistoryHasNoTimelineOrAlerts() {
    var monitor = new LogMonitor(new CentralLogStore());
    assertEquals("", monitor.timeline());
    assertTrue(monitor.alerts(event -> true).isEmpty());
  }

  @Test
  void integratesMultipleServicesWithTimelineAndProductionFailureAlerts() {
    var store = new CentralLogStore();
    var clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
    var orders = new DeploymentPipeline(store, clock);
    var payments = new DeploymentPipeline(store, clock);
    orders.execute(
        "orders", "2", "production", "alice", ChangeType.DEPLOYMENT, "Release", () -> {});
    for (var environment : new String[] {"staging", "production"}) {
      assertThrows(
          IllegalStateException.class,
          () ->
              payments.execute(
                  "payments",
                  "1",
                  environment,
                  "bob",
                  ChangeType.CONFIGURATION,
                  "Timeout",
                  () -> {
                    throw new IllegalStateException();
                  }));
    }
    var monitor = new LogMonitor(store);
    assertEquals(
        String.join(
            System.lineSeparator(),
            "1970-01-01T00:00:00Z | orders | 2 | production | alice | DEPLOYMENT | SUCCESS | Release",
            "1970-01-01T00:00:00Z | payments | 1 | staging | bob | CONFIGURATION | FAILURE | Timeout",
            "1970-01-01T00:00:00Z | payments | 1 | production | bob | CONFIGURATION | FAILURE | Timeout"),
        monitor.timeline());
    var alerts =
        monitor.alerts(event -> !event.successful() && "production".equals(event.environment()));
    assertEquals(1, alerts.size());
    assertEquals(store.getEvents().get(2), alerts.getFirst());
    assertEquals(3, monitor.alerts(event -> true).size());
  }
}
