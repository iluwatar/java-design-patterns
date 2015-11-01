package com.iluwatar.leaderfollower;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Leader Follower is a concurrency pattern where multiple threads can efficiently demultiplex
 * events and dispatch to event handlers.
 * <p>
 * In this example we use ThreadPool which basically acts as the ThreadPool. One of the Workers
 * becomes Leader and listens on the {@link HandleSet} for work. HandleSet basically acts as the
 * source of input events for the Workers, who are spawned and controlled by the {@link WorkStation}
 * . When Work arrives which implements the {@link Handle} interface then the leader takes the work
 * and calls the {@link ConcreteEventHandler}. However it also selects one of the waiting Workers as
 * leader, who can then process the next work and so on.
 *
 */
public class App {

  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(4);
    WorkStation station = new WorkStation(exec);
    station.startWork();
    exec.awaitTermination(5, TimeUnit.SECONDS);
  }
}
