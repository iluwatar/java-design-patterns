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
package com.iluwatar.clientsideuicomposition;

import java.util.Map;
import java.util.Random;

/**
 * FrontendComponent is an abstract class representing an independent frontend component that
 * fetches data dynamically based on the provided parameters.
 */
public abstract class FrontendComponent {

  public static final Random random = new Random();

  /**
   * Simulates asynchronous data fetching by introducing a random delay and then fetching the data
   * based on dynamic input.
   *
   * @param params a map of parameters that may affect the data fetching logic
   * @return the data fetched by the frontend component
   */
  public String fetchData(Map<String, String> params) {
    try {
      // Simulate delay in fetching data (e.g., network latency)
      Thread.sleep(random.nextInt(1000));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    // Fetch and return the data based on the given parameters
    return getData(params);
  }

  /**
   * Abstract method to be implemented by subclasses to return data based on parameters.
   *
   * @param params a map of parameters that may affect the data fetching logic
   * @return the data for this specific component
   */
  protected abstract String getData(Map<String, String> params);
}
