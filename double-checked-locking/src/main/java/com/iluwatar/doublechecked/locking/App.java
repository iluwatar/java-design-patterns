/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.doublechecked.locking;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * Double Checked Locking is a concurrency design pattern used to reduce the overhead of acquiring a
 * lock by first testing the locking criterion (the "lock hint") without actually acquiring the
 * lock. Only if the locking criterion check indicates that locking is required does the actual
 * locking logic proceed.
 *
 * <p>In {@link Inventory} we store the items with a given size. However, we do not store more
 * items than the inventory size. To address concurrent access problems we use double checked
 * locking to add item to inventory. In this method, the thread which gets the lock first adds the
 * item.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    final var inventory = new Inventory(1000);
    var executorService = Executors.newFixedThreadPool(3);
    IntStream.range(0, 3).<Runnable>mapToObj(i -> () -> {
      while (inventory.addItem(new Item())) {
        LOGGER.info("Adding another item");
      }
    }).forEach(executorService::execute);

    executorService.shutdown();
    try {
      executorService.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.error("Error waiting for ExecutorService shutdown");
      Thread.currentThread().interrupt();
    }
  }
}
