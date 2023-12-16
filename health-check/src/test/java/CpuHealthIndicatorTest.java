import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.iluwatar.health.check.CpuHealthIndicator;
import com.sun.management.OperatingSystemMXBean;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

/**
 * Test class for the {@link CpuHealthIndicator} class.
 *
 * @author ydoksanbir
 */
class CpuHealthIndicatorTest {

  /** The CPU health indicator to be tested. */
  private CpuHealthIndicator cpuHealthIndicator;

  /** The mocked operating system MXBean used to simulate CPU health information. */
  private OperatingSystemMXBean mockOsBean;

  /**
   * Sets up the test environment before each test method.
   *
   * <p>Mocks the {@link OperatingSystemMXBean} and sets it in the {@link CpuHealthIndicator}
   * instance.
   */
  @BeforeEach
  void setUp() {
    // Mock the com.sun.management.OperatingSystemMXBean
    mockOsBean = Mockito.mock(com.sun.management.OperatingSystemMXBean.class);
    cpuHealthIndicator = new CpuHealthIndicator();
    setOperatingSystemMXBean(cpuHealthIndicator, mockOsBean);
  }

  /**
   * Reflection method to set the private osBean in CpuHealthIndicator.
   *
   * @param indicator The CpuHealthIndicator instance to set the osBean for.
   * @param osBean The OperatingSystemMXBean to set.
   */
  private void setOperatingSystemMXBean(
      CpuHealthIndicator indicator, OperatingSystemMXBean osBean) {
    try {
      Field osBeanField = CpuHealthIndicator.class.getDeclaredField("osBean");
      osBeanField.setAccessible(true);
      osBeanField.set(indicator, osBean);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests that the health status is DOWN when the system CPU load is high.
   *
   * <p>Sets the system CPU load to 90% and mocks the other getters to return appropriate values.
   * Executes the health check and asserts that the health status is DOWN and the error message
   * indicates high system CPU load.
   */
  @Test
  void whenSystemCpuLoadIsHigh_thenHealthIsDown() {
    // Set thresholds for testing within the test method to avoid issues with Spring's @Value
    cpuHealthIndicator.setSystemCpuLoadThreshold(80.0);
    cpuHealthIndicator.setProcessCpuLoadThreshold(50.0);
    cpuHealthIndicator.setLoadAverageThreshold(0.75);

    // Mock the getters to return your desired values
    when(mockOsBean.getCpuLoad()).thenReturn(0.9); // Simulate 90% system CPU load
    when(mockOsBean.getAvailableProcessors()).thenReturn(8);
    when(mockOsBean.getSystemLoadAverage()).thenReturn(9.0);

    // Execute the health check
    Health health = cpuHealthIndicator.health();

    // Assertions
    assertEquals(
        Status.DOWN,
        health.getStatus(),
        "Health status should be DOWN when system CPU load is high");
    assertEquals(
        "High system CPU load",
        health.getDetails().get("error"),
        "Error message should indicate high system CPU load");
  }

  /**
   * Tests that the health status is DOWN when the process CPU load is high.
   *
   * <p>Sets the process CPU load to 80% and mocks the other getters to return appropriate values.
   * Executes the health check and asserts that the health status is DOWN and the error message
   * indicates high process CPU load.
   */
  @Test
  void whenProcessCpuLoadIsHigh_thenHealthIsDown() {
    // Set thresholds for testing within the test method to avoid issues with Spring's @Value
    cpuHealthIndicator.setSystemCpuLoadThreshold(80.0);
    cpuHealthIndicator.setProcessCpuLoadThreshold(50.0);
    cpuHealthIndicator.setLoadAverageThreshold(0.75);

    // Mock the getters to return your desired values
    when(mockOsBean.getCpuLoad()).thenReturn(0.5); // Simulate 50% system CPU load
    when(mockOsBean.getProcessCpuLoad()).thenReturn(0.8); // Simulate 80% process CPU load
    when(mockOsBean.getAvailableProcessors()).thenReturn(8);
    when(mockOsBean.getSystemLoadAverage()).thenReturn(5.0);

    // Execute the health check
    Health health = cpuHealthIndicator.health();

    // Assertions
    assertEquals(
        Status.DOWN,
        health.getStatus(),
        "Health status should be DOWN when process CPU load is high");
    assertEquals(
        "High process CPU load",
        health.getDetails().get("error"),
        "Error message should indicate high process CPU load");
  }
}
