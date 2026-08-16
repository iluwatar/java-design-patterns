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
package com.iluwatar.fallback;

/**
 * A concrete implementation of the remote service representing the primary service. It can be
 * configured to simulate latency and errors to test resilience features.
 */
public class PrimaryService implements RemoteService {
  private final long latencyMs;
  private final String response;
  private final boolean shouldThrowException;

  /**
   * Constructor for PrimaryService.
   *
   * @param response the successful response to return
   * @param latencyMs simulated latency in milliseconds
   * @param shouldThrowException if true, the service will throw an exception
   */
  public PrimaryService(String response, long latencyMs, boolean shouldThrowException) {
    this.latencyMs = latencyMs;
    this.response = response;
    this.shouldThrowException = shouldThrowException;
  }

  @Override
  public String execute() throws Exception {
    if (shouldThrowException) {
      throw new RuntimeException("Primary service failed!");
    }
    if (latencyMs > 0) {
      Thread.sleep(latencyMs);
    }
    return response;
  }
}
