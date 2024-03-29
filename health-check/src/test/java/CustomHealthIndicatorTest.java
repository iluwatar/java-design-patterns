import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.iluwatar.health.check.AsynchronousHealthChecker;
import com.iluwatar.health.check.CustomHealthIndicator;
import com.iluwatar.health.check.HealthCheckRepository;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tests class< for {@link CustomHealthIndicator}. *
 *
 * @author ydoksanbir
 */
class CustomHealthIndicatorTest {

  /** Mocked AsynchronousHealthChecker instance. */
  @Mock private AsynchronousHealthChecker healthChecker;

  /** Mocked CacheManager instance. */
  @Mock private CacheManager cacheManager;

  /** Mocked HealthCheckRepository instance. */
  @Mock private HealthCheckRepository healthCheckRepository;

  /** Mocked Cache instance. */
  @Mock private Cache cache;

  /** `CustomHealthIndicator` instance to be tested. */
  private CustomHealthIndicator customHealthIndicator;

  /** Sets up the test environment. */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(cacheManager.getCache("health-check")).thenReturn(cache);
    customHealthIndicator =
        new CustomHealthIndicator(healthChecker, cacheManager, healthCheckRepository);
  }

  /**
   * Test case for the `health()` method when the database is up.
   *
   * <p>Asserts that when the `health()` method is called and the database is up, it returns a
   * Health object with Status.UP.
   */
  @Test
  void whenDatabaseIsUp_thenHealthIsUp() {
    CompletableFuture<Health> future =
        CompletableFuture.completedFuture(Health.up().withDetail("database", "reachable").build());
    when(healthChecker.performCheck(any(), anyLong())).thenReturn(future);
    when(healthCheckRepository.checkHealth()).thenReturn(1);

    Health health = customHealthIndicator.health();

    assertEquals(Status.UP, health.getStatus());
  }

  /**
   * Test case for the `health()` method when the database is down.
   *
   * <p>Asserts that when the `health()` method is called and the database is down, it returns a
   * Health object with Status.DOWN.
   */
  @Test
  void whenDatabaseIsDown_thenHealthIsDown() {
    CompletableFuture<Health> future =
        CompletableFuture.completedFuture(
            Health.down().withDetail("database", "unreachable").build());
    when(healthChecker.performCheck(any(), anyLong())).thenReturn(future);
    when(healthCheckRepository.checkHealth()).thenReturn(null);

    Health health = customHealthIndicator.health();

    assertEquals(Status.DOWN, health.getStatus());
  }

  /**
   * Test case for the `health()` method when the health check times out.
   *
   * <p>Asserts that when the `health()` method is called and the health check times out, it returns
   * a Health object with Status.DOWN.
   */
  @Test
  void whenHealthCheckTimesOut_thenHealthIsDown() {
    CompletableFuture<Health> future = new CompletableFuture<>();
    when(healthChecker.performCheck(any(), anyLong())).thenReturn(future);

    Health health = customHealthIndicator.health();

    assertEquals(Status.DOWN, health.getStatus());
  }

  /**
   * Test case for the `evictHealthCache()` method.
   *
   * <p>Asserts that when the `evictHealthCache()` method is called, the health cache is cleared.
   */
  @Test
  void whenEvictHealthCache_thenCacheIsCleared() {
    doNothing().when(cache).clear();

    customHealthIndicator.evictHealthCache();

    verify(cache, times(1)).clear();
    verify(cacheManager, times(1)).getCache("health-check");
  }

  /** Configuration static class for the health check cache. */
  @Configuration
  static class CacheConfig {
    /**
     * Creates a concurrent map cache manager named "health-check".
     *
     * @return a new ConcurrentMapCacheManager instance
     */
    @Bean
    public CacheManager cacheManager() {
      return new ConcurrentMapCacheManager("health-check");
    }
  }
}
