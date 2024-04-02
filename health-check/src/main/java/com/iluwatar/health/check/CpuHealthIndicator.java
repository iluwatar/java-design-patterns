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

import jakarta.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * A health indicator that checks the health of the system's CPU.
 *
 * @author ydoksanbir
 */
@Getter
@Setter
@Slf4j
@Component
public class CpuHealthIndicator implements HealthIndicator {

  /** The operating system MXBean used to gather CPU health information. */
  private OperatingSystemMXBean osBean;

  /** Initializes the {@link OperatingSystemMXBean} instance. */
  @PostConstruct
  public void init() {
    this.osBean = ManagementFactory.getOperatingSystemMXBean();
  }

  /**
   * The system CPU load threshold. If the system CPU load is above this threshold, the health
   * indicator will return a `down` health status.
   */
  @Value("${cpu.system.load.threshold:80.0}")
  private double systemCpuLoadThreshold;

  /**
   * The process CPU load threshold. If the process CPU load is above this threshold, the health
   * indicator will return a `down` health status.
   */
  @Value("${cpu.process.load.threshold:50.0}")
  private double processCpuLoadThreshold;

  /**
   * The load average threshold. If the load average is above this threshold, the health indicator
   * will return an `up` health status with a warning message.
   */
  @Value("${cpu.load.average.threshold:0.75}")
  private double loadAverageThreshold;

  /**
   * The warning message to include in the health indicator's response when the load average is high
   * but not exceeding the threshold.
   */
  @Value("${cpu.warning.message:High load average}")
  private String defaultWarningMessage;

  private static final String ERROR_MESSAGE = "error";

  private static final String HIGH_SYSTEM_CPU_LOAD_MESSAGE = "High system CPU load: {}";
  private static final String HIGH_PROCESS_CPU_LOAD_MESSAGE = "High process CPU load: {}";
  private static final String HIGH_LOAD_AVERAGE_MESSAGE = "High load average: {}";
  private static final String HIGH_PROCESS_CPU_LOAD_MESSAGE_WITHOUT_PARAM = "High process CPU load";
  private static final String HIGH_SYSTEM_CPU_LOAD_MESSAGE_WITHOUT_PARAM = "High system CPU load";
  private static final String HIGH_LOAD_AVERAGE_MESSAGE_WITHOUT_PARAM = "High load average";

  /**
   * Checks the health of the system's CPU and returns a health indicator object.
   *
   * @return a health indicator object
   */
  @Override
  public Health health() {

    if (!(osBean instanceof com.sun.management.OperatingSystemMXBean sunOsBean)) {
      LOGGER.error("Unsupported operating system MXBean: {}", osBean.getClass().getName());
      return Health.unknown()
          .withDetail(ERROR_MESSAGE, "Unsupported operating system MXBean")
          .build();
    }

    double systemCpuLoad = sunOsBean.getCpuLoad() * 100;
    double processCpuLoad = sunOsBean.getProcessCpuLoad() * 100;
    int availableProcessors = sunOsBean.getAvailableProcessors();
    double loadAverage = sunOsBean.getSystemLoadAverage();

    Map<String, Object> details = new HashMap<>();
    details.put("timestamp", Instant.now());
    details.put("systemCpuLoad", String.format("%.2f%%", systemCpuLoad));
    details.put("processCpuLoad", String.format("%.2f%%", processCpuLoad));
    details.put("availableProcessors", availableProcessors);
    details.put("loadAverage", loadAverage);

    if (systemCpuLoad > systemCpuLoadThreshold) {
      LOGGER.error(HIGH_SYSTEM_CPU_LOAD_MESSAGE, systemCpuLoad);
      return Health.down()
          .withDetails(details)
          .withDetail(ERROR_MESSAGE, HIGH_SYSTEM_CPU_LOAD_MESSAGE_WITHOUT_PARAM)
          .build();
    } else if (processCpuLoad > processCpuLoadThreshold) {
      LOGGER.error(HIGH_PROCESS_CPU_LOAD_MESSAGE, processCpuLoad);
      return Health.down()
          .withDetails(details)
          .withDetail(ERROR_MESSAGE, HIGH_PROCESS_CPU_LOAD_MESSAGE_WITHOUT_PARAM)
          .build();
    } else if (loadAverage > (availableProcessors * loadAverageThreshold)) {
      LOGGER.error(HIGH_LOAD_AVERAGE_MESSAGE, loadAverage);
      return Health.up()
          .withDetails(details)
          .withDetail(ERROR_MESSAGE, HIGH_LOAD_AVERAGE_MESSAGE_WITHOUT_PARAM)
          .build();
    } else {
      return Health.up().withDetails(details).build();
    }
  }
}
