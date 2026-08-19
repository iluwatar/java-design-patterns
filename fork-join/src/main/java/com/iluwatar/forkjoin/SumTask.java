package com.iluwatar.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * SumTask demonstrates the Fork/Join pattern by recursively splitting an array summation problem
 * into smaller subtasks until each subtask is small enough to compute directly.
 *
 * <p>How it works: If the portion of the array is smaller than THRESHOLD, sum it in a simple loop.
 * Otherwise, split the array in half, fork one half to run in parallel, compute the other half in
 * the current thread, and then join the results. This approach utilizes multiple CPU cores to
 * perform the summation significantly faster than a single-threaded loop for large arrays.
 */
public class SumTask extends RecursiveTask<Long> {

  /**
   * If the number of elements to process is at or below this threshold, the task computes the sum
   * directly instead of splitting further.
   */
  private static final int THRESHOLD = 1000;

  private final long[] numbers;
  private final int start;
  private final int end;

  /**
   * Creates a task to sum elements of the given array from index {@code start} (inclusive) to index
   * {@code end} (exclusive).
   *
   * @param numbers the array of numbers to sum
   * @param start the starting index (inclusive)
   * @param end the ending index (exclusive)
   */
  public SumTask(long[] numbers, int start, int end) {
    if (start > end) {
      throw new IllegalArgumentException(
          "start (" + start + ") must not be greater than end (" + end + ")");
    }
    this.numbers = numbers;
    this.start = start;
    this.end = end;
  }

  /**
   * The main computation method. This is where the fork/join magic happens.
   *
   * @return the sum of elements from start to end
   */
  @Override
  protected Long compute() {
    int length = end - start;

    // BASE CASE: if the chunk is small enough, just sum directly
    if (length <= THRESHOLD) {
      return computeDirectly();
    }

    // FORK: split the task into two halves
    int mid = start + length / 2;

    // Create subtask for the left half
    SumTask leftTask = new SumTask(numbers, start, mid);

    // Create subtask for the right half
    SumTask rightTask = new SumTask(numbers, mid, end);

    // Fork the left task — it will run in a separate thread
    leftTask.fork();

    // Compute the right task in the current thread (no need to fork both)
    long rightResult = rightTask.compute();

    // JOIN: wait for the left task to finish and get its result
    long leftResult = leftTask.join();

    // Combine the results from both halves
    return leftResult + rightResult;
  }

  /**
   * Computes the sum directly using a simple loop. This is used when the chunk size is at or below
   * the threshold — no further splitting needed.
   *
   * @return the sum of elements in the range [start, end)
   */
  private long computeDirectly() {
    long sum = 0;
    for (int i = start; i < end; i++) {
      sum += numbers[i];
    }
    return sum;
  }
}
