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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The Main class serves as the entry point for executing the MapReduce program.
 * It processes a list of text inputs, applies the MapReduce pattern, and prints the results.
 */
public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());
  /**
   * The main method initiates the MapReduce process and displays the word count results.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    List<String> inputs = Arrays.asList(
        "Hello world hello",
        "MapReduce is fun",
        "Hello from the other side",
        "Hello world"
    );
    List<Map.Entry<String, Integer>> result = MapReduce.mapReduce(inputs);
    for (Map.Entry<String, Integer> entry : result) {
      logger.info(entry.getKey() + ": " + entry.getValue());
    }
  }
}