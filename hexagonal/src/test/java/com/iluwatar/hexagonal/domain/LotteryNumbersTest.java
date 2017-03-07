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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

/**
 * 
 * Unit tests for {@link LotteryNumbers}
 *
 */
public class LotteryNumbersTest {
  
  @Test
  public void testGivenNumbers() {
    LotteryNumbers numbers = LotteryNumbers.create(
            new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    assertEquals(numbers.getNumbers().size(), 4);
    assertTrue(numbers.getNumbers().contains(1));
    assertTrue(numbers.getNumbers().contains(2));
    assertTrue(numbers.getNumbers().contains(3));
    assertTrue(numbers.getNumbers().contains(4));
  }
  
  @Test(expected = UnsupportedOperationException.class)
  public void testNumbersCantBeModified() {
    LotteryNumbers numbers = LotteryNumbers.create(
            new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    numbers.getNumbers().add(5);
  }
  
  @Test
  public void testRandomNumbers() {
    LotteryNumbers numbers = LotteryNumbers.createRandom();
    assertEquals(numbers.getNumbers().size(), LotteryNumbers.NUM_NUMBERS);
  }
  
  @Test
  public void testEquals() {
    LotteryNumbers numbers1 = LotteryNumbers.create(
            new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    LotteryNumbers numbers2 = LotteryNumbers.create(
            new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    assertTrue(numbers1.equals(numbers2));
    LotteryNumbers numbers3 = LotteryNumbers.create(
            new HashSet<>(Arrays.asList(11, 12, 13, 14)));
    assertFalse(numbers1.equals(numbers3));
  }
}
