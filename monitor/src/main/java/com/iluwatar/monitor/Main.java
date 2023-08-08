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
package com.iluwatar.monitor;

import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
/**
 * The Monitor pattern is used in concurrent algorithms to achieve mutual exclusion.
 *
 * <p>Bank is a simple class that transfers money from an account to another account using {@link
 * Bank#transfer}. It can also return the balance of the bank account stored in the bank.
 *
 * <p>Main class uses ThreadPool to run threads that do transactions on the bank accounts.
 */
@Slf4j
public class Main {

  private static final int NUMBER_OF_THREADS = 5;
  private static final int BASE_AMOUNT = 1000;
  private static final int ACCOUNT_NUM = 4;

  /**
   * Runner to perform a bunch of transfers and handle exception.
   *
   * @param bank  bank object
   * @param latch signal finished execution
   */
  public static void runner(Bank bank, CountDownLatch latch) {
    try {
      SecureRandom random = new SecureRandom();
      Thread.sleep(random.nextInt(1000));
      LOGGER.info("Start transferring...");
      for (int i = 0; i < 1000000; i++) {
        bank.transfer(random.nextInt(4), random.nextInt(4), random.nextInt(0, BASE_AMOUNT));
      }
      LOGGER.info("Finished transferring.");
      latch.countDown();
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) throws InterruptedException {
    var bank = new Bank(ACCOUNT_NUM, BASE_AMOUNT);
    var latch = new CountDownLatch(NUMBER_OF_THREADS);
    var executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    for (int i = 0; i < NUMBER_OF_THREADS; i++) {
      executorService.execute(() -> runner(bank, latch));
    }

    latch.await();
  }
}
