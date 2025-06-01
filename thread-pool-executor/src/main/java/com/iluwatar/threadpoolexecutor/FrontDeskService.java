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
package com.iluwatar.threadpoolexecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * FrontDeskService represents the hotel's front desk with a fixed number of employees. This class
 * demonstrates the Thread-Pool Executor pattern using Java's ExecutorService.
 */
@Slf4j
public class FrontDeskService {

  private final ExecutorService executorService;
  private final int numberOfEmployees;

  /**
   * Creates a new front desk with the specified number of employees.
   *
   * @param numberOfEmployees the number of employees (threads) at the front desk
   */
  public FrontDeskService(int numberOfEmployees) {
    this.numberOfEmployees = numberOfEmployees;
    this.executorService = Executors.newFixedThreadPool(numberOfEmployees);
    LOGGER.info("Front desk initialized with {} employees.", numberOfEmployees);
  }

  /**
   * Submits a regular guest check-in task to an available employee.
   *
   * @param task the check-in task to submit
   * @return a Future representing pending completion of the task
   */
  public Future<Void> submitGuestCheckIn(Runnable task) {
    LOGGER.debug("Submitting regular guest check-in task");
    return executorService.submit(task, null);
  }

  /**
   * Submits a VIP guest check-in task to an available employee.
   *
   * @param task the VIP check-in task to submit
   * @param <T> the type of the task's result
   * @return a Future representing pending completion of the task
   */
  public <T> Future<T> submitVipGuestCheckIn(Callable<T> task) {
    LOGGER.debug("Submitting VIP guest check-in task");
    return executorService.submit(task);
  }

  /**
   * Closes the front desk after all currently checked-in guests are processed. No new check-ins
   * will be accepted.
   */
  public void shutdown() {
    LOGGER.info("Front desk is closing - no new guests will be accepted.");
    executorService.shutdown();
  }

  /**
   * Waits for all check-in processes to complete or until timeout.
   *
   * @param timeout the maximum time to wait
   * @param unit the time unit of the timeout argument
   * @return true if all tasks completed, false if timeout elapsed
   * @throws InterruptedException if interrupted while waiting
   */
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    LOGGER.info("Waiting for all check-ins to complete (max wait: {} {})", timeout, unit);
    return executorService.awaitTermination(timeout, unit);
  }

  /**
   * Gets the number of employees at the front desk.
   *
   * @return the number of employees
   */
  public int getNumberOfEmployees() {
    return numberOfEmployees;
  }
}
