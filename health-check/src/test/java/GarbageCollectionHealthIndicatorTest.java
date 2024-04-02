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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.iluwatar.health.check.GarbageCollectionHealthIndicator;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

/**
 * Test class for {@link GarbageCollectionHealthIndicator}.
 *
 * @author ydoksanbir
 */
class GarbageCollectionHealthIndicatorTest {

  /** Mocked garbage collector MXBean. */
  @Mock private GarbageCollectorMXBean garbageCollectorMXBean;

  /** Mocked memory pool MXBean. */
  @Mock private MemoryPoolMXBean memoryPoolMXBean;

  /** Garbage collection health indicator instance to be tested. */
  private GarbageCollectionHealthIndicator healthIndicator;

  /** Set up the test environment before each test case. */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    healthIndicator =
        spy(
            new GarbageCollectionHealthIndicator() {
              @Override
              protected List<GarbageCollectorMXBean> getGarbageCollectorMxBeans() {
                return Collections.singletonList(garbageCollectorMXBean);
              }

              @Override
              protected List<MemoryPoolMXBean> getMemoryPoolMxBeans() {
                return Collections.singletonList(memoryPoolMXBean);
              }
            });
    healthIndicator.setMemoryUsageThreshold(0.8);
  }

  /** Test case to verify that the health status is up when memory usage is low. */
  @Test
  void whenMemoryUsageIsLow_thenHealthIsUp() {
    when(garbageCollectorMXBean.getCollectionCount()).thenReturn(100L);
    when(garbageCollectorMXBean.getCollectionTime()).thenReturn(1000L);
    when(garbageCollectorMXBean.getMemoryPoolNames()).thenReturn(new String[] {"Eden Space"});

    when(memoryPoolMXBean.getUsage()).thenReturn(new MemoryUsage(0, 100, 500, 1000));
    when(memoryPoolMXBean.getName()).thenReturn("Eden Space");

    var health = healthIndicator.health();
    assertEquals(Status.UP, health.getStatus());
  }

  /** Test case to verify that the health status contains a warning when memory usage is high. */
  @Test
  void whenMemoryUsageIsHigh_thenHealthContainsWarning() {
    // Arrange
    double threshold = 0.8; // 80% threshold for test
    healthIndicator.setMemoryUsageThreshold(threshold);

    String poolName = "CodeCache";
    when(garbageCollectorMXBean.getName()).thenReturn("G1 Young Generation");
    when(garbageCollectorMXBean.getMemoryPoolNames()).thenReturn(new String[] {poolName});

    long maxMemory = 1000L; // e.g., 1000 bytes
    long usedMemory = (long) (threshold * maxMemory) + 1; // e.g., 801 bytes to exceed 80% threshold
    when(memoryPoolMXBean.getUsage())
        .thenReturn(new MemoryUsage(0, usedMemory, usedMemory, maxMemory));
    when(memoryPoolMXBean.getName()).thenReturn(poolName);

    // Act
    Health health = healthIndicator.health();

    // Assert
    Map<String, Object> gcDetails =
        (Map<String, Object>) health.getDetails().get("G1 Young Generation");
    assertNotNull(gcDetails, "Expected details for 'G1 Young Generation', but none were found.");

    String memoryPoolsDetail = (String) gcDetails.get("memoryPools");
    assertNotNull(
        memoryPoolsDetail, "Expected memory pool details for 'CodeCache', but none were found.");

    // Extracting the actual usage reported in the details for comparison
    String memoryUsageReported = memoryPoolsDetail.split(": ")[1].trim().replace("%", "");
    double memoryUsagePercentage = Double.parseDouble(memoryUsageReported);

    assertTrue(
        memoryUsagePercentage > threshold,
        "Memory usage percentage should be above the threshold.");

    String warning = (String) gcDetails.get("warning");
    assertNotNull(warning, "Expected a warning for high memory usage, but none was found.");

    // Check that the warning message is as expected
    String expectedWarningRegex =
        String.format("Memory pool '%s' usage is high \\(\\d+\\.\\d+%%\\)", poolName);
    assertTrue(
        warning.matches(expectedWarningRegex),
        "Expected a high usage warning, but format is incorrect: " + warning);
  }

  /** Test case to verify that the health status is up when there are no garbage collections. */
  @Test
  void whenNoGarbageCollections_thenHealthIsUp() {
    // Arrange: Mock the garbage collector to simulate no collections
    when(garbageCollectorMXBean.getCollectionCount()).thenReturn(0L);
    when(garbageCollectorMXBean.getCollectionTime()).thenReturn(0L);
    when(garbageCollectorMXBean.getName()).thenReturn("G1 Young Generation");
    when(garbageCollectorMXBean.getMemoryPoolNames()).thenReturn(new String[] {});

    // Act: Perform the health check
    Health health = healthIndicator.health();

    // Assert: Ensure the health is up and there are no warnings
    assertEquals(Status.UP, health.getStatus());
    Map<String, Object> gcDetails =
        (Map<String, Object>) health.getDetails().get("G1 Young Generation");
    assertNotNull(gcDetails, "Expected details for 'G1 Young Generation', but none were found.");
    assertNull(
        gcDetails.get("warning"),
        "Expected no warning for 'G1 Young Generation' as there are no collections.");
  }
}
