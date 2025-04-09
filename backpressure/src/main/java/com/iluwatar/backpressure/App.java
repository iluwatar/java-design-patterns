package com.iluwatar.backpressure;

import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;

/**
 * The Backpressure pattern is a flow control mechanism. It allows a consumer to signal to a
 * producer to slow down or stop sending data when it's overwhelmed.
 * <li>Prevents memory overflow, CPU thrashing, and resource exhaustion.
 * <li>Ensures fair usage of resources in distributed systems.
 * <li>Avoids buffer bloat and latency spikes.
 * Key concepts of this design paradigm involves
 * <li>Publisher/Producer: Generates data.
 * <li>Subscriber/Consumer: Receives and processes data.
 *
 * <p>In this example we will create a {@link Publisher} and a {@link Subscriber}. Publisher will
 * emit a stream of integer values with a predefined delay. Subscriber takes 500 ms to process one
 * integer. Since the subscriber can't process the items fast enough we apply backpressure to the
 * publisher so that it will request 10 items first, process 5 items and request for the next 5
 * again. After processing 5 items subscriber will keep requesting for another 5 until the stream
 * ends.
 */
@Slf4j
public class App {

  protected static CountDownLatch latch;

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) throws InterruptedException {

    /*
     * This custom subscriber applies backpressure:
     * - Has a processing delay of 0.5 milliseconds
     * - Requests 10 items initially
     * - Process 5 items and request for the next 5 items
     */
    Subscriber sub = new Subscriber();
    // slow publisher emit 15 numbers with a delay of 200 milliseconds
    Publisher.publish(1, 17, 200).subscribe(sub);

    latch = new CountDownLatch(1);
    latch.await();

    sub = new Subscriber();
    // fast publisher emit 15 numbers with a delay of 1 millisecond
    Publisher.publish(1, 17, 1).subscribe(sub);

    latch = new CountDownLatch(1);
    latch.await();
  }
}