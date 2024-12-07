package com.iluwatar.retry;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Decorates {@link BusinessOperation} with "retry with exponential backoff" capabilities.
 *
 * @param <T> the remote operation's return type
 */
public final class RetryExponentialBackoff<T> implements BusinessOperation<T> {

  private final BusinessOperation<T> operation;
  private final int maxAttempts;
  private final long maxDelay;
  private final Predicate<Exception> ignoreCondition;
  private final RetryDelayCalculator delayCalculator;
  private final AtomicInteger attempts;
  private final List<Exception> encounteredErrors;

  /**
   * Constructor.
   *
   * @param operation       the business operation to retry
   * @param maxAttempts     the maximum number of retry attempts
   * @param maxDelay        the maximum delay between retries
   * @param delayCalculator a delay calculator for customizable backoff logic
   * @param ignoreCondition a condition to test whether exceptions should be retried
   */
  public RetryExponentialBackoff(
      BusinessOperation<T> operation,
      int maxAttempts,
      long maxDelay,
      RetryDelayCalculator delayCalculator,
      Predicate<Exception> ignoreCondition
  ) {
    this.operation = operation;
    this.maxAttempts = maxAttempts;
    this.maxDelay = maxDelay;
    this.delayCalculator = delayCalculator;
    this.ignoreCondition = ignoreCondition;
    this.attempts = new AtomicInteger(0);
    this.encounteredErrors = new ArrayList<>();
  }

  /**
   * Returns an unmodifiable list of encountered errors during retries.
   *
   * @return the list of errors
   */
  public List<Exception> getEncounteredErrors() {
    return Collections.unmodifiableList(encounteredErrors);
  }

  /**
   * Returns the number of attempts made.
   *
   * @return the number of retry attempts
   */
  public int getAttempts() {
    return attempts.intValue();
  }

  @Override
  public T perform() throws BusinessException {
    do {
      try {
        return operation.perform();
      } catch (BusinessException e) {
        encounteredErrors.add(e);

        if (attempts.incrementAndGet() >= maxAttempts || !ignoreCondition.test(e)) {
          throw e; // Terminate retries if max attempts reached or ignore condition is not met
        }

        try {
          long delay = Math.min(delayCalculator.calculate(attempts.intValue()), maxDelay);
          Thread.sleep(delay);
        } catch (InterruptedException interruptedException) {
          Thread.currentThread().interrupt(); // Restore interrupt status
          throw new BusinessException("Retry operation interrupted", interruptedException);
        }
      }
    } while (true);
  }

  /**
   * Interface for calculating retry delay.
   */
  public interface RetryDelayCalculator {
    long calculate(int attempt);
  }

  /**
   * Default implementation of exponential backoff with jitter.
   */
  public static class ExponentialBackoffWithJitter implements RetryDelayCalculator {
    @Override
    public long calculate(int attempt) {
      long baseDelay = (long) Math.pow(2, attempt) * 1000;
      return baseDelay + ThreadLocalRandom.current().nextInt(1000);
    }
  }
}
