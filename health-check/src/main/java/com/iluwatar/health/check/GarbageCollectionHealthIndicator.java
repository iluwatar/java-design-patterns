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
 * @author ydoksanbir
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
      Map<String, String> collectorDetails = new HashMap<>();
      long count = gcBean.getCollectionCount();
      long time = gcBean.getCollectionTime();
      collectorDetails.put("count", String.format("%d", count));
      collectorDetails.put("time", String.format("%dms", time));

      String[] memoryPoolNames = gcBean.getMemoryPoolNames();
      List<String> memoryPoolNamesList = Arrays.asList(memoryPoolNames);
      if (!memoryPoolNamesList.isEmpty()) {
        // Use ManagementFactory to get a list of all memory pools and iterate over it
        for (MemoryPoolMXBean memoryPoolmxbean : memoryPoolMxBeans) {
          if (memoryPoolMxBeans.contains(memoryPoolmxbean)) {
            double memoryUsage =
                memoryPoolmxbean.getUsage().getUsed()
                    / (double) memoryPoolmxbean.getUsage().getMax();
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
      } else {
        // If the garbage collector does not have any memory pools, log a warning
        LOGGER.error("Garbage collector '{}' does not have any memory pools", gcBean.getName());
      }

      gcDetails.put(gcBean.getName(), collectorDetails);
    }

    return Health.up().withDetails(gcDetails).build();
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
