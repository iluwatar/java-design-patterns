/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.Set;

/**
 *
 * Value object representing lottery numbers. This lottery uses sets of 4 numbers. The numbers must be unique and
 * between 1 and 20.
 *
 */
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
   * @return random LotteryNumbers
   */
  public static LotteryNumbers createRandom() {
    return new LotteryNumbers();
  }

  /**
   * @return given LotteryNumbers
   */
  public static LotteryNumbers create(Set<Integer> givenNumbers) {
    return new LotteryNumbers(givenNumbers);
  }
  
  /**
   * @return lottery numbers
   */
  public Set<Integer> getNumbers() {
    return Collections.unmodifiableSet(numbers);
  }

  /**
   * @return numbers as comma separated string
   */
  public String getNumbersAsString() {
    List<Integer> list = new ArrayList<>();
    list.addAll(numbers);
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < NUM_NUMBERS; i++) {
      builder.append(list.get(i));
      if (i < NUM_NUMBERS - 1) {
        builder.append(",");
      }
    }
    return builder.toString();
  }
  
  /**
   * Generates 4 unique random numbers between 1-20 into numbers set.
   */
  private void generateRandomNumbers() {
    numbers.clear();
    RandomNumberGenerator generator = new RandomNumberGenerator(MIN_NUMBER, MAX_NUMBER);
    while (numbers.size() < NUM_NUMBERS) {
      int num = generator.nextInt();
      if (!numbers.contains(num)) {
        numbers.add(num);
      }
    }
  }

  @Override
  public String toString() {
    return "LotteryNumbers{" + "numbers=" + numbers + '}';
  }

  /**
   * 
   * Helper class for generating random numbers.
   *
   */
  private static class RandomNumberGenerator {

    private PrimitiveIterator.OfInt randomIterator;

    /**
     * Initialize a new random number generator that generates random numbers in the range [min, max]
     * 
     * @param min the min value (inclusive)
     * @param max the max value (inclusive)
     */
    public RandomNumberGenerator(int min, int max) {
      randomIterator = new Random().ints(min, max + 1).iterator();
    }

    /**
     * @return a random number in the range (min, max)
     */
    public int nextInt() {
      return randomIterator.nextInt();
    }
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((numbers == null) ? 0 : numbers.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    LotteryNumbers other = (LotteryNumbers) obj;
    if (numbers == null) {
      if (other.numbers != null) {
        return false;
      }
    } else if (!numbers.equals(other.numbers)) {
      return false;
    }
    return true;
  }  
}
