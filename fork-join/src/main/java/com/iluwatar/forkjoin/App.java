package com.iluwatar.forkjoin;

import java.util.stream.LongStream;

/**
 * The Fork/Join pattern is a concurrency design pattern that splits a large task into smaller
 * subtasks (fork), processes them in parallel, and then combines the results (join).
 *
 * <p>In Java, this pattern is implemented using {@link java.util.concurrent.ForkJoinPool} and
 * {@link java.util.concurrent.RecursiveTask}. Worker threads in the pool use a work-stealing
 * algorithm — idle threads take tasks from busy threads — maximizing CPU utilization.
 *
 * <p>In this example, we demonstrate the pattern by computing the sum of a large array in parallel.
 * The array is recursively split in half until each piece is small enough to sum directly, then
 * results are combined back up.
 */
public final class App {

  private App() {}

  /**
   * @param args command line arguments, not used
   */
  public static void main(String[] args) {
    // Create an array of 10 million numbers: [1, 2, 3, ..., 10_000_000]
    long[] numbers = LongStream.rangeClosed(1, 10_000_000).toArray();

    // Calculate sum using Fork/Join
    ForkJoinSumCalculator calculator = new ForkJoinSumCalculator();
    long startTime = System.currentTimeMillis();
    long result = calculator.calculateSum(numbers);
    long endTime = System.currentTimeMillis();

    // The expected sum of 1 to N is N*(N+1)/2
    long expected = 10_000_000L * 10_000_001L / 2;

    System.out.println("Fork/Join sum: " + result);
    System.out.println("Expected sum:  " + expected);
    System.out.println("Correct: " + (result == expected));
    System.out.println("Time taken: " + (endTime - startTime) + " ms");
    System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
  }
}
