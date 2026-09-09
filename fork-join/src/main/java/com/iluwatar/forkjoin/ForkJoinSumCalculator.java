package com.iluwatar.forkjoin;

import java.util.concurrent.ForkJoinPool;

/**
 * ForkJoinSumCalculator provides a convenient API to sum an array of numbers using the Fork/Join
 * framework. It creates a {@link ForkJoinPool}, submits a {@link SumTask}, and returns the computed
 * sum.
 *
 * <p>The pool manages a set of worker threads that process subtasks in parallel. Idle threads can
 * "steal" work from busy threads, maximizing CPU utilization.
 */
public class ForkJoinSumCalculator {

  private final ForkJoinPool pool;

  /** Creates a calculator using the common ForkJoinPool (uses all available CPU cores). */
  public ForkJoinSumCalculator() {
    this.pool = ForkJoinPool.commonPool();
  }

  /**
   * Creates a calculator with a specific number of threads.
   *
   * @param parallelism the number of worker threads to use
   */
  public ForkJoinSumCalculator(int parallelism) {
    this.pool = new ForkJoinPool(parallelism);
  }

  /**
   * Calculates the sum of all elements in the array using Fork/Join parallelism.
   *
   * @param numbers the array of numbers to sum
   * @return the total sum of all elements
   */
  public long calculateSum(long[] numbers) {
    if (numbers == null || numbers.length == 0) {
      return 0;
    }
    SumTask task = new SumTask(numbers, 0, numbers.length);
    return pool.invoke(task);
  }
}
