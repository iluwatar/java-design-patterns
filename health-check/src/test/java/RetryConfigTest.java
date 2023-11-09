import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.health.check.RetryConfig;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;

/**
 * Unit tests for the {@link RetryConfig} class.
 *
 * @author ydoksanbir
 */
@SpringBootTest(classes = RetryConfig.class)
class RetryConfigTest {

  /** Injected RetryTemplate instance. */
  @Autowired private RetryTemplate retryTemplate;

  /**
   * Tests that the retry template retries three times with a two-second delay.
   *
   * <p>Verifies that the retryable operation is executed three times before throwing an exception,
   * and that the total elapsed time for the retries is at least four seconds.
   */
  @Test
  void shouldRetryThreeTimesWithTwoSecondDelay() {
    AtomicInteger attempts = new AtomicInteger();
    Runnable retryableOperation =
        () -> {
          attempts.incrementAndGet();
          throw new RuntimeException("Test exception for retry");
        };

    long startTime = System.currentTimeMillis();
    try {
      retryTemplate.execute(
          context -> {
            retryableOperation.run();
            return null;
          });
    } catch (Exception e) {
      // Expected exception
    }
    long endTime = System.currentTimeMillis();

    assertEquals(3, attempts.get(), "Should have retried three times");
    assertTrue(
        (endTime - startTime) >= 4000,
        "Should have waited at least 4 seconds in total for backoff");
  }
}
