package com.iluwatar.backpressure;

import java.time.Duration;
import reactor.core.publisher.Flux;

/** This class is the publisher that generates the data stream. */
public class Publisher {

  /**
   * On message method will trigger when the subscribed event is published.
   *
   * @param start starting integer
   * @param count how many integers to emit
   * @param delay delay between each item in milliseconds
   * @return a flux stream of integers
   */
  public static Flux<Integer> publish(int start, int count, int delay) {
    return Flux.range(start, count).delayElements(Duration.ofMillis(delay)).log();
  }
}
