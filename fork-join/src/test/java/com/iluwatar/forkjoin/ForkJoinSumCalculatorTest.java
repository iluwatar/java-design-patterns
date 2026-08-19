package com.iluwatar.forkjoin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;

class ForkJoinSumCalculatorTest {

  @Test
  void shouldReturnZeroForNullArray() {
    ForkJoinSumCalculator calculator = new ForkJoinSumCalculator();

    assertEquals(0L, calculator.calculateSum(null));
  }

  @Test
  void shouldReturnZeroForEmptyArray() {
    ForkJoinSumCalculator calculator = new ForkJoinSumCalculator();

    assertEquals(0L, calculator.calculateSum(new long[0]));
  }

  @Test
  void shouldCalculateSumOfSmallArray() {
    ForkJoinSumCalculator calculator = new ForkJoinSumCalculator();
    long[] numbers = {10, 20, 30, 40, 50};

    long result = calculator.calculateSum(numbers);

    assertEquals(150L, result);
  }

  @Test
  void shouldCalculateSumOfLargeArray() {
    ForkJoinSumCalculator calculator = new ForkJoinSumCalculator();
    long[] numbers = LongStream.rangeClosed(1, 100_000).toArray();

    long result = calculator.calculateSum(numbers);

    long expected = 100_000L * 100_001L / 2;
    assertEquals(expected, result);
  }

  @Test
  void shouldWorkWithCustomParallelism() {
    // Use only 2 threads
    ForkJoinSumCalculator calculator = new ForkJoinSumCalculator(2);
    long[] numbers = LongStream.rangeClosed(1, 50_000).toArray();

    long result = calculator.calculateSum(numbers);

    long expected = 50_000L * 50_001L / 2;
    assertEquals(expected, result);
  }
}
