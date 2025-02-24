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
package com.iluwatar;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ReducerTest {

  @Test
  void testReduceWithMultipleWords() {
    Map<String, List<Integer>> input = new HashMap<>();
    input.put("apple", Arrays.asList(2, 3, 1));
    input.put("banana", Arrays.asList(1, 1));
    input.put("cherry", List.of(4));

    List<Map.Entry<String, Integer>> result = Reducer.reduce(input);

    assertEquals(3, result.size());
    assertEquals("apple", result.get(0).getKey());
    assertEquals(6, result.get(0).getValue());
    assertEquals("cherry", result.get(1).getKey());
    assertEquals(4, result.get(1).getValue());
    assertEquals("banana", result.get(2).getKey());
    assertEquals(2, result.get(2).getValue());
  }

  @Test
  void testReduceWithEmptyInput() {
    Map<String, List<Integer>> input = new HashMap<>();

    List<Map.Entry<String, Integer>> result = Reducer.reduce(input);

    assertTrue(result.isEmpty());
  }

  @Test
  void testReduceWithTiedCounts() {
    Map<String, List<Integer>> input = new HashMap<>();
    input.put("tie1", Arrays.asList(2, 2));
    input.put("tie2", Arrays.asList(1, 3));

    List<Map.Entry<String, Integer>> result = Reducer.reduce(input);

    assertEquals(2, result.size());
    assertEquals(4, result.get(0).getValue());
    assertEquals(4, result.get(1).getValue());
    // Note: The order of tie1 and tie2 is not guaranteed in case of a tie
  }
}
