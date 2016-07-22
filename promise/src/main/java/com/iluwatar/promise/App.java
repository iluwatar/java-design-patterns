package com.iluwatar.promise;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * Application that uses promise pattern.
 */
public class App {

  /**
   * Program entry point
   * @param args arguments
   * @throws InterruptedException if main thread is interruped.
   * @throws ExecutionException if an execution error occurs.
   */
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    try {
      promiseUsage(executor);
    } finally {
      executor.shutdownNow();
    }
  }

  private static void promiseUsage(Executor executor)
      throws InterruptedException, ExecutionException {
    Promise<Integer> consumedPromise = new Promise<>();
    consumedPromise.fulfillInAsync(() -> {
      Thread.sleep(1000);
      return 10;
    }, executor).then(value -> {
      System.out.println("Consumed int value: " + value);
    });
    
    Promise<String> transformedPromise = new Promise<>();
    transformedPromise.fulfillInAsync(() -> {
      Thread.sleep(1000);
      return "10";
    }, executor).then(value -> { return Integer.parseInt(value); }).then(value -> {
      System.out.println("Consumed transformed int value: " + value);
    });
    
    consumedPromise.get();
    transformedPromise.get();
  }
}
