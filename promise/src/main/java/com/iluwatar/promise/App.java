package com.iluwatar.promise;

public class App {

  /**
   * Program entry point
   * @param args arguments
   */
  public static void main(String[] args) {
    ThreadAsyncExecutor executor = new ThreadAsyncExecutor();
    executor.execute(() -> {
      Thread.sleep(1000);
      return 10;
    }).then(value -> {System.out.println("Consumed the value: " + value);})
    .then(nullVal -> {System.out.println("Post consuming value");});
    
    
    executor.execute(() -> {
      Thread.sleep(1000);
      return "10";
     }).then(value -> {return 10 + Integer.parseInt(value);})
    .then(intValue -> {System.out.println("Consumed int value: " + intValue);});
  }
}
