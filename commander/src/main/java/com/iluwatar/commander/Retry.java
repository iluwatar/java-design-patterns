package com.iluwatar.commander;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Retry class that applies the retry pattern with customizable backoff and error handling.
 *
 * @param <T> The type of object passed into HandleErrorIssue as a parameter.
 */
public class Retry<T> {

  /**
   * Operation interface for performing the core operation.
   */
  public interface Operation {
    void operation(List<Exception> list) throws Exception;
  }

  /**
   * HandleErrorIssue defines how to handle errors during retries.
   *
   * @param <T> The type of object passed into the method as a parameter.
   */
  public interface HandleErrorIssue<T> {
    void handleIssue(T obj, Exception e);
  }

  /**
   * BackoffStrategy defines the strategy for calculating retry delay.
   */
  public interface BackoffStrategy {
    long calculateDelay(int attempt);
  }

  private final Operation operation;
  private final HandleErrorIssue<T> errorHandler;
  private final int maxAttempts;
  private final BackoffStrategy backoffStrategy;
  private final Predicate<Exception> ignoreCondition;
  private final AtomicInteger attempts;
  private final List<Exception> errorList;

  /**
   * Constructor for Retry class.
   *
   * @param operation       The operation to retry.
   * @param errorHandler    The handler for errors.
   * @param maxAttempts     The maximum number of retry attempts.
   * @param backoffStrategy The backoff strategy for retry delays.
   * @param ignoreCondition A predicate to determine whether to ignore certain exceptions.
   */
  public Retry(Operation operation, HandleErrorIssue<T> errorHandler, int maxAttempts,
               BackoffStrategy backoffStrategy, Predicate<Exception> ignoreCondition) {
    this.operation = operation;
    this.errorHandler = errorHandler;
    this.maxAttempts = maxAttempts;
    this.backoffStrategy = backoffStrategy;
    this.ignoreCondition = ignoreCondition;
    this.attempts = new AtomicInteger(0);
    this.errorList = new ArrayList<>();
  }

  /**
   * Perform the operation with retries.
   *
   * @param exceptions The list of exceptions encountered during retries.
   * @param obj        The object passed to the error handler.
   */
  public void perform(List<Exception> exceptions, T obj) {
    do {
      try {
        operation.operation(exceptions);
        return; // Exit if successful
      } catch (Exception e) {
        errorList.add(e);

        if (attempts.incrementAndGet() >= maxAttempts || !ignoreCondition.test(e)) {
          errorHandler.handleIssue(obj, e);
          return; // Stop retrying if max attempts are exceeded or exception is non-recoverable
        }

        try {
          long delay = backoffStrategy.calculateDelay(attempts.intValue());
          Thread.sleep(delay);
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt(); // Restore interrupted status
          errorHandler.handleIssue(obj, new RuntimeException("Thread interrupted during retry", ie));
          return;
        }
      }
    } while (true);
  }

  /**
   * Returns an unmodifiable list of errors encountered during retries.
   *
   * @return A list of encountered errors.
   */
  public List<Exception> getErrorList() {
    return Collections.unmodifiableList(errorList);
  }

  /**
   * Default ExponentialBackoffStrategy with jitter.
   */
  public static class ExponentialBackoffWithJitter implements BackoffStrategy {
    private final long maxDelay;

    public ExponentialBackoffWithJitter(long maxDelay) {
      this.maxDelay = maxDelay;
    }

    @Override
    public long calculateDelay(int attempt) {
      long baseDelay = (long) Math.pow(2, attempt) * 1000;
      return Math.min(baseDelay + ThreadLocalRandom.current().nextInt(1000), maxDelay);
    }
  }
}
