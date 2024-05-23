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
package com.iluwatar.health.check;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * A custom health indicator that checks the garbage collection status of the application and
 * reports the health status accordingly. It gathers information about the collection count,
 * collection time, memory pool name, and garbage collector algorithm for each garbage collector and
 * presents the details in a structured manner.
 *
 */
@Slf4j
@Component
@Getter
@Setter
public class GarbageCollectionHealthIndicator implements HealthIndicator {

  /**
   * The memory usage threshold above which a warning message is included in the health check
   * report.
   */
  @Value("${memory.usage.threshold:0.8}")
  private double memoryUsageThreshold;

  /**
   * Performs a health check by gathering garbage collection metrics and evaluating the overall
   * health of the garbage collection system.
   *
   * @return a {@link Health} object representing the health status of the garbage collection system
   */
  @Override
  public Health health() {
    List<GarbageCollectorMXBean> gcBeans = getGarbageCollectorMxBeans();
    List<MemoryPoolMXBean> memoryPoolMxBeans = getMemoryPoolMxBeans();
    Map<String, Map<String, String>> gcDetails = new HashMap<>();

    for (GarbageCollectorMXBean gcBean : gcBeans) {
      Map<String, String> collectorDetails = createCollectorDetails(gcBean, memoryPoolMxBeans);
      gcDetails.put(gcBean.getName(), collectorDetails);
    }

    return Health.up().withDetails(gcDetails).build();
  }

  /**
   * Creates details for the given garbage collector, including collection count, collection time,
   * and memory pool information.
   *
   * @param gcBean The garbage collector MXBean
   * @param memoryPoolMxBeans List of memory pool MXBeans
   * @return Map containing details for the garbage collector
   */
  private Map<String, String> createCollectorDetails(
      GarbageCollectorMXBean gcBean, List<MemoryPoolMXBean> memoryPoolMxBeans) {
    Map<String, String> collectorDetails = new HashMap<>();
    long count = gcBean.getCollectionCount();
    long time = gcBean.getCollectionTime();
    collectorDetails.put("count", String.format("%d", count));
    collectorDetails.put("time", String.format("%dms", time));

    String[] memoryPoolNames = gcBean.getMemoryPoolNames();
    List<String> memoryPoolNamesList = Arrays.asList(memoryPoolNames);
    if (!memoryPoolNamesList.isEmpty()) {
      addMemoryPoolDetails(collectorDetails, memoryPoolMxBeans, memoryPoolNamesList);
    } else {
      LOGGER.error("Garbage collector '{}' does not have any memory pools", gcBean.getName());
    }

    return collectorDetails;
  }

  /**
   * Adds memory pool details to the collector details.
   *
   * @param collectorDetails Map containing details for the garbage collector
   * @param memoryPoolMxBeans List of memory pool MXBeans
   * @param memoryPoolNamesList List of memory pool names associated with the garbage collector
   */
  private void addMemoryPoolDetails(
      Map<String, String> collectorDetails,
      List<MemoryPoolMXBean> memoryPoolMxBeans,
      List<String> memoryPoolNamesList) {
    for (MemoryPoolMXBean memoryPoolmxbean : memoryPoolMxBeans) {
      if (memoryPoolNamesList.contains(memoryPoolmxbean.getName())) {
        double memoryUsage =
            memoryPoolmxbean.getUsage().getUsed() / (double) memoryPoolmxbean.getUsage().getMax();
        if (memoryUsage > memoryUsageThreshold) {
          collectorDetails.put(
              "warning",
              String.format(
                  "Memory pool '%s' usage is high (%2f%%)",
                  memoryPoolmxbean.getName(), memoryUsage));
        }

        collectorDetails.put(
            "memoryPools", String.format("%s: %s%%", memoryPoolmxbean.getName(), memoryUsage));
      }
    }
  }

  /**
   * Retrieves the list of garbage collector MXBeans using ManagementFactory.
   *
   * @return a list of {@link GarbageCollectorMXBean} objects representing the garbage collectors
   */
  protected List<GarbageCollectorMXBean> getGarbageCollectorMxBeans() {
    return ManagementFactory.getGarbageCollectorMXBeans();
  }

  /**
   * Retrieves the list of memory pool MXBeans using ManagementFactory.
   *
   * @return a list of {@link MemoryPoolMXBean} objects representing the memory pools
   */
  protected List<MemoryPoolMXBean> getMemoryPoolMxBeans() {
    return ManagementFactory.getMemoryPoolMXBeans();
  }
}
