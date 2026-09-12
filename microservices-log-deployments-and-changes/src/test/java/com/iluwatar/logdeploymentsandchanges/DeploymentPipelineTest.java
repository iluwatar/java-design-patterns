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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class DeploymentPipelineTest {
  private static final Instant NOW = Instant.parse("2026-01-01T12:00:00Z");
  private final CentralLogStore store = new CentralLogStore();
  private final DeploymentPipeline pipeline =
      new DeploymentPipeline(store, Clock.fixed(NOW, ZoneOffset.UTC));

  @Test
  void logsEveryChangeTypeWithAllMetadataAfterExecution() {
    var calls = new AtomicInteger();
    for (var type : ChangeType.values()) {
      pipeline.execute(
          "orders",
          "2.0",
          "staging",
          "alice",
          type,
          "Example change",
          () -> {
            assertEquals(calls.get(), store.getEvents().size());
            calls.incrementAndGet();
          });
    }
    assertEquals(ChangeType.values().length, calls.get());
    assertEquals(calls.get(), store.getEvents().size());
    for (int index = 0; index < calls.get(); index++) {
      assertEquals(
          new ChangeEvent(
              NOW,
              "orders",
              "2.0",
              "staging",
              "alice",
              ChangeType.values()[index],
              "Example change",
              true),
          store.getEvents().get(index));
    }
  }

  @Test
  void recordsFailureAndRethrowsOriginalException() {
    var failure = new IllegalStateException("Deployment failed");
    var thrown =
        assertThrows(
            IllegalStateException.class,
            () ->
                pipeline.execute(
                    "orders",
                    "2.0",
                    "production",
                    "bob",
                    ChangeType.DEPLOYMENT,
                    "Release",
                    () -> {
                      throw failure;
                    }));
    assertSame(failure, thrown);
    assertEquals(1, store.getEvents().size());
    assertEquals(
        new ChangeEvent(
            NOW, "orders", "2.0", "production", "bob", ChangeType.DEPLOYMENT, "Release", false),
        store.getEvents().getFirst());
  }
}
