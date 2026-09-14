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
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class AppTest {

  @Test
  void logsCompleteTimelineAndAlertsOnlyOnFailedChange() {
    var logger = (Logger) LoggerFactory.getLogger(App.class);
    var previousLevel = logger.getLevel();
    var appender = new ListAppender<ILoggingEvent>();
    appender.start();
    logger.addAppender(appender);
    logger.setLevel(Level.INFO);
    try {
      App.main(new String[0]);

      var timelines =
          appender.list.stream()
              .filter(
                  event ->
                      event.getFormattedMessage().startsWith("Deployment and change timeline:"))
              .toList();
      assertEquals(1, timelines.size());
      assertEquals(Level.INFO, timelines.getFirst().getLevel());
      var lines = timelines.getFirst().getFormattedMessage().lines().skip(1).toList();
      var actor = System.getenv().getOrDefault("GITHUB_ACTOR", "release-engineer");
      var expected =
          List.of(
              "orders | 2.0 | production | "
                  + actor
                  + " | DEPLOYMENT | SUCCESS | Deploy orders release",
              "payments | 1.1 | production | "
                  + actor
                  + " | CONFIGURATION | SUCCESS | Increase request timeout",
              "orders | 2.0 | production | "
                  + actor
                  + " | ENDPOINT | SUCCESS | Route payments to /v2/payments",
              "payments | 1.1 | production | "
                  + actor
                  + " | PROTOCOL | FAILURE | Switch inter-service protocol to gRPC");
      assertEquals(expected.size(), lines.size());
      for (int index = 0; index < lines.size(); index++) {
        var timestampEnd = lines.get(index).indexOf(" | ");
        assertTrue(timestampEnd > 0);
        Instant.parse(lines.get(index).substring(0, timestampEnd));
        assertEquals(expected.get(index), lines.get(index).substring(timestampEnd + 3));
      }

      var warnings =
          appender.list.stream()
              .filter(event -> event.getLevel().equals(Level.WARN))
              .map(ILoggingEvent::getFormattedMessage)
              .toList();
      assertEquals(
          List.of(
              "Simulated change failed: Protocol health check failed",
              "ALERT: payments PROTOCOL failed"),
          warnings);
    } finally {
      logger.detachAppender(appender);
      logger.setLevel(previousLevel);
      appender.stop();
    }
  }
}
