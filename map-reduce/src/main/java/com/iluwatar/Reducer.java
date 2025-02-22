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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Reducer class is responsible for aggregating word counts from the shuffled data.
 */
public class Reducer {
  private Reducer() {
    throw new UnsupportedOperationException("Reducer is a utility class and cannot be instantiated.");
  }
  /**
   * Sums the occurrences of each word and sorts the results in descending order.
   *
   * @param grouped A map where keys are words and values are lists of their occurrences.
   * @return A sorted list of word counts in descending order.
   */
  public static List<Map.Entry<String, Integer>> reduce(Map<String, List<Integer>> grouped) {
    Map<String, Integer> reduced = new HashMap<>();
    for (Map.Entry<String, List<Integer>> entry : grouped.entrySet()) {
      reduced.put(entry.getKey(), entry.getValue().stream().mapToInt(Integer::intValue).sum());
    }

    List<Map.Entry<String, Integer>> result = new ArrayList<>(reduced.entrySet());
    result.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
    return result;
  }
}
