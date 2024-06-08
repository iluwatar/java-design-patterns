/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.hexagonal.domain;

import com.google.common.base.Joiner;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashSet;
import java.util.PrimitiveIterator;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Value object representing lottery numbers. This lottery uses sets of 4 numbers. The numbers must
 * be unique and between 1 and 20.
 */
@EqualsAndHashCode
@ToString
public class LotteryNumbers {

  private final Set<Integer> numbers;

  public static final int MIN_NUMBER = 1;
  public static final int MAX_NUMBER = 20;
  public static final int NUM_NUMBERS = 4;

  /**
   * Constructor. Creates random lottery numbers.
   */
  private LotteryNumbers() {
    numbers = new HashSet<>();
    generateRandomNumbers();
  }

  /**
   * Constructor. Uses given numbers.
   */
  private LotteryNumbers(Set<Integer> givenNumbers) {
    numbers = new HashSet<>();
    numbers.addAll(givenNumbers);
  }

  /**
   * Creates a random lottery number.
   *
   * @return random LotteryNumbers
   */
  public static LotteryNumbers createRandom() {
    return new LotteryNumbers();
  }

  /**
   * Creates lottery number from given set of numbers.
   *
   * @return given LotteryNumbers
   */
  public static LotteryNumbers create(Set<Integer> givenNumbers) {
    return new LotteryNumbers(givenNumbers);
  }

  /**
   * Get numbers.
   *
   * @return lottery numbers
   */
  public Set<Integer> getNumbers() {
    return Collections.unmodifiableSet(numbers);
  }

  /**
   * Get numbers as string.
   *
   * @return numbers as comma separated string
   */
  public String getNumbersAsString() {
    return Joiner.on(',').join(numbers);
  }

  /**
   * Generates 4 unique random numbers between 1-20 into numbers set.
   */
  private void generateRandomNumbers() {
    numbers.clear();
    var generator = new RandomNumberGenerator(MIN_NUMBER, MAX_NUMBER);
    while (numbers.size() < NUM_NUMBERS) {
      var num = generator.nextInt();
      numbers.add(num);
    }
  }

  /**
   * Helper class for generating random numbers.
   */
  private static class RandomNumberGenerator {

    private final PrimitiveIterator.OfInt randomIterator;

    /**
     * Initialize a new random number generator that generates random numbers in the range [min,
     * max].
     *
     * @param min the min value (inclusive)
     * @param max the max value (inclusive)
     */
    public RandomNumberGenerator(int min, int max) {
      randomIterator = new SecureRandom().ints(min, max + 1).iterator();
    }

    /**
     * Gets next random integer in [min, max] range.
     *
     * @return a random number in the range (min, max)
     */
    public int nextInt() {
      return randomIterator.nextInt();
    }
  }

}
