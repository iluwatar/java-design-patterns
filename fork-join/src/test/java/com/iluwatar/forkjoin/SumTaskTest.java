package com.iluwatar.forkjoin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;

class SumTaskTest {

  @Test
  void shouldSumSmallArrayDirectly() {
    // Array smaller than threshold — should compute without forking
    long[] numbers = {1, 2, 3, 4, 5};
    SumTask task = new SumTask(numbers, 0, numbers.length);

    long result = ForkJoinPool.commonPool().invoke(task);

    assertEquals(15L, result);
  }

  @Test
  void shouldSumLargeArrayUsingForkJoin() {
    // Array larger than threshold — will fork into subtasks
    long[] numbers = LongStream.rangeClosed(1, 10_000).toArray();
    SumTask task = new SumTask(numbers, 0, numbers.length);

    long result = ForkJoinPool.commonPool().invoke(task);

    // Sum of 1 to N = N*(N+1)/2
    long expected = 10_000L * 10_001L / 2;
    assertEquals(expected, result);
  }

  @Test
  void shouldSumPartialRange() {
    // Sum only a portion of the array (indices 2 to 5)
    long[] numbers = {10, 20, 30, 40, 50, 60};
    SumTask task = new SumTask(numbers, 2, 5);

    long result = ForkJoinPool.commonPool().invoke(task);

    // 30 + 40 + 50 = 120
    assertEquals(120L, result);
  }

  @Test
  void shouldReturnZeroForEmptyRange() {
    long[] numbers = {1, 2, 3};
    SumTask task = new SumTask(numbers, 1, 1); // start == end, empty range

    long result = ForkJoinPool.commonPool().invoke(task);

    assertEquals(0L, result);
  }

  @Test
  void shouldHandleSingleElement() {
    long[] numbers = {42};
    SumTask task = new SumTask(numbers, 0, 1);

    long result = ForkJoinPool.commonPool().invoke(task);

    assertEquals(42L, result);
  }

  @Test
  void shouldProduceCorrectResultForMillionElements() {
    long[] numbers = LongStream.rangeClosed(1, 1_000_000).toArray();
    SumTask task = new SumTask(numbers, 0, numbers.length);

    long result = ForkJoinPool.commonPool().invoke(task);

    long expected = 1_000_000L * 1_000_001L / 2;
    assertEquals(expected, result);
  }

  @Test
  void shouldThrowExceptionWhenStartGreaterThanEnd() {
    long[] numbers = {1, 2, 3, 4, 5};

    assertThrows(IllegalArgumentException.class, () -> new SumTask(numbers, 4, 2));
  }
}
