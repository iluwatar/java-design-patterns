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

import com.iluwatar.mapreduce.App;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

  @Test
  void testMap_singleSentence() {

    List<String> sentences = Arrays.asList("hello world");
    List<Map.Entry<String, Integer>> result = App.map(sentences);

    // Assert
    assertEquals(2, result.size(), "Should have 2 entries");
    assertEquals("hello", result.get(0).getKey());
    assertEquals(1, result.get(0).getValue());
    assertEquals("world", result.get(1).getKey());
    assertEquals(1, result.get(1).getValue());
  }

  @Test
  void testMap_multipleSentences() {

    List<String> sentences = Arrays.asList("hello world", "hello java");
    List<Map.Entry<String, Integer>> result = App.map(sentences);

    // Assert
    assertEquals(4, result.size(), "Should have 4 entries (2 words per sentence)");
    assertEquals("hello", result.get(0).getKey());
    assertEquals(1, result.get(0).getValue());
    assertEquals("world", result.get(1).getKey());
    assertEquals(1, result.get(1).getValue());
    assertEquals("hello", result.get(2).getKey());
    assertEquals(1, result.get(2).getValue());
    assertEquals("java", result.get(3).getKey());
    assertEquals(1, result.get(3).getValue());
  }

  @Test
  void testReduce_singleWordMultipleEntries() {

    List<Map.Entry<String, Integer>> mappedWords = Arrays.asList(
        new AbstractMap.SimpleEntry<>("hello", 1),
        new AbstractMap.SimpleEntry<>("hello", 1)
    );
    Map<String, Integer> result = App.reduce(mappedWords);

    // Assert
    assertEquals(1, result.size(), "Should only contain one unique word");
    assertEquals(2, result.get("hello"), "The count of 'hello' should be 2");
  }

  @Test
  void testReduce_multipleWords() {
    List<Map.Entry<String, Integer>> mappedWords = Arrays.asList(
        new AbstractMap.SimpleEntry<>("hello", 1),
        new AbstractMap.SimpleEntry<>("world", 1),
        new AbstractMap.SimpleEntry<>("hello", 1),
        new AbstractMap.SimpleEntry<>("java", 1)
    );
    Map<String, Integer> result = App.reduce(mappedWords);

    // Assert
    assertEquals(3, result.size(), "Should contain 3 unique words");
    assertEquals(2, result.get("hello"), "The count of 'hello' should be 2");
    assertEquals(1, result.get("world"), "The count of 'world' should be 1");
    assertEquals(1, result.get("java"), "The count of 'java' should be 1");
  }

}

