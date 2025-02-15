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

import java.util.HashMap;
import java.util.Map;


/**
 * The Mapper class is responsible for processing an input string
 * and generating a map of word occurrences.
 */
public class Mapper {
  private Mapper() {
    throw new UnsupportedOperationException("Mapper is a utility class and cannot be instantiated.");
  }
  /**
   * Splits a given input string into words and counts their occurrences.
   *
   * @param input The input string to be mapped.
   * @return A map where keys are words and values are their respective counts.
   */
  public static Map<String, Integer> map(String input) {
    Map<String, Integer> wordCount = new HashMap<>();
    String[] words = input.split("\\s+");
    for (String word : words) {
      word = word.toLowerCase().replaceAll("[^a-z]", "");
      if (!word.isEmpty()) {
        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
      }
    }
    return wordCount;
  }
}
