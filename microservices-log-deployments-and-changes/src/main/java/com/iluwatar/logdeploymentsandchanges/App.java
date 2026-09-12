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

import java.time.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Demonstrates automatic deployment and change logging for two services. */
public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /** Run simulated pipeline steps, then display their timeline and failure alerts. */
  public static void main(String[] args) {
    var store = new CentralLogStore();
    var pipeline = new DeploymentPipeline(store, Clock.systemUTC());
    var monitor = new LogMonitor(store);
    var actor = System.getenv().getOrDefault("GITHUB_ACTOR", "release-engineer");

    pipeline.execute(
        "orders",
        "2.0",
        "production",
        actor,
        ChangeType.DEPLOYMENT,
        "Deploy orders release",
        () -> LOGGER.info("Deploying orders 2.0"));
    pipeline.execute(
        "payments",
        "1.1",
        "production",
        actor,
        ChangeType.CONFIGURATION,
        "Increase request timeout",
        () -> LOGGER.info("Updating payments timeout"));
    pipeline.execute(
        "orders",
        "2.0",
        "production",
        actor,
        ChangeType.ENDPOINT,
        "Route payments to /v2/payments",
        () -> LOGGER.info("Updating payments endpoint"));
    try {
      pipeline.execute(
          "payments",
          "1.1",
          "production",
          actor,
          ChangeType.PROTOCOL,
          "Switch inter-service protocol to gRPC",
          () -> {
            throw new IllegalStateException("Protocol health check failed");
          });
    } catch (IllegalStateException exception) {
      LOGGER.warn("Simulated change failed: {}", exception.getMessage());
    }

    LOGGER.info("Deployment and change timeline:\n{}", monitor.timeline());
    monitor
        .alerts(event -> !event.successful() && "production".equals(event.environment()))
        .forEach(event -> LOGGER.warn("ALERT: {} {} failed", event.service(), event.type()));
  }
}
