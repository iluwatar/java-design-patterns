/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.producer.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Producer Consumer Design pattern is a classic concurrency or threading pattern which reduces
 * coupling between Producer and Consumer by separating Identification of work with
 * Execution of Work.
 *
 * <p>In producer consumer design pattern a shared queue is used to control the flow and this
 * separation allows you to code producer and consumer separately. It also addresses the issue
 * of different timing require to produce item or consuming item. by using producer consumer
 * pattern both Producer and Consumer Thread can work with different speed.
 * 
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   * 
   * @param args
   *          command line args
   */
  public static void main(String[] args) {

    ItemQueue queue = new ItemQueue();

    ExecutorService executorService = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 2; i++) {

      final Producer producer = new Producer("Producer_" + i, queue);
      executorService.submit(() -> {
        while (true) {
          producer.produce();
        }
      });
    }

    for (int i = 0; i < 3; i++) {
      final Consumer consumer = new Consumer("Consumer_" + i, queue);
      executorService.submit(() -> {
        while (true) {
          consumer.consume();
        }
      });
    }

    executorService.shutdown();
    try {
      executorService.awaitTermination(10, TimeUnit.SECONDS);
      executorService.shutdownNow();
    } catch (InterruptedException e) {
      LOGGER.error("Error waiting for ExecutorService shutdown");
    }
  }
}
