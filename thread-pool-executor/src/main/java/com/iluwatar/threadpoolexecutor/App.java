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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * The Thread-Pool Executor pattern demonstrates how a pool of worker threads can be used to execute
 * tasks concurrently. This pattern is particularly useful in scenarios where you need to execute a
 * large number of independent tasks and want to limit the number of threads used.
 *
 * <p>In this example, a hotel front desk with a fixed number of employees processes guest
 * check-ins. Each employee is represented by a thread, and each check-in is a task.
 *
 * <p>Key benefits demonstrated:
 *
 * <ul>
 *   <li>Resource management - Limiting the number of concurrent threads
 *   <li>Efficiency - Reusing threads instead of creating new ones for each task
 *   <li>Responsiveness - Handling many requests with limited resources
 * </ul>
 */
@Slf4j
public class App {

  /**
   * Program main entry point.
   *
   * @param args program runtime arguments
   */
  public static void main(String[] args) throws InterruptedException, ExecutionException {

    FrontDeskService frontDesk = new FrontDeskService(5);
    LOGGER.info("Hotel front desk operation started!");

    LOGGER.info("Processing 30 regular guest check-ins...");
    for (int i = 1; i <= 30; i++) {
      frontDesk.submitGuestCheckIn(new GuestCheckInTask("Guest-" + i));
      Thread.sleep(100);
    }

    LOGGER.info("Processing 3 VIP guest check-ins...");
    List<Future<String>> vipResults = new ArrayList<>();

    for (int i = 1; i <= 3; i++) {
      Future<String> result =
          frontDesk.submitVipGuestCheckIn(new VipGuestCheckInTask("VIP-Guest-" + i));
      vipResults.add(result);
    }

    frontDesk.shutdown();

    if (frontDesk.awaitTermination(1, TimeUnit.HOURS)) {
      LOGGER.info("VIP Check-in Results:");
      for (Future<String> result : vipResults) {
        LOGGER.info(result.get());
      }
      LOGGER.info("All guests have been successfully checked in. Front desk is now closed.");
    } else {
      LOGGER.warn("Check-in timeout. Forcefully shutting down the front desk.");
    }
  }
}
