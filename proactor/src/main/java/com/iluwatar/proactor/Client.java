package com.iluwatar.proactor;

import java.io.NotActiveException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client side.
 */
public class Client {

  /**
   *  Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

  /**
   * A function to be run.
   */
  public void run(AsynchronousOperationProcessor op) throws InterruptedException {
    final var c1 = new ConcreteCompletionHandler("short");
    ExecutorService executor = Executors.newFixedThreadPool(3);
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      try {
        return op.register("task1", c1);
      } catch (NotActiveException | InterruptedException e) {
        LOGGER.info("context", e);
        Thread.currentThread().interrupt();
      }
      return null;
    }, executor);

    future.thenAccept(LOGGER::info);
    Thread.sleep(750);
    final var c2 = new ConcreteCompletionHandler("long");

    CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
      try {
        return op.register("task2", c2);
      } catch (NotActiveException | InterruptedException e) {
        LOGGER.info("context", e);
        Thread.currentThread().interrupt();
      }
      return null;
    }, executor);
    future1.thenAccept(LOGGER::info);
    executor.shutdown();
  }
}
