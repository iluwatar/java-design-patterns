/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK
 * framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License Copyright © 2014-2025 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.bulkhead;

/**
 * Service for handling background processing tasks with dedicated resources.
 *
 * <p>This service handles non-critical, asynchronous operations that can tolerate higher latency.
 * By isolating its resources from user-facing services, failures or slowdowns in background
 * processing don't impact critical user operations.
 *
 * <p>Example use cases:
 *
 * <ul>
 *   <li>Email sending
 *   <li>Report generation
 *   <li>Data synchronization
 *   <li>Scheduled batch jobs
 * </ul>
 */
public class BackgroundService extends BulkheadService {

  private static final int DEFAULT_QUEUE_CAPACITY = 20;

  /**
   * Creates a BackgroundService with specified thread pool size.
   *
   * @param maxThreads maximum number of threads for background processing
   */
  public BackgroundService(int maxThreads) {
    super("BackgroundService", maxThreads, DEFAULT_QUEUE_CAPACITY);
  }

  @Override
  protected void handleRejectedTask(Task task) {
    super.handleRejectedTask(task);
  }
}
