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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Shuffler class is responsible for grouping word occurrences from multiple mappers.
 */
public class Shuffler {

  private Shuffler() {
    throw new UnsupportedOperationException("Shuffler is a utility class and cannot be instantiated.");
  }
  /**
   * Merges multiple word count maps into a single grouped map.
   *
   * @param mapped List of maps containing word counts from the mapping phase.
   * @return A map where keys are words and values are lists of their occurrences across inputs.
   */
  public static Map<String, List<Integer>> shuffleAndSort(List<Map<String, Integer>> mapped) {
    Map<String, List<Integer>> grouped = new HashMap<>();
    for (Map<String, Integer> map : mapped) {
      for (Map.Entry<String, Integer> entry : map.entrySet()) {
        grouped.putIfAbsent(entry.getKey(), new ArrayList<>());
        grouped.get(entry.getKey()).add(entry.getValue());
      }
    }
    return grouped;
  }
}
