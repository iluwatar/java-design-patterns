package com.iluwatar.leaderfollower;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Tolerant Reader is an integration pattern that helps creating robust communication systems. The
 * idea is to be as tolerant as possible when reading data from another service. This way, when the
 * communication schema changes, the readers must not break.
 * <p>
 * In this example we use Java serialization to write representations of {@link RainbowFish} objects
 * to file. {@link RainbowFish} is the initial version which we can easily read and write using
 * {@link RainbowFishSerializer} methods. {@link RainbowFish} then evolves to {@link RainbowFishV2}
 * and we again write it to file with a method designed to do just that. However, the reader client
 * does not know about the new format and still reads with the method designed for V1 schema.
 * Fortunately the reading method has been designed with the Tolerant Reader pattern and does not
 * break even though {@link RainbowFishV2} has new fields that are serialized.
 *
 */
public class App {

  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(4);
    WorkStation station = new WorkStation(exec);
    station.startWork();
    exec.awaitTermination(10, TimeUnit.SECONDS);
  }
}
