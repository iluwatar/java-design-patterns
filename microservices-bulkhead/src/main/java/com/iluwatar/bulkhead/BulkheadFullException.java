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
package com.iluwatar.bulkhead;

/**
 * Thrown by a {@link Bulkhead} when a call cannot be accepted because every worker thread is busy
 * and the waiting queue is full. The caller receives this exception immediately instead of
 * blocking, which is the fail-fast behaviour the pattern relies on: the caller can degrade
 * gracefully while the overloaded dependency keeps consuming only the capacity reserved for it.
 */
public class BulkheadFullException extends RuntimeException {

  private final String bulkheadName;

  public BulkheadFullException(String bulkheadName) {
    super("Bulkhead '" + bulkheadName + "' is full, call rejected");
    this.bulkheadName = bulkheadName;
  }

  /** Name of the bulkhead that rejected the call. */
  public String getBulkheadName() {
    return bulkheadName;
  }
}
