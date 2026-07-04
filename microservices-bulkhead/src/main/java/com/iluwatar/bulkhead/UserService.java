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
 * Service for handling user-facing requests with dedicated resources.
 *
 * <p>This service represents critical user interactions that require high availability and fast
 * response times. By isolating its resources using the Bulkhead pattern, it remains responsive even
 * when other services (like background processing) are experiencing issues or heavy load.
 *
 * <p>Example use cases:
 *
 * <ul>
 *   <li>HTTP API requests from users
 *   <li>Real-time user interactions
 *   <li>Critical business operations
 * </ul>
 */
public class UserService extends BulkheadService {

  private static final int DEFAULT_QUEUE_CAPACITY = 10;

  /**
   * Creates a UserService with specified thread pool size.
   *
   * @param maxThreads maximum number of threads for handling user requests
   */
  public UserService(int maxThreads) {
    super("UserService", maxThreads, DEFAULT_QUEUE_CAPACITY);
  }

  @Override
  protected void handleRejectedTask(Task task) {
    super.handleRejectedTask(task);
  }
}
