package com.iluwatar.producer.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Producer Consumer Design pattern is a classic concurrency or threading pattern which reduces coupling between
 * Producer and Consumer by separating Identification of work with Execution of Work.
 * <p>
 * In producer consumer design pattern a shared queue is used to control the flow and this separation allows you to code
 * producer and consumer separately. It also addresses the issue of different timing require to produce item or
 * consuming item. by using producer consumer pattern both Producer and Consumer Thread can work with different speed.
 * 
 */
public class App {

  /**
   * Program entry point
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
      System.out.println("Error waiting for ExecutorService shutdown");
    }
  }
}
